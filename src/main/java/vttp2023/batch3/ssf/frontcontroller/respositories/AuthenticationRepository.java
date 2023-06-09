package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	private RedisTemplate<String, String> template;

	public void saveAccount (){

		template.opsForValue().set("fred", "fredfred");
		template.opsForValue().set("barney", "barneybarney");
	}

	public Map<String, String> addAccounts(){

		Map<String, String> iGiveUp = new HashMap<String, String>();
		iGiveUp.put("fred", "fredfred");
		iGiveUp.put("barney", "barneybarney");

		return iGiveUp;
	}

	public String getAccountPassword(String user) {

		return template.opsForValue().get(user);
	}

	public void addBlockedAccount(String user){

		Duration d = Duration.ofMinutes(30);
		template.opsForValue().setIfAbsent("blocked", user, d);
	}

	public boolean checkBlocked(String user) {

		String pass =(String) template.opsForValue().get("blocked");
		if (pass == user){
			return true;
		}

		return false;
	}

	public boolean checkPresent(String user){

		String pass = (String) template.opsForValue().get(user);
		if (pass == null) {
			return true;
		}

		return false;

	}
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

}
