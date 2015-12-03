package swingExamples;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MouseMotionListenerExample implements MouseMotionListener, ActionListener, MouseListener {
	
	private JFrame frame;
	private JTextField textField;
	private JButton button;
	private int blue;
	
	public MouseMotionListenerExample() {
		frame = new JFrame("Colors are cooooool!");
		textField = new JTextField(3);
		button = new JButton("Click me to change color");
		blue = 0;
		
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(textField);
		frame.add(button);
		
		frame.addMouseMotionListener(this);
		button.addActionListener(this);
		frame.addMouseListener(this);
		button.addMouseListener(this);
		
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		blue = Integer.parseInt(textField.getText());
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Color c = new Color(arg0.getX()%255, arg0.getY()%255, blue);
		frame.getContentPane().setBackground(c);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Color c = frame.getBackground();
		frame.getContentPane().setBackground(c.brighter());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
//		if (arg0.getSource() instanceof JButton) {
//			System.out.println("Button");
//			frame.remove((JComponent)arg0.getSource());
//		} else {
//			System.out.println("Else");
//		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		frame.getContentPane().setBackground(frame.getBackground().darker());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	public static void main(String[] args) {
		MouseMotionListenerExample mmle = new MouseMotionListenerExample();
	}

}
