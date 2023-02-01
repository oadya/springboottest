package com.testing.springboottest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.testing.springboottest.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

	@Modifying
	@Transactional
	@Query("update Student u set u.note = :note, u.mention = :mention where u.username = :username")
	public void updateNoteByUserName(@Param(value = "username") String username, @Param(value = "note") double note,
			@Param(value = "mention") String mention);

	Student findByUsername(String name);

}