import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Contains two ArrayLists of data. One ArrayList is all the photographs that are objects type Photo and the second one is all the tags that are objects type Tag.
 * Contains methods that have access to those two ArrayLists and can either read or modify them.
 * @author Evangelos Karvounis
 *
 */
public class DataPhoto {
	/**Filename of the output file*/
	private static final String OUTPUT_FILE = "Co-occurenceMatrix.csv";
	/**The number of photos that exists in our database.*/
	private static final int NUMBER_OF_PHOTOS = 10000;
	/**ArrayList of Photos.*/
	private ArrayList<Photo> groupPhotos;
	/**ArrayList of Tags.*/
	private ArrayList<Tag> groupTags;
	/**Co occurence matrix.*/
	private int[][] matrix;
	/**IDF matrix.*/
	private double[][] IDFmatrix;

	public DataPhoto(){
		groupPhotos = new ArrayList<Photo>();
		groupTags = new ArrayList<Tag>();
	}

	/**Adds a Photo the the List of Photos.*/
	public void addPhoto(Photo photo){
		groupPhotos.add(photo);
	}

	/**Adds a Tag the the List of Tags.*/
	public void addTag(Tag tag){
		groupTags.add(tag);
	}

	/**
	 * Creates a new array that stores the IDF values of the tags.
	 * For every tag, this method calculate the IDF = log(I / I(x)), 
	 * where I is the total number of photos(in our case 10000) and I(x) is the number of photos tagged with this tag.
	 * For every cell in the co-occurence matrix, multiplies its value with the tag's - row's IDF.
	 * @return	A double[][] containing all the IDF values of every tag.
	 */
	public double[][] createIDFMatrix(){
		IDFmatrix = new double[groupTags.size()][groupTags.size()];

		//Deep copy of the co occurence matrix
		for(int rows = 0; rows < matrix[0].length; rows++){
			for(int cols = rows; cols < matrix.length; cols++){
				IDFmatrix[rows][cols] = matrix[rows][cols];
				IDFmatrix[cols][rows] = matrix[cols][rows];
			}
		}
		//Multiply every cell with the IDF of the tag's row.
		for(int rows = 0; rows < matrix[0].length; rows++){
			double tempNumPhotos = groupTags.get(rows).getNumPhotos();
			double IDF = Math.log(NUMBER_OF_PHOTOS / tempNumPhotos);

			for(int cols = 0; cols < matrix.length; cols++){
				IDFmatrix[rows][cols] *= IDF;
			}
		}
		//outputCoOccurenceMatrixIDF(IDFmatrix);
		return IDFmatrix;
	}

	/**
	 * Creates and calculates the co-occurrence matrix.
	 * @return int[][] - Co occurence matrix
	 */
	public int[][] createOccurenceMatrix(){
		//Sorts the collections of Photos and Tags
		Collections.sort(groupPhotos);
		Collections.sort(groupTags);

		matrix = new int[groupTags.size()][groupTags.size()];

		//For every Photo object that he have in the groupPhoto ArrayList, calculate every pair of tags. 
		for(int index = 0; index < groupPhotos.size(); index++){
			ArrayList<Tag> tempTagsList = groupPhotos.get(index).getTags();

			int tagListSize = tempTagsList.size();

			//Algorithm that calculates the number of times a pair of tags have co-occured in a photograph.
			//Increments the value of the cells that these two tags can be found in the co-occurence matrix.
			for(int i = 0; i < tagListSize; i++){
				Tag tempTag = tempTagsList.get(i);
				int rowIndex = groupTags.indexOf(tempTag);

				for(int j = i; j < tagListSize; j++){					
					if(i == j){
						continue;
					}
					Tag tempTag2 = tempTagsList.get(j);
					int colIndex = groupTags.indexOf(tempTag2);

					matrix[rowIndex][colIndex]++;
					matrix[colIndex][rowIndex]++;
				}
			}
		}
		outputCoOccurenceMatrix();
		return matrix;
	}

	/**
	 * Computes and prints the top 5 tags after the application of IDF to all the cells in the co-occurence matrix.
	 * Given a Tag title and a 2D array, computes the top 5 tags that co-occur with the Tag with this specific title.
	 * The algorithm searches the column of the IDF 2D matrix that corresponds to the tag that we passed as input.
	 * For example: if we search for the 5 top tags for <i>'sky'</i>, it goes to the column of the IDF matrix 
	 * and searches the top 5 values in that column.
	 * @param title tag title
	 * @param IDFmatrix double[][]
	 */
	public void computeTopTagsIDF(String title, double[][] IDFmatrix){
		//Index of the column.
		int inputIndex = -1;
		for(Tag tag : groupTags){
			if(tag.getTagTitle().equals(title)){
				inputIndex = groupTags.indexOf(tag);
				break;
			}
		}
		int count = 0;
		//Deep copies the co-occurence matrix so that we can make changes to it.
		double[][] tempMatrix = deepCopy2Darray(IDFmatrix);
		//The column that holds the values of the tags that co-occur with the tag that is passed as input. 
		double[] temp = new double[100];
		for(int row = 0; row < IDFmatrix.length; row++){
			temp[row] = tempMatrix[row][inputIndex]; 
		}

		//Prints the top 5 tags that co-occur with the tag with the input title.
		while(count < 5){
			int tempIndex = findIndexwithMaxValue(temp);
			count++;
			System.out.println(groupTags.get(tempIndex).getTagTitle() + " " + temp[tempIndex]);
			temp[tempIndex] = 0;
		}
	}

	/**
	 * Given a Tag title and a 2D array, computes and prints the top 5 tags that co-occur with the Tag with this specific title.
	 * @param title tag title
	 * @param matrix A 2D array
	 */
	public void computeTopTags(String title, int[][] matrix){
		int inputIndex = -1;
		for(Tag tag : groupTags){
			if(tag.getTagTitle().equals(title)){
				inputIndex = groupTags.indexOf(tag);
				break;
			}
		}		

		int count = 0;
		//Deep copies the co-occurence matrix so that we can make changes to it.
		int[][] tempMatrix = deepCopy2Darray(matrix);

		int[] temp = tempMatrix[inputIndex];

		//Outputs the top 5 tags that co-occur with the tag with the input title.
		while(count < 5){
			int tempIndex = findIndexwithMaxValue(temp);
			count++;
			System.out.println(groupTags.get(tempIndex).getTagTitle() + " " + temp[tempIndex]);
			temp[tempIndex] = 0;
		}
	}

	/**
	 * Given a 2dimensional integer array, returns a deep copy of this array so that we can not change the original array during the calculations.
	 * @param matrix A 2D array.
	 * @return
	 */
	private int[][] deepCopy2Darray(int [][] matrix){
		int[][] tempMatrix = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < tempMatrix.length; i++)
			tempMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length);

		return tempMatrix;
	}

	/**
	 * Given a 2dimensional double array, returns a deep copy of this array so that we can not change the original array during the calculations.
	 * @param matrix A 2D array.
	 * @return
	 */
	private double[][] deepCopy2Darray(double [][] matrix){
		double[][] tempMatrix = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < tempMatrix.length; i++)
			tempMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length);

		return tempMatrix;
	}

	/**
	 * Finds the maximum integer from an array of integers.
	 * @param array An array of integers.
	 * @return The index of the maximum integer
	 */
	private int findIndexwithMaxValue(int[] array){

		int maxValue = 0, index = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] > maxValue){
				maxValue = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Finds the maximum double from an array of doubles.
	 * @param array An array of doubles.
	 * @return The index of the maximum double
	 */
	private int findIndexwithMaxValue(double[] array){
		double maxValue = 0;
		int index = 0;

		for(int i = 0; i < array.length; i++){
			if(array[i] > maxValue){
				maxValue = array[i];
				index = i;
			}
		}
		return index;
	}
	/*
	 *//**
	 * Creates a "IDF_Co-occurenceMatrix.csv" file and prints the co-occurence matrix in that file.
	 * The first row and column of this file are the titles of all the tags.
	 *//*
	public void outputCoOccurenceMatrixIDF(double[][] IDFMatrix){
		FileWriter fout;
		StringBuilder str;
		try{
			fout = new FileWriter("IDF_" + OUTPUT_FILE);
			str = new StringBuilder("");
			for(Tag tag : groupTags){
				str.append("," + tag.getTagTitle());
			}
			str.append("\n");
			for(int i = 0; i<100; i++){
				str.append(groupTags.get(i).getTagTitle());
				for(int j = 0; j<100; j++){
					str.append( "," + IDFMatrix[i][j]);
				}
				str.append("\n");
			}

			fout.write(str.toString());
			fout.close();

		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while writing the IDF co-occurence matrix!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}		
	}*/

	/**
	 * Creates a "Co-occurenceMatrix.csv" file and prints the co-occurence matrix in that file.
	 * The first row and column of this file are the titles of all the tags.
	 */
	public void outputCoOccurenceMatrix(){
		FileWriter fout;
		StringBuilder str;
		try{
			fout = new FileWriter(OUTPUT_FILE);
			str = new StringBuilder("");

			for(Tag tag : groupTags){
				str.append("," + tag.getTagTitle());
			}
			str.append("\n");

			for(int i = 0; i<100; i++){
				str.append(groupTags.get(i).getTagTitle());
				for(int j = 0; j<100; j++){
					str.append("," + matrix[i][j]);
				}
				str.append("\n");
			}

			fout.write(str.toString());
			fout.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while writing the co-occurence matrix!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}		
	}

	/**
	 * Given a title, returns the Tag object with that title.
	 * @param tagTitle
	 * @return
	 */
	public Tag getTagTitle(String tagTitle){
		for(Tag tag : groupTags){
			if(tag.getTagTitle().equalsIgnoreCase(tagTitle)){
				return tag;
			}
		}
		return null;
	}

	/**
	 * Given an id, returns the Photo object with that id.
	 * @param id
	 * @return
	 */
	public Photo getPhotoID(int id){
		for(Photo foto : groupPhotos){
			if(foto.getID() == id){
				return foto;
			}
		}
		return null;
	}
}
