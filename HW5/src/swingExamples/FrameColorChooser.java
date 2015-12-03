package swingExamples;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;


public class FrameColorChooser implements MouseMotionListener, ActionListener, MouseListener {
	
	private JFrame frame;
	private JButton button;
	private JTextField field;
	private int blue;
	
	public FrameColorChooser() {
		frame = new JFrame("Colors are awesome");
		button = new JButton("Change color");
		field = new JTextField(3);
		blue = 0;
		
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(field);
		frame.add(button);
		
		frame.addMouseMotionListener(this);
		button.addActionListener(this);
		frame.addMouseListener(this);
		
		frame.setSize(800, 600);
		frame.setVisible(true);
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Color c = new Color(arg0.getX()%255, arg0.getY()%255, blue);
		frame.setBackground(c);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		blue = Integer.parseInt(field.getText());
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		frame.setBackground(frame.getBackground().darker());
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		frame.setBackground(frame.getBackground().brighter());
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
