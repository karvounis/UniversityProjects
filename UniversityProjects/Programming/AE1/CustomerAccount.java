package AE1;
/**
 * One of the Model classes. Stores the customer's name and current balance and performs operations on the latter variable to update it.
 * */
class CustomerAccount {

	private int currentBalance;		//Represents the current Balance
	private String customerName;	//Represents the name of the account holder  
	private static final int SERVICE_CHARGE = 20;	//Class constant representing the percentage service charge for returns

	//Constructor with 2 inputs. Initializes the instance variables
	public CustomerAccount(String custName, int currBalance){
		customerName = custName;
		currentBalance = currBalance;
	}

	/**
	 * Processes the wine bottle sales and updates the current balance.
	 * @param wineProcess A Wine object.
	 * @return Double value that represents the amount of the current transaction rounded to two decimals.
	 * */
	public double processSale(Wine wineProcess){

		int numBottles = wineProcess.getQuantity(); 	//Number of bottles
		double costBottle = wineProcess.getPricePerBottle();	//Price of bottle
		
		//Calculates the transaction Amount.
		double costTransaction =  numBottles * costBottle;

		//Transforms it to pennies by multiplying it by 100, rounding it and casting it to integer.
		int costTransactionPennies = (int) Math.round(costTransaction*100);
		
		//Updates the customer's current balance by adding the pennies amount to it
		currentBalance += costTransactionPennies;

		return costTransaction;		
	}

	/**
	 * Processes the wine bottle returns and updates the current balance.
	 * @param wineProcess A Wine object.
	 * @return Double value that represents the amount of the current transaction rounded to two decimals.
	 * */
	public double processReturn(Wine wineProcess){
		
		int numBottles = wineProcess.getQuantity(); 	//Number of bottles
		double costBottle = wineProcess.getPricePerBottle();	//Price of bottle
		
		//Calculates the transaction Amount
		double costTransaction = numBottles * costBottle * (100 - SERVICE_CHARGE);

		//Transforms it to pennies by multiplying it by 100, rounding it and casting it to integer.
		int costTransactionPennies = (int) Math.round(costTransaction);
		
		//Subtracts the pennies amount from customer's current balance
		currentBalance -= costTransactionPennies;

		//Returns the divided by 100 value because we multiplied it before with 80 = (100 - SERVICE_CHARGE)
		return costTransaction/100;			
	}	

	//Returns the name of the customer
	public String getCustomerName(){
		return customerName;
	}

	//Returns the current balance of the customer
	public int getCurrentBalance(){
		return currentBalance;
	}

}
