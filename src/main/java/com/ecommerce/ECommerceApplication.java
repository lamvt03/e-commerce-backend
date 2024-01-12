package com.ecommerce;

import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Bean
//	public CommandLineRunner commandLineRunner(){
//		return args -> {
//			User user = new User();
//			user.setFirstName("Vo");
//			user.setLastName("Lam");
//			user.setEmail("votruonglam2109@gmail.com");
//			user.setMobile("0886338217");
//			user.setRole(UserRole.ADMIN);
//			user.setPassword(passwordEncoder.encode("21092003@"));
//            userRepository.save(user);
//        };
//    }

}
