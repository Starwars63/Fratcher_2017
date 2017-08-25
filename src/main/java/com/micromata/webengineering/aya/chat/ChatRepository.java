package com.micromata.webengineering.aya.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.micromata.webengineering.aya.user.User;


@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
	@Query("SELECT c FROM User_ u, Chat c WHERE (c.source = :source and c.destination = :dest) or (c.source = :dest and c.destination = :source) ")
	List<Chat> findChats(@Param("source") User source, @Param("dest") User dest);
}
