package com.truextend.exercise.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.truextend.exercise.model.Student;
import com.truextend.exercise.tools.Utilities;

/**
 * 
 * @author Daniel Valencia
 *
 * This class is the orchestrator, here is implemented all control functions of our project.
 *
 */

public class Controller {
	
	private String path;
	private String filename;
	private int searchLimit;
	private boolean allowRepetead;
	
	public Controller(String filename) throws IOException {

		this.filename = filename;
		
		/*
		 * We have properties where is defined some limitation of our searching to handle our performance.
		 * 
		 */
		
		Properties config = Utilities.loadProperties();
		
		String sSearchLimit = (String) config.get("searchLimit");
		if (sSearchLimit != null && !sSearchLimit.isEmpty()) {
			searchLimit = Integer.parseInt(sSearchLimit);
		} else {
			searchLimit = 20;
		}
		
		/*
		 * We have defined a path where find the students into the file CSV.
		 * 
		 */
		
		path = (String) config.get("directory");
		if (path == null || path.isEmpty()) {
			path = ".";
		}
		
		/*
		 * We have flag that define whether you want to handle data repeated the students into the file CSV.
		 * 
		 */
		
		String sAllowRepeated = (String) config.get("allowRepeated");
		if (sAllowRepeated != null && !sAllowRepeated.isEmpty()) {
			allowRepetead = sAllowRepeated.equalsIgnoreCase("true");
		} else {
			allowRepetead = false;
		}
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 
	 * @param student is our filter
	 * @return List o students according to parameters defined and limit quantity.
	 * @throws Exception
	 */

	public List<Student> searchStudent(Student student) throws Exception {

		/*
		 * The outcome will be in this result list
		 */
		List<Student> result = new ArrayList<Student>();
		
		boolean orderByName = student.getName() != null && !student.getName().isEmpty();
		
		FileReader reader = null;
		BufferedReader bufferReader = null;
		
		try {
			reader = new FileReader(path + "/" + filename);
			bufferReader = new BufferedReader(reader);

			/*
			 * We find the coincidences in file input 
			 * and the result we save the result in our result list
			 * 
			 */
			
			String line;
			while ( (line = bufferReader.readLine()) != null ) {
				Student studentFromFile = Utilities.LineToObj(line);
				if (studentFromFile.isSimilar(student)) {
					result = Utilities.insertStudent(studentFromFile, result, orderByName, searchLimit);
				}
			}
			
		} finally {
			
			if (bufferReader != null) bufferReader.close();
			if (reader != null) reader.close();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param student is the object to be evaluated
	 * @return a flag that shows if the student was registered or not.
	 * @throws Exception
	 */
	
	public boolean existStudent(Student student) throws Exception {
		
		boolean result = false;
		
		FileReader reader = null;
		BufferedReader bufferReader = null;
		
		try {
			
			reader = new FileReader(path + "/" + filename);
			bufferReader = new BufferedReader(reader);

			String line;
			while ( !result && (line = bufferReader.readLine()) != null ) {
				Student studentFromFile = Utilities.LineToObj(line);
				if (student.equals(studentFromFile)) {
					result = true;
				}
			}
			
		} finally {

			bufferReader.close();
			reader.close();
			
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param student the object must have all information required
	 * @return a flag that shows if the insert was performed successfully.
	 * @throws Exception
	 */
	
	public synchronized boolean addStudent(Student student) throws Exception {
	
		if (!allowRepetead && !existStudent(student)) {
			
			boolean result = true;
			FileWriter writer = null;
			
			try {
				writer = new FileWriter(path + "/" + filename, true);
				if (student.hasCompleteInformation()) {
					writer.write(Utilities.ObjToLine(student) + "\r\n");
					result = true;
				}
			} finally {
				if (writer != null) writer.close();
			}
			
			return result;
		} else {
			return false;
		}
		
	}
}
