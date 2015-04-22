package AE1;
import javax.swing.JOptionPane;

/**
 * The class that contains the main method.
 * */
public class AssEx1 {

	public static void main(String[] args) {

		//Initialization of variables
		String currBalance="";
		double currentBalance=0.0;
		int currentBalancePennies = 0;
		
		/*Starts the first input dialog that asks for the customer name. If the name is null or empty it exits the program by calling System.exit and giving it 1 as a parameter. 
		Also we use the trim method to get rid of the unwanted white spaces.*/
		String customerName = JOptionPane.showInputDialog("Please enter the customer's name: ");

		System.err.println(customerName);
		if(customerName!=null && !customerName.isEmpty()){

			customerName = customerName.trim();

		}else{
			System.exit(1);
		}

		/*Loop that checks if we have entered a value for current balance. If we give a value and it is not a number, we catch the exception that Double.parseDouble throws
		and repeat the loop until the user inputs a numerical input. If the user press the cancel or the 'X' button, then the System terminates with '1' as parameter 
		that indicates that something not normal happened.*/
		while(currBalance.isEmpty()){

			currBalance = (JOptionPane.showInputDialog("Please enter the customer's initial balance: "));

			if(currBalance!=null){

				try{
					
					currentBalance = Double.parseDouble(currBalance);
					//As soon as we parse the double value, we multiply it by 100, we round it to the nearest value, we cast it to integer and store it to currentBalancePennies.
					currentBalancePennies = (int) Math.round(currentBalance*100);
					
				}catch(Exception e){					
					JOptionPane.showMessageDialog(null, "You entered a non-numerical input!! Be careful next time please!", "Non-numerical input", JOptionPane.ERROR_MESSAGE);
					currBalance="";	//Resets the currBalance String.			
				}

			}else{
				System.exit(1);	
			}
		}
		
		//Creates the CustomerAccount object and instantiates it with the variables that the user inputed.
		CustomerAccount customer = new CustomerAccount(customerName, currentBalancePennies);
		
		//Instantiates and displays the LWMGUI object, passing the CustomerAccount object we created above as a parameter.
		LWMGUI lwmgui = new LWMGUI(customer);
		lwmgui.setVisible(true);
	}

}
