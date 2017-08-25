package com.micromata.webengineering.aya.chat;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micromata.webengineering.aya.user.User;
import com.micromata.webengineering.aya.user.UserService;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Handle all CRUD operations for posts.
 */
@Service
public class ChatService {
	// private static final Logger LOG = LoggerFactory.getLogger(ChatService.class);

	@Autowired
	public ChatRepository repository;

	@Autowired
	public UserService userService;

	/**
	 * send a message to user.
	 *
	 * @param source the sender
	 * @param message
	 *            text of the chat
	 * @return id of the corresponding message
	 */
	@Transactional
	public Long addChat(User dest, String message) {
		// Persist chat.
		Chat chat = new Chat();
		chat.setSource(userService.getCurrentUser());
		chat.setDestination(dest);
		chat.setMessage(message);
		chat.setCreatedAt(new Date(System.currentTimeMillis()));
		repository.save(chat);

		return chat.getId();
	}

	/**
	 * get a friendship chat history.
	 *
	 * @param destination a user
	 * @return id of the corresponding message
	 */
	@Transactional
	public List<Chat> getChats(User otherUser) {
		// Persist chat.
		return repository.findChats(otherUser, userService.getCurrentUser());

	}

}
