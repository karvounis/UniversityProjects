package AdvancedProgramming;

public class Main {

	public static void main(String[] args){
		Folder root = new Folder("root");
		Document MyDocument = new Document("MyDocument", 100);
		root.add(MyDocument);
		Document TestDocument = new Document("TestDocument", 200);
		root.add(TestDocument);
		
		Folder MyFolder = new Folder("MyFolder");
		Document AgainAnotherDocument = new Document("AgainAnotherDocument", 450);
		Document AnotherDocument = new Document("AnotherDocument", 300);
		
		Folder AnotherFolder = new Folder("Another Folder");
		Document MoreWork = new Document("MoreWork", 20);
		AnotherFolder.add(MoreWork);
		MyFolder.add(AnotherDocument);
		MyFolder.add(AgainAnotherDocument);
		MyFolder.add(AnotherFolder);
		root.add(MyFolder);
		
		Folder AnotherFolder2 = new Folder("Another Folder");
		Document BigDocument = new Document("BigDocument", 200);
		AnotherFolder2.add(BigDocument);
		root.add(AnotherFolder2);
		
		Document LittleDocument = new Document("LittleDocument", 100);
		root.add(LittleDocument);
		
		Document LastDocument = new Document("LastDocument", 100);
		root.add(LastDocument);
		
		System.out.println(root.toString());
		System.out.println(root.getSize());
		System.out.println(LastDocument.getSize());
		System.out.println(AnotherFolder2.getSize());
		
		System.out.println(root.getNumberOfDocuments());
		System.out.println(LastDocument.getNumberOfDocuments());
		System.out.println(AnotherFolder2.getNumberOfDocuments());
	}
}
