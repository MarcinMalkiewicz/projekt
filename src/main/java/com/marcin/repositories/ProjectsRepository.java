package com.marcin.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcin.entities.Project;
@Repository
public interface ProjectsRepository extends CrudRepository<Project,Long> {
	Project findById(long id);
	Project findByName(String name);
	List<Project> findByOwner(String owner);
}
