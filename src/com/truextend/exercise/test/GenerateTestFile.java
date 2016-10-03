package com.truextend.exercise.test;

import java.io.FileWriter;
import java.util.Random;

import com.truextend.exercise.model.Student;
import com.truextend.exercise.tools.Utilities;

public class GenerateTestFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		for (int i = 0; i < 91 - 65; i++) {
//			System.out.println((char) (65 + i));
//		}

		try {

			FileWriter writer = null;
			
			try {
				writer = new FileWriter("files/input.csv", true);
				for (int i = 0; i < 100000; i++) {
					
					Student student = getRandom();
					writer.write(Utilities.ObjToLine(student) + "\r\n");
				}
			} finally {
				if (writer != null) writer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static Student getRandom() {

		Student s = new Student();
		
		int rv = new Random().nextInt(3);
		switch (rv) {
		case 0:
			s.setType("kinder");
			break;
		case 1:
			s.setType("elementary");
			break;
		case 2:
			s.setType("high");
			break;
		case 3:
			s.setType("university");
			break;
		default:
			break;
		}
		rv = new Random().nextInt(2);
		switch (rv) {
		case 0:
			s.setGender('M');
			break;
		case 1:
			s.setGender('F');
			break;
		default:
			break;
		}
		int year = 1980 + new Random().nextInt(26);
		int mounth = new Random().nextInt(11) + 1;
		int day = new Random().nextInt(31) + 1;
		int hour = new Random().nextInt(24) + 1;
		int minute = new Random().nextInt(59) + 1;
		int second = new Random().nextInt(59) + 1;
		
		s.setTimestamp(Long.parseLong(year + "" + mounth + "" + day + "" + hour + "" + minute + "" + second));
		
		int count = 5 + new Random().nextInt(20);
		String name = "";
		for (int i = 0; i < count; i++) {
			byte bLetter = (byte) (65 + new Random().nextInt(91 - 65));
			name += (char) bLetter;
		}
		
		s.setName(name);
		
		return s;
	}

}
