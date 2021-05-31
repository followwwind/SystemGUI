package Control.tool;

import entity.Course;
import entity.MyRecordLesson;
import entity.Order;
import entity.recordLesson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class will manage the file
 * including add, read, write, modify and delete content
 */
public class FileUtil {

	/**
	 * add content into flie
	 * @param content the content to be added
	 * @param fileName the file's name
	 */
	public static void insert(String content, String fileName) throws IOException  {
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("temp.txt")));
		String line;
		while ((line=in.readLine())!=null) {
				out.println(line);
		}
		out.println(content);
		in.close();
		out.close();
		File file1 = new File(fileName);
		if(file1.exists()){
			file1.delete();
		}
		File file2 = new File("temp.txt");
		if(file2.exists()){
			file2.renameTo(new File(fileName));
		}
	}

	/**
	 * Change content in the file
	 * @param oldStr string to be replaced
	 * @param newStr new string to write into
	 * @param fileName
	 */
	public static void change(String oldStr,String newStr, String fileName) throws IOException {
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("temp.txt")));
		String line;
		boolean flag=false;
		while ((line=in.readLine())!=null) {
			String[] splitStr=line.split(",");
			if(splitStr[0].equals(oldStr)) {
				flag = true;
			}
			if(flag) {
				out.println(newStr);
			}
			else {
				out.println(line);
			}
			flag=false;
		}
		in.close();
		out.close();
		File file1 = new File(fileName);
		if(file1.exists()){
			file1.delete();
		}
		File file2 = new File("temp.txt");
		if(file2.exists()){
			file2.renameTo(new File(fileName));
		}
	}

	/**
	 * delete content in the file
	 * @param uName member's name
	 * @param courseID the ID of course to delete
	 * @param fileName
	 */
	public static void delete(String uName, String courseID, String fileName) throws IOException {
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("temp.txt")));
		String line;
		while ((line=in.readLine())!=null) {
			String[] splitStr=line.split(",");
			if(fileName.equals("LessonInfo.txt")) {
				if (splitStr[0].equals(uName) && splitStr[0].equals(courseID)) {
					out.print("");
				} else {
					out.println(line);
				}
			}
			else if(fileName.equals("mylesson.txt")){
				if (splitStr[0].equals(uName) && splitStr[1].equals(courseID)) {
					out.print("");
				} else {
					out.println(line);
				}
			}
		}
		in.close();
		out.close();
		File file1 = new File(fileName);
		if(file1.exists()){
			file1.delete();
		}
		File file2 = new File("temp.txt");
		if(file2.exists()){
			file2.renameTo(new File(fileName));
		}
	}
	/**
	 * delete content in the file
	 * @param fileName
	 * @param keyword delete the line which contains the keyword
	 */
	public static void delete(String keyword,String fileName) throws IOException {
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("temp.txt")));
		String line;
		while ((line=in.readLine())!=null) {
			if (line.contains(keyword)) {
				out.print("");
			} else {
				out.println(line);
			}
		}
		in.close();
		out.close();
		File file1 = new File(fileName);
		if(file1.exists()){
			file1.delete();
		}
		File file2 = new File("temp.txt");
		if(file2.exists()){
			file2.renameTo(new File(fileName));
		}
	}

	/**
	 * find the legal lesson
	 * @param fileName
	 * @param lessonDate the date of live course
	 * @param coaId the coach's ID
	 * @return List of member's order information
	 */
	public static boolean findLegalOrder(String fileName,String coaId, String lessonDate) throws IOException {
		List<String> line1 = file.open(fileName);
		for (String string1 : line1) {
			if (!string1.isEmpty()) {
				String[] line = string1.split(",");
				if (line[2].equals(coaId)) {
					if (line[5].equals(lessonDate)) {
						if (!line[7].equals("payed")) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 * find the course information
	 * @param fileName
	 * @param courseId the course's ID
	 * @return List of member's record lesson
	 */
	public static Course findCourseInfo(String fileName, String courseId) throws IOException {
		Course course = new Course();
		List<String> line1 = file.open(fileName);
		for (String string1 : line1) {
			if (!string1.isEmpty()) {
				String[] line = string1.split(",");
				if (line[0].equals(courseId)) {
					course = new Course(line[0], line[1], line[2], line[3], line[4], line[5]);
				}
			}
		}
		return course;
	}
}
