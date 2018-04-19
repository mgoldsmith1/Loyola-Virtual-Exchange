package osdi.configEditor;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Writer {
	private BufferedWriter writerBuffer;
	private FileWriter fileWriter;
	private Scanner scribe;
	
	public Writer (String filePath) {
		Reader info = new Reader(filePath);
		List <String> inforArray = info.fetchReaderContent();
		BufferedWriter writer = null;
		try {
		//HOW DO WE GET THIS TO READ FROM A SWING GUI WINDOW?
		//TODO
		}
		finally {
			
		}
	}
}
