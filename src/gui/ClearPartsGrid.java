package gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/*
 * ####################################################################
 * ClearPartsGrid class
 * 
 * The ClearPartsGrid class clears all existing elements from the
 * partsGrid region (the scrollable pane of the window), and 
 * repopulates the header labels at the top.
 * ####################################################################
 */
class ClearPartsGrid extends StartPage {
	
	/******************************************************************
	 * 
	 * ClearPartsGrid.clearGrid() method
	 * 
	 * This method will clear all child elements from the 
	 * StartPage.partsGrid pane and write the header labels for
	 * displaying part entries.
	 * 
	 *****************************************************************/
	public static void clearGrid() {
		
		// Clear all current child elements from the pane
		partsGrid.getChildren().clear();
		
		// Create header Labels and add to the top row of the pane
		Text num = new Text("Part #");
		num.setFill(Color.GREEN);
		num.setFont(Font.font("serif", FontWeight.BOLD, 16));
		partsGrid.add(num, 1, 0);
		Text descr = new Text("Description");
		descr.setFill(Color.GREEN);
		descr.setFont(Font.font("serif", FontWeight.BOLD, 16));
		partsGrid.add(descr, 2, 0);
		Text mke = new Text("Make");
		mke.setFill(Color.GREEN);
		mke.setFont(Font.font("serif", FontWeight.BOLD, 16));
		partsGrid.add(mke, 3, 0);
		Text loc = new Text("Location");
		loc.setFill(Color.GREEN);
		loc.setFont(Font.font("serif", FontWeight.BOLD, 16));
		partsGrid.add(loc, 4, 0);
		Text stock = new Text("In stock");
		stock.setFill(Color.GREEN);
		stock.setFont(Font.font("serif", FontWeight.BOLD, 16));
		partsGrid.add(stock, 5, 0);
	}
}

