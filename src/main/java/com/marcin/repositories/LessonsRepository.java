package com.marcin.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcin.entities.Lesson;

@Repository
public interface LessonsRepository extends CrudRepository<Lesson,Long> {
	List<Lesson> findById(long id);
	List<Lesson> findByName(String name);
	List<Lesson> findByProjectname(String projectname);
	Lesson findByNameAndProjectname(String name, String projectname);
    @Query(value = "SELECT lesson.name FROM Lesson lesson where lesson.projectname = :projectName ")
    List<String> findOnlyNames(@Param("projectName") String projectName);
   
}
