package com.tcs.usaa.todotask;

import com.tcs.usaa.todotask.domain.Role;
import com.tcs.usaa.todotask.domain.Task;
import com.tcs.usaa.todotask.domain.User;
import com.tcs.usaa.todotask.service.TaskService;
import com.tcs.usaa.todotask.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class TaskProjectV3Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskProjectV3Application.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, TaskService taskService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(1, "Luis Soto", "luis", "123456", new ArrayList<>()));
			userService.saveUser(new User(2, "Vicky Delgadillo", "vicky", "123456", new ArrayList<>()));
			userService.saveUser(new User(3, "User_xyz", "user_xyz", "123456", new ArrayList<>()));
			userService.saveUser(new User(4, "User_abc", "user_abc", "123456", new ArrayList<>()));

			userService.addRoleToUser("luis", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("luis", "ROLE_ADMIN");
			userService.addRoleToUser("luis", "ROLE_MANAGER");
			userService.addRoleToUser("luis", "ROLE_USER");

			userService.addRoleToUser("vicky", "ROLE_MANAGER");
			userService.addRoleToUser("vicky", "ROLE_ADMIN");
			userService.addRoleToUser("vicky", "ROLE_USER");

			userService.addRoleToUser("user_xyz", "ROLE_MANAGER");
			userService.addRoleToUser("user_xyz", "ROLE_USER");

			userService.addRoleToUser("user_abc", "ROLE_USER");

			taskService.save(new Task(1L,"Primera tarea spring","Mi primera tarea del proyecto to do api rest",new Date(),new Date(2023,7,05),"Yes","ON_TIME"));
			taskService.save(new Task(2L,"Segunda tarea spring","Mi segunda tarea del proyecto to do api rest",new Date(),new Date(2023,8,10),"Yes","ON_TIME"));
			taskService.save(new Task(3L,"Tercera tarea spring","Mi tercera tarea del proyecto to do api rest",new Date(),new Date(2023,9,28),"Yes","ON_TIME"));
		};
	}

}
