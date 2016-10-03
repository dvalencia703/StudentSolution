package com.truextend.exercise.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.truextend.exercise.model.Student;

/**
 * 
 * @author Daniel Valencia
 * 
 * This class gives functions (interaction with console, transform information, and load global properties) in order to do more clear the main code in others classes.
 *
 */

public final class Utilities {

	private static Scanner scanner = null;
	
	public static Scanner getScannerInstance() {
		
		return scanner == null ? scanner = new Scanner(System.in) : scanner;
	}
	
	public static Student getStudent() throws Exception {
		
		Scanner scanner = getScannerInstance();

		Student student = new Student();
		
		System.out.print("Please, enter type: ");
		student.setType(scanner.nextLine());
		System.out.print("Please, enter name: ");
		student.setName(scanner.nextLine());
		System.out.print("Please, enter gender: ");
		student.setGender(scanner.nextLine().charAt(0));
		System.out.print("Please, enter timestamp (<year><month><day><hour><minute><second>): ");
		student.setTimestamp(scanner.nextLong());
		
		System.out.println();
		
		return student;
	}

	public static String ObjToLine(Student student) {
		return student.getType() + "," +
				student.getName() + "," +
				student.getGender() + "," +
				student.getTimestamp();
	}

	public static Student LineToObj(String line) {
		
		if (line != null) {
			Student student = new Student();
			
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			
			student.setType(tokenizer.nextToken());
			student.setName(tokenizer.nextToken());
			
			String gender = tokenizer.nextToken();
			if (!gender.isEmpty() && gender.length() > 0) {
				student.setGender(gender.charAt(0));
			}
			student.setTimestamp(Long.parseLong(tokenizer.nextToken()));
			
			return student;
		} else {
			
			return null;
		}
	}
	
	private static Gson gson = new Gson();
	
	public static String ObjToJson(Student student) {
		
		return gson.toJson(student);
	}
	
	public static String ListToJson(List<Student> students) {
		
		return gson.toJson(students);
	}

	public static Properties loadProperties() throws IOException {
		
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");
			prop.load(input);

		} finally {
			if (input != null) {
					input.close();
			}
		}

		return prop;
	}

	public static Student toStudent(String[] strStudent, int index) {
		
		Student studentFilter = new Student();
		
		for (int i = index; i < strStudent.length; i++) {
			toStudent(strStudent[i], studentFilter);
		}
		
		return studentFilter;
	}
	
	public static void toStudent(String strStudent, Student studentFilter) {

		String[] fieldvalues = strStudent.split(" ");
		for (String fieldValue : fieldvalues) {
			String[] fv = fieldValue.split("=");
			if (fv[0].equals("name") && !fv[1].isEmpty()) {
				studentFilter.setName(fv[1]);
			} else if (fv[0].equals("type") && !fv[1].isEmpty()) {
				studentFilter.setType(fv[1]);
			} else if (fv[0].equals("gender") && !fv[1].isEmpty()) {
				studentFilter.setGender(fv[1].charAt(0));
			}
		}
	}

	public static String getDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new java.util.Date());
	}

	/**
	 * 
	 * @param student to be insert sorted according to input parameters
	 * @param result set of students
	 * @param orderByName kind of sorter
	 * @param searchLimit 
	 * @return
	 * 
	 * With this method we avoid overflow problems
	 */
	
	public static List<Student> insertStudent(Student student,
			List<Student> result, boolean orderByName, int searchLimit) {

		if (result.isEmpty()) {
			result.add(student);
		} else {
			boolean finish = false;
			for (int i = 0; !finish && i < result.size(); i++) {
				
				Student studentStored = result.get(i);
				if (orderByName) {
					if (student.getName().compareToIgnoreCase(studentStored.getName()) < 0) {
						result.add(i, student);
						finish = true;
					}
					
				} else {
					if (student.getTimestamp() > studentStored.getTimestamp()) {
						result.add(i, student);
						finish = true;
					}
					
				}
			}
		}
		
		return result.size() > searchLimit ? result.subList(0, searchLimit) : result;
	}
	
}
