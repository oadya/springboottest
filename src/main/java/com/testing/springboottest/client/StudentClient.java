package com.testing.springboottest.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.testing.springboottest.entity.Student;

@FeignClient(name = "microservice-users", url = "localhost:8080")
//@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class})
public interface StudentClient {

	@GetMapping(value = "/students")
	List<Student> getAllStudents();

}