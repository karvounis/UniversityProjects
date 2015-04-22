public class Main {

	//private static final String[] TAGS = new String[]{"water", "people", "london"};
	private static final String[] TAGS = new String[]{"sky", "flower", "green"};
	
	public static void main(String[] args) {

		int[][] CoMatrix = new int[100][100];
		double[][] IDFMatrix = new double[100][100];

		DataPhoto dataPhoto = new DataPhoto();
		ReadFiles read = new ReadFiles(dataPhoto);

		read.readPhotos();
		read.readTags();
		read.readPhotosTags();

		CoMatrix = dataPhoto.createOccurenceMatrix();
		IDFMatrix = dataPhoto.createIDFMatrix();

		for(String title : TAGS){
			System.out.print(title + ": \n");
			dataPhoto.computeTopTags(title, CoMatrix);
			System.out.print("\n");
		}

		for(String title : TAGS){
			System.out.println(title + ": ");
			dataPhoto.computeTopTagsIDF(title, IDFMatrix);
			System.out.print("\n");
		}
		 
		//System.out.println(play.isSymmetrical(CoMatrix));
	}
}
