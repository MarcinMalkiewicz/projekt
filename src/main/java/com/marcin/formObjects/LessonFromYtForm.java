package com.marcin.formObjects;


public class LessonFromYtForm {


	private String name;
	private String description;
	private String data;
	private String ytLink;
	
	public LessonFromYtForm(String name, String description, String data, String ytLink) {
		super();
		this.name = name;
		this.description = description;
		this.data = data;
		this.ytLink = ytLink;
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
	public String getYtLink() {
		return this.ytLink;
	}
	public void setYtLink(String ytLink) {
		this.ytLink = ytLink;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;	
	}

	
}
