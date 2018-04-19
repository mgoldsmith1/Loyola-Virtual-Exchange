package osdi.configEditor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CFGFrame extends JPanel implements ActionListener{
	private JFileChooser fileChooser;
	private JButton openButton;
	private BufferedReader br;
	private File file;
	int returnVal;
	String currentLine;
	
	public CFGFrame(){
		fileChooser = new JFileChooser();
		openButton = new JButton("Select");
		
		setPreferredSize(new Dimension(278, 179));
		setLayout(null);
		
		add(openButton);
		
		openButton.setBounds(84, 145, 100, 25);
		openButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == openButton){
			returnVal = fileChooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				file = fileChooser.getSelectedFile();
				
				//Read the file!
				try{
					br = new BufferedReader(new FileReader(file));
					
					while((currentLine = br.readLine()) != null){
						System.out.println(currentLine);
					}
				} catch (Exception error){
					error.printStackTrace();
				}
				
			}
		}
	}
}