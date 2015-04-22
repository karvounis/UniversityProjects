/**
 * Used to store tags and their information.
 * @author Evangelos Karvounis
 */
public class Tag implements Comparable<Tag>{

	private String tagTitle;
	private int numPhotos;

	public Tag(String tagTitle, int numPhotos){
		this.tagTitle = tagTitle;
		this.numPhotos = numPhotos;
	}

	/**
	 * Returns the title of the tag.
	 * @return
	 */
	public String getTagTitle() {
		return tagTitle;
	}

	/**
	 * Returns the number of photos that this tag can be found.
	 * @return
	 */
	public int getNumPhotos() {
		return numPhotos;
	}

	/**
	 * Returns a String representation of this Tag.
	 */
	public String toString(){
		return tagTitle;
	}

	/**
	 * Compares two Tag objects based on their titles.
	 */
	@Override
	public int compareTo(Tag o) {

		return this.getTagTitle().compareTo(o.getTagTitle());
	}
}