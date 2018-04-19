package osdi.configEditor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
	private BufferedReader read;
	private String content;
	private List <String> collectedData;
	
	public Reader(String fileName) {
		try {
			read = new BufferedReader(new FileReader(fileName));
			collectedData = new ArrayList<String>();
			content = read.readLine();
			collectedData.add(content);
			while(content != null) {
				content = read.readLine();
				collectedData.add(content);
			}
			read.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(read != null) {
					read.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> getContent() {
		for(int i = 0; i < collectedData.size()-1;i++) {
			System.out.println(collectedData.get(i));
		}
		return null;
	}
	public List<String> fetchReaderContent(){
		return collectedData;
	}
	
}
