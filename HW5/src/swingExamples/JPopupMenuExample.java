package swingExamples;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JCheckBoxMenuItem;

public class JPopupMenuExample{
	public static void main(String[] args) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new JCheckBoxMenuItem("Other Court"));
		menu.add(new JCheckBoxMenuItem("Tribunal Court"));
		menu.add(new JCheckBoxMenuItem("High Court"));
		menu.add(new JCheckBoxMenuItem("Supreme Court"));

		JButton button = new JButton();
		
		button.setAction(new AbstractAction("Court") {
	    public void actionPerformed(ActionEvent e) {
	        menu.show(button, 10, button.getHeight());//invoker, x offset, y offset (from upper Left)
	    }
			});

		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.getContentPane().add(button);
//		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}