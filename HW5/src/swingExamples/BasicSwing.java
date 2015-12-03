package swingExamples;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class BasicSwing implements ActionListener{
	
	JFrame frame;
	JLabel label;
	JTextField textField;
	JButton button;
	int count = 0;
	
	public BasicSwing() {
		frame = new JFrame("This is SOOOO cool!!!!");
		label = new JLabel("Howdy mate!");
		textField = new JTextField(20);
		button = new JButton("I'm an awesome button");
		
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		button.addActionListener(this);
		
		frame.add(label);
		frame.add(textField);
		frame.add(button);
		
		//frame.pack();
		//automatically size the frame
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		count++;
		label.setText("Someone pressed the awesome button " + count + " times");
		
	}

}
