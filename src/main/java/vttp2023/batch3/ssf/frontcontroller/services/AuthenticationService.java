package vttp2023.batch3.ssf.frontcontroller.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch3.ssf.frontcontroller.Model.Login;
import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;

@Service
public class AuthenticationService {

	@Value("${assessment.open.authenticate.url}")
	private String url;

	@Autowired
	private AuthenticationRepository repo;
	
	private static Map<String, String> logins;
	private boolean loginFail;
	

	public boolean isLoginFail() {
		return loginFail;
	}

	public void setLoginFail(boolean loginFail) {
		this.loginFail = loginFail;
	}

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {

		String url = UriComponentsBuilder
						.fromUriString("https://authservice-production-e8b2.up.railway.app/api/authenticate")
						.queryParam("username", username)
						.queryParam("password", password)
						.toUriString();

		if(username == null || password == null) {
			JsonObject message = Json.createObjectBuilder()
				.add("empty", "Invalid payload")
				.build();

			HttpHeaders header = new HttpHeaders();
			header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			RequestEntity<String> req = RequestEntity
					.post(url)
					.contentType(MediaType.APPLICATION_JSON)
					.headers(header)
					.body(message.toString(), String.class);

			RestTemplate template = new RestTemplate();
			ResponseEntity<String> resp = template.exchange(req, String.class);
			
		}
		
		JsonObject message = Json.createObjectBuilder().add("username", username).add("password", password).build();

		HttpHeaders header = new HttpHeaders();
		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		RequestEntity<String> req = RequestEntity
			.post(url)
			.contentType(MediaType.APPLICATION_JSON)
			.headers(header)
			.body(message.toString(), String.class);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = template.exchange(req, String.class);


		logins = repo.addAccounts();

		if(repo.checkBlocked(username)){
			System.out.println(" you failure");
			this.loginFail=true;
			return;
		}
		if(!repo.checkPresent(username)){
			System.out.println("youfuckedup");
			this.loginFail = true;
			return;
		}

		if(!logins.containsKey(username) || !logins.containsValue(password)){
			System.out.println("loser");
			this.loginFail = true;
			return;
		}

		System.out.println("goodjob");
		this.loginFail=false;
		
			
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {

		repo.addBlockedAccount(username);

	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {

		boolean isBlocked = repo.checkBlocked(username);

		if(isBlocked==true){
			return true;
		}

		return false;
	}
}
