package com.marcin.controllers;


import java.io.ByteArrayInputStream;
import java.io.File;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.Blob;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marcin.entities.Lesson;
import com.marcin.entities.Project;
import com.marcin.formObjects.LessonForm;
import com.marcin.formObjects.ProjectForm;
import com.marcin.repositories.LessonsRepository;
import com.marcin.repositories.ProjectsRepository;

import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	private LessonsRepository lessons;
	@Autowired
	private ProjectsRepository projects;
	@Autowired
	private ObjectMapper mapper;
	@GetMapping("/")
	public @ResponseBody ObjectNode getAll() {
	ObjectNode node = mapper.createObjectNode();
	ArrayNode array = mapper.valueToTree(projects.findAll());
	node.putArray("projects").addAll(array);
	return node;
	}

	@GetMapping("/courses")
	public String courses() {
		return "courses";
	}

	@GetMapping("/lessons/{projectName}")
	public String lessonHtml() {
		return "lessons";
	}

	@GetMapping("/lessons/lesson/{variable}")
	public String getLesson()
	{
		return "lesson";
	}
	@GetMapping("/{projectName}/new/lesson")
	public String newLessonGet() {
		return "newLesson";
	}

	@GetMapping("/lesson/unblock")
	public String unLesson() {
		return "unblockLessons";
	}

	@GetMapping("/{projectName}/{lessonName}/unlock")
	public RedirectView unbLesson(@PathVariable("projectName") String projectName,@PathVariable("lessonName") String lessonName,HttpSession session){
		Lesson lesson = lessons.findByNameAndProjectname(lessonName, projectName);
		if(session.getAttribute("logged")=="LOGGED" && lesson!=null) {
		lesson.setIsLocked("UNLOCKED");
		lessons.save(lesson);
		}
		return new RedirectView("/",true);
	}

	@GetMapping("/{projectName}/{lessonName}/html")
	public String htmlLesson(){
		return "lesson";
	}

	@PostMapping("/{project}/new/lesson")
	public RedirectView newLessonPost(@PathVariable("project") String projectName,LessonForm form, HttpSession session)  throws IOException, SerialException, SQLException{
		if(session.getAttribute("logged")=="LOGGED" && form.getName()!=null
		&& form.getData()!=null && form.getDescription()!=null && form.getVideo()!=null) {
		Lesson lesson = new Lesson(
				form.getName(),
				projectName,
				form.getDescription(),
				form.getVideo().getBytes(),
				form.getData(),
				"UNLOCKED"
				);
		lessons.save(lesson);
		}
		else {
			return new RedirectView("/projects/",true);
		}
		return new RedirectView("/",true);
	}

	@GetMapping("/{projectName}")
	public @ResponseBody ObjectNode getProject(@PathVariable("projectName") String projectName) {
		Project project = projects.findByName(projectName);
		//List<Lesson> less = lessons.findByProjectname(projectName);
		List<String> less =lessons.findOnlyNames(projectName);
		ObjectNode node = mapper.createObjectNode();
		node.put("name", project.getName());
		node.put("owner",project.getOwner());
		node.put("description",project.getDescription());
		ArrayNode arr = mapper.valueToTree(less);
		node.putArray("lessons").addAll(arr);
		return node;
	}




	@GetMapping(value="/{projectName}/{lessonName}/video",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] blobuj(@PathVariable("projectName") String projectName,@PathVariable("lessonName") String lessonName) throws IOException {
		Lesson less = lessons.findByNameAndProjectname(lessonName,projectName);
		if(less!=null)
	 return less.getVideo();
		else return null;
	}

	@GetMapping("/{projectName}/{lessonName}")
	public @ResponseBody ObjectNode getLesson(@PathVariable String projectName, @PathVariable String lessonName,HttpSession session) {
		ObjectNode node = mapper.createObjectNode();
		if(session.getAttribute("logged").equals("LOGGED")) {
		Lesson lesson = lessons.findByNameAndProjectname(lessonName, projectName);
		if (lesson == null) {
			node.put("Error","No such entry in database");
			return node;
		}
		if(lesson.getIsLocked().equals("UNLOCKED") || lesson!=null && lesson.getUnlock_date()!=null && lesson.getDescription()!=null && lesson.getIs_locked()!=null && lesson.getName()!=null && lesson.getUnlock_date().compareTo(new java.util.Date())<=0) {
		node.put("name", lesson.getName());
		node.put("description",lesson.getDescription());
		node.put("project",lesson.getProjectname());
		node.put("isLocked",lesson.getIsLocked());
		node.put("unlockDate",lesson.getUnlock_date().toString());
		}
		else node.put("Error","This lesson is not avalaible for you");
		}
		else {
			node.put("Error","You are not logged");
		}
		return node;
		}

	@GetMapping("/new/project")
	public String newProjectGet(HttpSession session) {
		if(session.getAttribute("logged")!= null && session.getAttribute("logged").toString()=="LOGGED")
			return "newProject";

		else
		return "forbidden";
		}

	@PostMapping("/new/project")
	public RedirectView newProjectPost(HttpSession session,ProjectForm form) {
		if (session.getAttribute("logged")=="LOGGED") {
			String owner = session.getAttribute("user")!=null ? session.getAttribute("user").toString() : "Owner";
			String name= Jsoup.clean(form.getName(),Whitelist.none());
			String desc = Jsoup.clean(form.getDescription(),Whitelist.none());
			if (name == null || desc == null)
				return new RedirectView("/");
		    Project project = new Project(name,owner,desc);
		    projects.save(project);
			return new RedirectView("/projects/new/project");
		}
		else return new RedirectView("/forbidden",true);
	}



}
