package services;

import enums.Cell;

public interface ScreenService {
	
	
	int getHeight();
	int getWidth();
	
	/**
	 * pre:  
	 * (0<= x and x < getHeight()) && (0<=y and y <getWidth())
	 * 
	 * 
	 */
	Cell cellNature(int x,int y);
	
	/* Constructors */
	/**
	 * pre: 0<h && 0<w
	 * post:  getHeight() == h && getWidth() == w 
	 *&& \forall x:Integer \in [0..getHeight()] {
	 *			\forall y:Integer \in [0..getWidth()]{
	 *				cellNature(x,y) == EMP
	 *           }     
	 *           } 
	 */
	public void init(int h, int w);
	
	/* Operators */
	/**
	 * pre: cellNature(x,y) = PLT
	 * post:cellNature(u,v) = HOL
	 * &&
	 *  \forall x:Integer \in [0..getHeight()] {
	 *			\forall y:Integer \in [0..getWidth()]{
	 *				x!= u && y !=v => cellNature(u,v) == cellNature(x,y)
	 *           }     
	 *           } 
	 */
	public void dig(int u, int v);
	
	/**
	 *pre: cellNature(x,y) = HOL
	 *post:cellNature(u,v) = PLT
	 * &&
	 *  \forall x:Integer \in [0..getHeight()] {
	 *			\forall y:Integer \in [0..getWidth()]{
	 *				x!= u && y !=v => cellNature(u,v) == cellNature(x,y)
	 *           }     
	 * }
	 */
	public void fill(int u, int v);
	
	public Cell[][] getStateMatrice();

}
