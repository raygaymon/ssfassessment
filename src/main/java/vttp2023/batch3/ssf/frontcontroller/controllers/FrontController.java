package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.Model.Login;

@RestController
@RequestMapping(path="/")
public class FrontController {

	@GetMapping
	public String landingPage (Model m) {
		m.addAttribute("login", new Login());
		return "view0";
	}

	@PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded")
	public String login(Model m, HttpSession session, @ModelAttribute @Valid Login login, BindingResult br) {

		if (br.hasErrors()) {
			return "view0";
		}

		return "view1";
	}

	@PostMapping(path ="/api/authenticate", consumes = "applicatiom/json" ,produces="application/json")
	public ResponseEntity<String> getLoginJson (@RequestBody Login login) {

		JsonObject message = Json.createObjectBuilder().add("message", "Authenticated %s".formatted(login.getUsername())).build();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(message.toString() + login.toJSON().toString());
	}

	// TODO: Task 2, Task 3, Task 4, Task 6

	
}
