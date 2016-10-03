package com.truextend.exercise.runner;

import java.util.List;

import com.truextend.exercise.control.Controller;
import com.truextend.exercise.model.Student;
import com.truextend.exercise.tools.Utilities;

/**
 * 
 * @author Daniel Valencia
 * @version 1.0
 * 
 * Project Overview & Requirements
 * You are modeling a system to manage students that will be used by high schools, 
 * elementary schools, kindergardens, etc.
 * Your task is to create the business objects to manage the students in the system:
 * Store the students in the system
 * Create new students
 * Delete a specific student
 * Search for students in ways that make sense for the clients
 * By name, sorted alphabetically
 * By student type (kinder, elementary, high, university) sorting by date, most recent to least recent.
 * By gender and type (female elementary) sorting by date, most recent to least recent.
 * Your solution should focus on the server objects necessary to implement the core functionality 
 * of managing the students and searching for students.
 * 
 * This class begins the program and has an instance of our controller.
 *
 */

public class StudentSolution {

	/**
	 * @param args has the initial parameters in order to the program execute the correct instruction
	 * 
	 */
	public static void main(String[] args) {

		try {
			
			if (args.length == 1) {

				/*
				 * If args has only one parameter, it ask for specific information about the student to store in a file.
				 * Filename is the only parameter that the application need.
				 * 
				 */
				
				System.out.println("Welcome to the JavaCodeExerciseDaniel program");
				System.out.println("");
				System.out.println("Please, enter the following data to create a new student");
				
				Student new_student = Utilities.getStudent();
				
				Controller controller = new Controller(args[0]);
				if ( controller.addStudent(new_student) ) {
					System.out.println("A new student was stored.");
				} else {
					System.out.println("A new student already exist.");
				}
				
			} else if (args.length == 5) {
				
				/*
				 * If args has 5 parameters, it means the student information was in the last four parameters to store in a file.
				 * Filename is the first parameter into args.
				 * 
				 */
				
				Student new_student = new Student(
						args[1], args[2], args[3].charAt(0), 
						Long.parseLong(args[4]));
				
				Controller controller = new Controller(args[0]);
				if ( controller.addStudent(new_student) ) {
					System.out.println("A new student was stored.");
				} else {
					System.out.println("A new student already exist.");
				}
				
				System.out.println("A new student was stored.");
				
			} else if (args.length == 2 || args.length == 3 || args.length == 4) {
				
				/*
				 * If args has 2,3,4 parameters, it means that you want to do a searching into the file.
				 * Filename is the first parameter into args.
				 * You can specify the field and value (Ex1: name:Luis or Ex2: type=university or Ex3: type=elementary gender=female)
				 * If you search by name, the result will be sorted by name.
				 * If you search by type or gender, the result will be sorted by most recent to least recent.
				 */
				
				Controller controller = new Controller(args[0]);
				
				Student student = Utilities.toStudent(args, 1);
				if (student.hasInformation()) {
					List<Student> students = controller.searchStudent(student);
					System.out.println(Utilities.ListToJson(students));
				} else {
					System.out.println("[]");
				}
				
			} else {
				
				/*
				 * If args is not defined, the program offers you help about how execute correctly.
				 */
				
				System.out.println("Read students at system startup from a CSV input file " +
						"as well as reading the search request from the command line");
				
				System.out.println("");
				System.out.println("Ex: java studentSolution input.csv name=leia");
				System.out.println("Ex: java studentSolution input.csv type=kinder");
				System.out.println("Ex: java studentSolution input.csv type=elementary gender=female");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}

}
