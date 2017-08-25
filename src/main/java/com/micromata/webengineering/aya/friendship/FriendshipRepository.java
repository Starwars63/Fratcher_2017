package com.micromata.webengineering.aya.friendship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.micromata.webengineering.aya.user.User;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

    @Query("update Friendship set status = :status where id = :friendshipId")
    Friendship acceptFriendship(@Param("friendshipId") Long friendshipId,  @Param("status") int status);
    
   // @Query("SELECT u FROM Friendship f WHERE u.source.id = :userId")
    List<Friendship> findBySource(@Param("userId") User userId);
    
    @Query("SELECT f FROM Friendship f WHERE (f.source  = :source and f.destination=:dest) or (f.source  = :dest and f.destination=:source)")
    Friendship findBySourceAndDestination(@Param("source") User source, @Param("dest") User dest);
    
    @Query("SELECT f FROM User_ u, Friendship f WHERE u.id = :id and ( (u = f.source or u = f.destination) and f.status = 3 )")
    Iterable<Friendship> findMatches(@Param("id") Long id);
    
    @Query("SELECT f FROM User_ u, Friendship f WHERE u.id = :id and u = f.destination and f.status = 0 )")
    Iterable<Friendship> findRequests(@Param("id") Long id);
}
