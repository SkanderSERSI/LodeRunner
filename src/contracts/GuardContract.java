package contracts;

import decorators.GuardDecorator;
import enums.Cell;
import enums.ItemType;
import enums.Move;
import exceptions.InvariantError;
import exceptions.PostconditionError;
import exceptions.PreconditionError;
import impl.ItemImpl;
import services.EnvironnementService;
import services.GuardService;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService guard) {
		super(guard);
	}

	public void checkInvariant() {
		// inv : cellNature(getEnvi(c),getHeight(c),getWidth) in { EMP,HOL,LAD,HDR}
		EnvironnementService env = getEnvi();
		if (env.cellNature(getHeight(), getWidth()) != Cell.EMP && env.cellNature(getHeight(), getWidth()) != Cell.HOL
				&& env.cellNature(getHeight(), getWidth()) != Cell.LAD
				&& env.cellNature(getHeight(), getWidth()) != Cell.HDR) {
			throw new InvariantError("Invariant error:  La nature de la cellule est differente de EMP,HOL,HDR,LAD ");

		}

		// inv : getHeight(c) >=0 && getHeight(c)<Environnement::getHeight() && getWidth(c) >=0 &&
		// 0<=getWidth(c)<Environnement::getWidth() && 0 <= getHeight(c)<Environnement::getHeight()
		if (getHeight() < 0 || (getHeight() >= env.getHeight()) || (getWidth() < 0) || (getWidth() >= env.getWidth())) {
			throw new InvariantError("Invariant error: guard sort de la map ");
		}

		// inv
		// Environment::CellNature(Envi(G),Hgt(G),Wdt(G)) = LAD
		// and Hgt(G) < Character::Hgt(Target(G))
		// and Environment::CellNature(Envi(G),Hgt(G)+1,Wdt(G)) not in {PLT, MTL}
		// and not exists Character c in Environment::CellContent(Envi(G),Hgt(G)+1,Wdt(G))
		// and Environment::Hgt(Target(G)) - Hgt(G) < |Environment::Wdt(Target(G))-Wdt(G)|
		// and not getCatched()
		// implies Behaviour(G) = Up
		if (getEnvi().cellNature(getHeight(), getWidth()) == Cell.LAD 
			&& getHeight() < getTarget().getHeight()
			&& getEnvi().cellNature(getHeight() + 1, getWidth()) != Cell.PLT
			&& getEnvi().cellNature(getHeight() + 1, getWidth()) != Cell.MTL
			&& getEnvi().cellContent(getHeight() - 1, getWidth()).getCharacter() == null
			&& getTarget().getHeight() - getHeight() < Math.abs(getTarget().getWidth() - getWidth())
			&& !getCatched()
			&& behaviour() != Move.Up) {
			throw new InvariantError("Invariant error : guard doit faire UP");
		}
		
		
		
		
		
//		(Environment::CellNature(Envi(G),Hgt(G),Wdt(G) = LAD
//				 and Hgt(G) > Character::Hgt(Target(G))
//				 and Environment::CellNature(Envi(G),Hgt(G)-1,Wdt(G)) not in {PLT, MTL}
//				 and not exists Character c in Environment::CellContent(Envi(G),Hgt(G)+1,Wdt(G))
//				 and Environment::Hgt(Target(G)) - Hgt(G) > |Environment::Wdt(Target(G))-Wdt(G)|)
//				 and !getCatched())
//				 implies Behaviour(G) = Down
		if (getEnvi().cellNature(getHeight(), getWidth()) == Cell.LAD 
				&& getHeight() > getTarget().getHeight()
				&& getEnvi().cellNature(getHeight() - 1, getWidth()) != Cell.PLT
				&& getEnvi().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
				&& getEnvi().cellContent(getHeight() - 1, getWidth()).getCharacter() == null
				&& (getTarget().getHeight() - getHeight() < Math.abs(getTarget().getWidth() - getWidth())
				&& !getCatched()
				&& behaviour() != Move.Down)) {
			throw new InvariantError("Invariant error : guard doit faire Down");
		}

		
//		Environnement::cellNature(getHeight(),getWidth()) not in {LAD,HOL}
//      and Environnement::cellNature(getHeight(),getWidth()) != EMP
//		 and Environnement::cellNature(getHeight()-1,getWidth()) != HDR
//				 and Environnement::cellNature(getHeight(),getWidth()) in {HDR}
//				 or Environnement::cellNature(getHeight()-1,getWidth()) in {PLT,MTL}
//				 or (Environnement::cellNature(getHeight()-1,getWidth()) in {HOL,EMP}
//				      and Character c in Environnement::cellContent(getHeight()-1,getWidth()))
//				 and getTarget().getWidth() < getWidth()
//				 and !getCatched()
//				 and Environnement::cellNature(getHeight(),getWidth()-1) != Cell.PLT
//      and Environnement::cellNature(getHeight()-1,getWidth()) != Cell.EMP
//		 and Player C not in Environment::CellContent(getHeight(),getWidth()-1)
//		 and Guard G not in Environment::CellContent(getHeight()-1,getWidth())
//				 implies behaviour() == Move.Left
		
		if(getEnvi().cellNature(getHeight(), getWidth()) != Cell.LAD && getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL
				&& getEnvi().cellNature(getHeight(), getWidth()) != Cell.EMP && (getEnvi().cellNature(getHeight()-1, getWidth()) != Cell.HDR)		
		&& ((getEnvi().cellNature(getHeight(), getWidth()) == Cell.HDR)
		|| (getEnvi().cellNature(getHeight()-1, getWidth()) == Cell.PLT || getEnvi().cellNature(getHeight()-1, getWidth()) == Cell.MTL)
		|| (getEnvi().cellContent(getHeight()-1,getWidth()).getCharacter() != null))
		&& getTarget().getWidth() < getWidth() && behaviour() != Move.Left
		&& !getCatched()
		&& getEnvi().cellNature(getHeight(), getWidth()-1) != Cell.PLT
		&& getEnvi().cellNature(getHeight()-1, getWidth()) != Cell.EMP
		&& !(getEnvi().cellContent(getHeight(), getWidth()-1).getCharacter() instanceof PlayerContract
		&& !(getEnvi().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract))){
			throw new  InvariantError("Invarriant error: guard doit aller à gauche");
			
		}
		
//		 Environnement::cellNature(getHeight(),getWidth()) not in {LAD,HOL}
//       and Environnement::cellNature(getHeight(),getWidth()) != EMP
// 		 and Environnement::cellNature(getHeight()-1,getWidth()) != HDR
//		 and (Environnement::cellNature(getHeight(),getWidth()) in {HDR}
//		 or Environnement::cellNature(getHeight()-1,getWidth()) in {PLT,MTL}
//		 or (Environnement::cellNature(getHeight()-1,getWidth()) in {HOL,EMP}
//		      and Character c in Environnement::cellContent(getHeight()-1,getWidth())))
//		 and getTarget().getWidth() > getWidth()
//       and !getCatched()
//		 and Environnement::cellNature(getHeight(),getWidth()+1) != Cell.PLT
//       and Environnement::cellNature(getHeight()-1,getWidth()) != Cell.EMP
// 		 and Player C not in Environment::CellContent(getHeight(),getWidth()+1)
//		 and Guard G not in Environment::CellContent(getHeight()-1,getWidth())
//		 implies behaviour() == Move.Right
		
		if(getEnvi().cellNature(getHeight(), getWidth()) != Cell.LAD && getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL
				&& getEnvi().cellNature(getHeight(), getWidth()) != Cell.EMP && (getEnvi().cellNature(getHeight()-1, getWidth()) != Cell.HDR)
				&& (getEnvi().cellNature(getHeight(), getWidth()) == Cell.HDR
				|| (getEnvi().cellNature(getHeight()-1, getWidth()) == Cell.PLT || getEnvi().cellNature(getHeight()-1, getWidth()) == Cell.MTL)
				|| (getEnvi().cellContent(getHeight()-1,getWidth()).getCharacter() != null))
				&& getTarget().getWidth() > getWidth() && behaviour() != Move.Right && !getCatched()
				&& getEnvi().cellNature(getHeight(), getWidth()+1) != Cell.PLT 
				&& getEnvi().cellNature(getHeight()-1, getWidth()) != Cell.EMP
				&& !(getEnvi().cellContent(getHeight(), getWidth()+1).getCharacter() instanceof PlayerContract)
				&& !(getEnvi().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract)) {
			
					throw new  InvariantError("Invarriant error: guard doit aller à droite");		
		}
		
		
		
//		 inv
//		 Environnement::cellNature(getHeight(),getWidth()) == LAD
//		 and Environnement::cellNature(getHeight()-1,getWidth()) == LAD
//		 and getTarget().getHeight() == getHeight()
//		 and getTarget().getWidth() > getWidth()
//		 and !getCatched()
//		 implies behaviour() == Move.Right
		if (env.cellNature(getHeight(), getWidth()) == Cell.LAD
			&& env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD
			&& getTarget().getHeight() == getHeight()
			&& getTarget().getWidth() > getWidth()
			&& getTarget().getWidth() - getWidth() < Math.abs(getTarget().getHeight() - getHeight())
			&& !getCatched()
			&& behaviour() != Move.Right) {
				
			throw new InvariantError("Invariant error : guard doit faire Right");
		}
		
//		 inv
//		 Environnement::cellNature(getHeight(),getWidth()) == LAD
//		 and Environnement::cellNature(getHeight()-1,getWidth()) == LAD
//		 and getTarget().getHeight() == getHeight()
//		 and getTarget().getWidth() == getWidth() and !getCatched()
//		 implies behaviour() == Move.Neutral
		if (env.cellNature(getHeight(), getWidth()) == Cell.LAD
			&& env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD
			&& getTarget().getHeight() == getHeight()
			&& getTarget().getWidth() == getWidth()
			&& getTarget().getWidth() - getWidth() < Math.abs(getTarget().getHeight() - getHeight())
			&& !getCatched()
			&& behaviour() != Move.Neutral) {
				
			throw new InvariantError("Invariant error : guard ne doit pas bouger");
		}		
		
//		Environnement::cellNature(getHeight(),getWidth()) = LAD
//		and Environnement::cellNature(getHeight()+1,getWidth()) = LAD
//		and (Environnement::cellNature(getHeight()-1,getWidth()) not in {EMP, LAD, HDR,HOL}
//		or ( Environnement::cellNature(getHeight()-1,getWidth()) in {EMP, LAD, HDR,HOL} and
//		Character c in Environnement::cellContent(getHeight()-1, getWidth() and !getCatched()
//      getHeight() != getTarget().getHeight() and getWidth() != getTarget().getWidth()
//		implies behaviour() == Suit_LAXE 
		
		if(env.cellNature(getHeight(), getWidth()) == Cell.LAD && env.cellNature(getHeight()+1, getWidth()) != Cell.LAD 
				&& ((env.cellNature(getHeight()-1, getWidth()) == Cell.MTL || env.cellNature(getHeight()-1, getWidth()) == Cell.PLT)
				|| (env.cellNature(getHeight()-1, getWidth()) != Cell.MTL && env.cellNature(getHeight()-1, getWidth()) != Cell.PLT
				&& env.cellContent(getHeight()-1, getWidth()).getCharacter() != null ))&& getCatched() == false
				&& getHeight() != getTarget().getHeight() && getWidth() != getTarget().getWidth()) {
			
			int val_width  =  Math.abs(getWidth() - getTarget().getWidth());
			int val_height =  Math.abs(getHeight() - getTarget().getHeight());
			
			if (val_width < val_height) {
				
				if (getWidth() - getTarget().getWidth()<0) {
					
					if (behaviour() != Move.Right) {
						throw new InvariantError("Invariant error : guard doit bouger à droite (AXE)");
					}
				}
				if (getWidth() - getTarget().getWidth()>=0) {
					if (behaviour() != Move.Left) {
						throw new InvariantError("Invariant error : guard doit bouger à gauche (AXE)");
					}
				}
			}
			if (val_width > val_height) {
				if (getHeight() - getTarget().getHeight()<0) {
					if (behaviour() != Move.Up) {
						throw new InvariantError("Invariant error : guard doit bouger en haut (AXE)");
					}
				}
				if (getHeight() - getTarget().getHeight()>=0) {
					if (behaviour() != Move.Down) {
						throw new InvariantError("Invariant error : guard doit bouger en bas (AXE)");
					}
				}
			}
			
		}
		

		// inv
		// Environnement::cellNature(getHeight(),getWidth()) == LAD
		// and Environnement::cellNature(getHeight()-1,getWidth()) == LAD
		// and getTarget().getHeight() == getHeight()
		// and getTarget().getWidth() < getWidth()
		// !getCatched()
		// implies behaviour() == Move.Left
		if (env.cellNature(getHeight(), getWidth()) == Cell.LAD
			&& env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD
			&& getTarget().getHeight() == getHeight()
			&& getTarget().getWidth() < getWidth()
			&& getWidth() - getTarget().getWidth() < Math.abs(getTarget().getHeight() - getHeight())
			
			&& behaviour() != Move.Left && !getCatched()) {
				
			throw new InvariantError("Invariant error : guard doit faire Left");
		}

	}

	@Override
	public Move behaviour() {
		Move m = super.behaviour();
		return m;
	}

	@Override
	public void climbLeft() {
		int hgt = getHeight();
		int wdt = getWidth();

		// pre: CellNature(hgt,wdt) in {HOL} 
		if (getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL
				|| (getEnvi().cellNature(getHeight() + 1, getWidth() - 1) == Cell.MTL
						|| getEnvi().cellNature(getHeight() + 1, getWidth() - 1) == Cell.PLT)) {
			throw new PreconditionError(
					"Pre-condition error: On est pas dans un trou ou on ne peut pas grimper parce que la case de gauche en haut n'est pas libre");
		}

		checkInvariant();
		super.climbLeft();
		checkInvariant();
//post:		Wdt(C) = 0 implies Wdt(ClimbLeft(C))= Wdt(C) and Hgt(ClimbLeft(C))= Hgt(C)
		if(wdt == 0 && (getWidth() != wdt || getHeight() != hgt)) {
			throw new PostconditionError("Pre-Condition error: On ne devrait pas grimper à gauche quand on a wdt == 0");
		}

//post:				Screen::CellNature(Envi(C),Hgt(C) +1,Wdt(C)-1) in {MTL, PLT}
//				implies Wdt(ClimbLeft(C)) = Wdt(C) and Hgt(ClimbLeft(C)) = Hgt(C)
		if((getEnvi().cellNature(getHeight()+1, getWidth()-1) == Cell.MTL || getEnvi().cellNature(getHeight()+1, getWidth()-1) == Cell.PLT)
				&& (getWidth() != wdt || getHeight() != hgt)) {
			throw new PostconditionError("Pre-Condition error: On ne devrait pas grimper à gauche quand on a PLT ou MTL ou on va se poser");
		}
		
//post:				exists Character c in Environment::CellContent(Envi(C),Hgt(C)+1,Wdt(C)-1)
//				implies Wdt(ClimbLeft(C)) = Wdt(C) and Hgt(ClimbLeft(C)) = Hgt(C)
		if (getEnvi().cellContent(hgt+1, wdt-1).getCharacter() != null
				&& (getWidth() != wdt ||getHeight() != hgt)) {
			throw new PostconditionError("Pre-Condition error: On ne devrait pas grimper à gauche quand on a un caracter ou on va se poser");
		}
		
		// post : wdt != 0 && Screen::CellNature(Envi(C),Hgt(C)+1,Wdt(C)-1) not in {PLT,MTL}
		// and not exists Character c in Environment::CellNature(Envi(C),Hgt(C)+1,Wdt(C)-1)
		// 
		// implies Wdt(climbLeft(C)) = Wdt(C) - 1 && Hgt(climbLeft(C)) = Hgt(C) + 1

		if ((wdt !=0 && (getEnvi().cellNature(hgt + 1, wdt - 1) != Cell.MTL
						&& getEnvi().cellNature(hgt + 1, wdt - 1) != Cell.PLT)
				&& getWidth() != (wdt - 1) || getHeight() != (hgt + 1))) {
			throw new PostconditionError("Post-condition error il devrait grimper a gauche mais il n'a pas grimpe");
		}
	}

	@Override
	public void climbRight() {
		int hgt = getHeight();
		int wdt = getWidth();
		// pre: CellNature(hgt,wdt) in {HOL} && cellNature(hgt+1,wdt+1) in {
		// EMP,LAD,HDR,HOl}
		if (getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL
				|| (getEnvi().cellNature(getHeight() + 1, getWidth() + 1) == Cell.MTL
						|| getEnvi().cellNature(getHeight() + 1, getWidth() + 1) == Cell.PLT)) {
			throw new PreconditionError(
					"Pre-condition error: On est pas dans un trou ou on ne peut pas grimper parce que la case de droite en haut n'est pas libre");
		}

		checkInvariant();
		super.climbRight();
		checkInvariant();
		
		//post: Screen::CellNature(Envi(C),Hgt(C) +1,Wdt(C)+1) in {MTL, PLT}
		//		implies Wdt(ClimbRight(C)) = Wdt(C) and Hgt(ClimbRight(C)) = Hgt(C)
		if((getEnvi().cellNature(getHeight()+1, getWidth()+1) == Cell.MTL || getEnvi().cellNature(getHeight()+1, getWidth()+1) == Cell.PLT)
		&& (getWidth() != wdt || getHeight() != hgt)) {
			throw new PostconditionError("Pre-Condition error: On ne devrait pas grimper à droite quand on a PLT ou MTL ou on va se poser");
		}

		//post:	exists Character c in Environment::CellContent(Envi(C),Hgt(C)+1,Wdt(C)+1)
		//		implies Wdt(ClimbRight(C)) = Wdt(C) and Hgt(ClimbRight(C)) = Hgt(C)
		if (getEnvi().cellContent(hgt+1, wdt+1).getCharacter() != null
				&& (getWidth() != wdt ||getHeight() != hgt)) {
			throw new PostconditionError("Pre-Condition error: On ne devrait pas grimper à droite quand on a un caracter ou on va se poser");
		}


		// post : wdt != 0 && Screen::CellNature(Envi(C),Hgt(C)+1,Wdt(C)+1) not in {PLT,MTL}
		// and not exists Character c in Environment::CellNature(Envi(C),Hgt(C)+1,Wdt(C)+1)
		// 
		// implies Wdt(climbRight(C)) = Wdt(C) + 1 && Hgt(climbRight(C)) = Hgt(C) + 1

		if ((wdt !=0 && (getEnvi().cellNature(hgt + 1, wdt + 1) != Cell.MTL
		&& getEnvi().cellNature(hgt + 1, wdt - 1) != Cell.PLT)
		&& getWidth() != (wdt - 1) || getHeight() != (hgt + 1))) {
			throw new PostconditionError("Post-condition error il devrait grimper a gauche mais il n'a pas grimpe");
		}
	}

	@Override
	public void step() {
		int hgt = getHeight();
		int wdt = getWidth();
		
		ItemImpl item_avant = getItem();
		checkInvariant();
		super.step();
		checkInvariant();
		
		// post :
//				 cellNature(hgt,wdt):Cell not \in {LAD,HDR,HOL}
//				 and cellNature(hgt-1,wdt):Cell \in {EMP,LAD,HDR,HOL}
//				 and \not \exist c:Character \in cellContent(hgt-1,wdt):CellContent
//				 and getTarget().getHeight() != getHeight()	
//				 \implies hgt(step(C)) = hgt(C) - 1
		
		if (getEnvi().cellNature(hgt, wdt) != Cell.LAD
				&& getEnvi().cellNature(hgt, wdt) != Cell.HDR
				&& getEnvi().cellNature(hgt, wdt) != Cell.HOL) {
			if (getEnvi().cellNature(hgt - 1, wdt) == Cell.EMP
					|| getEnvi().cellNature(hgt - 1, wdt) == Cell.HDR
					|| getEnvi().cellNature(hgt - 1, wdt) == Cell.HOL) {
				if (getEnvi().cellContent(hgt - 1, wdt).getCharacter() == null) {
					if (getHeight() == (hgt)) {
						throw new PostconditionError("Post-condition error: le gardien n'est pas tombe");
					}
				}
			}
		} 
		
		// post
//		 getPv() == 0  
//		 implies not Exists Guard in Environnement::CellContent(hgt,wdt)
		
		if(getPv() == 0 && getEnvi().cellContent(hgt,wdt).getCharacter() != null) {
			throw new PostconditionError("Post-condition error: Le player n'a pas été tué ");	
		}
		
		// post :
		// getPv() == 0 and item == null 
		//implies Environnement::CellContent(getHeight(),getWidth()).getItem() == null
				
		if(getPv() == 0 && item_avant == null && 
				getEnvi().cellContent(hgt, wdt).getItem() != null) {
			throw new PostconditionError("Post-condition error: le tresor relaché apres la mort du garde mais il n'avait pas de tresor avant");
		}
				
		// post :
//		 getPv() == 0 and item != null 
//		implies Environnement::CellContent(getHeight(),getWidth()).getItem().nature() == ItemType.treasure
		
		if (getEnvi().cellContent(hgt, wdt).getItem() != null) {
			if(getPv() == 0 && item_avant != null && 
					getEnvi().cellContent(hgt, wdt).getItem().nature() != ItemType.Treasure) {
				throw new PostconditionError("Post-condition error: le tresor n'a pas été relaché apres la mort du garde");
			}
		}

		// post:
		//		 old_item != null  && getItem() == null
		//		 implies (Environnement::CellNature(height,width) = HOL
		//		 || pv =0)
		if (item_avant != null  && getItem()== null
				&& getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL && (getPv() ==0)) {
			throw new PostconditionError("Post-condition error: la nature du tresor a changé sans que le guard soit mort ni dans un trou");
		}
		
		// post:
		// old_time != null and Environnement::CellContent(height(),width()) = HOL
		// implies Environnement:: CellContent(height()+1,width()) = Treasure 
		// and getItem() = null
	
		if (item_avant != null 
		&& getEnvi().cellNature(getHeight(),getWidth()) == Cell.HOL
		&& getEnvi().cellNature(getHeight()+1,getWidth()) != Cell.HOL
		&& getItem() != null) {
			throw new PostconditionError("Post-condition error: le garde est tombé dans un trou sans que le tresor n'aie été relaché");
		}
		
		// post:
//		 Player P in Environnement::CellContent(Hgt,Wdt+1) && behaviour() = Move.Right
//		 implies Player P not in Environnement::CellContent(Hgt,Wdt+1)
		if(wdt<getEnvi().getWidth()-1) {
			if(getEnvi().cellContent(hgt, wdt+1).getCharacter() instanceof PlayerContract
					&& behaviour() == Move.Right && !(getEnvi().cellContent(hgt, wdt+1).getCharacter() instanceof PlayerContract)) {
					throw new PostconditionError("Post-condition error: Le garde n'a pas mange le joueur à droite alors qu'il le devrait");
				}
		}
		
		
// post:
//		 Player P in Environnement::CellContent(Hgt,Wdt-1) && behaviour() = Move.Left
//		 implies Player P not in Environnement::CellContent(Hgt,Wdt-1)
		
		if (wdt>= 1) {
			if(getEnvi().cellContent(hgt, wdt-1).getCharacter() instanceof PlayerContract
					&& behaviour() == Move.Left && !(getEnvi().cellContent(hgt, wdt-1).getCharacter() instanceof PlayerContract)) {
					throw new PostconditionError("Post-condition error: Le garde n'a pas mange le joueur à gauche alors qu'il le devrait");
				}
		}
		
		
		// post:
//		 Player P in Environnement::CellContent(Hgt+1,Wdt) && behaviour() = Move.Up
//		 implies Player P not in Environnement::CellContent(Hgt+1,Wdt)
		
		if(hgt <getEnvi().getHeight()-1) {
			if(getEnvi().cellContent(hgt+1, wdt).getCharacter() instanceof PlayerContract
					&& behaviour() == Move.Up && !(getEnvi().cellContent(hgt+1, wdt).getCharacter() instanceof PlayerContract)) {
					throw new PostconditionError("Post-condition error: Le garde n'a pas mange le joueur en haut alors qu'il le devrait");
				}	
		}
		
		
		// post:
//		 Player P in Environnement::CellContent(Hgt-1,Wdt) && behaviour() = Move.Down
//		 implies Player P not in Environnement::CellContent(Hgt-1,Wdt)
				
		if(getEnvi().cellContent(hgt-1, wdt).getCharacter() instanceof PlayerContract
			&& behaviour() == Move.Down && !(getEnvi().cellContent(hgt-1, wdt).getCharacter() instanceof PlayerContract)) {
			throw new PostconditionError("Post-condition error: Le garde n'a pas mange le joueur en bas alors qu'il le devrait");
		}
		
		

		// post
		// getPV() == 0 && getItem != null 
		// implies Environnement::cellContent(getHeight(),getWidth()).getItem() != null
		if(getPv() == 0 && getItem() != null && getEnvi().cellContent(getHeight(), getWidth()) == null) {
			throw new PostconditionError("Le garde n'a pas lache son trésor");
		}
	}

	@Override
	public String toString() {
		return "G" + super.getId();
	}
}
