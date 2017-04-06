package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/*
 * ####################################################################
 * CloseWindowHandler class
 * 
 * The CloseWindowHandler class provides an ActionEvent handler to
 * close the window and terminate the JavaFX application.
 * ####################################################################
 */
class CloseWindowHandler implements EventHandler<ActionEvent> {
	
	// Override the default handle method
	@Override
	public void handle(ActionEvent e) {
		
		// Print exiting message to console
		System.out.println("Exiting");
		
		// Exit the JavaFX Application platform
		Platform.exit();
	}
}
