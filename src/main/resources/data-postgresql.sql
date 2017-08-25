-- Initialize database on startup. See
-- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-initialize-a-database-using-spring-jdbc
-- for explanation. This is a cool spring feature :-).


-- Remove everything.
DELETE FROM USER_;
DELETE FROM FRIENDSHIP;
DELETE FROM CHAT;

-- Insert new users.
INSERT INTO USER_ (ID, NAME, EMAIL, PASSWORD, POST, ONLINE, REGISTER_DATE) VALUES
(1, 'User 1', 'user1@mail.com', '50a861710c1e6d89e14ae235e69e9fd8a2a9a512d86caf9fb45ff64c1ca9dd4ec1974a2e996cb3e9d4a30693ff150bc5ff636b6be2fa3237378a898ec1414a1b', 'User 1 Sample Post', 0, '2017-08-25'),
(2, 'User 2', 'user2@mail.com', '50a861710c1e6d89e14ae235e69e9fd8a2a9a512d86caf9fb45ff64c1ca9dd4ec1974a2e996cb3e9d4a30693ff150bc5ff636b6be2fa3237378a898ec1414a1b', 'User 2 Sample Post', 0, '2017-08-25'),
(3, 'User 3', 'user3@mail.com', '50a861710c1e6d89e14ae235e69e9fd8a2a9a512d86caf9fb45ff64c1ca9dd4ec1974a2e996cb3e9d4a30693ff150bc5ff636b6be2fa3237378a898ec1414a1b', 'User 3 Sample Post', 0, '2017-08-25'),
(4, 'User 4', 'user4@mail.com', '50a861710c1e6d89e14ae235e69e9fd8a2a9a512d86caf9fb45ff64c1ca9dd4ec1974a2e996cb3e9d4a30693ff150bc5ff636b6be2fa3237378a898ec1414a1b', 'User 4 Sample Post', 0, '2017-08-25')

-- Insert new friendship.
INSERT INTO FRIENDSHIP  (ID, STATUS, DESTINATION_ID, SOURCE_ID, CREATED_AT  ) VALUES 
(1, '2017-08-25', 0, 2, 1), --Status = 0 -> new reques
(2, '2017-08-25', 3, 2, 3), --Status = 3 -> accepted
(3, '2017-08-25', 1, 1, 3) --Status = 1 -> ignored

-- Insert new Chat.
INSERT INTO CHAT (ID, CREATED_AT ,MESSAGE , 	DESTINATION_ID,  	SOURCE_ID   ) VALUES
(1, '2017-08-25', 'Hi, test message', 1, 2),
(2, '2017-08-25', 'Message reply', 2, 1),
(3, '2017-08-25', 'Hello', 2, 1)