package AdvancedProgramming;

import java.util.*;

public class Folder implements Resource{
	
	private ArrayList<Resource> children;
	private String name;
	public static int depth = 0;
	
	public Folder(String name){
		this.name = name;
		children = new ArrayList<Resource>();
	}
	
/*	public int getNumberOfItems(){
		return children.size();
	}*/
	
	public String toString(){
		String totalString = "";
		
		for(int i=0;i<depth;i++){
			totalString+="\t";
		}
		if(!name.equals("root")){
			depth++;
			totalString += "Folder: " + name + " containing:";
		}		
		for(Resource ch:children){
			totalString += "\n" + ch.toString() ;
		}
		
		depth--;
		return totalString;
	}
	
	public void add(Resource child){
		children.add(child);
	}

	public void remove(Resource child){
		children.remove(child);
	}

	@Override
	public int getSize() {
		int sumSize = 0;
		if(children.size() > 0){
			for(Resource ch:children){
				sumSize += ch.getSize();
			}
		}
		return sumSize;
	}
	
	public int getNumberOfDocuments(){
		int sumDocs = 0;
		for(Resource ch : children){
			sumDocs += ch.getNumberOfDocuments();
		}
		return sumDocs;
	}
}
