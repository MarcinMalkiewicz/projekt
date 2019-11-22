package com.marcin.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcin.entities.User;

import java.util.List;
@Repository
public interface UsersRepository extends CrudRepository<User,Long>{
	User findByUsername(String username);
	User findById(long id);
	User findByEmail(String email);
	List<User> findByType(String type);
	User findByEmailAndPassword(String email,String password);
}
