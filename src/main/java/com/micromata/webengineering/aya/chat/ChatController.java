package com.micromata.webengineering.aya.chat;

import com.micromata.webengineering.aya.user.User;
import com.micromata.webengineering.aya.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP endpoint for a comment-related HTTP requests.
 */
@RestController
public class ChatController {
    private static class NewChat {
        public String message;
    }

    @Autowired
    public ChatService chatService;

    @Autowired
    public UserService userService;





    @RequestMapping(value = "/api/chat/{destinationId}/message", method = RequestMethod.POST)
    public ResponseEntity<Long> addComment(@PathVariable Long destinationId, @RequestBody NewChat newMessage) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
User user = userService.getUser(destinationId);
if(user == null) {
	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
        Long id = chatService.addChat(user, newMessage.message);
        return ResponseEntity.ok(id);
    }
    
    @RequestMapping(value = "/api/chats/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserChats(@PathVariable Long id) {
    	if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
		User user = userService.getUser(id);
		if(user == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(chatService.getChats(user));

	}

/*
    @RequestMapping(value = "/api/chat/{friendshipId}/message/{id}", method = RequestMethod.POST)
    public void editComment(@PathVariable Long id, @RequestBody Chat chat) {
    	chatService.update(id, chat);
    }


    @RequestMapping(value = "/api/chat/{friendshipId}/message/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long id) {
    	chatService.deleteComment(id);
    }*/
}
