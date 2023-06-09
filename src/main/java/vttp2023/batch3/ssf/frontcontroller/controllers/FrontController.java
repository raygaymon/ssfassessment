package vttp2023.batch3.ssf.frontcontroller.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.Model.Captcha;
import vttp2023.batch3.ssf.frontcontroller.Model.Login;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping(path="/")
public class FrontController {

	@Autowired
	private AuthenticationService service;

	@GetMapping
	public String landingPage (Model m, HttpSession session) {
		m.addAttribute("login", new Login());
		return "view0";
	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String login(Model m, HttpSession session, @ModelAttribute Login login, BindingResult br, @ModelAttribute Captcha c) throws Exception {


		//capturing the correct answer using session for later comparison
		int answer = 0;
		if(session.getAttribute("answer") != null) {
			 answer = (int) session.getAttribute("answer");
			 System.out.println(answer);
		}
		
		System.out.println(login.getUsername());

		service.authenticate(login.getUsername(), login.getPassword());
		
		if (br.hasErrors() || !c.checkAnswer(c.getAnswer(), c.getGivenAnswer())) {

			//generate captcha math problem once error in login detected	
			c.setProblem();
			m.addAttribute("captcha", c);
			session.setAttribute("answer", c.getAnswer());
			System.out.println(c.getAnswer());
			return "view0";
		}

		if(login.getLoginFails() == 3){

			//setting failed logins as a login attribute for keeping track

			service.disableUser(login.getUsername());
			login.setLoginFails(0);
			m.addAttribute("login", login);
			return "view2";
		}

		if(service.isLocked(login.getUsername())){

			m.addAttribute("login", login);
			login.setLoginFails(login.getLoginFails() + 1);
			return "view2";
		}

		if (service.isLoginFail()){
			c.setProblem();
			m.addAttribute("captcha", c);
			login.setLoginFails(login.getLoginFails() + 1);
			System.out.println("loser");
			return "view0";
		}


		session.setAttribute("username", login.getUsername());
		session.setAttribute("password", login.getPassword());

		return "view1";			



	
	}

	// TODO: Task 2, Task 3, Task 4, Task 6

	
}
