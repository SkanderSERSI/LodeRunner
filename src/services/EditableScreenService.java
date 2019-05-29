package services;

import enums.Cell;

public interface EditableScreenService extends ScreenService {

	/**
	 * 
	 * post: \forall x:Integer in [0,getHeight()]{
	 * 				\forall y:Integer in [0,getWidth()]{
	 * 					getCellNature(x,y) != HOL && 
	 * 					\forall y in [0,getWidth()]{
	 * 						getCellNature(x,) == MTL
	 * 				}
	 * 				}
	 * }
	 * 
	 */
	public boolean Playable();
	
	/**
	 * pre:0<=x && x<getHeight() && 0<= y && y< getWidth()
	 * post:getCellNature(x,y,c) = c
	 *&& \forall x:Integer \in [0..getHeight()] {
	 *			\forall y:Integer \in [0..getWidth()]{
	 *				x!= u && y !=v => getCellNature(u,v) == getCellNature(x,y)
	 *           }     
	 * }
	 */
	
	public void setNature(int x,int y,Cell c);
}
