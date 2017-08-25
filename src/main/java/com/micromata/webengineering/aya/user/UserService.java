package com.micromata.webengineering.aya.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Service for all user-related functional operations.
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Sets the current user to anonymous.
     */
    public void setAnonymous() {
        setCurrentUser(-1L, "<anonymous>");
    }


    /**
     * Check if the current user is not authenticated.
     *
     * @return true if the user is not authenticated.
     */
    public boolean isAnonymous() {
        return getCurrentUser() == null || getCurrentUser().getId() == -1L;
    }


    /**
     * Retrieve the currently active user or null, if no user is logged in.
     *
     * @return the current user.
     */
    public User getCurrentUser() {
    	LOG.info("Getting current user {}", SecurityContextHolder.getContext().getAuthentication());
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Set a user for the current request.
     *
     * @param id    user id
     * @param email user email
     */
    public void setCurrentUser(Long id, String email) {
        LOG.debug("Setting user context. id={}, user={}", id, email);
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        UsernamePasswordAuthenticationToken secAuth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(secAuth);
    }


    /**
     * Retrieve a user with the given email and password.
     *
     * @param email    email
     * @param password password
     * @return the user or null if none could be found
     */
    public User getUser(String email, String password) {
        LOG.debug("Retrieving user from database. user={}", email);
        return userRepository.findByEmailAndPassword(email, password);
    }
    
    /**
     * Retrieve the list of all users.
     *
     * @return users list
     */
    public Iterable<User> getUsers() {
        LOG.info("Returning users={}");
        
        return userRepository.findAll();
    }
    
    
    /**
     * Add a new user.
     *
     * @param user the user to register
     */
    public void addUser(User user) {
        LOG.info("Adding user.");
        userRepository.save(user);
    }
    
    
    /**
     * Update user 
     *
     * @param user the user to update
     */
    public void updateUser(User user) {
        LOG.info("Updating user.");
        userRepository.save(user);
    }
    

    /**
     * Retrieve a single user.
     *
     * @param id user id
     * @return user with the id or null if no user is found
     */
    public User getUser(Long id) {
        LOG.info("Retrieving user.", id);
        return userRepository.findOne(id);
    }

    /**
     * Retrieve users except logged
     *
     * @param id user id
     * @return list of users
     */
	public Iterable<User> findByAllExcept(Long id) {
		LOG.info("Retrieving list of users.", id);
		return userRepository.findByAllExcept(id);
	}
	

}
