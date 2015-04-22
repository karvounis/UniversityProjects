import java.util.*;

/**
 * Used to store photographs and their information. Also, contains an ArrayList of tags that a particular photograph has.
 * @author Evangelos Karvounis
 */
public class Photo implements Comparable<Photo> {
	private int id, width, height;
	private String title, dateUploaded;
	private ArrayList<Tag> tags;

	public Photo(int id, int width, int height, String title, String dateUploaded) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.title = title;
		this.dateUploaded = dateUploaded;
		tags = new ArrayList<Tag>();
	}

	/**
	 * Adds a Tag object to the ArrayList of this photograph.
	 * @param tag
	 */
	public void addTag(Tag tag){
		tags.add(tag);
	}

	/**
	 * Gets the ArrayList that contains all the Tags of this photograph.
	 * @return
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}

	/**
	 * Returns the ID(Primary Key) of the photograph.
	 * @return
	 */
	public int getID(){
		return id;
	}

	/**
	 * Returns the title.
	 * @return
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Returns the String representation of the Photograph.
	 * @return
	 */
	public String toString(){
		//return "ID: " + id + "--Width: " + width + "--Height: " + height + "--Title: " + title + "--Date Uploaded: " + dateUploaded;
		return "" + id;
	}

	/**
	 * Compares two Photo objects. Used to store them in ascending order based on their ID.
	 */
	@Override
	public int compareTo(Photo arg0) {
		int result = this.getID() - arg0.getID();
		if(result > 0)	return 1;
		else if(result == 0) return 0;
		else	return -1;
	}
}
