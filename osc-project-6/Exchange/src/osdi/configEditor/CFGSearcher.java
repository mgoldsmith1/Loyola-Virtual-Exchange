package osdi.configEditor;
import java.io.File;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileFilter;

public class CFGSearcher extends FileFilter{
	private String fileType = "cfg";
	private String dotPrefix = ".";
	
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		if (extension(f).equalsIgnoreCase(fileType)) {
			return true;
		}
		else
			return false;
	}
	public String getDescription() {
		return "Configuration File Search Only";
	}
	public String extension (File f) {
		String fileName = f.getName();
		int indexFile = fileName.lastIndexOf(dotPrefix);
		if(indexFile > 0 && indexFile < fileName.length()-1) {
			return fileName.substring(indexFile+1);
		}
		else {
			return "";
		}
	}
}