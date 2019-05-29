package contracts;

import decorators.EditableScreenDecorator;
import enums.Cell;
import exceptions.InvariantError;
import exceptions.PostconditionError;
import exceptions.PreconditionError;

public class EditableScreenContract extends EditableScreenDecorator {

	public EditableScreenContract(EditableScreenDecorator delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
		// inv: forall x in[0..height]
		//			cellNature(x,0) == MTL
		//      	forall y in[0..width]
		//				cellNature(x,y) != HOL 
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if(cellNature(i, j) == Cell.HOL) {
					throw new InvariantError("La cell x = " + i + " et y = " + j + " est a l'etat HOL");
				}
				if(cellNature(i, 0) != Cell.MTL) {
					throw new InvariantError("La cell x = "+i+" et y = 0 n'est pas a l'etat MTL");
				}
			}
		}
	}
	
	@Override
	public boolean Playable() {
		checkInvariant();
		return super.Playable();
	}
	
	@Override
	public void setNature(int x, int y, Cell c) {
		
		// pre: 0<=x && x<getHeight() && 0<= y && y< getWidth()
		if (0>x || x>=getHeight() || 0>y || y>= getWidth()) {
			throw new PreconditionError("Erreur de precondition x ou y hors cadre");
		}
		
		Cell[][] capture = getStateMatrice();
		checkInvariant();
		super.setNature(x, y, c);
		checkInvariant();
		
		//post:cellNature(x,y,c) = c
		if (cellNature(x, y) != c) {
			throw new PostconditionError("Erreur de post-condition la nature de la cellule n'a pas change");
		}
		
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()] {
		// 		x!= u && y !=v => cellNature(u,v) == cellNature(x,y)
		//   }     
		// }
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if ( (i!= x && j!=y) && (capture[i][j] != cellNature(i,j) )        ) {
					throw new PostconditionError("Erreur de post-condition on change la nature d'une cellule qu'il ne fallait pas changer");
				}
			}
		}
	}
	

}
