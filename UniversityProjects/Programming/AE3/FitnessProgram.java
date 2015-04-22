package AE3;

import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	//Class constant. Represents the maximum number of classes.
	private static final int MAX_NUM = 7;	

	//List of FitnessClass objects.
	private FitnessClass[] listFitClasses;	

	//No mutator class for this instance variable. Choice I made because we do not want this variable to change.
	private int actualObjects;	

	/**
	 * Default constructor. Initializes the array of FitnessClass object. 
	 * Later in the program this array will be correctly populated and in order of starting time. 
	 */
	public FitnessProgram(){
		listFitClasses = new FitnessClass[MAX_NUM];	//Instantiates an object with an array that contains 7 null objects.
		actualObjects = 0;	//Because the object has 7 null objects, the starting number of actual objects is set to 0.
	}

	/**
	 * Sets the Fitness classes to the list by a time order. Entry X in the array should be the class that starts at time 9+X.
	 * @param containerCl	The line of text from ClassesIn.txt that contain information about the Fitness class.
	 */
	public void setFitnessClasses(String containerCl){

		FitnessClass fitCl = new FitnessClass(containerCl);	//Creates a new FitnessClass object.

		listFitClasses[fitCl.getStartTime() - 9] = fitCl;	//Puts the newly created object to its position based on its starting time.
		actualObjects++;	//As soon as we input an object to the array, we want to increment the total number of actual objects.

	}

	/**
	 * Populates the attendance lists for a given Fitness Class in the array.
	 * @param containerAtt String that represents a single line of AttendancesIn.txt
	 */
	public void setFitnessClassAttendances(String containerAtt){

		int[] attendanceRates = new int[5];	//Creates a new integer array that holds 5 integers.

		Scanner scan = new Scanner(containerAtt);	//Uses Scanner to extract Strings from the line of text.	
		String tempClassID = scan.next();

		int counter = 0;	//Local variable that is used as an index of the attendanceRates array.

		while(scan.hasNext()){

			//Stores the integer to the array with index counter.
			attendanceRates[counter] = Integer.parseInt(scan.next());	
			counter++;	//Increments the counter.
		}

		//Calls the method getFitnessClassID that returns a FitnessClass object. 
		//We store this object to a newly created FitnessClass object.
		//Those 2 objects point to the same memory location so if we change the variables of the second, then the variables in the first object are changed as well.
		FitnessClass tempFitClass = getFitnessClassID(tempClassID);

		//First, checks if the newly created object is null. If it is not null, we store the array of attendances to this object.
		if(tempFitClass != null){

			tempFitClass.setAttendanceRecords(attendanceRates);
		}

		scan.close();
	}

	/**
	 * Returns the FitnessClass object with a class ID.
	 * @param classID The ID of the class.
	 * @return The FitnessClass object.
	 */
	public FitnessClass getFitnessClassID(String classID){

		for(FitnessClass fit : listFitClasses){
			if(fit != null){	//If null then continue the loop

				if(fit.getClassID().equals(classID)){
					//If it finds an object that has the same class id, then it returns that object.
					return fit;
				}
			}
		}
		return null;	//Otherwise return null;
	}

	/**
	 * Returns the FitnessClass object at index X.
	 * @param index	The index X.
	 * @return	The FitnessClass object.
	 */
	public FitnessClass getFitnessClassIndex(int index){

		return listFitClasses[index];
	}

	/**
	 * Returns the number of non-null objects in the list.
	 * @return
	 */
	public int getActualObjects(){

		return actualObjects;
	}

	/**
	 * Returns the FitnessClass object at a particular time.
	 * @param startTime The particular time
	 * @return The FitnessClass object. If it doesn't find any existing class with that starting time, it returns null
	 */
	public FitnessClass getFitnessClassTime(int startTime){

		//Loops through all the FitnessClass objects inside the listFitClasses array.
		for(FitnessClass fit : listFitClasses){

			if(fit != null){

				if(fit.getStartTime() == startTime){
					//If it finds an object that has the same starting time, then it returns that object.
					return fit;
				}
			}
		}
		return null;	//Otherwise return null;
	}

	/**
	 * Inserts a FitnessClass object to the list.
	 * @param fitClassObj The FitnessClass object
	 */
	public void insertFitClass(FitnessClass fitClassObj){

		//Array of 5 integers that each one has the value 0. Will be used for the attendances of the newly created FitnessClass.
		int[] newAttendances = new int[5];

		for(int i = 0; i < MAX_NUM ; i++){

			if(listFitClasses[i] == null){	

				//The program stores the new Fitness Class to the first position that it finds with a null object.
				listFitClasses[i] = fitClassObj;

				//Sets its starting time and its attendance figures.
				listFitClasses[i].setStartTime(9+i);
				listFitClasses[i].setAttendanceRecords(newAttendances);

				//Increments the number of actual objects.
				actualObjects++;
				return;
			}
		}
	}

	/**
	 * Deletes a FitnessClass object to the list.
	 * @param fitClassObj The FitnessClass object
	 */
	public void deleteFitClass(FitnessClass fitClassObj){

		//Loops all the FitnessClasses in the list.
		for(int i=0; i < MAX_NUM; i++){
			if(listFitClasses[i] != null){
				//If not null, checks whether that object has the same ID as the input object. 
				if(listFitClasses[i].getClassID().equals(fitClassObj.getClassID())){

					//If they have the same ID, the class in our instance list becomes null -> Deleted.
					listFitClasses[i] = null;

					//Decrement the number of actual objects.
					actualObjects--;
					return;	
				}
			}
		}
	}

	/**
	 * Returns a list sorted in non-increasing order on the average attendance at each class.
	 * @return
	 */
	public FitnessClass[] avgClassAtt(){

		//Creates a new list that contains as many FitnessClass objects as the number of actual objects.
		FitnessClass[] tempFitCl = new FitnessClass[actualObjects];
		int counter = 0;

		//Loops every FitnessClass object.
		for(FitnessClass fit : listFitClasses){
			if(fit != null){

				//If it is not a null object, add it to the array of FitnessClass objects called tempFitCl in the position 'counter'.
				tempFitCl[counter] = fit;	
				counter++;
			}
		}

		//Sorts it in a non-ascending order because the implementation of compareTo in the FitnessClass is made for descending order.
		Arrays.sort(tempFitCl);	

		return tempFitCl;
	}

	/**
	 * Returns the overall average attendance.
	 * @return
	 */
	public double overallAvgAtt(){

		double totalAvgAttend = 0.0;

		for(FitnessClass fit : listFitClasses){

			if(fit != null){

				//Loops each object and adds its averageAttendance to totalAvgAttend.
				totalAvgAttend += fit.averageAttendance();	
			}
		}

		//Returns the sum of average attendances divided by the number of actual objects.
		return totalAvgAttend / actualObjects;
	}

	/**
	 * Produces a String that contains the ID, the name, the starting time of the class and the name of the tutor of the class for all the FitnessClasses.
	 * @return
	 */
	public String totalReport(){

		//If the list is empty return null String.
		if(actualObjects ==0 ){

			return null;
		}

		StringBuilder sb = new StringBuilder();

		//The format of each line.
		String format = "%s %s %s %d\n";	

		for(FitnessClass fitfit : listFitClasses){

			if(fitfit != null){

				sb.append(String.format(format, fitfit.getClassID(), fitfit.getClassName(), fitfit.getTutorName(), fitfit.getStartTime()));
			}
		}

		//Returns the String representing the data in this StringBuilder.
		return sb.toString();
	}
}
