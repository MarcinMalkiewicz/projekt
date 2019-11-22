package com.marcin.controllers;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.marcin.entities.User;
import com.marcin.formObjects.Login;
import com.marcin.repositories.UsersRepository;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@Controller
public class MainController {
@Autowired
private UsersRepository users;

@RequestMapping(value = "/")
public String index(HttpSession session) {
	String logged = (String) session.getAttribute("logged");
	if (logged == null) {
		logged = "NOTLOGGED";
		session.setAttribute("logged", logged);
	}
	if (session.getAttribute("logged")=="NOTLOGGED") {
		return "login";
	}
	return "administrationPanel";

}

@GetMapping("/logout")
public RedirectView logut(HttpSession session){
	if(session.getAttribute("logged")!=null)
	session.setAttribute("logged", "NOTLOGGED");

	return new RedirectView("/",true);

}

@GetMapping("/newLessonMiddle")
public String middle() {
	return "newLessonMiddleware";
}

@PostMapping("/userLogin")
public RedirectView login(Login form,HttpSession session) {
	 String password = DigestUtils.sha256Hex(form.getPassword());
	System.out.print(password);
	 String email = Jsoup.clean(form.getEmail(),Whitelist.none());
	 System.out.print(email);
	User user = users.findByEmailAndPassword(email, password);
	if (user==null || user.getBlocked().equals("BLOCKED")) {
	session.setAttribute("logged", "NOTLOGGED");
	return new RedirectView("/",true);
	}
	else {
	session.setAttribute("logged", "LOGGED");
	session.setAttribute("user", email);
	}
	return new RedirectView("/",true);
}

@GetMapping("/isLogged")
@ResponseBody
public String isLogged(HttpSession session) {
	if (session.getAttribute("logged")!=null)
	return session.getAttribute("logged").toString();
	else {
		session.setAttribute("logged", "NOTLOGGED");
		return session.getAttribute("logged").toString();
	}
}

@GetMapping("/forbidden")
public String forbidden(){
	return "forbidden";
}


}
