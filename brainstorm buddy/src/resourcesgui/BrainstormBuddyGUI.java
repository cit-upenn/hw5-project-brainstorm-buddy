package resourcesgui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

/* TopLevelDemo.java requires no other files. */
public class BrainstormBuddyGUI implements ActionListener, MouseListener{
	//Declare elements
	
	/**
	 * This is the interface variable that will be used to set various
	 * booleans to determine which resources will be pulled from which APIs
	 */
	private BrainStormBuddyChoices bsbc = new BrainStormBuddyChoices();
	
	private JFrame frame;
	private JFileChooser fileChooser;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem open,save;
	private JScrollPane textScroll; 
	private JTextArea textArea;
	private JPanel checkBoxPanel, encycSettingPanel, newsOptionsPanel;;//Encyclopedia, Dictionary, News 
	private JCheckBox encyc, news, dict;
	private JComboBox<String> newsSourceOptions;
	private JComboBox<String> encycOptions;
	
	private String [] newsSources = {"New York Times", "JStor"};
	private String [] encycSettings = {"","Snippets and Links", "Just Links"};
	private JButton createResources;
	private GridLayout myLayout = new GridLayout(3,2);
    private final int WINDOW_HEIGHT = 570;
    private final int WINDOW_WIDTH = 700;
    
    //creating resource frame instance variables
    private JFrame resourceFrame;
    private JMenuBar resourceMenuBar; 
    private JMenuItem saveResources;
    private JMenu saveMenu;
    private JTextArea resourceTextArea;
    private JScrollPane resourceScroll; 
    

    /**
     * Constructor for the BrainstormBuddyGUI
     * initializes all instance variables, sets layouts for containers
     * adds elements to containers, instantiates listeners, adds listeners
     * to buttons/menu items etc. 
     */
    public BrainstormBuddyGUI(){
    	//everything for resource frame
    	resourceFrame = new JFrame("Resources from Brainstorm");
    	resourceFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	resourceFrame.setLayout(new BorderLayout());
    	resourceMenuBar = new JMenuBar();
    	resourceTextArea = new JTextArea("Stuff goes here");
    	resourceTextArea.setLineWrap(true);
    	resourceTextArea.setWrapStyleWord(true);
    	saveMenu = new JMenu("Save");
    	saveResources = new JMenuItem("Save Resource File");
    	saveMenu.add(saveResources);
    	resourceMenuBar.add(saveMenu);
    	resourceFrame.setMinimumSize(new Dimension(400,600));
    	resourceScroll = new JScrollPane(resourceTextArea);
    	resourceFrame.add(resourceMenuBar, BorderLayout.NORTH);
    	resourceFrame.add(resourceScroll);
    	resourceFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	
    	//initialize elements
    	frame = new JFrame("Brainstorm Buddy");
    	
    	menuBar = new JMenuBar();
    	file = new JMenu("File");
    	open = new JMenuItem("Open previous Brainstorm");
    	save = new JMenuItem("Save current Brainstorm");
    	fileChooser = new JFileChooser();
    	
    	textArea = new JTextArea();
    	textArea.setEditable(true);
    	textArea.setLineWrap(true);
    	textArea.setWrapStyleWord(true);
    	
    	checkBoxPanel = new JPanel();
    	newsOptionsPanel = new JPanel();
    	encyc = new JCheckBox("Encyclopedia");
    	news = new JCheckBox("News");
    	dict = new JCheckBox("Dictionary");
    	encycSettingPanel = new JPanel();
    	newsSourceOptions = new JComboBox<>(newsSources);
    	encycOptions = new JComboBox<>(encycSettings);
    	createResources = new JButton("Create Resource Document");
    	textScroll = new JScrollPane(textArea);
    	
    	//set layouts
    	frame.setLayout(new BorderLayout());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	checkBoxPanel.setLayout(new GridLayout(10,1));
    	checkBoxPanel.setBorder(new TitledBorder(new EtchedBorder(), "Resource Options"));
    	encycSettingPanel.setLayout(myLayout);
    	newsOptionsPanel.setLayout(myLayout);
    	createResources.setBorder(BorderFactory.createRaisedBevelBorder());
    	createResources.setSize(WINDOW_WIDTH,100);
    	frame.setMinimumSize(new Dimension(400,600));
    	
    	
    	//add elements to containers
    	frame.add(textScroll, BorderLayout.CENTER);
    	file.add(open);
    	file.add(save);
    	menuBar.add(file);
    	frame.setJMenuBar(menuBar);
    	
    	encycSettingPanel.add(encyc,0);
    	encycSettingPanel.add(encycOptions);
    	
    	
    	checkBoxPanel.add(encycSettingPanel);
    	checkBoxPanel.add(dict);
    	
    	newsOptionsPanel.add(news);
    	newsOptionsPanel.add(newsSourceOptions);
    	
    	checkBoxPanel.add(newsOptionsPanel);
    	checkBoxPanel.add(createResources);
    	
    	frame.add(checkBoxPanel, BorderLayout.EAST);
    	
    	frame.pack();
    	
    	//set size, visibility
    	frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	frame.setVisible(true);
    	
    	
    	//initialize listeners
    	OpenFileListener ofl = new OpenFileListener();
    	SaveFileListener sfl = new SaveFileListener();
    	
    	
    	//add listeners
    	createResources.addMouseListener(this);
    	createResources.addActionListener(ofl);
    	saveResources.addActionListener(sfl);
    	
    	news.addActionListener(this);
    	newsSourceOptions.addActionListener(this);
    	encyc.addActionListener(this);
    	encycOptions.addActionListener(this);
    	dict.addActionListener(this);
    	
    	
    	open.addActionListener(ofl);
    	save.addActionListener(sfl);
    }
   
    
    /** 
     * Will run, and display the GUI
     * This is taken from an Oracle training on Swing
     * @param args
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	BrainstormBuddyGUI bb = new BrainstormBuddyGUI();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	/**
	 * Changes the border around the create resources button 
	 * lowers the bevel when mouse pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		createResources.setBorder(BorderFactory.createLoweredBevelBorder());
		//connect to resource parser
	}
	/**
	 * Changes the border around the create resources button 
	 *  heightens the bevel when mouse released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		createResources.setBorder(BorderFactory.createRaisedBevelBorder());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
	/**
	 * Decides what to do based on which element is clicked/selected
	 * Sets booleans within the brainstorm buddy choice (bsbc) object
	 * to set which resources will be pulled for the user. 
	 */
	@Override
	public void actionPerformed(ActionEvent which) {
		//encyclopedia option
		 if (which.getSource().equals(encyc)){
			 if(encyc.isSelected()){
				bsbc.setEncyclopedia(true);
				//set encyclopedia master boolean false
			 } else {
				 bsbc.setEncyclopedia(false);
				 //set encyclopedia master boolean false
				
			 } 
		 }
		 //these only do anything if the master encyclopedia boolean is true
		 if (which.getSource().equals(encycOptions)){
			 int index = encycOptions.getSelectedIndex();
			 switch(index){
			 	case(1):{
			 		//set preview boolean true
			 		bsbc.setPreview(true);
			 		break;
			 	}
			 	case(2):{
			 		//set preview boolean false (just links)
			 		bsbc.setPreview(false);
			 		break;
			 	}
			 }
		 }
		 if (which.getSource().equals(dict)){
			 if (dict.isSelected()){
				 //set dictionary boolean true
				 bsbc.setDictionary(true);
			 }
			 else{
				 //set dictionary boolean false
				 bsbc.setDictionary(false);
			 }
			 
		 }
		 
		
		 
		 if (which.getSource().equals(news)){
			 if(news.isSelected()){
				 //set master news boolean true
				bsbc.setNewsSources(true);
			 } else {
				 bsbc.setNewsSources(false);
				 //set master news boolean false
				
			 } 
		 }
		 //will only matter if master news boolean is true
		 if (which.getSource().equals(newsSourceOptions)){
			 int index = newsSourceOptions.getSelectedIndex();
			 switch(index){
			 	case(0):{
			 		//set NYT boolean true, JStor false
			 		bsbc.setNewYorktimes(true);
			 		bsbc.setJstor(false);
//			 		System.out.println("New York Times");
			 		break;
			 	}
			 	case(1):{
			 		//set NYT boolean true, JStor false
			 		bsbc.setJstor(true);
			 		bsbc.setNewYorktimes(false);
//			 		System.out.println("JStor");
			 		break;
			 	}
			 }
		 }

		
	}
	
	/**
	 * inner class for open file listener - to open files when selected through the menu
	 * @author gabecolton
	 *
	 */
	class OpenFileListener implements ActionListener {

		public void actionPerformed(ActionEvent which) {
			if(which.getSource().equals(open)){
				if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)) {
					textArea.setText("");
					File file = fileChooser.getSelectedFile();
					Scanner in = null;
					try
					{	
						in = new Scanner(file);
						// keep reading while there is more to read
						while (in.hasNext())
						{
							// read an entire line
							String line = in.nextLine();
							textArea.append(line+"\n");
						}
					} catch(Exception ea) {
						ea.printStackTrace();				
					} finally {
						in.close();
					}
				}
			}
			//creates a temporary file from the data in the text area
			//and passes it to the brainstorm buddy choices object
			//then pops up the resources frame. 
			if(which.getSource().equals(createResources)){
				resourceTextArea.setText("");
				
				File resultsFile;
				Scanner resultsIn = null;
				File tempFile = generateTempFileFromTextArea(textArea.getText());
				bsbc.gatherResources(tempFile.getName());
				resourceFrame.setVisible(true);
				try{
					resultsFile = new File ("results.txt");
					resultsIn = new Scanner(resultsFile);
					while (resultsIn.hasNext())
					{	
						// read an entire line
						String line = resultsIn.nextLine();
//						System.out.println(line);
						resourceTextArea.append(line+"\n");
					}
				}catch(Exception ea) {
					ea.printStackTrace();				
				} finally {
					resultsIn.close();
				}
			}
		}
	}
	
	/**
	 * Will save whichever text area's save menu is selected
	 * @author gabecolton
	 *
	 */
	class SaveFileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent which) {
			JTextArea textAreaToSave = null;
			if (which.getSource().equals(save)){
				textAreaToSave = textArea;
			}
			if(which.getSource().equals(saveResources)){
				textAreaToSave = resourceTextArea;
			}
			
			if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(frame)) {
				// create a null PrintWriter outside the try block
				PrintWriter out = null;
				try {
					// create the File
					File file = fileChooser.getSelectedFile();
					String filePath = file.getAbsolutePath();
					if(!filePath.endsWith(".txt")) {
					    file = new File(filePath + ".txt");
					}
					// initialize the PrintWriter
					out = new PrintWriter(file);

					// this is the String I will write to the file
					String output = textAreaToSave.getText();

					// now write to the file
					out.println(output);
					

				} catch(Exception ea) {
					ea.printStackTrace();				
				}finally {
				    // this is stuff that MUST happen
				    try { out.flush(); } catch (Exception ex) { }
				    try { out.close(); } catch (Exception ex) { }
				}

			}
		}
	}
	
	/**
	 * given a string, will create a temporary file in the current directory
	 * so that it is readily accesible to the bsbc object
	 * with that string as its contents
	 * @param textAString the string to be written to the temporary file
	 * @return  a temporary file in the current directory
	 */
	private File generateTempFileFromTextArea(String textAString){
		File temp= null;
		String currentDir;
		try {
		    // Create temp file.
			currentDir = System.getProperty("user.dir");
//		    System.out.println(currentDir);
			temp = File.createTempFile("temp", ".txt", new File(currentDir));

		    // Delete temp file when program exits.
		    temp.deleteOnExit();

		    // Write to temp file
		    BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		    out.write(textAString);
		    out.close();
		} catch (IOException e) {
		}
	
		return temp;
	
	}
	
	/**
	 * Used to test file contents
	 * @param f the file to be printed
	 */
	private void printFile (File f){
		Scanner in = null;
		try
		{	
			in = new Scanner(f);
			// keep reading while there is more to read
			while (in.hasNext())
			{
				// read an entire line
				String line = in.nextLine();
				System.out.println(line);
			}
		} catch(Exception ea) {
			ea.printStackTrace();				
		} finally {
			in.close();
		}
	}
	
}
