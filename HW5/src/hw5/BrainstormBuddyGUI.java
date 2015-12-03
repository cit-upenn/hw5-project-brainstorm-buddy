package hw5;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/* TopLevelDemo.java requires no other files. */
public class BrainstormBuddyGUI implements ActionListener, MouseListener{
	//Declare elements
	private JFrame frame;
	private JFileChooser fileChooser;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem open,save;
	
	private JTextArea textArea;
	private JPanel checkBoxPanel, encycSettingPanel, newsOptionsPanel;;//Encyclopedia, Dictionary, Websites 
	private JCheckBox encyc, news, dict, links;
	private JComboBox<String> newsSourceOptions;
	private JComboBox<String> encycOptions;
	
	private String [] newsSources = {"","BBC", "Google Scholar", "CNN"};
	private String [] encycSettings = {"","Snippets and Links", "Just Links"};
	private JButton createResources;
	private GridLayout tester = new GridLayout(3,2);
    private final int WINDOW_HEIGHT = 550;
    private final int WINDOW_WIDTH = 700;
    
    

    public BrainstormBuddyGUI(){
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
    	frame.add(textArea);
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
    	
    	
    	
    	//add listeners
    	createResources.addMouseListener(this);
    	newsSourceOptions.addActionListener(this);
    	encyc.addActionListener(this);
    	encycOptions.addActionListener(this);
    	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		createResources.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		createResources.setBorder(BorderFactory.createRaisedBevelBorder());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent which) {
		//open, save menu items dealt with separately
		//encyclopedia option
		 if (which.getSource().equals(encyc)){
			 if(encyc.isSelected()){
				//set some encyclopedia master boolean true
				 System.out.println("button is selected");
				 int index = encycOptions.getSelectedIndex();
				 switch(index){
				 	case(1):{
				 		//set some boolean true, others false
				 		System.out.println("Snippets");
				 		break;
				 	}
				 	case(2):{
				 		//set some boolean true, others false
				 		System.out.println("Snippets");
				 		break;
				 	}
				 }
				 
			 } else {
				//set some encyclopedia master boolean false
				 System.out.println("button is not selected");
				
			 } 
		 }
		 
		 
		 if (which.getSource().equals(newsSourceOptions)){
				if(news.isSelected()){
					//can get rid of, put it all in if which == news and news.isSelected()
					int index = newsSourceOptions.getSelectedIndex();
					switch(index){
				 		case(1):{
				 			//set some boolean true, others false
				 			System.out.println("BBC selected");
				 			break;
				 		}
				 		case(2):{
				 			//set some boolean true, others false
				 			System.out.println("Google Scholar selected");
				 			break;
				 		}
				 		case(3):{
				 			//set some boolean true, others false
				 			System.out.println("CNN selected");
				 			break;
				 		}
				 	
					}
				}
			 }
		
	}
}