package com.fr.clubdisco.controller;

import com.fr.clubdisco.config.CurrentUser;
import com.fr.clubdisco.dto.LocalUser;
import com.fr.clubdisco.dto.UserDTO;
import com.fr.clubdisco.dto.UserInfo;
import com.fr.clubdisco.dto.WidgetDTO;
import com.fr.clubdisco.exception.user.UserNotFoundException;
import com.fr.clubdisco.model.Role;
import com.fr.clubdisco.model.User;
import com.fr.clubdisco.model.Widget;
import com.fr.clubdisco.service.UserService;
import com.fr.clubdisco.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content goes here");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content goes here");
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('USER')")
	public UserInfo getUserById(@PathVariable(value = "userId") Long userId){
		User userTemp = userService.findUserById(userId).get();
		List<String> str = new ArrayList<>();
		for (Role r : userTemp.getRoles()){
			str.add(r.getName());
		}
		UserInfo user = new UserInfo(userTemp.getId().toString(),userTemp.getDisplayName(), userTemp.getEmail(), str);
		return user;
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUserById(@PathVariable(value = "userId") Long userId) throws UserNotFoundException {
		userService.deleteUserById(userId);
	}

	@PreAuthorize("hasRole('USER')")
	@PutMapping("/{userId}")
	public UserDTO updateUserById(@RequestBody UserDTO userDTO, @PathVariable Long userId) throws UserNotFoundException {
		userService.updateUserById(userDTO, userId);
		return userDTO;
	}

	@GetMapping("/user/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserInfo> getAllUsers(){
		List<UserInfo> users = new ArrayList<>();
		for (User u : userService.getAllUsers()){
			List<String> role = new ArrayList<>();
			for (Role r : u.getRoles()){
				role.add(r.getName());
			}
			users.add(new UserInfo(u.getId().toString(),u.getDisplayName(),u.getEmail(),role));
		}
		return users;
	}

	@PutMapping("/user/upgrade/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public void upgrade(@PathVariable Long userId) throws UserNotFoundException {
		userService.upgrade(userId);
	}

}