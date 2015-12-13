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
	private BrainStormBuddyChoices bsbc = new BrainStormBuddyChoices();
	
	private JFrame frame;
	private JFileChooser fileChooser;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem open,save;
	
	private JScrollPane textScroll; 
	
	private JTextArea textArea;
	private JPanel checkBoxPanel, encycSettingPanel, newsOptionsPanel;;//Encyclopedia, Dictionary, Websites 
	private JCheckBox encyc, news, dict, links;
	private JComboBox<String> newsSourceOptions;
	private JComboBox<String> encycOptions;
	
	private String [] newsSources = {"New York Times", "JStor"};
	private String [] encycSettings = {"","Snippets and Links", "Just Links"};
	private JButton createResources;
	private GridLayout tester = new GridLayout(3,2);
    private final int WINDOW_HEIGHT = 570;
    private final int WINDOW_WIDTH = 700;
    
    //creating resource pane instance variables
    private JFrame resourceFrame;
    private JMenuBar resourceMenuBar; 
    private JMenuItem saveResources;
    private JMenu saveMenu;
    private JTextArea resourceTextArea;
    private JScrollPane resourceScroll; 
    

    public BrainstormBuddyGUI(){
    	//everything for resource pane
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
//    	encycLinks = new JRadioButton("Just Links");
//    	snippet = new JRadioButton("Snippets and Links");
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
    	links = new JCheckBox("Websites");
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
    	encycSettingPanel.setLayout(tester);
    	newsOptionsPanel.setLayout(tester);
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
    	checkBoxPanel.add(links);
    	
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
    	links.addActionListener(this);
    	
    	
    	open.addActionListener(ofl);
    	save.addActionListener(sfl);
    }
   
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//                createAndShowGUI();
            	BrainstormBuddyGUI bb = new BrainstormBuddyGUI();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		createResources.setBorder(BorderFactory.createLoweredBevelBorder());
		//connect to resource parser
	}

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

	@Override
	public void actionPerformed(ActionEvent which) {
		//open, save menu items dealt with separately
		//encyclopedia option
		 if (which.getSource().equals(encyc)){
			 if(encyc.isSelected()){
				bsbc.setEncyclopedia(true);
//				 System.out.println("button is selected");				 
			 } else {
				 bsbc.setEncyclopedia(false);
				 //set some encyclopedia master boolean false
				//encycOptions booleans are irrelevant? 
//				 System.out.println("button is not selected");
				
			 } 
		 }
		 if (which.getSource().equals(encycOptions)){
			 int index = encycOptions.getSelectedIndex();
			 switch(index){
			 	case(1):{
			 		//set some boolean true, others false
			 		bsbc.setPreview(true);
			 		System.out.println("Snippets");
			 		break;
			 	}
			 	case(2):{
			 		//set some boolean true, others false
			 		bsbc.setPreview(false);
			 		System.out.println("Just Links");
			 		break;
			 	}
			 }
		 }
		 if (which.getSource().equals(dict)){
			 if (dict.isSelected()){
				 //set some boolean true
				 bsbc.setDictionary(true);
				 System.out.println("dictionary button");
			 }
			 else{
				 //set some boolean false
				 bsbc.setDictionary(false);
			 }
			 
		 }
		 
		 if (which.getSource().equals(links)){
			 if (links.isSelected()){
				 //set some boolean true
				 System.out.println("websites button");
			 }
			 else{
				 //set some boolean false
			 }
			 
		 }
		 
		 if (which.getSource().equals(news)){
			 if(news.isSelected()){
				bsbc.setNewsSources(true);
			 } else {
				 bsbc.setNewsSources(false);
				 //set some encyclopedia master boolean false
				//encycOptions booleans are irrelevant? 
				
			 } 
		 }
		 if (which.getSource().equals(newsSourceOptions)){
			 int index = newsSourceOptions.getSelectedIndex();
			 switch(index){
			 	case(0):{
			 		//set some boolean true, others false
			 		bsbc.setNewYorktimes(true);
			 		bsbc.setJstor(false);
			 		System.out.println("New York Times");
			 		break;
			 	}
			 	case(1):{
			 		//set some boolean true, others false
			 		bsbc.setJstor(true);
			 		bsbc.setNewYorktimes(false);
			 		System.out.println("JStor");
			 		break;
			 	}
			 }
		 }
//		 if (which.getSource().equals(newsSourceOptions)){
//				if(news.isSelected()){
//					//can get rid of, put it all in if which == news and news.isSelected()
//					int index = newsSourceOptions.getSelectedIndex();
//					switch(index){
//				 		case(0):{
//				 			//set some boolean true, others false
//				 			System.out.println("BBC selected");
//				 			break;
//				 		}
//				 		case(1):{
//				 			//set some boolean true, others false
//				 			System.out.println("Google Scholar selected");
//				 			break;
//				 		}
//				 		case(2):{
//				 			//set some boolean true, others false
//				 			System.out.println("CNN selected");
//				 			break;
//				 		}
//				 	
//					}
//				}
//			 }
		
	}
	
	class OpenFileListener implements ActionListener {

		public void actionPerformed(ActionEvent which) {
			// TODO Auto-generated method stub
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
			if(which.getSource().equals(createResources)){
				resourceTextArea.setText("");
				
				File resultsFile;
				Scanner resultsIn = null;
				File tempFile = generateTempFileFromTextArea(textArea.getText());
//				System.out.println(textArea.getText());
				bsbc.gatherResources(tempFile.getName());
				resourceFrame.setVisible(true);
				try{
					resultsFile = new File ("results.txt");
					resultsIn = new Scanner(resultsFile);
					while (resultsIn.hasNext())
					{	
						// read an entire line
						String line = resultsIn.nextLine();
						System.out.println(line);
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
	
	public File generateTempFileFromTextArea(String textAString){
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
	
	public void printFile (File f){
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