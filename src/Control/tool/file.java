package Control.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import entity.recordLesson;

/**
 * read the information in the file
 */
public class file {

	//read the information in the file
	public static List<String> open(String file) throws IOException {

		//String file = "LessonInfo.txt";

		List<String> lines = new ArrayList<>();

		FileInputStream inputStream = null;

		InputStreamReader streamReader = null;

		BufferedReader reader = null;

		try {

			inputStream = new FileInputStream(file);

			streamReader = new InputStreamReader(inputStream);

			reader = new BufferedReader(streamReader);

			String line1 = "";

			while ((line1 = reader.readLine()) != null) { // read each line
				lines.add(line1);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (streamReader != null) {
				streamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return lines;
	}

}
