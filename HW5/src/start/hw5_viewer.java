package start;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.*;
public class hw5_viewer {

	public static void main(String[] args) {
		JFrame frame = new hw5practice();
//		Container contentPane = frame.getContentPane();
//		contentPane.setLayout(new BorderLayout());
		frame.setTitle("Brainstorm Buddy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
