package com.testing.springboottest.service;

import java.util.List;

import com.testing.springboottest.entity.Student;

public interface StudentService {

	List<Student> getAll();

	Student findById(int id);

	Student findByName(String name);

	void create(Student user);

	void update(Student user);

	void delete(int id);

	boolean exists(Student user);

	public List<String> getAllMentions();

	public String getMention(double ratrapage);

	void externalCall();
}