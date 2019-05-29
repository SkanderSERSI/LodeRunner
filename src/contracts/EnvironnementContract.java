package contracts;

import decorators.EnvironnementDecorators;
import enums.Cell;
import enums.ItemType;
import exceptions.InvariantError;
import exceptions.PostconditionError;
import exceptions.PreconditionError;
import services.EnvironnementService;
import services.CellContentService;
import services.CharacterService;

public class EnvironnementContract extends EnvironnementDecorators {

	public EnvironnementContract(EnvironnementService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		
		// inv:  
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()] {
		// \forall c1,c2:Character \in cellContent(x,y)^2 { 
		//		c1=c2 
		//	   }
		//   }
		// }
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				CharacterService c1 = super.cellContent(x, y).getCharacter();
				CharacterService c2 = super.cellContent(x, y).getCharacter();
				if(c1 != c2) {
					throw new InvariantError("Plus d'un personnage sur la case");
				}
			}
		}
		
		// inv:  
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()] {
		// 		cellNature(x,y):Cell \in {MTL, PLT} 
		//			\implies not exists Character c \in cellContent(x,y) 
		//				and not exists Item t \in cellContent(x,y)
		//   }
		// }
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if(cellNature(x, y) == Cell.MTL || cellNature(x, y) == Cell.PLT) {
					if(((super.cellContent(x, y).getCharacter() != null && super.cellContent(x, y).getItem() != null))) {
						throw new InvariantError("Une cell de type MTL ou PLT ne peut contenir un character ou un item");
					}
				}
			}
		}
		
		// inv:  
		// \forall x:Integer \in [0..getHeight()] {
		// \forall y:Integer \in [0..getWidth()] {
		// 		exists t:Item \in cellContent(x,y).getItem() \implies cellNature(x,y) = EMP && cellNature(x-1,y) in {PLT,MTL}
		//   }
		// }
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if (super.cellContent(x, y).getItem() != null) {
					if(super.cellContent(x, y).getItem().nature() != ItemType.Treasure  && (super.cellNature(x,y) != Cell.EMP || (super.cellNature(x-1, y) != Cell.PLT
							&& super.cellNature(x-1,y ) != Cell.MTL))) {
							throw new InvariantError("Un item ne peut se trouver au dessus du vide ou se trouver dans une cell non EMP");
						}
					}
			}
		}
		
	}
	
	
	@Override
	public void init(int h, int w) {
		//pre: h>0 && w>0
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
	public CellContentService cellContent(int x, int y) {
		// pre:
		// x>=0 && x<height() && y>=0 && y<width()
		if (x< 0 || x>=getHeight() || y<0 || y>=getWidth()) {
			throw new PreconditionError("Precondition error : x et y hors cadre");
		}
		checkInvariant();
		return super.cellContent(x, y);
	}
}