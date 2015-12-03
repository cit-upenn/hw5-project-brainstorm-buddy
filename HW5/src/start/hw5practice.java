package start;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;




public class hw5practice extends JFrame {

	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 450;
	
	private static final int AREA_ROWS = 10;
	private static final int AREA_COLUMNS = 30;
	
	private static final double DEFAULT_RATE = 5;
	private static final double INITIAL_BALANCE = 1000;
	
	private JLabel rateLabel;
	private JTextField rateField;
	private JButton button;
	private JRadioButton radButton;
	private JTextArea resultArea;
	private double balance;
	private boolean chosen = false;
	private JComponent southButtons;
	private Container southButtonsContainer;
	public hw5practice(){
		balance = INITIAL_BALANCE;
		resultArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		resultArea.setText(balance + "\n");
		resultArea.setEditable(true);
		southButtonsContainer = new Container();
		
		
		createTextField();
		createButtons();
		createPanel();
		
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	

	private void createTextField(){
		rateLabel = new JLabel("Interest Rate: ");
		
		final int FIELD_WIDTH = 10; 
		rateField = new JTextField(FIELD_WIDTH);
		rateField.setText(""+ DEFAULT_RATE);
		
	}
	class AddInterestListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			String userInput = rateField.getText();
			String userInput2 = resultArea.getText();
			resultArea.append(userInput + "\n");
			
		}
		
	}
	class PrintStuffListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			chosen = !chosen; 
			if(radButton.isSelected()){
			resultArea.append("You chose the thing!!" + "\n");
				if(chosen){
					resultArea.append("button is pressed\n");
					
				}
			
			}
			
		}
		
	}
	
	private void createButtons(){
		button = new JButton("Add Interest");
		ActionListener listener = new AddInterestListener();
		button.addActionListener(listener);
		radButton = new JRadioButton("Choose here?");
		ActionListener psl = new PrintStuffListener();
		radButton.addActionListener(psl);
		
	}
	
	private void createPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(rateField, BorderLayout.SOUTH);
//		panel.add(rateLabel, BorderLayout.SOUTH);
//		panel.add(button, BorderLayout.EAST);
//		panel.add(radButton, BorderLayout.EAST);
		panel.add(southButtonsContainer, BorderLayout.SOUTH);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		add(panel);
	}
	
	

}
