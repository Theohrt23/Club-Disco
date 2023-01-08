package com.fr.clubdisco.service;

import com.fr.clubdisco.dto.LocalUser;
import com.fr.clubdisco.dto.SignUpRequest;
import com.fr.clubdisco.dto.UserDTO;
import com.fr.clubdisco.exception.UserAlreadyExistAuthenticationException;
import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.exception.user.UserNotFoundException;
import com.fr.clubdisco.model.User;
import com.fr.clubdisco.model.Widget;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException, ConfigConflictException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) throws ConfigConflictException;

	void deleteUserById(Long userId) throws UserNotFoundException;

	User updateUserById(UserDTO userDTO, Long userId) throws UserNotFoundException;

	List<User> getAllUsers();

	void upgrade(Long userId) throws UserNotFoundException;
}
