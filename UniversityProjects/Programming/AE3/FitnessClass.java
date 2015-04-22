package AE3;

import java.util.Scanner;

/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {

	// Class variable representing the number of weeks
	private static final int WEEKS = 5;

	//Instance variables
	private int startTime;	//Represents the class ID and the start time.
	private String classID, className, tutorName;	//Represent the ID and the name of the class and the name of the tutor.
	private int[] attendanceRecords;	//Represents the set of attendance records as an array of integers.

	/**Default constructor.*/
	public FitnessClass(){}

	/**
	 * Non-default constructor that takes as input a String. 
	 * This String is a line of text that has to be parsed in order to extract information about the instance variables.
	 * Uses a Scanner object for that purpose.
	 * @param container	A line of text from ClassesIn.txt
	 */
	public FitnessClass(String container){
		Scanner sc = new Scanner(container);

		classID = sc.next();

		className = sc.next();

		tutorName = sc.next();

		startTime = Integer.parseInt(sc.next());

		sc.close();

	}

	/**Sets the classID.*/
	public void setClassID(String classID){
		this.classID = classID;
	}

	/**Sets the starting time.*/
	public void setStartTime(int startTime){
		this.startTime = startTime;
	}

	/**Sets the name of the class.*/
	public void setClassName(String className){
		this.className = className;
	}

	/**Sets the tutor name.*/
	public void setTutorName(String tutorName){
		this.tutorName = tutorName;
	}

	/**Sets the list of attendances.*/
	public void setAttendanceRecords(int[] attendanceRecords){
		this.attendanceRecords = attendanceRecords;
	}

	/**Accessor method that returns the classID.*/
	public String getClassID(){
		return classID;
	}

	/**Accessor method that returns the starting time of the class.*/
	public int getStartTime(){
		return startTime;
	}

	/**Accessor method that returns the name of the class.*/
	public String getClassName(){
		return className;
	}

	/**Accessor method that returns the name of the tutor.*/
	public String getTutorName(){
		return tutorName;
	}

	/**Accessor method that returns the list of attendances for this class.*/
	public int[] getAttendanceRecords(){
		return attendanceRecords;
	}

	/**
	 * Compares two FitnessClass objects based on their average attendance.
	 * For two objects, their average attendance is calculated by calling the averageAttendance() method
	 * and then these average attendances are compared.
	 * Implemented in a way that when sort is called, the FitnessClass objects are sorted in descending order.
	 */
	public int compareTo(FitnessClass other) {

		double thisAvg = this.averageAttendance();
		double otherAvg = other.averageAttendance();

		if(thisAvg < otherAvg){

			return 1;

		}else if(thisAvg == otherAvg){

			return 0;

		}else{

			return -1;	

		}
	}

	/**
	 * Returns a String that contains the ID, the name of the class, the tutor of the class, the attendances and the average Attendance of the class.
	 * The String is formatted appropriately for the attendance report.
	 * @return
	 */
	public String attendanceReport(){

		StringBuilder strb = new StringBuilder("");

		//Displays the ID and the name of the class and the name of the tutor of the class.
		strb.append(String.format("%10s %25s %25s ", getClassID(), getClassName(), getTutorName()));

		for(int attendance : getAttendanceRecords()){
			//Loops the attendances for each week
			strb.append(String.format("%6d", attendance));
		}

		strb.append(String.format("%22.2f\n", averageAttendance()));

		return strb.toString();
	}

	/**
	 * Calculates the average attendance of a Fitness Class.
	 * First, calculates the sum of attendances and then divides it by the class constant WEEKS.
	 * @return The average attendance of a Fitness Class.
	 */
	public double averageAttendance(){

		double total = 0.0;

		for(int i : attendanceRecords){
			total += i;
		}

		return total / WEEKS;
	}

}
