package com.microservice.identity;

import com.microservice.identity.entity.Role;
import com.microservice.identity.entity.User;
import com.microservice.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IdentityApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userService.adminExists()) {
			User user = new User();
			user.setEmail("webmaster@wm.com");
			user.setPassword(new BCryptPasswordEncoder().encode("webmaster"));
			user.setRole(Role.Admin);
			userService.addUser(user);
		}



	}
}
