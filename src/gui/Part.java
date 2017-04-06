package gui;

import java.sql.Date;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/*
 * ####################################################################
 * Part class
 *
 * The Part() object stores the properties of a given part entry
 * and the methods enable creation and manipulation of the data
 * ####################################################################
 */
class Part {

	// Create ArrayList of all Part objects -- not used, but may be
	public static ArrayList<Part> PARTS = new ArrayList<Part>();
	
	// Declare private properties of the Part object	
	private int id;
	private String partNo;
	private String description;
	private String make;
	private String location;
	private int inStock;
	private Date updated;
	
	
	/******************************************************************
	 * 
	 * Begin Part object getter methods
	 * 
	 *****************************************************************/
	
	public int getId() {
		return this.id;
	}
	
	public String getPartNo() {
		return this.partNo;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getMake() {
		return this.make;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public int getInStock() {
		return this.inStock;
	}
	
	public Date getUpdated() {
		return this.updated;
	}
	
	
	/******************************************************************
	 * 
	 * Begin Part object setter methods
	 * 
	 *****************************************************************/
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPartno(String partNo) {
		this.partNo = partNo;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	
	/**************************************************************
	 *
	 * Part.createPart() method
	 * 
	 * Creates a new Part() object and adds it to the ArrayList
	 *
	 * Requires arguments for:
	 *  int id (the database PRIMARY KEY),
	 *  String partNo,
	 *  String description,
	 *  String make,
	 *  String location,
	 *  int inStock
	 *
	 * Returns the new Part() object
	 *
	 *************************************************************/

	public static Part createPart(int id, 
				      String partNo, 
				      String description, 
				      String make, 
				      String location,
			              int inStock) {

		Part newPart = new Part();
		newPart.id = id;
		newPart.partNo = partNo;
		newPart.description = description;
		newPart.make = make;
		newPart.location = location;
		newPart.inStock = inStock;
		PARTS.add(newPart);
		return newPart;		
	}

	
	/**************************************************************
	 *
	 * Part.addPartScreen() method
	 *
	 * This method will display the page to add a new part to
	 * the database, and calls the Database method to perform
	 * the SQL insert.
	 *
	 *************************************************************/
	
	public static void addPartScreen() {

		// Clear the partsGrid pane
		StartPage.partsGrid.getChildren().clear();
		
		// Add message prompting user to add part
		Text addMsg = new Text("Add a New Part");
		addMsg.setFill(Color.GREEN);
		addMsg.setFont(Font.font("serif", FontWeight.BOLD, 20));
		addMsg.setTextAlignment(TextAlignment.CENTER);
		StartPage.partsGrid.add(addMsg, 0, 0, 2, 1);
		
		// Create labels and TextFields for the new part properties
		Label newPartNoLabel = new Label("Part #: ");
		StartPage.partsGrid.add(newPartNoLabel, 0, 1);
		TextField newPartNo = new TextField();
		newPartNo.setPromptText("Enter part number");
		StartPage.partsGrid.add(newPartNo, 1, 1);
		
		Label newPartDescLabel = new Label("Description: ");
		StartPage.partsGrid.add(newPartDescLabel, 0, 2);
		TextField newPartDesc = new TextField();
		newPartDesc.setPromptText("Enter description (optional)");
		StartPage.partsGrid.add(newPartDesc, 1, 2);
		
		Label newPartMakeLabel = new Label("Make: ");
		StartPage.partsGrid.add(newPartMakeLabel, 0, 3);
		TextField newPartMake = new TextField();
		newPartMake.setPromptText("Enter make (optional)");
		StartPage.partsGrid.add(newPartMake, 1, 3);
		
		Label newPartLocLabel = new Label("Location: ");
		StartPage.partsGrid.add(newPartLocLabel, 0, 4);
		TextField newPartLoc = new TextField();
		newPartLoc.setPromptText("Enter location (e.g. A-1)");
		StartPage.partsGrid.add(newPartLoc, 1, 4);
		
		Label newInStockLabel = new Label("In stock: ");
		StartPage.partsGrid.add(newInStockLabel, 0, 5);
		TextField newInStock = new TextField();
		newInStock.setPromptText("Enter quantity in stock (integer)");
		StartPage.partsGrid.add(newInStock, 1, 5);
		
		// Create button to add new info to database
		Button addButton = new Button("Add to Database");
		StartPage.partsGrid.add(addButton, 0, 6);
		
		// Set action on button click
		addButton.setOnAction(
				e -> {
					
					// Declare variables for new Part params
					String partNum;
					String desc;
					String make;
					String location;
					int inStock;
					
					// New Part params are user input, unless null
					if (newPartNo.getText() == null) {
						partNum = "";						
					} 
					else {
						partNum = newPartNo.getText();
					}
					
					if (newPartDesc.getText() == null) {
						desc = "";
					} 
					else {
						desc = newPartDesc.getText();
					}
					
					if (newPartMake.getText() == null) {
						make = "";
					}
					else {
						make = newPartMake.getText();
					}
					
					if (newPartLoc.getText() == null) {
						location = "";
					} 
					else {
						location = newPartLoc.getText();
					}
					
					// Try to parse an int from user input for inStock
					try {
						String stockString = newInStock.getText();
						inStock = Integer.parseInt(stockString);
					}
					catch (Exception ex) {
						// Set inStock to default 0 if no integer input
						inStock = 0;
					}
					
					
					// Try to call the addPart method to INSERT info
					try {
						
						// Call addPart with user's input as params
						Database.addPart(Database.connectDB(), 
								 partNum, 
								 desc, 
								 make, 
								 location, 
								 inStock);
						
						// Redirect to page showing the new Part object
						Database.fetchPartByNumber(Database.connectDB(),
									   partNum);
						
					} 
					catch (Exception ex) {
						
						// If error, print a message to the console
						System.out.println("Error: Item not added");
					} 
					
				}
		);
		
	}
	
	
	/******************************************************************
	 * 
	 * Part.showAll() method
	 * 
	 * This method prints the data from all Part objects in the PARTS
	 * ArrayList to the window
	 * 
	 *****************************************************************/
	public static void showAll() {
		for (int i = 0; i < PARTS.size(); i++) {
			
			// Row is one more than index due to headers
			int row = i + 1;
			
			// Add button to edit the Part object
			Button editButton = new Button("Edit Part");
			StartPage.partsGrid.add(editButton, 0, row);
			
			// Create object p of current Part iteration
			Part p = PARTS.get(i);
			
			// Set action on editButton click
			editButton.setOnAction(
					e -> {
						// move to the Part object's edit screen
						Part.editPartScreen(p.getPartNo());
					}
			);
			
			// Create Text objects of data
			Text partNoText = new Text(p.getPartNo());
			Text partDescText = new Text(p.getDescription());
			Text makeText = new Text(p.getMake());
			Text locationText = new Text(p.getLocation());
			Text inStockText = new Text(Integer.toString(
						    p.getInStock()));
			
			// Add the Text objects to current row
			StartPage.partsGrid.add(partNoText, 1, row);
			StartPage.partsGrid.add(partDescText , 2, row);
			StartPage.partsGrid.add(makeText, 3, row);
			StartPage.partsGrid.add(locationText, 4, row);
			StartPage.partsGrid.add(inStockText, 5, row);
		}
	}
	
	
	/******************************************************************
	 * 
	 * Part.editPartScreen() method
	 * 
	 * This method calls a page to display the details about a
	 * specific Part object, and allow the user to modify it.
	 * 
	 * Requires argument for:
	 *  String partNo
	 * 
	 *****************************************************************/
	public static void editPartScreen(String partNo) {
		
		// Try to SELECT info from database
		try {
			
			// Clear the window and parts grid to start fresh
			ClearWindow.clearWin();
			ClearPartsGrid.clearGrid();
			
			// SELECT part with the input part # from database
			Database.fetchPartByNumber(Database.connectDB(), partNo);
		} 
		catch (Exception e) {
			// If database error, print message to console
			System.out.println("Database error");
		}
		
	}
}

