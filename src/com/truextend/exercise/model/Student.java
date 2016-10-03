package com.truextend.exercise.model;

/**
 * 
 * @author Daniel
 *
 * This class is the model of bussiness object, when it is defined all student fields registered in the CSV File
 * It also has methods to compare and validate data information.
 */

public class Student {

	private String type; 
	private String name; 
	private char gender; 
	private long timestamp;
	
	public Student() {
		type = "";
		name = "";
		gender = 0;
		timestamp = 0L;
	}
	
	public Student(String type, String name, char gender, long timestamp) {
		type = "";
		name = "";
		gender = 0;
		timestamp = 0L;
	}
	
	public Student(Student s) {
		type = s.getType();
		name = s.getName();
		gender = s.getGender();
		timestamp = s.getTimestamp();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		boolean result = false;
		
		if (obj != null && obj instanceof Student) {
			Student student = (Student) obj;
			result = this.type.toUpperCase().equals(student.type.toUpperCase())
						&& this.name.toUpperCase().equals(student.name.toUpperCase())
						&& Character.toUpperCase(this.gender) == Character.toUpperCase(student.gender)
						&& this.timestamp == student.timestamp;
		}
		
		return result;
	}
	
	public boolean isSimilar(Student student) {

		boolean result = true;
		
		if (student != null && student.hasInformation()) {
			
			if (isFieldValid(this.type) && isFieldValid(student.type)) {
				result = result && this.type.toUpperCase().indexOf(student.type.toUpperCase()) != -1;
			}
			
			if (isFieldValid(this.name) && isFieldValid(student.name)) {
				result = result && this.name.toUpperCase().indexOf(student.name.toUpperCase()) != -1;
			}
			
			if (this.gender > 0 && student.gender > 0) {
				result = result && Character.toUpperCase(this.gender) == Character.toUpperCase(student.gender);
			}
			
			if (this.timestamp  > 0 && student.timestamp  > 0) {
				result = result && this.timestamp == student.timestamp;
			}
		} else {
			result = false;
		}
		
		return result;
	}

	private boolean isFieldValid(String fieldValue) {
		return fieldValue != null && !fieldValue.isEmpty();
	}

	public boolean hasInformation() {
		
		return ( type != null && !type.isEmpty() ) ||
				( name != null && !name.isEmpty() ) || 
				( gender > 0 ) ||
				( timestamp > 0 );
	}
	
	public boolean hasCompleteInformation() {
		
		return ( type != null && !type.isEmpty() ) &&
				( name != null && !name.isEmpty() ) && 
				( gender > 0 ) &&
				( timestamp > 19950101000000L );
	}
	
}
