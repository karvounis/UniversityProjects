package AE3;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {

	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "Programming/AE3/ClassesIn.txt";
	private final String classesOutFile = "Programming/AE3/ClassesOut.txt";
	private final String attendancesFile = "Programming/AE3/AttendancesIn.txt";

	private FitnessProgram fitProgGui;

	/**The first line of the timetable.*/
	private final String FIRST_LINE = String.format("%12s %12s %12s %12s %12s %12s %12s\n", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");

	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(900, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();

		//Inputs the timetable data.
		initLadiesDay();

		//Inputs the attendance data.
		initAttendances();

		//Updates the display
		updateDisplay();
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {

		FileReader fin;
		String temp = null;
		Scanner sc = null;

		//Instantiates a FitnessProgram object.
		fitProgGui = new FitnessProgram();	

		try {
			fin = new FileReader(classesInFile);
			sc = new Scanner(fin);

			while(sc.hasNext()){	//Reads every line of the ClassesIn.txt
				temp = sc.nextLine();
				//Sets the FitnessClasses to the list ordered by start time.
				fitProgGui.setFitnessClasses(temp);	
			}
			sc.close();
			fin.close();

		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {

		FileReader fin;
		Scanner sc = null;
		String temp = null;

		try {
			fin = new FileReader(attendancesFile);
			sc = new Scanner(fin);

			while(sc.hasNext()){
				temp = sc.nextLine();	//Reads every line of the AttendancesIn.txt

				//Calls this method given a single line of text as input.
				fitProgGui.setFitnessClassAttendances(temp); 			
			}
			sc.close();
			fin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {

		display.setText(null);	//Clears all the previous text so that it can update the display.

		//Uses a StrinBuffer to create the String that will be displayed
		StringBuffer sbf = new StringBuffer("");

		//Displays the hour slots using the instance constant String.
		sbf.append(FIRST_LINE);

		//Loops every FitnessClass in the FitnessProgram instance class fitProgGui. 
		for(int startingTime = 9; startingTime < 16; startingTime++){

			if(fitProgGui.getFitnessClassTime(startingTime) == null){	//If there is no class at that time, display 'Available'.

				sbf.append(String.format("%12s", "Available"));
			}else{
				//Display the class name using a format for String. The maximum width of the String is 12.
				sbf.append(String.format("%12s", fitProgGui.getFitnessClassTime(startingTime).getClassName()));
			}
			sbf.append(" ");
		}
		sbf.append("\n");

		for(int startingTime = 9; startingTime < 16; startingTime++){

			if(fitProgGui.getFitnessClassTime(startingTime) == null){	

				sbf.append(String.format("%12s", " ")); //If there is no class at that time, display a whitespace String with a width of 12.
			}else{
				//Displays the name of the tutor of the class.
				sbf.append(String.format("%12s",fitProgGui.getFitnessClassTime(startingTime).getTutorName()));
			}
			sbf.append(" ");
		}
		sbf.append("\n");

		String temp = new String(sbf);

		display.append(temp);
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class. 
	 * If the program meets any conditions that force it to display a warning message, the text fields are not cleared. 
	 * That way, the user can check more easily where he did the mistake.
	 * If the adding of the class is successful, then the text fields are cleared.
	 */
	public void processAdding() {

		//Gets the information from the text fields.
		String addID = idIn.getText();
		String addName = classIn.getText();
		String addTutor = tutorIn.getText();

		if(fitProgGui.getActualObjects() == 7){

			JOptionPane.showMessageDialog(null, "List full!", "Full list", JOptionPane.WARNING_MESSAGE);
			return;

		}else if(!addID.isEmpty() && !addName.isEmpty() && !addTutor.isEmpty()){

			if(fitProgGui.getFitnessClassID(addID) == null){

				String newObjAttr = new String(String.format("%s %s %s %d", addID, addName, addTutor, 0));

				FitnessClass newFitnessClass = new FitnessClass(newObjAttr);		

				fitProgGui.insertFitClass(newFitnessClass);

			}else{

				JOptionPane.showMessageDialog(null, "There is another class with the same ID.", "Class with same ID.", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else{

			JOptionPane.showMessageDialog(null, "There are empty fields.", "Empty fields", JOptionPane.WARNING_MESSAGE);
			return;
		}

		//Clears the text fields.
		idIn.setText(null);	
		classIn.setText(null);
		tutorIn.setText(null);

		updateDisplay();	//Updates the timetable display
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {

		String delID = idIn.getText();

		if(delID.isEmpty()){

			//If the text is empty displays a warning message.
			JOptionPane.showMessageDialog(null, "Empty ID.", "Invalid ID", JOptionPane.WARNING_MESSAGE);

		}else if(fitProgGui.getFitnessClassID(delID) != null){

			//Calls the deleteFitClass method on the fitProgGui object giving as parameter the FitnessClass object with the appropriate ID.
			fitProgGui.deleteFitClass(fitProgGui.getFitnessClassID(delID));

		}else{
			//Prints a warning if the program does not find a FitnessClass object with the appropriate ID.
			JOptionPane.showMessageDialog(null, "No class with that ID!", "Invalid ID", JOptionPane.WARNING_MESSAGE);
		}

		idIn.setText(null);	//Clears the field.
		updateDisplay();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {

		report = new ReportFrame(fitProgGui);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {

		FileWriter fout;

		//String that contains the records of FitnessClass date, in the same format as ClassesIn.txt.
		String totalOutput = fitProgGui.totalReport();

		try {
			//Instantiates a FileWriter object.
			fout = new FileWriter(classesOutFile);	

			//Appends the String to the FileWriter object.
			fout.append(totalOutput);

			fout.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == addButton){

			processAdding();

		}else if(ae.getSource() == deleteButton){

			processDeletion();

		}else if(ae.getSource() == attendanceButton){

			displayReport();

		}else if(ae.getSource() == closeButton){

			processSaveAndClose();
			System.exit(0);
		}
	}
}
