package AE1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The class that provides the GUI and methods for event handling. View/Controller class.
 */
public class LWMGUI extends JFrame implements ActionListener{

	private JLabel winePurchasedLabel;
	private JTextField nameField, quantityField, priceField, amountTransField, currentBalanceField;
	private JButton processSale, processReturn;
	private JPanel top, middle, bottom;
	private CustomerAccount customerObj;

	/**
	 * Constructor of the GUI. Adds all the components to the frame. Sets the title by getting the name from the CustomerAccount object.
	 * */
	public LWMGUI(CustomerAccount customer){
		
		customerObj = customer; //Stores it in an instance variable

		setSize(750,200);
		setTitle("Lilybank Wine Merchants: " + customerObj.getCustomerName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Sets layout
		layoutComponents();
	}

	/**
	 * Creates everything in the GUI and puts them to particular places.
	 * */
	private void layoutComponents(){

		//Creates new JPanel and sets its background color to white
		top = new JPanel();
		top.setBackground(Color.white);

		//Creates a JLabel and adds it to the top JPanel
		JLabel nameLabel = new JLabel("Name: ");
		top.add(nameLabel);

		//Creates a JTextField, that is used to input the name of the wine,  sets its number of columns to 20 and adds it to the top JPanel
		nameField = new JTextField(20);
		top.add(nameField);

		//Creates a JLabel and adds it to the top JPanel
		JLabel quantityLabel = new JLabel("Quantity: ");
		top.add(quantityLabel);

		//Creates a JTextField, that is used to input the number of bottles,  sets its number of columns to 10 and adds it to the top JPanel
		quantityField = new JTextField(10);
		top.add(quantityField);

		//Creates a JLabel and adds it to the top JPanel
		JLabel priceLabel = new JLabel("Price: £");
		top.add(priceLabel);

		//Creates a JTextField, that is used to input the price of the wine,  sets its number of columns to 15 and adds it to the top JPanel
		priceField = new JTextField(15);
		top.add(priceField);

		//Creates a new JPanel called middle
		middle = new JPanel();
		middle.setBackground(Color.white);

		//Creates 2 JButtons that have actionListeners and adds them to middle JPanel
		processSale = new JButton("Process Sale");
		processSale.addActionListener(this);
		middle.add(processSale);

		processReturn = new JButton("Process Return");
		processReturn.addActionListener(this);
		middle.add(processReturn);

		//Creates a new JPanel called bottom
		bottom = new JPanel();
		bottom.setBackground(Color.white);

		//Creates two JLabels and adds them to bottom JPanel
		winePurchasedLabel = new JLabel("Wine purchased: ");
		bottom.add(winePurchasedLabel);
		JLabel amountTransactionLabel = new JLabel("Amount of Transaction: ");
		bottom.add(amountTransactionLabel);

		//Creates a non-editable JTextField that is used to display the amount of the transaction and adds it to the bottom JPanel
		amountTransField = new JTextField(12);
		amountTransField.setEditable(false);
		bottom.add(amountTransField);

		JLabel currentBalanceLabel = new JLabel("Current Balance: ");
		bottom.add(currentBalanceLabel);

		//Creates a non-editable JTextField that is used to display the customer's current balance
		currentBalanceField = new JTextField(12);
		currentBalanceField.setEditable(false);

		//Calls the method that accepts a CustomerAccount object as input and displays the customer's current balance
		setCurrentbalanceField(customerObj);
		bottom.add(currentBalanceField);

		//Adds the three JPanels to JFrame
		this.add(top,BorderLayout.NORTH);
		this.add(middle,BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);
	}

	/**
	 * Handles the events.
	 * */
	public void actionPerformed(ActionEvent e) {

		double transactionAmount = 0.0;

		//Single method that creates a Wine object. Checks if all the data that the user inputed is correct.
		Wine wine = createWine();

		//If wine is null, resets the fields by calling the resetFields method and then exits the actionPerformed method
		if(wine==null){
			resetFields();
			return;
		}

		//Sets the wine name in the JLabel called winePurchasedLabel
		setWineNameLabel(wine);

		//Checks which source caused the event and calls the responsible method. Stores the double value that the method returns.
		if(e.getSource()==processSale){

			transactionAmount = customerObj.processSale(wine);

		}else if(e.getSource()==processReturn){

			transactionAmount = customerObj.processReturn(wine);
		}
		//The event handler code updates the GUI by calling the three following methods:
		
		//Sets the Amount Transaction Field by passing the transactionAmount value to it
		setAmountTransactionField(transactionAmount);	

		//Sets the current balance Field by passing the customerObj to it
		setCurrentbalanceField(customerObj);

		//Resets the fields
		resetFields();
	}

	/**
	 * Method that is used to create a Wine object. Responsible for getting details from the text fields when either the sale or return button is pressed.
	 * If the inputs are not valid, returns null that informs that something is wrong with the inputs.
	 * */
	private Wine createWine(){
		
		//Gets the Strings from the three JTextFields.
		String wineName = nameField.getText();
		String wineQuantityField = quantityField.getText();				
		String winePriceField = priceField.getText();

		int wineQuantity = 0;
		double winePrice = 0.0;

		try{
			//Checks if the inputs are integer and double. If any of them is non-numerical, returns null.
			wineQuantity = Integer.parseInt(wineQuantityField);
			winePrice = Double.parseDouble(winePriceField);

		}catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "You entered invalid data! Be careful next time please!", "Invalid data input", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		/*Checks if the quantity of the bottles is a positive integer, if the price of the bottle is a positive double and if the name of the wine is not empty.
		If at least one thing is not valid, prints an Error Message and returns null.*/
		if(wineQuantity<=0 || winePrice<=0.0 || wineName.isEmpty()){
			JOptionPane.showMessageDialog(null, "You entered invalid data! Be careful next time please!", "Invalid data input", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		//We multiply by 100, round it and then divide by 100 the double price of the bottle to make sure that the double price of the wine has at most 2 decimals.
		winePrice *= 100;
		winePrice = Math.round(winePrice);
		winePrice /= 100;
		
		//At this point, we are certain that the given parameters are valid. Returns a Wine object with the given parameters as input.
		Wine wineReady = new Wine(wineName, winePrice, wineQuantity);
		
		return wineReady;

	}

	/**
	 * Sets the wine name label.
	 * */
	private void setWineNameLabel(Wine win){

		winePurchasedLabel.setText("Wine purchased: " + win.getWineName());

	}

	/**
	 * Sets the current balance Text Field. 
	 * @param customer An object of CustomerAccount type that holds all the necessary information. It extracts the current balance value from this object
	 * and checks if it is positive or negative. If positive, it prints the value and if negative, it prints the absolute value followed by the characters 'CR'.
	 * */
	private void setCurrentbalanceField(CustomerAccount customer){

		int currBal = customer.getCurrentBalance();

		if(currBal >= 0){

			//Dividing an integer with the double value 100.0 automatically casts it to double.
			currentBalanceField.setText("£ " + currBal/100.0);

		}else{
			
			//Math.abs returns the absolute value of the input
			currentBalanceField.setText("£ " + Math.abs(currBal)/100.0 + " CR");
		}		
	}

	/**
	 * Sets the Amount of Transaction field.
	 * @param amounTr A double value that will be displayed in the Field.
	 * */
	private void setAmountTransactionField(double amountTr){

		//Multiplies the inputed double value, rounds it and casts it to integer
		int temp = (int) Math.round(amountTr * 100);

		//Divides the above integer with 100.0 (which casts it to double) and displays that double value to the amount Transaction Text Field.
		//By doing that we ensure that at most values with 2 decimals will be displayed.
		amountTransField.setText("£ " + temp/100.0);		
	}

	/**
	 * Resets the name, number of bottles and price of bottle JTtextFields.
	 * */
	private void resetFields(){

		nameField.setText("");
		quantityField.setText("");
		priceField.setText("");	
	}

}
