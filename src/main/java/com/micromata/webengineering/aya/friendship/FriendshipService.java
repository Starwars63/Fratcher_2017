package com.micromata.webengineering.aya.friendship;

import com.micromata.webengineering.aya.user.User;
import com.micromata.webengineering.aya.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handle all CRUD operations for posts.
 */
@Service
public class FriendshipService {
	private static final Logger LOG = LoggerFactory.getLogger(FriendshipService.class);

	@Autowired
	public FriendshipRepository repository;

	@Autowired
	public UserService userService;

	/**
	 * Retrieve the list of all friendship of a user.
	 *
	 * @return friendship list
	 */
	public Iterable<Friendship> getFriendships() {
		LOG.info("Returning friendships. user={}", userService.getCurrentUser().getEmail());
		return repository.findBySource(userService.getCurrentUser());
	}

	/**
	 * Add a new friendship.
	 *
	 * @param dest
	 *            the user to add
	 */
	public void addFriendship(User dest) {
		LOG.info("Adding friendship.");
		Friendship request = new Friendship();
		request.setSource(userService.getCurrentUser());
		request.setDestination(dest);

		repository.save(request);
	}
	
	/**
	 * Update a new friendship.
	 *
	 * @param friendship
	 *            the friendship to save
	 */
	public void saveFriendship(Friendship friendship) {
		LOG.info("Updating friendship.");

		repository.save(friendship);
	}
	

	/**
	 * Retrieve a single friendship.
	 *
	 * @param id the friendship id
	 * @return friendship with the id or null if no friendship is found
	 */
	public Friendship getFriendship(Long id) {
		LOG.info("Retrieving Friendship. user={}, id={}", userService.getCurrentUser().getEmail(), id);
		return repository.findOne(id);
	}

	/**
	 * Remove a friendship.
	 *
	 * @param id
	 *            the Friendship id.
	 */
	public void deleteFriendship(Long id) {
		// Validate that user is allowed to delete friendship
		Friendship friendship = repository.findOne(id);
		if (!friendship.getSource().equals(userService.getCurrentUser()) && !friendship.getDestination().equals(userService.getCurrentUser())) {
			LOG.info("Deleting friendship not allowed. user={}, id={}", userService.getCurrentUser().getEmail(), id);
			throw new IllegalStateException("User not allowed to delete friendship");
		}
		LOG.info("Deleting friendship. user={}, id={}", userService.getCurrentUser().getEmail(), id);

		repository.delete(id);
	}

	
	
	/**
	 * Check a friendship.
	 *
	 * @param source the source user id.
	 * @param source the destination user id.
	 */
	public Friendship checkFriendship(User source, User dest ) {		// Validate that user is allowed to delete friendship
		
		LOG.info("Checking friendship. source={}, dest={}", source.getEmail(), dest.getEmail());

		return repository.findBySourceAndDestination(source, dest);
	}
	
	/**
	 * Approve a friendship.
	 *
	 * @param id
	 *            the Friendship id.
	 */
	public void acceptFriendship(Long id) {
		// Validate that user is allowed to delete friendship
		Friendship friendship = repository.findOne(id);
		if (!friendship.getDestination().equals(userService.getCurrentUser())) {
			LOG.info("Accepting friendship not allowed. user={}, id={}", userService.getCurrentUser().getEmail(), id);
			throw new IllegalStateException("User not allowed to accept friendship");
		}
		LOG.info("Accepting friendship. user={}, id={}", userService.getCurrentUser().getEmail(), id);

		repository.acceptFriendship(id, 2);
	}
	
    /**
     * Retrieve matches users 
     *
     * @param id user id
     * @return list of friendships
     */
	public Iterable<Friendship> findMatches(Long id) {
		LOG.info("Retrieving list of matches users.", id);
		return repository.findMatches(id);
	}
	
    /**
     * Retrieve requests users 
     *
     * @param id user id
     * @return list of friendships
     */
	public Iterable<Friendship> findRequests(Long id) {
		LOG.info("Retrieving list of requests users.", id);
		return repository.findRequests(id);
	}
}
