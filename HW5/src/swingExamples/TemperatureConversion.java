package swingExamples;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class TemperatureConversion implements ActionListener {

	private JFrame frame;
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JTextField fahrenheit;
	private JTextField celsius;
	private JButton fToC;
	private JButton cToF;

	public TemperatureConversion() {
		frame = new JFrame("I can convert temperaturez!");
		firstLabel = new JLabel("The temperature in Fahrenheit is:");
		secondLabel = new JLabel("The temperature in Celsius is:");
		fahrenheit = new JTextField(5);
		celsius = new JTextField(5);
		fToC = new JButton("Hit me for F->C!");
		cToF = new JButton("Hit me for C->F!");

		frame.setLayout(new GridLayout(3, 2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(firstLabel);
		frame.add(fahrenheit);
		frame.add(secondLabel);
		frame.add(celsius);
		frame.add(fToC);
		frame.add(cToF);

		fToC.addActionListener(this);
		cToF.addActionListener(this);

		frame.pack();
//		frame.setSize(800, 600);
		frame.setVisible(true);

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource().equals(fToC)) {

			String fahrenheitTemperature = fahrenheit.getText();
			double fah = Double.parseDouble(fahrenheitTemperature);

			double cel = (fah - 32) * 5.0/9.0;

			celsius.setText(String.valueOf(cel));
		}

		if (arg0.getSource().equals(cToF)) {

			String celsiusTemperature = celsius.getText();
			double cel = Double.parseDouble(celsiusTemperature);

			double fah = (cel * 9.0/5.0) + 32;

			fahrenheit.setText(String.valueOf(fah));
		}

	}

}
