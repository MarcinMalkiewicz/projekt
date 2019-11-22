package com.marcin.entities;



import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;



@Entity
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="projectname")
	private String projectname;

	@Column(name="description")
	private String description;

	@Lob
	@Column(name="video", columnDefinition="LONGBLOB")
	private byte[] video;

	@Column(name="is_locked")
	private String is_locked;

	@Column(name="unlock_date")
	private Date unlock_date;
	public Lesson() {
		super();
	}


public Lesson(String name, String projectname, String description, byte[] video, String data,String isLocked) {
	super();
	this.name = name;
	this.projectname = projectname;
	this.description = description;
	this.video = video;
	this.unlock_date = Date.valueOf(data);
	this.is_locked = isLocked;
	}


	public void setId(Long id) {
		this.id = id;
	}
	public String getIs_locked() {
		return is_locked;
	}
	public Date getUnlock_date() {
		return unlock_date;
	}
	public void setUnlock_date(Date unlock_date) {
		this.unlock_date = unlock_date;
	}
	public void setVideo(byte[] video) {
		this.video = video;
	}
	public void setIs_locked(String is_locked) {
		this.is_locked = is_locked;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public byte[] getVideo() {
		return video;
	}
	public String getIsLocked() {
		return is_locked;
	}
	public void setIsLocked(String is_locked) {
	this.is_locked = is_locked;
	}

}
