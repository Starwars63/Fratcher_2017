package com.micromata.webengineering.aya.friendship;

import com.micromata.webengineering.aya.user.User;
import com.micromata.webengineering.aya.user.UserService;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP endpoint for a post-related HTTP requests.
 */
@RestController
public class FriendshipController {

	@Autowired
	public FriendshipService friendshipService;

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/api/friends", method = RequestMethod.GET)
	public Iterable<Friendship> getUserFriendshipList() {
		return friendshipService.getFriendships();
	}

	@RequestMapping(value = "/api/friendship/matches", method = RequestMethod.GET)
	public Iterable<User> getMatchesUserList() {
		if (userService.isAnonymous()) {
			return null;
		}

		List<User> users = new ArrayList<>();
		Iterable<Friendship> fs = friendshipService.findMatches(userService.getCurrentUser().getId());
		for (Iterator<Friendship> iterator = fs.iterator(); iterator.hasNext();) {
			Friendship f = iterator.next();
			if(f.getSource().getId() == userService.getCurrentUser().getId()) {
			users.add(f.getDestination());
			}
			else {
				users.add(f.getSource());
			}
		}
		return users;
	}
	
	
	@RequestMapping(value = "/api/friendship/requests", method = RequestMethod.GET)
	public Iterable<User> getRequestsUserList() {
		if (userService.isAnonymous()) {
			return null;
		}

		List<User> users = new ArrayList<>();
		Iterable<Friendship> fs = friendshipService.findRequests(userService.getCurrentUser().getId());
		for (Iterator<Friendship> iterator = fs.iterator(); iterator.hasNext();) {
			Friendship f = iterator.next();
			
			users.add(f.getSource());
			
		}
		return users;
	}
	
	@RequestMapping(value = "/api/friendship/{destId}/like", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addFriendshipWithLike(@PathVariable Long destId) {
		if (userService.isAnonymous()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getUser(destId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Friendship friendship = friendshipService.checkFriendship(user, userService.getCurrentUser());
		if (friendship == null) {

			friendship = new Friendship();
			friendship.setDestination(user);
			friendship.setStatus(2);// Pending
			friendshipService.addFriendship(user);
		} else if (friendship.getSource().getId() == destId) {
			friendship.setStatus(3);// If the other user already likes my post so it is accepted by default
			friendshipService.saveFriendship(friendship);
		}

		// ELSE do nothing as friendship already accepted or rejected
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping(value = "/api/friendship/{destId}/dislike", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addFriendshipWithDisLike(@PathVariable Long destId) {
		if (userService.isAnonymous()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getUser(destId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Friendship friendship = new Friendship();
		friendship.setDestination(user);
		friendship.setStatus(1);
		friendshipService.addFriendship(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/api/friendship/{destId}/accept", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> acceptFriendship(@PathVariable Long destId) {
		if (userService.isAnonymous()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getUser(destId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Friendship friendship = friendshipService.checkFriendship(user, userService.getCurrentUser());
		if (friendship == null) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (friendship.getSource().getId() == destId) {
			friendship.setStatus(3);// If the other user already likes my post so it is accepted by default
			friendshipService.saveFriendship(friendship);
		}

		// ELSE do nothing as friendship already accepted or rejected
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/friendship/{destId}/ignore", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> ignoreFriendship(@PathVariable Long destId) {
		if (userService.isAnonymous()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getUser(destId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Friendship friendship = friendshipService.checkFriendship(user, userService.getCurrentUser());
		if (friendship == null) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (friendship.getSource().getId() == destId) {
			friendship.setStatus(1);// If the other user already likes my post so it is accepted by default
			friendshipService.saveFriendship(friendship);
		}

		// ELSE do nothing as friendship already accepted or rejected
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
