package gui;

import java.sql.Connection;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * ####################################################################
 * ClearWindow class
 * 
 * The ClearWindow class clears all existing elements from the
 * StartPage.mainGrid region (the non-scrolling top pane), and
 * repopulates it with the logo, search, and navigation infrastructure.
 * ####################################################################
 */
class ClearWindow extends StartPage {
	
	/******************************************************************
	 * 
	 * ClearWindow.clearWin() method
	 * 
	 * This method will clear all child elements from the 
	 * StartPage.mainGrid pane and replace the logo, search and add
	 * buttons, and the search text fields.
	 * 
	 *****************************************************************/
	public static void clearWin() {
		
		// Clear all current child elements from the pane
		mainGrid.getChildren().clear();
		
		// Fetch logo image and create ImageView
		Image pvgcLogo = new Image("pvgcLogo1.jpg");
		ImageView logoView = new ImageView(pvgcLogo);
		// Attach to pane, spanning 9 rows
		mainGrid.add(logoView, 0, 0, 1, 9);
		
		// Create button to add new part
		Button addNewPart = new Button("Add New Part");
		// Attach to pos (1,1), to the right of logo
		mainGrid.add(addNewPart, 1, 1);
		// Set action event on click
		addNewPart.setOnAction(
				e -> {
					// Call the Part method to view the addPart screen
					Part.addPartScreen();
				}
		);
		
		// Create button to view all db entries
		Button viewAllButton = new Button("View all parts");
		// Attach immediately below the add button
		mainGrid.add(viewAllButton, 1, 2);
		
		// Set action event on click
		viewAllButton.setOnAction(
				e -> {
					// Try to query the db to fetch all parts
					try {
						
						// Create connection object with Database method
						Connection dbconn = Database.connectDB();
						
						// Clear the current partsGrid children
						ClearPartsGrid.clearGrid();
						
						// Fetch all parts with the Database method
						Database.fetchParts(dbconn);
					} 
					catch (Exception ex) {
						// Print error message to console if unsuccessful
						System.out.println("Database error");
					}
				}				
		);
		
		// Create TextField to search by part number
		TextField partSearchBox = new TextField();
		partSearchBox.setPromptText("Part number");
		mainGrid.add(partSearchBox, 1, 4);
		
		// Create button to search by part number
		Button partNumGo = new Button("Find by Part #");
		mainGrid.add(partNumGo, 2, 4);
		
		// Set action event on click
		partNumGo.setOnAction( 
				e -> {
					// Try to query db with user input
					try {
						
						// Fetch the user's input from TextField
						String searchQuery = partSearchBox.getText();
						
						// Create Connection using Database method
						Connection dbconn = Database.connectDB();
						
						// Clear current children from the partsGrid
						ClearPartsGrid.clearGrid();
						
						// Print status message to console
						System.out.println("Finding parts like " + searchQuery);
						
						// Clear the user's input in search box
						partSearchBox.clear();
						
						// Call the Database method to search by partNo
						Database.searchPartsByNo(dbconn, searchQuery);
					} 
					catch (Exception ex) {
						// Print error message if unsuccessful
						System.out.println("Database error");
					}
				}
		);
		
		// Create TextField to search by machine make
		TextField makeSearchBox = new TextField();
		makeSearchBox.setPromptText("Machine make");
		mainGrid.add(makeSearchBox, 1, 5);
		
		// Create button to search by make
		Button makeGo = new Button("Find by make");
		mainGrid.add(makeGo, 2, 5);
		
		// Set action event on click
		makeGo.setOnAction(  
				e -> {
					// Try to query the db with user input
					try {
						
						// Fetch the user's input from TextField
						String searchQuery = makeSearchBox.getText();
						
						// Create Connection using Database method
						Connection dbconn = Database.connectDB();
						
						// Clear current children from the partsGrid
						ClearPartsGrid.clearGrid();
						
						// Print status message to console
						System.out.println("Finding makes like " + searchQuery);
						
						// Clear the user's input from search box
						makeSearchBox.clear();
						
						// Call the Database method to search by make
						Database.searchByMake(dbconn, searchQuery);
					} 
					catch (Exception ex) {
						// Print error message if unsuccessful
						System.out.println("Database error");
					}
				}
		);
		
		// Create TextField to search by description
		TextField descriptionSearchBox = new TextField();
		descriptionSearchBox.setPromptText("Part description");
		mainGrid.add(descriptionSearchBox, 1, 6);
		
		// Create button to search by description
		Button descriptionGo = new Button("Find by description");
		mainGrid.add(descriptionGo, 2, 6);
		
		// Set action event on click
		descriptionGo.setOnAction(
				e -> {
					// Try to query db with user input
					try {
						
						// Fetch the user's input from TextField
						String searchQuery = descriptionSearchBox.getText();
						
						// Create Connection using Database method
						Connection dbconn = Database.connectDB();
						
						// Clear current children from the partsGrid
						ClearPartsGrid.clearGrid();
						
						// Print status message to console
						System.out.println("Finding parts like " + searchQuery);
						
						// Clear user's input from search box
						descriptionSearchBox.clear();
						
						// Call the Database method to search by description
						Database.searchPartsByDescription(dbconn, searchQuery);
					} 
					catch (Exception ex) {
						// Print error message if unsuccessful
						System.out.println("Database error");
					}
				}				
		);
		
		// Create TextField to search by location
		TextField locationSearchBox = new TextField();
		locationSearchBox.setPromptText("Location, e.g. A-1");
		mainGrid.add(locationSearchBox, 1, 7);
		
		// Create button to search by location
		Button locationGo = new Button("Find by location");
		mainGrid.add(locationGo, 2, 7);
		
		// Set action event on click
		locationGo.setOnAction(
				e -> {
					// Try to query the db with user input
					try {
						
						// Fetch the user's input from TextField
						String searchQuery = locationSearchBox.getText();
						
						// Create Connection with the Database method
						Connection dbconn = Database.connectDB();
						
						// Clear current children from the partsGrid
						ClearPartsGrid.clearGrid();
						
						// Print status message to console
						System.out.println("Finding parts located at " + searchQuery);
						
						// Clear the user's input from search box
						locationSearchBox.clear();
						
						// Call the Database method to search by location
						Database.searchPartsByLocation(dbconn, searchQuery);
					} 
					catch (Exception ex) {
						// Print error message if unsuccessful
						System.out.println("Database error");
					}
				}
		);
	}
}

