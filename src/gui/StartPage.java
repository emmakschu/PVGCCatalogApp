package gui;

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.sql.Date;
import java.sql.*;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/*
 * StartPage class
 * Extends the javafx.Application class
 *
 * This class creates the JavaFX GUI application for the 
 * PVGC Parts Catalog
 */
public class StartPage extends Application {
	
	// Declare static Pane objects to reference througout app
	static BorderPane mainWindow = new BorderPane();
	static GridPane mainGrid = new GridPane();
	static BorderPane innerBorder = new BorderPane();
	static GridPane partsGrid = new GridPane();
	
	
	// Override the standard start method	
	@Override

	/**
	 * StartPage.start() method
	 *
	 * This method creates the basic GUI window elements which will
	 * be populated by the methods of various other classes in the app
	 */
	public void start(Stage primaryStage) {
	
		/**************************************************************
		 *
		 * Begin creating and formatting panes
		 *
		 *************************************************************/

		// Adjust alignment and padding for the mainGrid object
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setPadding(new Insets(30,30,30,30));
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		
		// Call the method to populate the navigation/search elements
		ClearWindow.clearWin();
		
		// Adjust alignment and padding for the partsGrid object
		partsGrid.setAlignment(Pos.CENTER);
		partsGrid.setPadding(new Insets(30,30,30,30));
		partsGrid.setHgap(30);
		partsGrid.setVgap(30);
		
		// Assign the mainGrid (nav/search) to top of the innerBorder
		innerBorder.setTop(mainGrid);
		
		// Place main GridPane in a ScrollPane, set fits
		ScrollPane mainScroll = new ScrollPane(partsGrid);
		mainScroll.setFitToWidth(true);
		mainScroll.setFitToHeight(true);

		// Assign the mainScroll pane to center of innerBorder pane
		innerBorder.setCenter(mainScroll);

		// Set minimum dimensions for the innerBorder pane
		innerBorder.setMinHeight(900);
		innerBorder.setMinWidth(1200);
		
		
		/**************************************************************
		 * 
		 * Begin creating menu toolbar for top of window
		 *
		 *************************************************************/

		MenuBar menuToolbar = new MenuBar();
		
		// Create file menu with its items, add to menu toolbar
		Menu menuFile = new Menu("File");
		
		MenuItem menuProperties = new MenuItem("Properties");
		
		MenuItem fileQuit = new MenuItem("Quit");
		CloseWindowHandler fileQuitAct = new CloseWindowHandler();
		fileQuit.setOnAction(fileQuitAct);
		
		menuFile.getItems().add(menuProperties);
		menuFile.getItems().add(fileQuit);
		
		menuToolbar.getMenus().add(menuFile);
		
		// Create edit menu with its items, add to menu toolbar
		Menu menuEdit = new Menu("Edit");
		
		MenuItem editPreferences = new MenuItem("Preferences");
		
		menuEdit.getItems().add(editPreferences);
		
		menuToolbar.getMenus().add(menuEdit);
		
		/**************************************************************
		 *
		 * Begin populating the BorderPane which is the parent 
		 * window object, scene, and stage
		 *
		 *************************************************************/
		
		mainWindow.setTop(menuToolbar);
		mainWindow.setCenter(innerBorder);
		
		// Begin creating scene and stage to show to screen
		Scene scene = new Scene(mainWindow);
		scene.getStylesheets().clear();
		primaryStage.setTitle("PVGC Inventory App");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * StartPage.main() method
	 *
	 * This method launches the application
	 */	
	public static void main(String[] args) {
		
		// Print launch status message to console
		System.out.println("PVGC Parts Catalog App");
		System.out.println("Launching");

		// Launch the JavaFX Application
		Application.launch(args);
	}
}


/*
 * Part class
 *
 * The Part() object stores the properties of a given part entry
 * and the methods enable creation and manipulation of the data
 *
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
		StartPage.partsGrid.add(addMsg, 0, 0);
		
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


/*
 * Database class
 * 
 * The Database class stores the information and methods needed to
 * connect to and interact with the database.
 *  
 */
class Database extends StartPage {
	
	// Declare and set private params
	private static final String server = "jdbc:mysql://localhost/";
	private static final String dbName = "pvgcCatalog";
	private static final String dbUser = "javaApp";
	private static final String dbPasswd = "myPassword";
	
	
	/******************************************************************
	 * 
	 * Database.connectDB() method
	 * 
	 * This method initiates a connection to the MySQL database using
	 * the JDBC MariaDB driver, and returns the connection as a
	 * java.sql Connection object.
	 * 
	 *****************************************************************/
	public static Connection connectDB() throws 
				    SQLException, ClassNotFoundException {
		
		// Print status message to console
		System.out.println("Connecting to database...");
		
		// Define driver forName
		Class.forName("org.mariadb.jdbc.Driver");
		
		// Print message to console if driver loads correctly
		System.out.println("Database driver loaded...");
		
		// Concatenate the server and database into one string
		String callConnection = server + dbName;		
		
		// Create Connection object using getConnection() method
		Connection dbconn = DriverManager.getConnection(callConnection, 
								dbUser,
								dbPasswd);
		
		// Print message to console if connection succeeds
		System.out.println("Database connected.");
		
		// Return the Connection object
		return dbconn;
	}
	
	
	/******************************************************************
	 * 
	 * Database.fetchParts() method
	 * 
	 * This method will SELECT all parts from the database, create
	 * Part objects from the data, and print the data to the partsGrid
	 * in the GUI by calling the showAll() method from the Parts class
	 * 
	 * Requires argument:
	 *  Connection dbConn
	 * 
	 *****************************************************************/
	public static void fetchParts(Connection dbConn) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL query string
		String sqlFetch = "SELECT id,partNo,description,make,location," +
				  "inStock FROM parts ORDER BY id ASC;";
		
		// Try to query the db and save to ResultSet object
		try {
			
			// Create connection statement
			stmt = dbConn.createStatement();
			
			// Execute statement and save to a ResultSet
			ResultSet rs = stmt.executeQuery(sqlFetch);
			
			// Clear the current Part ArrayList
			Part.PARTS.clear();
									
			// While loop to create Part for row of ResultSet
			while (rs.next()) {
				
				// Get info from the SQL ResultSet
				int id = rs.getInt("id");
				String partNo = rs.getString("partNo");
				String description = rs.getString("description");
				String make = rs.getString("make");
				String location = rs.getString("location");
				int inStock = rs.getInt("inStock");
				
				// Create new Part object from data
				Part.createPart(id, partNo, description, 
						make, location, inStock);
				
			}
			
			// Call the Part method to print all data to screen
			Part.showAll();
					
		} 
		catch (Exception e) {
			// Print error message to screen if SELECT error
			System.out.println("Database error");
		} 
		// Finally to close the database connection
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			// Handle the possible Exception
			catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.searchPartsByNo() method
	 * 
	 * This method will SELECT entries from the database with a
	 * partNo similar to the user's entry, and print the results
	 * to the window
	 * 
	 * Requires arguments:
	 *  Connection dbconn,
	 *  String query
	 * 
	 *****************************************************************/
	public static void searchPartsByNo(Connection dbconn, 
					   String query) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL query string
		String sqlSelect = "SELECT id,partNo,description,make,location," +
			"inStock FROM parts WHERE partNo LIKE '%" + query + "%';";
		
		// Try to query the db and save to a ResultSet object
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement and save to a ResultSet
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			// Clear the current Part ArrayList
			Part.PARTS.clear();
			
			// While loop to create Part for row of ResultSet
			while (rs.next()) {
				
				// Get info from the SQL ResultSet
				int id = rs.getInt("id");
				String partNo = rs.getString("partNo");
				String description = rs.getString("description");
				String make = rs.getString("make");
				String location = rs.getString("location");
				int inStock = rs.getInt("inStock");
								
				// Create a new Part object from data
				Part.createPart(id, partNo, description,
						make, location, inStock);
				
			}
			
			// Call the Part method to print all data to screen
			Part.showAll();
			
		} 
		catch (Exception e) {
			// Print error message to console if SELECT error
			System.out.println("Database error");
		} 
		// Finally to close the database connection
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.fetchPartByNumber() method
	 * 
	 * This method will SELECT only a part with the exact part number
	 * entered as an argument; thus, it is not called with direct user
	 * input, but with auto input by button clicked. It then shows
	 * the Part object iteration properties, and allows for editing
	 * the database entry.
	 * 
	 * Requires arguments:
	 *  Connection dbconn,
	 *  String query
	 * 
	 *****************************************************************/
	public static void fetchPartByNumber(Connection dbconn, 
					     String query) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL query string
		String sqlSelect = "SELECT id,partNo,description,make," +
		     "location,inStock,updated FROM parts WHERE partNo = '" 
				+ query + "';";
		
		// Try to query the db and save to ResultSet object
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement and save to ResultSet
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			// ResultSet defautlts to before first, so skip empty row
			rs.next();
			
			// Get the info from SQL ResultSet
			int id = rs.getInt("id");
			String partNo = rs.getString("partNo");
			String description = rs.getString("description");
			String make = rs.getString("make");
			String location = rs.getString("location");
			int inStock = rs.getInt("inStock");
			Date updated = rs.getDate("updated");
			
			// Format the Date object as a String
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:MM");
			String updatedString = df.format(updated);
			
			// Create a Part object from result
			Part p = Part.createPart(id, partNo, description, 
						 make, location, inStock);
			
			// Clear the old data from partsGrid
			ClearPartsGrid.clearGrid();
			
			// Create Text objects of data
			Text partNoText = new Text(p.getPartNo());
			Text partDescText = new Text(p.getDescription());
			Text makeText = new Text(p.getMake());
			Text locationText = new Text(p.getLocation());
			Text inStockText = new Text(Integer.toString(
						     p.getInStock()));
			partsGrid.add(partNoText, 1, 1);
			partsGrid.add(partDescText , 2, 1);
			partsGrid.add(makeText, 3, 1);
			partsGrid.add(locationText, 4, 1);
			partsGrid.add(inStockText, 5, 1);
			
						
			// Create Labels and TextFields to update db entry
			Label currentLabel = new Label("Current:");
			Label updateLabel = new Label("Update:");
			TextField updatePartNo = new TextField();
			updatePartNo.setPromptText("New Part #");
			updatePartNo.setPrefColumnCount(10);
			TextField updateDesc = new TextField();
			updateDesc.setPromptText("New Descr.");
			TextField updateMake = new TextField();
			updateMake.setPromptText("New Make");
			TextField updateLocation = new TextField();
			updateLocation.setPromptText("New Location");
			updateLocation.setPrefColumnCount(5);
			TextField updateInStock = new TextField();
			updateInStock.setPromptText("New Qty (integer)");
			updateInStock.setPrefColumnCount(4);
			partsGrid.add(currentLabel, 0, 1);
			Label updatedLabel = new Label("Last updated");
			partsGrid.add(updatedLabel, 6, 0);
			Label updatedInfo = new Label(updatedString);
			partsGrid.add(updatedInfo, 6, 1);
			partsGrid.add(updateLabel, 0, 2);
			partsGrid.add(updatePartNo, 1, 2);
			partsGrid.add(updateDesc, 2, 2);
			partsGrid.add(updateMake, 3, 2);
			partsGrid.add(updateLocation, 4, 2);
			partsGrid.add(updateInStock, 5, 2);
			
			// Create button to save changes
			Button commitChange = new Button("Save changes");
			partsGrid.add(commitChange, 1, 4);
			
			// Set action on button click
			commitChange.setOnAction(
					e -> {
						
						// Declare variables to update entry
						String updatedPartNo;
						String updatedDesc;
						String updatedMake;
						String updatedLocation;
						int updatedInStock;
						
						// If TextFields are empty, that column will
						// remain the same, otherwise get new info
						if (!updatePartNo.getText().isEmpty()) {
							updatedPartNo = updatePartNo.getText();
						}
						else {
							updatedPartNo = p.getPartNo();
						}
						
						if (!updateDesc.getText().isEmpty()) {
							updatedDesc = updateDesc.getText();
						}
						else {
							updatedDesc = p.getDescription();
						}
						
						if (!updateMake.getText().isEmpty()) {
							updatedMake = updateMake.getText();
						}
						else {
							updatedMake = p.getMake();
						}
						
						if (!updateLocation.getText().isEmpty()) {
							updatedLocation = updateLocation.getText();
						}
						else {
							updatedLocation = p.getLocation();
						}
						
						try {
							updatedInStock = Integer.parseInt(
										updateInStock.getText());
						}
						catch (Exception e1) {
							updatedInStock = p.getInStock();
						}
						
						try {
							// Try to connect to update the part
							Database.updatePart(Database.connectDB(),
									    p.getPartNo(), 
									    updatedPartNo, 
									    updatedDesc, 
									    updatedMake, 
									    updatedLocation, 
									    updatedInStock);
							
							// If successful, clear the user's text
							updatePartNo.clear();
							updateDesc.clear();
							updateMake.clear();
							updateLocation.clear();
							updateInStock.clear();
							
							// Go to page with the updated current entry
							Database.fetchPartByNumber(Database.connectDB(), 
										   updatedPartNo);
							
						} 
						catch (Exception ex) {
							// If error, print message to console
							System.out.println("Method try failed");
						}
					}
				);
		} 
		catch (Exception e) {
			// If the SELECT failed, print message to console
			System.out.println("Database error");
		} 
		// Finally close the database connection
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.searchByMake() method
	 * 
	 * This method will SELECT entries from the database with a make
	 * similar to the user's entry, and print the results to the 
	 * window
	 * 
	 * Requires arguments:
	 *  Connection dbconn,
	 *  String query
	 * 
	 *****************************************************************/
	public static void searchByMake(Connection dbconn, String query) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL query string
		String sqlSelect = "SELECT id,partNo,description,make,location," +
				"inStock FROM parts WHERE make LIKE '%" + query + "%';";
		
		// Try to query the db and save to a ResultSet object
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement and save to ResultSet
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			// Clear the current Part ArrayList
			Part.PARTS.clear();
			
			// While loop to create Part for row of ResultSet
			while (rs.next()) {
				
				// Get info from the SQL ResultSet
				int id = rs.getInt("id");
				String partNo = rs.getString("partNo");
				String description = rs.getString("description");
				String make = rs.getString("make");
				String location = rs.getString("location");
				int inStock = rs.getInt("inStock");
				
				// Create a new Part object from data
				Part.createPart(id, partNo, description,
						make, location, inStock);
				
			}
			
			// Call the Part method to print all data to screen
			Part.showAll();
			
		} 
		catch (Exception e) {
			// Print error message to console if SELECT error
			System.out.println("Database error");
		} 
		// Finally close the database connection
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.searchPartsByDescription() method
	 * 
	 * This method will SELECT entries from the database with a desc-
	 * ription similar to the user's input, and print the results to
	 * the window
	 * 
	 * Requires arguments:
	 *  Connection dbconn,
	 *  String query
	 * 
	 *****************************************************************/
	public static void searchPartsByDescription(Connection dbconn, 
						    String query) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL query string
		String sqlSelect = "SELECT id,partNo,description,make," +
		     "location,inStock FROM parts WHERE description LIKE " +
				"'%" + query + "%';";
		
		// Try to query the db and save to a ResultSet object
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement and save to ResultSet
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			// Clear the current Part ArrayList
			Part.PARTS.clear();
			
			// While loop to create Part for row of ResultSet
			while (rs.next()) {
				
				// Get info from the SQL ResultSet
				int id = rs.getInt("id");
				String partNo = rs.getString("partNo");
				String description = rs.getString("description");
				String make = rs.getString("make");
				String location = rs.getString("location");
				int inStock = rs.getInt("inStock");
				
				// Create a new Part object from data
				Part.createPart(id, partNo, description,
						make, location, inStock);

			}
			
			// Call the Part method to print all data to screen
			Part.showAll();
			
		}
		catch (Exception e) {
			// Print error message to console if SELECT error
			System.out.println("Database error");
		} 
		// Finally close the database connection
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.searchPartsByLocation() method
	 * 
	 * This method will SELECT entries from the database with a locat-
	 * ion similar to the user's entry, and print the results to the
	 * window.
	 * 
	 * Requires arguments:
	 *  Connection dbconn,
	 *  String query
	 * 
	 *****************************************************************/
	public static void searchPartsByLocation(Connection dbconn, 
						 String query) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL SELECT string
		String sqlSelect = "SELECT id,partNo,description,make,location,inStock FROM parts " +
				   "WHERE location LIKE '%" + query + "%';";
		
		// Try to retrieve data from db and save to ResultSet
		try {
			
			// Create database connection statement
			stmt = dbconn.createStatement();

			// Execute statement and save to ResultSet
			ResultSet rs = stmt.executeQuery(sqlSelect);
			
			int i = 1;
			while (rs.next()) {
				int id = rs.getInt("id");
				String partNo = rs.getString("partNo");
				String description = rs.getString("description");
				String make = rs.getString("make");
				String location = rs.getString("location");
				int inStock = rs.getInt("inStock");
								
				Part p = Part.createPart(id, partNo, description, make, location, inStock);
				Text partNoText = new Text(p.getPartNo());
				Text partDescText = new Text(p.getDescription());
				Text makeText = new Text(p.getMake());
				Text locationText = new Text(p.getLocation());
				Text inStockText = new Text(Integer.toString(p.getInStock()));
				
				Button editButton = new Button("Edit");
				
				editButton.setOnAction(
						e -> {
							Part.editPartScreen(partNo);
						}
				);
				
				partsGrid.add(editButton, 0, i);
				
				partsGrid.add(partNoText, 1, i);
				partsGrid.add(partDescText , 2, i);
				partsGrid.add(makeText, 3, i);
				partsGrid.add(locationText, 4, i);
				partsGrid.add(inStockText, 5, i);
				i++;
			}
		} catch (Exception e) {
			System.out.println("Database error");
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Database error");
			}
		}
	}
	
	/*********************************************************************
	 *
	 * Database.addPart() method
	 *
	 * This method will retrieve input from user to add a new part entry
	 * to the database with a SQL INSERT statement. The PRIMARY KEY int
	 * will be set automatically be AUTO_INCREMENT.
	 *
	 * Requires arguments:
	 *  Connection dbconn (the DB connection object),
	 *  String partNo (matching a SQL VARCHAR(32)),
	 *  String description (matching a SQL VARCHAR(64)),
	 *  String make (matching a SQL VARCHAR(32)),
	 *  String location (matching a SQL VARCHAR(32)),
	 *  int inStock (matching a SQL INT(11))
	 *
	 ********************************************************************/
	public static void addPart(Connection dbconn,
								String partNo,
								String description,
								String make,
								String location,
								int inStock) {
		Statement stmt = null;
		
		String sqlInsert = "INSERT INTO parts (partNo,description,make,location,inStock) VALUES(" +
				"\"" + partNo + "\",\"" + description + "\",\"" + make + "\",\"" + location + 
				"\"," + inStock + ");";
		
		try {
			stmt = dbconn.createStatement();
			stmt.execute(sqlInsert);
			
			System.out.println("Entry added to database");
			
		} catch (Exception e) {
			System.out.println("Database error");
		}
	}
	
	public static void updatePart(Connection dbconn,
									String partNo,
									String newPartNo,
									String description,
									String make,
									String location,
									int inStock) {
		Statement stmt = null;
		
		String sqlInsert = "UPDATE parts SET partNo = \"" + newPartNo + "\", description = \"" + 
				   description + "\", " + "make = \"" + make + "\", location = \"" + location + 
				   "\", inStock = " + inStock + ", updated = " + 
				   "CURRENT_TIMESTAMP WHERE partNo = \"" + partNo + "\";";
		
		try {
			stmt = dbconn.createStatement();
			stmt.execute(sqlInsert);
			System.out.println("Changes saved successfully");
			
		} catch (Exception e) {
			System.out.println("Database error: Changes not saved");
		}
	}
}

class ClearPartsGrid extends StartPage {
	public static void clearGrid() {
		partsGrid.getChildren().clear();
		Label num = new Label("Part #");
		partsGrid.add(num, 1, 0);
		Label descr = new Label("Description");
		partsGrid.add(descr, 2, 0);
		Label mke = new Label("Make");
		partsGrid.add(mke, 3, 0);
		Label loc = new Label("Location");
		partsGrid.add(loc, 4, 0);
		Label stock = new Label("In stock");
		partsGrid.add(stock, 5, 0);
	}
}

class ClearWindow extends StartPage {
	public static void clearWin() {
		mainGrid.getChildren().clear();
		
		Text welcomeMsg = new Text("Welcome to the PVGC Maintenance Catalog App");
		welcomeMsg.setId("testID");
		mainGrid.add(welcomeMsg, 0, 0);
		
		Button addNewPart = new Button("Add New Part");
		mainGrid.add(addNewPart, 2, 0);
		addNewPart.setOnAction(
				e -> {
					Part.addPartScreen();
				}
		);
		
		Text searchByPart = new Text("Search by part number:");
		TextField partSearchBox = new TextField();
		mainGrid.add(searchByPart, 0, 2);
		mainGrid.add(partSearchBox, 1, 2);
		Button partNumGo = new Button("Find by Part #");
		mainGrid.add(partNumGo, 2, 2);
		partNumGo.setOnAction( 
				e -> {
					try {
						String searchQuery = partSearchBox.getText();
						Connection dbconn = Database.connectDB();
						ClearPartsGrid.clearGrid();
						System.out.println("Finding parts like " + searchQuery);
						Database.searchPartsByNo(dbconn, searchQuery);
					} catch (Exception ex) {
						System.out.println("Database error");
					}
				}
		);
		
		Text searchByMake = new Text("Search by machine make:");
		TextField makeSearchBox = new TextField();
		mainGrid.add(searchByMake, 0, 3);
		mainGrid.add(makeSearchBox, 1, 3);
		Button makeGo = new Button("Find by make");
		mainGrid.add(makeGo, 2, 3);
		makeGo.setOnAction(  
				e -> {
					try {
						String searchQuery = makeSearchBox.getText();
						Connection dbconn = Database.connectDB();
						ClearPartsGrid.clearGrid();
						System.out.println("Finding makes like " + searchQuery);
						Database.searchByMake(dbconn, searchQuery);
					} catch (Exception ex) {
						System.out.println("Database error");
					}
				}
		);
		
		Text searchByDescription = new Text("Search by part description:");
		TextField descriptionSearchBox = new TextField();
		mainGrid.add(searchByDescription, 0, 4);
		mainGrid.add(descriptionSearchBox, 1, 4);
		Button descriptionGo = new Button("Find by description");
		mainGrid.add(descriptionGo, 2, 4);
		descriptionGo.setOnAction(
				e -> {
					try {
						String searchQuery = descriptionSearchBox.getText();
						Connection dbconn = Database.connectDB();
						ClearPartsGrid.clearGrid();
						System.out.println("Finding parts like " + searchQuery);
						Database.searchPartsByDescription(dbconn, searchQuery);
					} catch (Exception ex) {
						System.out.println("Database error");
					}
				}				
		);
		
		Text searchByLocation = new Text("Search by location:");
		TextField locationSearchBox = new TextField();
		locationSearchBox.setPromptText("e.g. A-1");
		mainGrid.add(searchByLocation, 0, 5);
		mainGrid.add(locationSearchBox, 1, 5);
		Button locationGo = new Button("Find by location");
		mainGrid.add(locationGo, 2, 5);
		locationGo.setOnAction(
				e -> {
					try {
						String searchQuery = locationSearchBox.getText();
						Connection dbconn = Database.connectDB();
						ClearPartsGrid.clearGrid();
						System.out.println("Finding parts located at " + searchQuery);
						Database.searchPartsByLocation(dbconn, searchQuery);
					} catch (Exception ex) {
						System.out.println("Database error");
					}
				}
		);
		
		Button viewAllButton = new Button("View all parts");
		mainGrid.add(viewAllButton, 0, 1);
		
		viewAllButton.setOnAction(
				e -> {
					try {
						Connection dbconn = Database.connectDB();
						ClearPartsGrid.clearGrid();
						Database.fetchParts(dbconn);
					} catch (Exception ex) {
						System.out.println("Database error");
					}
				}				
		);
	}
}

class CloseWindowHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent e) {
		System.out.println("Exiting");
		Platform.exit();
	}
}
