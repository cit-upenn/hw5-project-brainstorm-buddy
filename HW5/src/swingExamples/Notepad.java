package swingExamples;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

public class Notepad {
	// 1.Declare Controls
	JFrame frame;
	JFileChooser fileChooser;
	JMenuBar menuBar;
	JMenu file;
	JMenuItem open,save,exit;
	JTextArea textArea;

	Notepad() {
		// 2.Initialize Controls
		frame=new JFrame("Notepad");
		menuBar=new JMenuBar();
		file=new JMenu("File");
		open=new JMenuItem("Open");
		save=new JMenuItem("Save");
		exit=new JMenuItem("Exit");
		fileChooser=new JFileChooser();
		textArea=new JTextArea();

		// 3.Change Layout Manager
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 4.Add Controls
		frame.add(textArea);
		file.add(open);
		file.add(save);
		file.add(exit);
		menuBar.add(file);
		frame.setJMenuBar(menuBar);

		// 5.Register Listeners
		OpenFileListener openListListener = new OpenFileListener();
		SaveFileListener saveListListener = new SaveFileListener();
		ExitFileListener exitListListener = new ExitFileListener();
		open.addActionListener(openListListener);
		save.addActionListener(saveListListener);
		exit.addActionListener(exitListListener);

		// 6.Set Size and Visible
		frame.setSize(600,600);
		frame.setVisible(true);
	}

	class OpenFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
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
	}

	class SaveFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(frame)) {
				// create a null PrintWriter outside the try block
				PrintWriter out = null;
				try {
					// create the File
					File file = fileChooser.getSelectedFile();

					// initialize the PrintWriter
					out = new PrintWriter(file);

					// this is the String I will write to the file
					String output = textArea.getText();

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

	class ExitFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}

	public static void main(String args[]) {
		Notepad x=new Notepad();
	}
}