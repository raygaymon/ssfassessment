package vttp2023.batch3.ssf.frontcontroller.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AuthenticationService {

	@Value("${assessment.open.authenticate.url}")
	private String url;

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {

		String URIBuilder = UriComponentsBuilder
							.fromUriString(url)
							.toUriString();
		RequestEntity req = RequestEntity.get(URIBuilder).build();
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> re = rt.exchange(req, String.class);
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
