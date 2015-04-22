package AE3;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {

	private FitnessProgram fitProg;
	private JTextArea displayArea;
	private FitnessClass[] tempFitCl;

	private char[] chars = new char[120];

	//Constructor for the window. Displays the attendance information to it.
	public ReportFrame(FitnessProgram fitProg){

		setTitle("Attendance Report");
		setSize(1150, 250);

		//Fills the character array with 120 '='. Use this array for display reasons only.
		Arrays.fill(chars, '=');	

		this.fitProg = fitProg;
		displayArea = new JTextArea();

		displayArea.setFont(new Font("Courier", Font.PLAIN, 14));
		add(displayArea, BorderLayout.CENTER);

		displayArea.append(displayReport());
		setVisible(true);

	}

	/**
	 * Builds the report for display on the JTextArea.
	 * @return
	 */
	public String displayReport(){

		displayArea.setText(null);

		double totalAttendances = 0.0;

		StringBuffer sb = new StringBuffer("");

		//First line of the window.
		sb.append(String.format("%10s %25s %25s %30s %22s\n", "Id", "Class", "Tutor", "Attendances", "Average Attendance"));	

		sb.append(chars);	//The 120 '='.
		sb.append("\n");

		//TempFitCl is a list of classes in a descending order of average attendances.
		tempFitCl = fitProg.avgClassAtt();	

		for(FitnessClass fitfit : tempFitCl){

			if(fitfit != null){	//Checks if an object is null

				sb.append(fitfit.attendanceReport());

				totalAttendances += fitfit.averageAttendance();

			}
		}

		//Prints the last line that displays the Overall average. It is the sum of average attendances divided by the number of actual objects of the FitnessProgram object.
		sb.append(String.format("\n%35sOverall average: %10.2f"," ", totalAttendances / fitProg.getActualObjects()));

		return sb.toString();
	}

	public void setFitnessProgram(FitnessProgram fitProg){

		this.fitProg = fitProg;
	}
}
