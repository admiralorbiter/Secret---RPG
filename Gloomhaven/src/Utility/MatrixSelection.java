package Utility;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Unsorted.GUI;
import Unsorted.GUISettings;
import Unsorted.Item;

/**
 * A general class to create a matrix that can be used select items
 * @author admir
 */
public class MatrixSelection {
	private int width;															//Pixel Width of Matrix
	private int height;															//Pixel Height of Matrix
	private int itemTotal;														//Number of items in the matrix
	private int row;															//Number of Rows
	private int col;															//Number of Columns
	private List<Point> itemIndexes = new ArrayList<>();						//Keeps track of item index for selection
	
	/**
	 * Creates a matrix that holds items for selection
	 * @param width			Pixel Width
	 * @param height		Pixel Height
	 * @param itemTotal		Items in Matrix
	 */
	//TODO - Need to refactor so row and col is switched
	public MatrixSelection(int width, int height, int itemTotal) {
		this.width=width;
		this.height=height;
		this.itemTotal=itemTotal;
		
		//Rows and Col determined by number of items
		if(itemTotal<9) {
			row=3;
			//col=3;
		}else if(itemTotal<16) {
			row=4;
			//col=4;
		}
		else if(itemTotal<25) {
			row=5;
			//col=5;
		}else {
			row=6;
			//col=6;
		}
		if(itemTotal%row==0)
			col=itemTotal/row;
		else
			col=itemTotal/row+1;
	}
	
	/**
	 * Draws the items for selection
	 * @param g				Graphics object
	 * @param itemList		Item List
	 */
	public void drawSelection(Graphics g, List<Item> itemList) {
		int drawRow=0;
		int drawCol=0;
		
		//Adds the items index for later recall and draws each item text
		for(int i=0; i<itemTotal; i++) {
			itemIndexes.add(new Point(drawRow, drawCol));

			if(i%row==0) {
				if(i!=0)
					drawRow++;
				drawCol=0;
			}
			else {
				drawCol++;
			}
		
			GUI.drawMatrixSelection(g, drawRow, drawCol, width/row, height/col+5, itemList.get(i));
		}
	}
	
	private boolean betweenZeroAndCol(Point selection) {
		return (selection.getX()>=0 && selection.getX()<=col);
	}
	
	private boolean betweenZeroAndRow(Point selection) {
		return (selection.getY()>=0 && selection.getY()<=row);
	}
	
	/**
	 * Updates the matrix with mouse click and returns index if item selected
	 * @param mouseClick	Last mouse click pixel position
	 * @return Item Index if an item is selected
	 */
	public int selectItem(Point mouseClick) {
		
		if(mouseClick==null)													//If no mouse click, return no item selection flag														
			return -99;
		
		Point selection = new Point(findSelection(mouseClick));					//Finds matrix index based on mouse click
		
		if(betweenZeroAndCol(selection) && betweenZeroAndRow(selection))		//If matrix index is in bounds, return item index
				return itemIndexes.indexOf(new Point(selection));

		return -99;																//return no item selection flag	
	}
	
	/**
	 * Finds the matrix index of the mouse click
	 * @param  mouseClick	Last mouse click pixel position	
	 * @return Matrix index of the mouse click
	 */
	public Point findSelection(Point mouseClick) {
		Point point=null;
		
		int x = mouseClick.x-GUISettings.width/2;
		int y=mouseClick.y-GUISettings.height/6+15;
		int w=width/col;
		int h=height/row;
			
		x=x/w;
		y=y/h;
		point = new Point(x, y);
		return point;
	}

}
