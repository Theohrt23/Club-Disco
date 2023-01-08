package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.dto.LocalUser;
import com.fr.clubdisco.dto.SignUpRequest;
import com.fr.clubdisco.dto.SocialProvider;
import com.fr.clubdisco.dto.UserDTO;
import com.fr.clubdisco.exception.OAuth2AuthenticationProcessingException;
import com.fr.clubdisco.exception.UserAlreadyExistAuthenticationException;
import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.exception.user.UserNotFoundException;
import com.fr.clubdisco.model.Role;
import com.fr.clubdisco.model.User;
import com.fr.clubdisco.model.Widget;
import com.fr.clubdisco.repo.RoleRepository;
import com.fr.clubdisco.repo.UserRepository;
import com.fr.clubdisco.security.oauth2.user.OAuth2UserInfo;
import com.fr.clubdisco.security.oauth2.user.OAuth2UserInfoFactory;
import com.fr.clubdisco.service.ConfigService;
import com.fr.clubdisco.service.UserService;
import com.fr.clubdisco.service.WidgetService;
import com.fr.clubdisco.util.GeneralUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WidgetService widgetService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ConfigService configService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static String generateSecurePassword() {

		CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
		LCR.setNumberOfCharacters(2);

		CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
		UCR.setNumberOfCharacters(2);

		CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
		DR.setNumberOfCharacters(2);

		CharacterRule SR = new CharacterRule(EnglishCharacterData.Special);
		SR.setNumberOfCharacters(2);

		PasswordGenerator passGen = new PasswordGenerator();

		String password = passGen.generatePassword(8, SR, LCR, UCR, DR);

		return password;
	}

	public void sendEmail(String mail, String name, String pwd) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mail);
		message.setTo(mail);
		message.setSubject("[ClubDisco] Votre mot de passe.");
		message.setText("Bonjour " + name + " !\nBienvenue sur ClubDisco ! \n\nVoici votre mot de passe: " + pwd + "\n\nN'oublier pas de le changer !");
		mailSender.send(message);
	}

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException, ConfigConflictException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		userRepository.flush();
		configService.createConfig(user.getId());

		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) throws ConfigConflictException {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		String generatedPwd =generateSecurePassword();
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo,generatedPwd);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
			sendEmail(userDetails.getEmail(),userDetails.getDisplayName(),generatedPwd);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo, String generatedPassword) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword(generatedPassword).build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteUserById(Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}

		Set<User> userSet = roleRepository.findById(1L).get().getUsers();
		for (User u : roleRepository.findById(1L).get().getUsers()) {
			if (u.getId().equals(userId)){
				userSet.remove(u);
			}
		}
		Role r = roleRepository.findById(1L).get();
		r.setUsers(userSet);
		roleRepository.save(r);


		if (widgetService.userHaveWidgetId(userId,1L)){
			widgetService.removeWidgetToUser(userId,1L);
		}
		if (widgetService.userHaveWidgetId(userId,2L)){
			widgetService.removeWidgetToUser(userId,2L);
		}
		if (widgetService.userHaveWidgetId(userId,3L)){
			widgetService.removeWidgetToUser(userId,3L);
		}
		if (widgetService.userHaveWidgetId(userId,4L)){
			widgetService.removeWidgetToUser(userId,4L);
		}






		userRepository.deleteById(userId);
	}

	@Override
	public User updateUserById(UserDTO userDTO, Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}
		userRepository.findById(userId).map(user ->{
					user.setDisplayName(userDTO.getDisplayName());
					user.setEmail(userDTO.getEmail());
					user.setModifiedDate(java.sql.Date.valueOf(LocalDate.now()));
					user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
					return userRepository.save(user);
				}
		);
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void upgrade(Long userId) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}

		Set<User> userSet = roleRepository.findById(2L).get().getUsers();
		userSet.add(userRepository.findById(userId).get());

		Role r = roleRepository.findById(2L).get();
		r.setUsers(userSet);
		roleRepository.save(r);

		User u = userRepository.findById(userId).get();
		u.getRoles().add(r);
		userRepository.save(u);
	}
}
