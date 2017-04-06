package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.*;


/*
 * ####################################################################
 * StartPage class
 * Extends the javafx.Application class
 *
 * This class creates the JavaFX GUI application for the 
 * PVGC Parts Catalog
 * ####################################################################
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
