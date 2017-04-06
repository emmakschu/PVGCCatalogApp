package gui;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/*
 * ####################################################################
 * Database class
 * 
 * The Database class stores the information and methods needed to
 * connect to and interact with the database.
 *  ###################################################################
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
			Text updatedLabel = new Text("Last updated");
			updatedLabel.setFill(Color.GREEN);
			updatedLabel.setFont(Font.font("serif", FontWeight.BOLD, 16));
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
		String sqlSelect = "SELECT id,partNo,description,make,location," +
				"inStock FROM parts WHERE location LIKE '%" + query + "%';";
		
		// Try to retrieve data from db and save to ResultSet
		try {
			
			// Create database connection statement
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
			
			// Call the Part method to print results to screen
			Part.showAll();
		} 
		catch (Exception e) {
			// Print error message if SELECT error
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
	 * Database.addPart() method
	 *
	 * This method will retrieve input from user to add a new part 
	 * entry to the database with a SQL INSERT statement. The PRIMARY 
	 * KEY int will be set automatically be AUTO_INCREMENT.
	 *
	 * Requires arguments:
	 *  Connection dbconn (the DB connection object),
	 *  String partNo (matching a SQL VARCHAR(32)),
	 *  String description (matching a SQL VARCHAR(64)),
	 *  String make (matching a SQL VARCHAR(32)),
	 *  String location (matching a SQL VARCHAR(32)),
	 *  int inStock (matching a SQL INT(11))
	 *
	 *****************************************************************/
	public static void addPart(Connection dbconn,
				   String partNo,
				   String description,
				   String make,
				   String location,
				   int inStock) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL insert string
		String sqlInsert = "INSERT INTO parts (partNo,description,make," +
				"location,inStock) VALUES(\"" + partNo + "\",\"" +
				description + "\",\"" + make + "\",\"" + location + 
				"\"," + inStock + ");";
		
		// Try to query the db and INSERT new part entry
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement to add entry
			stmt.execute(sqlInsert);
			
			// Print message to console if successful
			System.out.println("Entry added to database");
			
		} 
		catch (Exception e) {
			// Print error message to console if SQL error
			System.out.println("Database error");
		}
	}
	
	
	/******************************************************************
	 * 
	 * Database.updatePart() method
	 * 
	 * This method will retrieve input from user to update entries for
	 * an existing part in the database. Fields that are left blank
	 * will default to keep the current entry, while those that are
	 * filled will update the field in the database.
	 * 
	 * Requires arguments:
	 *  Connection dbconn (the DB connection object),
	 *  String partNo (matching a SQL VARCHAR(32)),
	 *  String description (matching a SQL VARCHAR(64)),
	 *  String make (matching a SQL VARCHAR(32)),
	 *  String location (matching a SQL VARCHAR(32)),
	 *  int inStock (matching a SQL INT(11))
	 * 
	 *****************************************************************/
	public static void updatePart(Connection dbconn,
				      String partNo,
				      String newPartNo,
				      String description,
				      String make,
				      String location,
				      int inStock) {
		
		// Declare Statement object, initialize to null
		Statement stmt = null;
		
		// Write SQL UPDATE string
		String sqlUpdate = "UPDATE parts SET partNo = \"" + newPartNo + 
				"\", description = \"" + description + "\", " + 
				"make = \"" + make + "\", location = \"" + location + 
				"\", inStock = " + inStock + ", updated = " + 
				"CURRENT_TIMESTAMP WHERE partNo = \"" + partNo + "\";";
		
		// Try to query the db and update part entry
		try {
			
			// Create connection statement
			stmt = dbconn.createStatement();
			
			// Execute statement to update entry
			stmt.execute(sqlUpdate);
			
			// Print message to console if successful
			System.out.println("Changes saved successfully");
			
		} 
		catch (Exception e) {
			// Print message to console if SQL error
			System.out.println("Database error: Changes not saved");
		}
	}
}

