package AdvancedProgramming;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.List;
import javax.swing.*;

public class highLowGame extends JFrame implements ActionListener {

	private JTextField text;
	private JButton submit;
	private JTextField countdown, response;
	private int magicNumber, userGuess;
	private Game pf;
	/**Countdown timer*/
	private static int Timer = 30;

	public highLowGame(){
		super("Welcome to higher/lower game!"); 

		Random rand = new Random();
		magicNumber = rand.nextInt(51);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(500, 300);

		text = new JTextField(10);
		text.setText("" + 0);
		text.setEditable(true);
		getContentPane().add(text);

		submit = makeButton("Submit");

		countdown = new JTextField(10);
		countdown.setText("" + Timer);
		countdown.setEditable(false);
		getContentPane().add(countdown);

		response = new JTextField(10);
		response.setEditable(false);
		getContentPane().add(response);

		//pack();
		setVisible(true);
		(pf = new Game()).execute();
	}

	private JButton makeButton(String caption) {
		JButton b = new JButton(caption);
		b.setActionCommand(caption);
		b.addActionListener(this);
		getContentPane().add(b);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand() == "Submit"){			

			userGuess = Integer.parseInt(text.getText());
			if( userGuess < 0 || userGuess > 50){
				JOptionPane.showMessageDialog(null, "Invalid integer", "Invalid integer", JOptionPane.ERROR_MESSAGE);
				text.setText("0");
				return;
			}
			
			String state = checkHigher();
			
			if(!state.equals("Same")){
				response.setText(state);
			}else{				
				System.out.println( "Bravo! " + userGuess + " is the correct integer.\nFound it in " + (30 - Timer) + " seconds!");
				System.exit(0);
			}
		}
	}

	/**
	 * Checks if the user guess is higher, equal or lower than the random number.
	 * @return
	 */
	private String checkHigher(){
		if(userGuess > magicNumber){
			return "Higher";
		}else if(userGuess < magicNumber){
			return "Lower";
		}else{
			return "Same";
		}
	}

	
	private class Game extends SwingWorker<Void, Integer>{

		@Override
		protected Void doInBackground() {
			try {
				while(!isCancelled()) {					
					Thread.sleep(1000);
					Timer--;					
					//ElapsedTime++;					
					publish(Timer);
					if(Timer == 0){
						System.out.println("Unfortunately you ran out of time!");
						System.exit(1);
					}
				}
			}catch(InterruptedException e) {}
			return null;
		}
		
		protected void process(List<Integer> visits) {
			Integer last = visits.get(visits.size()-1);
			//Prints the countdown timer
			countdown.setText("" + last);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new highLowGame();
			}
		});
	}
}