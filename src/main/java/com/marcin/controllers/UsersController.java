package com.marcin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marcin.entities.User;
import com.marcin.enums.UserType;
import com.marcin.formObjects.Register;
import com.marcin.repositories.UsersRepository;

@Controller
@RequestMapping("/users")
public class UsersController {

@Autowired
private UsersRepository users;
@Autowired
private ObjectMapper mapper;

    @GetMapping("/getAll")
    public @ResponseBody ObjectNode getAll() {
    	ObjectNode node = mapper.createObjectNode();
    	ArrayNode array = mapper.valueToTree(users.findAll());
    	node.putArray("users").addAll(array);
    	return node;
    }
   
	@GetMapping("/html/{userName}")
	public String lessonHtml() {
		return "changeUser";
	}
	
	@GetMapping("/html/block")
	public String blockUser()
	{
		return "users";
	}
	
	
    
	@GetMapping("/{userName}") 
	public @ResponseBody ObjectNode getUser(@PathVariable("userName") String userName) {
		 User user = users.findByEmail(userName);
		 ObjectNode node = mapper.createObjectNode();
		 if (user == null) {
				node.put("Error","No such entry in database");
				return node;
			}
		 node.put("email", user.getEmail());
		 node.put("type",user.getType().toString());
		 return node;
	}
	
	@GetMapping("/new")
	public String newUserGet() {
		return "register";
	}
	
	@PostMapping("/new")
	public RedirectView newUserPost(HttpSession session,Register form) {
		if (session.getAttribute("logged")==null) session.setAttribute("logged", "NOTLOGGED");
		if(session.getAttribute("logged").toString()!="LOGGED") {
			if (form.getPassword()==null) return new RedirectView("/projects");
			
			String password = DigestUtils.sha256Hex(form.getPassword());
			 String email = Jsoup.clean(form.getEmail(),Whitelist.none());
			 String type = Jsoup.clean(form.getType(),Whitelist.none());
			 if(email == null || password==null || type == null)
				 return new RedirectView("/");
			User user = new User(email,email,password,UserType.valueOf(type),"NOTBLOCKED");
			users.save(user);
		}
		return new RedirectView("/");
	}
	
	@GetMapping("/block/{email}")
	public RedirectView block(@PathVariable("email") String userName, HttpSession session) {
		if(session.getAttribute("logged")=="LOGGED"){
		    if(session.getAttribute("user")!= null) {
		        User user = users.findByEmail(session.getAttribute("user").toString());
		        if (user.getType()==UserType.valueOf("ADMIN")) {
		        User blockedUser = users.findByEmail(userName);
		        if (blockedUser!=null) {
		        	blockedUser.setBlocked("BLOCKED");
		        	users.save(blockedUser);
		        }
		        }
		    }
		}
		return new RedirectView("/",true);
	}
	
	@GetMapping("/unblock/{email}")
	public RedirectView unblock(@PathVariable("email") String userName, HttpSession session) {
		if(session.getAttribute("logged")=="LOGGED"){
		    if(session.getAttribute("user")!= null) {
		        User user = users.findByEmail(session.getAttribute("user").toString());
		        if (user.getType()==UserType.valueOf("ADMIN")) {
		        User unblockedUser = users.findByEmail(userName);
		        if (unblockedUser!=null) {
		        	unblockedUser.setBlocked("UNBLOCKED");
		        	users.save(unblockedUser);
		        }
		        }
		    }
		}
		return new RedirectView("/",true);
	}
	
}
