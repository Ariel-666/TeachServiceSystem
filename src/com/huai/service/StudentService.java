package com.huai.service;

import java.util.List;

import com.huai.beans.Student;

public interface StudentService {

	/**
	 * 获得此课程中的所有学生
	 * @param courseId
	 * @return List<Student>
	 */
	public List<Student> getStudentsInTheCourse(String courseId);
	/**
	 * 在此课程中增加一个学生
	 * @param student
	 * @param courseId
	 */
	public void addStudent(Student student, String courseId);
	/**
	 * 把一个学生从此课程中删除
	 * @param studentNo
	 * @param courseId
	 */
	public void deleteStudentFromTheCourse(String studentNo, String courseId);
}