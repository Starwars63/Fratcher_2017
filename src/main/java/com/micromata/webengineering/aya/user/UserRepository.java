package com.micromata.webengineering.aya.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User_ u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u from User_ u WHERE u.email = :email AND u.password = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    
    @Query("SELECT u FROM User_ u WHERE u.id != :id")
    Iterable<User> findByAllExcept(@Param("id") Long id);
    
    
    
    
}
