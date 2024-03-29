package com.marcin.formObjects;

import org.springframework.web.multipart.MultipartFile;

public class LessonForm {


	private String name;
	private String description;
	private String data;
	private MultipartFile video;
	

	

	
	public LessonForm(String name, String description, String data, MultipartFile video) {
		super();
		this.name = name;
		this.description = description;
		this.data = data;
		this.video = video;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getVideo() {
		if (video!=null)
		return video;
		else return null;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;	
	}

	
}
