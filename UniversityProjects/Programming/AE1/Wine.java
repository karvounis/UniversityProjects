package AE1;

/**
 * Used to represent information about a wine type that is purchased / returned. It is another Model class.
 * */
public class Wine{

	private String wineName;	//Represents the name of the wine
	private double pricePerBottle;	//Represents the price of a bottle of a wine
	private int quantity;	//Represents the quantity of the wine

	//Constructors with 3 inputs to initialize instance variables
	public Wine(String Name, double priceBottle, int quant){
		wineName = Name;
		pricePerBottle = priceBottle;
		quantity = quant;
	}

	//Returns the name of the wine.
	public String getWineName(){
		return wineName;
	}

	//Returns the price of the wine.
	public double getPricePerBottle(){
		return pricePerBottle;
	}

	//Returns the quantity of the wine.
	public int getQuantity(){
		return quantity;
	}
}
