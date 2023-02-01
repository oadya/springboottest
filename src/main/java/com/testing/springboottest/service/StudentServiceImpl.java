package com.testing.springboottest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testing.springboottest.entity.Student;
import com.testing.springboottest.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	private static final AtomicInteger counter = new AtomicInteger();

	static List<Student> students = new ArrayList<Student>(
			Arrays.asList(new Student(counter.incrementAndGet(), "student1", "student1@test.com", 12),
					new Student(counter.incrementAndGet(), "student2", "student2@test.com", 15)));

	@Override
	public List<Student> getAll() {
		List<Student> students = new ArrayList<Student>();
		studentRepository.findAll().forEach(u -> students.add(u));
		return students;
	}

	@Override
	public Student findById(int id) {
		Optional<Student> user = studentRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public Student findByName(String name) {
		return studentRepository.findByUsername(name);
	}

	@Override
	public void create(Student user) {
		studentRepository.save(user);
	}

	@Override
	public void update(Student user) {
		studentRepository.save(user);
	}

	@Override
	public void delete(int id) {
		studentRepository.deleteById(id);
	}

	@Override
	public boolean exists(Student user) {
		return findByName(user.getUsername()) != null;
	}

	@Override
	public List<String> getAllMentions() {
		return Arrays.asList("Echec", "Passable", "Assez bien", "Bien", "Tres bien");
	}

	@Override
	public String getMention(double ratrapage) {

		String result = "";

		if (ratrapage < 10) {
			result = "Echec";
		} else if (ratrapage >= 10 && ratrapage < 12) {
			result = "Passable";
		} else if (ratrapage >= 12 && ratrapage < 14) {
			result = "Assez bien";
		} else if (ratrapage >= 14 && ratrapage < 16) {
			result = "Bien";
		} else if (ratrapage >= 16) {
			result = " Tres bien";
		}

		return result;

	}

	@Override
	public void externalCall() {
		System.out.println("External call ...");

	}
}
