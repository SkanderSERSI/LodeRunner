package contracts;

import decorators.ScreenDecorator;
import enums.Cell;
import exceptions.PostconditionError;
import exceptions.PreconditionError;
import services.ScreenService;

public class ScreenContract extends ScreenDecorator {
	
	
	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	}
	
	@Override
	public void init(int h, int w) {
		//pre: 0<h && 0<w
		if(0>=h || 0>=w) {
			throw new PreconditionError("Erreur h et w negatifs");
		}
		checkInvariant();
		super.init(h, w);
		checkInvariant();
		
		// post:  getHeight() == h && getWidth() == w 
		if (getHeight() != h || getWidth() != w) {
			throw new PostconditionError("Erreur de post condition h et w different du vrai height et width");
		}
		
		// post:
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()]{
		// cellNature(x,y) == EMP }     
		// } 
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (cellNature(i,j) != Cell.EMP) {
					throw new PostconditionError("Erreur de post condition cell nature differente de EMP");
				}
			}
			
		}
		
		
	}
	
	@Override
	public Cell cellNature(int x, int y) {
		//pre: (0 <= y and y < getWidth()) && (0 <= x and x < getHeight())
		if (0>y || y>=getWidth() || 0>x || x>=getHeight()){
			throw new PreconditionError("Erreur de precondition  y ou x hors screen");
		}
		return super.cellNature(x, y);
	}
	
	@Override
	public void dig(int u, int v) {
		 //pre: cellNature(x,y) = PLT
		if (cellNature(u,v) != Cell.PLT) {
			throw new PreconditionError("Erreur de pre condition  la nature de la cellule est differente de PLT");
		}
		
		// captures
		Cell[][] capture = getStateMatrice();
		
		checkInvariant();
		super.dig(u, v);
		checkInvariant();
		
		//post:cellNature(u,v) = HOL
		if (cellNature(u, v) != Cell.HOL) {
			throw new PostconditionError("Erreur la nature de la cell digged n'est pas HOL");
		}
		
		// post :
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()]{
		// 		x != u || y != v implies cellNature(u,v) == cellNature(x,y)
		// }     
		// } 
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if  ((i != u || j != v) && (capture[i][j] != cellNature(i,j))) {
					throw new PostconditionError(" Erreur on a change des cells qu'il ne fallait pas changer");
				}
				
			}
			
		}
		
	}
	
	@Override
	public void fill(int u, int v) {
		// pre: cellNature(x,y) = HOL
		if(cellNature(u,v) !=  Cell.HOL) {
			throw new PreconditionError(" Erreur de précondition la cellule n est pas vide");
		}
		// capture
		Cell[][] capture = getStateMatrice();
		checkInvariant();
		super.fill(u, v);
		checkInvariant();
		
		// post: cellNature(u,v) = PLT
		if( cellNature(u,v) != Cell.PLT) {
			throw new PostconditionError("Erreur de post-condition la cellule filled n'est pas de Nature PLT");
		}
		
		// \forall x:Integer \in [0..getHeight()] {
		//			\forall y:Integer \in [0..getWidth()]{
		// x!= u || y !=v => cellNature(u,v) == cellNature(x,y)
		// }     
		// }
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if  ((i != u || j != v) && (capture[i][j] != cellNature(i,j))) {
					throw new PostconditionError("Erreur on a change une cell qu'il ne fallait pas changer");
				}
			}
		}
	}
	
	
	

}
