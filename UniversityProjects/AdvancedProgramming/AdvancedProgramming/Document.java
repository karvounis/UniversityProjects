package AdvancedProgramming;

public class Document implements Resource{

	private String name;
	private int size;
	
	public Document(String name, int size){
		this.name = name;
		this.size = size;
	}
/*	@Override
	public int getNumberOfItems() {
		// TODO Auto-generated method stub
		return 1;
	}*/
	/*public String toString(){
		return name;
	}*/
	public String toString(){
		String output = "";
		for(int i=0; i<Folder.depth; i++){
			output += "\t";
		}
		return output + name;
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}
	@Override
	public int getNumberOfDocuments() {
		return 1;
	}

}
