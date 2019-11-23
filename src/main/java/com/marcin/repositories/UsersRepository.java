package com.marcin.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marcin.entities.User;

import java.util.List;


@Repository
public interface UsersRepository extends CrudRepository<User,Long>{
	User findByUsername(String username);
	User findById(long id);
	User findByEmail(String email);
	List<User> findByType(String type);
	User findByEmailAndPassword(String email,String password);
	@Modifying
	@Transactional
	(
	    propagation = Propagation.REQUIRED, 
	    readOnly = false,
	    rollbackFor = Throwable.class
	)
	@Query(nativeQuery = true,value="update User u set u.blocked=:status where u.email=:email")
	void blockUser(@Param("status") String status ,@Param("email") String email);
	
}
