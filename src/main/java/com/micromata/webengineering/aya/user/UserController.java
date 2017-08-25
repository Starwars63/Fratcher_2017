package com.micromata.webengineering.aya.user;

import com.micromata.webengineering.aya.chat.ChatService;
import com.micromata.webengineering.aya.user.UserService;

import java.sql.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP endpoint for a post-related HTTP requests.
 */
@RestController
public class UserController {

	public static class UserRegister {
		public String name;
		public String email;
		public String password;
	}

	@Value("${authenticationService.jwt.secret}")
	private String JWTSecret;

	@Value("${authenticationService.salt}")
	private String salt;

	@Autowired
	private UserService userService;

	@Autowired
	private ChatService chatService;
	
	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public Iterable<User> getUserList() {
		if (userService.isAnonymous()) {
			return userService.getUsers();
		}

		return userService.findByAllExcept(userService.getCurrentUser().getId());
	}

	

	
	@RequestMapping(value = "/api/user/logout", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> logout() {
		if (!userService.isAnonymous()) {
			User user = userService.getUser(userService.getCurrentUser().getId());
			user.setOnline(false);
			userService.updateUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@RequestBody UserRegister user) {
		// A pragmatic approach to security which does not use much framework-specific
		// magic. While other approaches
		// with annotations, etc. are possible they are much more complex while this is
		// quite easy to understand and
		// extend.
		if (!userService.isAnonymous()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		// Option 2: validating the title length is driven by a technical
		// (non-functional) requirement.
		// We choose this option to show the usage of ResponseEntity.
		if (user.name == null || user.password == null || user.email == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User u = new User();
		u.setName(user.name);
		u.setPost("");
		u.setEmail(user.email);
		u.setRegisterDate(new Date(System.currentTimeMillis()));
		u.setPassword(hashPassword(user.password));

		userService.addUser(u);
		return ResponseEntity.ok(u.getId());
	}

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id) {
		User user = userService.getUser(id);
		
		//user.setChats(chatService.getChats(id));
		
		
		
		return user;
	}
	
	@RequestMapping(value = "/api/user/post", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deletePost() {
		
		if(userService.isAnonymous())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		User user = userService.getUser(userService.getCurrentUser().getId());
		user.setPost("");
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/user/post", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> savePost(@RequestBody User post) {
		
		if(userService.isAnonymous())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		User user = userService.getUser(userService.getCurrentUser().getId());
		user.setPost(post.getPost());
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	/**
	 * Return (salt + password) hashed with SHA-512.
	 *
	 * The salt is configured in the property authenticationService.salt.
	 *
	 * @param password
	 *            plain text password
	 * @return hashed password
	 */
	private String hashPassword(String password) {
		return DigestUtils.sha512Hex(salt + password);

	}
}
