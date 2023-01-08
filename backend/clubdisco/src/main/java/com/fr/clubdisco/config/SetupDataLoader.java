package com.fr.clubdisco.config;

import com.fr.clubdisco.dto.SocialProvider;
import com.fr.clubdisco.model.Role;
import com.fr.clubdisco.model.User;
import com.fr.clubdisco.model.Widget;
import com.fr.clubdisco.model.WidgetsName;
import com.fr.clubdisco.repo.RoleRepository;
import com.fr.clubdisco.repo.UserRepository;
import com.fr.clubdisco.repo.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private WidgetRepository widgetRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		// Create initial roles
		Role userRole = createRoleIfNotFound(Role.ROLE_USER);
		Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
		createUserIfNotFound("admin@theo.fr", Set.of(userRole, adminRole));
		createWidgetIfNotFound();
		alreadySetup = true;
	}

	@Transactional
	private final User createUserIfNotFound(final String email, Set<Role> roles) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setDisplayName("Admin");
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode("admin@"));
			user.setRoles(roles);
			user.setProvider(SocialProvider.LOCAL.getProviderType());
			user.setEnabled(true);
			Date now = Calendar.getInstance().getTime();
			user.setCreatedDate(now);
			user.setModifiedDate(now);
			user = userRepository.save(user);
		}
		return user;
	}

	@Transactional
	private final void createWidgetIfNotFound(){
		Set<User> users = new HashSet<>();
		if (!widgetRepository.existsByName(WidgetsName.NASA.getWidgetName())){
			widgetRepository.save((new Widget(WidgetsName.NASA.getWidgetName(),users)));
		}
		if (!widgetRepository.existsByName(WidgetsName.SPOTIFY.getWidgetName())){
			widgetRepository.save((new Widget(WidgetsName.SPOTIFY.getWidgetName(),users)));
		}
		if (!widgetRepository.existsByName(WidgetsName.ZIPPOPOTAM.getWidgetName())){
			widgetRepository.save((new Widget(WidgetsName.ZIPPOPOTAM.getWidgetName(),users)));
		}
		if (!widgetRepository.existsByName(WidgetsName.WEATHER.getWidgetName())){
			widgetRepository.save((new Widget(WidgetsName.WEATHER.getWidgetName(),users)));
		}
	}

	@Transactional
	private final Role createRoleIfNotFound(final String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = roleRepository.save(new Role(name));
		}
		return role;
	}
}