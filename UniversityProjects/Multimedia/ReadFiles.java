

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

//INSERT INTO `photos` (`id`, `width`, `height`, `title`, `uploaded`) VALUES
/**
 * Class that implements the reading from input files, creating Photo and Tag objects and populating the ArrayLists of the DataPhoto object
 * @author Evangelos Karvounis
 */
public final class ReadFiles {
	/**Name of the file that contains information about the photos.*/
	private  static final String PHOTOS_FILE = "Multimedia/photos.csv";
	/**Name of the file that contains information about the tags.*/
	private  static final String TAGS_FILE = "Multimedia/tags.csv";
	/**Name of the file that contains information about the relation between photos and tags.*/
	private  static final String PHOTOS_TAGS_FILE = "Multimedia/photos_tags.csv";
	/**Splitter is the regular expression that we want to split each line of the input files with.*/
	private static final String SPLITTER = ",";

	private DataPhoto data;

	public ReadFiles(DataPhoto data){
		this.data = data;
	}

	/**
	 * Reads the PHOTOS_FILE and extracts information about the Photos.
	 * Creates the Photo objects and populates the ArrayList groupPhotos of the data variable.
	 */
	public void readPhotos(){
		FileReader fin;

		try {
			fin = new FileReader(PHOTOS_FILE);
			Scanner sc = new Scanner(fin);

			//System.out.println(sc.nextLine());
			while(sc.hasNext()){
				//Reads every line and produces an array of Strings that can be used to create objects.
				String tempLine = sc.nextLine();
				String[] tempElementsInLine = tempLine.split(SPLITTER);

				//Parses each String of the above array of Strings and creates a Photo object.
				Photo photograph = new Photo(Integer.parseInt(tempElementsInLine[0]), Integer.parseInt(tempElementsInLine[1]),Integer.parseInt(tempElementsInLine[2]), tempElementsInLine[3], tempElementsInLine[4]);

				//Adds this Photo object to the ArrayList.
				data.addPhoto(photograph);
			}

			sc.close();
			fin.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "photos.csv file not found!", "Tags not found", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while reading photos.csv!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * Reads the TAGS_FILE and extracts information about the Tags.
	 * Creates the Tags objects and populates the ArrayList groupTags of the data variable.
	 */
	public void readTags(){
		FileReader fin;

		try {
			fin = new FileReader(TAGS_FILE);
			Scanner sc = new Scanner(fin);

			while(sc.hasNext()){
				String tempLine = sc.nextLine();
				String[] tempElementsInLine = tempLine.split(SPLITTER);

				Tag newTag = new Tag(tempElementsInLine[0], Integer.parseInt(tempElementsInLine[1]));

				data.addTag(newTag);
			}

			sc.close();
			fin.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "tags.csv file not found!", "Tags not found", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while reading tags.csv!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * Reads the PHOTOS_TAGS_FILE and extracts information about the relations between Photos and Tags.
	 * For every line of that file, gets the Photo object and the Tag object and adds the Tag object to the specific Photo's ArrayList of Tags.
	 */
	public void readPhotosTags(){
		FileReader fin;

		try {
			fin = new FileReader(PHOTOS_TAGS_FILE);
			Scanner sc = new Scanner(fin);

			while(sc.hasNext()){
				String tempLine = sc.nextLine();
				String[] tempElementsInLine = tempLine.split(SPLITTER);

				int tempID = Integer.parseInt(tempElementsInLine[0]);
				String tagTitle = tempElementsInLine[1];

				Photo tempPhoto = data.getPhotoID(tempID);
				Tag tempTag = data.getTagTitle(tagTitle);

				tempPhoto.addTag(tempTag);
			}

			sc.close();
			fin.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "photos.csv file not found!", "Tags not found", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while reading photos.csv!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
