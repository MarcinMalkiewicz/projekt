package com.marcin.formObjects;

public class BlockForm {
private String email;

public BlockForm() {}
public BlockForm(String email) {
	super();
	this.email = email;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

}
