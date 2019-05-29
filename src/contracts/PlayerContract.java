package contracts;

import decorators.PlayerDecorator;
import enums.Cell;
import enums.Command;
import enums.ItemType;
import exceptions.InvariantError;
import exceptions.PostconditionError;
import services.EngineService;
import services.EnvironnementService;
import services.PlayerService;

public class PlayerContract extends PlayerDecorator {

	public PlayerContract(PlayerService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		// inv : cellNature(getEnvi(c),getHeight(c),getWidth) in { EMP,HOL,LAD,HDR}
		EnvironnementService env = getEnvi();
		if (env.cellNature(getHeight(), getWidth()) != Cell.EMP && env.cellNature(getHeight(), getWidth()) != Cell.HOL
				&& env.cellNature(getHeight(), getWidth()) != Cell.LAD
				&& env.cellNature(getHeight(), getWidth()) != Cell.HDR) {
			throw new InvariantError("Invariant error:  La nature de la cellule est differente de EMP,HOL,HDR,LAD ");

		}

		// inv : getHeight(c) >=0 && getHeight(c)<env.getHeight() && getWidth(c) >=0 &&
		// getWidth(c)<getWidth()
		if (getHeight() >= 0 && getHeight() < env.getHeight() && getWidth() >= 0 && env.getWidth() < env.getWidth()) {
			throw new InvariantError("Invariant error: player sort de la map ");

		}

	}

	@Override
	public EngineService getEngine() {
		return super.getEngine();
	}

	@Override
	public void step() {
		int hgt = getHeight();
		
		int nbBombs = getEngine().getNbBombs();
		EnvironnementService env = getEnvi();
		checkInvariant();
		super.step();
		checkInvariant();

		// post :
		// cellNature(hgt,wdt):Cell not \in {LAD,HDR}
		// and cellNature(hgt-1,wdt):Cell \in {EMP,LAD,HDR,HOL}
		// and \not \exist c:Character \in cellContent(hgt-1,wdt):CellContent
		// \implies hgt(step(C)) = hgt(C) - 1
		if (getEnvi().cellNature(getHeight(), getWidth()) != Cell.LAD
				&& getEnvi().cellNature(getHeight(), getWidth()) != Cell.HDR
				&& getEnvi().cellNature(getHeight(), getWidth()) != Cell.HOL) {
			if (getEnvi().cellNature(getHeight() - 1, getWidth()) == Cell.EMP
					|| getEnvi().cellNature(getHeight() - 1, getWidth()) == Cell.HDR
					|| getEnvi().cellNature(getHeight() - 1, getWidth()) == Cell.HOL) {
				if (getEnvi().cellContent(getHeight() - 1, getWidth()).getCharacter() == null) {
					if (getHeight() != (hgt - 1)) {
						throw new PostconditionError("Post-condition error: le joueur n'est pas tombe");
					}
				}
			}
		} 
			
		else {
			Command cmd = getEngine().nextCommand();

			// post: Engine::nextCommand() = {DigL} and cellNature(hgt-1,wdt) not in
			// {LAD,HDR,EMP,HOL}
			// || exists c :Character in cellContent(hgt-1,wdt) && cellNature(hgt,wdt-1) in
			// {LAD,HDR,EMP,HOL}
			// && cellNature(hgt-1,wdt-1) == {PLT}
			// implies cellNature(hgt-1,wdt-1) == HOL
			if (cmd == Command.DigL) {
				
				if((env.cellNature(getHeight() - 1, getWidth()) != Cell.HDR
					&& env.cellNature(getHeight() - 1, getWidth()) != Cell.EMP
					&& env.cellNature(getHeight() - 1, getWidth()) != Cell.HOL
					|| env.cellContent(getHeight() - 1, getWidth()).getCharacter() != null
					&& (env.cellNature(getHeight(), getWidth() - 1) != Cell.LAD
					&& env.cellNature(getHeight(), getWidth() - 1) != Cell.HDR
					&& env.cellNature(getHeight(), getWidth() - 1) != Cell.EMP
					&& env.cellNature(getHeight(), getWidth() - 1) != Cell.HOL))
					&& getEnvi().cellNature(getHeight() - 1, getWidth() - 1) != Cell.HOL) {
				
					throw new PostconditionError("Post-condition error: trou de gauche pas creuse");
				}
			}

			// post
			// Engine::nextCommand() = {DigR}
			// and cellNature(hgt-1,wdt) not in {LAD,HDR,EMP,HOL}
			// || exists c :Character in cellContent(hgt-1,wdt) && cellNature(hgt,wdt+1) in
			// {LAD,HDR,EMP,HOL}
			// && cellNature(hgt-1,wdt+1) == {PLT}
			// implies cellNature(hgt-1,wdt+1) == HOL
			if (cmd == Command.DigR) { 
				
				if ((env.cellNature(getHeight() - 1, getWidth()) != Cell.HDR
						&& env.cellNature(getHeight() - 1, getWidth()) != Cell.EMP
						&& env.cellNature(getHeight() - 1, getWidth()) != Cell.HOL
						|| env.cellContent(getHeight() - 1, getWidth()).getCharacter() != null
						&& (env.cellNature(getHeight(), getWidth() + 1) != Cell.LAD
						&& env.cellNature(getHeight(), getWidth() + 1) != Cell.HDR
						&& env.cellNature(getHeight(), getWidth() + 1) != Cell.EMP
						&& env.cellNature(getHeight(), getWidth() + 1) != Cell.HOL))
						&& getEnvi().cellNature(getHeight() - 1, getWidth() + 1) != Cell.HOL) {
					
					throw new PostconditionError("Post-condition error: trou de droite pas creuse");
				}
			}
			
			if (cmd == Command.BombL) {
			
				// post: Engine::getNbBombs() <= 0  
				// and Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1
				
				if ( getEngine().getNbBombs() <= 0
						&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem().nature() == ItemType.Bomb
						|| (getEngine().getNbBombs() ==  (nbBombs -1)))){
					throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée pas assez de munition");
				}
				
				// post: Environnement::cellContent(hgt,wdt-1).getItem() != null
				// implies ( Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBombs -1)
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem() != null
						&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem().nature() == ItemType.Bomb
								|| (getEngine().getNbBombs() ==  (nbBombs -1)))) {
					throw new PostconditionError("Post-Condition error: La bombe ne doit pas etre larguee sur un autre item");
				}
				
				// post: getWidth() <= 0  
				// implies ( Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1)
				
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem() != null) {
					if ( getWidth() <= 0
							&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem().nature() == ItemType.Bomb
							|| (getEngine().getNbBombs() ==  (nbBombs -1)))){
						throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée depassement map");
					}
				}
				
				// post: 
				// Environnement::cellNature(hgt-1,wdt) not in {PLT,MTL,LAD} and Guard G not in Environnement::cellContent(hgt-1,wdt)
				// or (Environnement::cellNature(hgt,wdt) not in {HDR,EMP}
				// or ((Environnement::cellNature(hgt-1,wdt-1) in {EMP,HOL}
				// or (Environnement::cellNature(hgt,wdt-1)  in {PLT,MTL,LAD}
				// and Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem() != null) {
					if(getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.MTL
							&& getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.PLT
							&& getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.LAD
							&& getEngine().getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() == null
							|| ((getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR || getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
							|| (getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()-1) == Cell.EMP || getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()-1) == Cell.HOL)
							|| (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) == Cell.PLT 
							||  getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) == Cell.MTL 
							||  getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.LAD))
							&& getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem().nature() == ItemType.Bomb
							&& (getEngine().getNbBombs() ==  (nbBombs -1))){
								throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée depassement map plein de trucs qui vont pas");
							}
				}
				// post
				// Engine::nextCommand() = {BombL}
				// and getWidth()>1
				// and Engine::getNbBombs() >0
				// and (Environnement::cellNature(hgt-1,wdt)  in {PLT,MTL,LAD} or Guard G  in Environnement::cellContent(hgt-1,wdt))
				// and (Environnement::cellNature(hgt,wdt) in {HDR,EMP}
				// and (Environnement::cellNature(hgt-1,wdt-1) not in {EMP,HOL}
				// and (Environnement::cellNature(hgt,wdt-1) not in {PLT,MTL,LAD}
				// and Environnement::cellContent(hgt,wdt-1).getItem() == ItemType.BOMB 
				// and Environnement::getNbBombs() == NbBomb -1
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem() != null) {
					if ((getEngine().getNbBombs() >0) &&(getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
							|| getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
							|| getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
							|| getEngine().getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null)
							&& (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR 
							|| getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
							&& (getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()-1) != Cell.EMP 
							&& getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()-1) != Cell.HOL)
							&& (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.PLT 
							&& getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.MTL 
							&& getEngine().getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.LAD
							&& getEngine().getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem().nature() == ItemType.Bomb
							&& (getEngine().getNbBombs() ==  (nbBombs -1)))) {
								throw new PostconditionError("Post-condition error: La bomb n a pas été larguée à gauche"); 
							}
				}
			}
			
			if (cmd == Command.BombR) {
				
				// post: Engine::getNbBombs() <= 0  
				// and Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem() != null) {
				if ( getEngine().getNbBombs() <= 0
						&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem().nature() == ItemType.Bomb
						|| (getEngine().getNbBombs() ==  (nbBombs -1)))){
					
				
					throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée pas assez de munition");
					}
				}
				
				// post: Environnement::cellContent(hgt,wdt+1).getItem() != null
				// implies ( Environnement::cellContent(hgt,wdt+1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBombs -1)
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem() != null
						&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem().nature() == ItemType.Bomb
								|| (getEngine().getNbBombs() ==  (nbBombs -1)))) {
					throw new PostconditionError("Post-Condition error: La bombe ne doit pas etre larguee sur un autre item");
				
				
				}
				
				
				
				
				// post: getWidth() <= Environnement:getWidth()
				// implies ( Environnement::cellContent(hgt,wdt-1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1)
			
				if (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem() != null) {
					if ( getWidth() <= getEngine().getEnvironnement().getWidth()
							&& (getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem().nature() == ItemType.Bomb
							|| (getEngine().getNbBombs() ==  (nbBombs -1)))){
						throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée depassement map");
					}
				}
				
				// post: 
				// Environnement::cellNature(hgt-1,wdt) not in {PLT,MTL,LAD} and Guard G not in Environnement::cellContent(hgt-1,wdt)
				// or (Environnement::cellNature(hgt,wdt) not in {HDR,EMP}
				// or ((Environnement::cellNature(hgt-1,wdt+1) in {EMP,HOL}
				// or (Environnement::cellNature(hgt,wdt+1)  in {PLT,MTL,LAD}
				// and Environnement::cellContent(hgt,wdt+1).getItem() != ItemType.BOMB 
				// and Environnement::getNbBombs() != NbBomb -1
				
				if(getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem() != null) {
					if(getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.MTL
							&& getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.PLT
							&& getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) != Cell.LAD
							&& getEngine().getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() == null
							|| ((getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR || getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
							|| (getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()+1) == Cell.EMP || getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()+1) == Cell.HOL)
							|| (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) == Cell.PLT 
							||  getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) == Cell.MTL 
							||  getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.LAD))
							&& getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem().nature() == ItemType.Bomb
							&& (getEngine().getNbBombs() ==  (nbBombs -1))){
								throw new PostconditionError("Post-condition error: La bomb ne doit pas etre larguée depassement map pleinde trucs qui vont pas");
							}
				}
				// post
				// Engine::nextCommand() = {BombR}
				// and getWidth()<Environnement::getWidth()
				// and Engine::getNbBombs() >0
				// and (Environnement::cellNature(hgt-1,wdt)  in {PLT,MTL,LAD} or Guard G not in Environnement::cellContent(hgt-1,wdt))
				// and (Environnement::cellNature(hgt,wdt) in {HDR,EMP}
				// and (Environnement::cellNature(hgt-1,wdt+1) not in {EMP,HOL}
				// and (Environnement::cellNature(hgt,wdt+1) not in {PLT,MTL,LAD}
				// and Environnement::cellContent(hgt,wdt+1).getItem() == ItemType.BOMB 
				// and Environnement::getNbBombs() == NbBomb -1
							
				if ((getEngine().getNbBombs() >0) &&(getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
						|| getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
						|| getEngine().getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
						|| getEngine().getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null)
						&& (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR || getEngine().getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
						&& (getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()+1) != Cell.EMP && getEngine().getEnvironnement().cellNature(getHeight()-1, getWidth()+1) != Cell.HOL)
						&& (getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.PLT && getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.MTL 
						&& getEngine().getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.LAD
						&& getEngine().getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem().nature() != ItemType.Bomb
						&& (getEngine().getNbBombs() !=  (nbBombs -1)))) {
							throw new PostconditionError("Post-condition error: La bomb n a pas été larguée à gauche"); 
						}
			}
			}

	}

	@Override
	public String toString() {
		return "O";
	}
}
