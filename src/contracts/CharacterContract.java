package contracts;

import decorators.CharacterDecorator;
import enums.Cell;
import exceptions.InvariantError;
import exceptions.PostconditionError;
import services.EnvironnementService;

public class CharacterContract extends CharacterDecorator {

	public void checkInvariant() {
		// inv : cellNature(getEnvi(c),getHeight(c),getWidth) in { EMP,HOL,LAD,HDR}
		EnvironnementService env = getEnvi();
		if (env.cellNature(getHeight(), getWidth()) != Cell.EMP && env.cellNature(getHeight(), getWidth()) != Cell.HOL
				&& env.cellNature(getHeight(), getWidth()) != Cell.LAD
				&& env.cellNature(getHeight(), getWidth()) != Cell.HDR) {
			throw new InvariantError("Invariant error: La nature de la cellule est differente de EMP,HOL,HDR,LAD ");

		}

		// inv : getHeight(c) >=0 && getHeight(c)<env.getHeight() && getWidth(c) >=0 &&
		// getWidth(c)<getWidth()
		if (getHeight() >= 0 && getHeight() < env.getHeight() && getWidth() >= 0 && env.getWidth() < env.getWidth()) {
			throw new InvariantError("Invariant error: charactere depasse de la map ");

		}

	}

	@Override
	public void goLeft() {

		int hgt = getHeight();
		int wdt = getWidth();

		checkInvariant();
		super.goLeft();
		checkInvariant();

		// post: Hgt(GoLeft(C)) = Hgt(C)
		if (getHeight() != hgt) {
			throw new PostconditionError(" Post-condition error la height  a change");
		}

		// post :Wdt(C) = 0 implies Wdt(GoLeft(C)) = Wdt(C)
		if (wdt == 0 && getWidth() != wdt) {
			throw new PostconditionError("POst-condition error la width a change on sort de la map √† gauche");
		}

		EnvironnementService env = getEnvi();
		// post :Environment::CellNature(Envi(C),Hgt(C),Wdt(C)-1) in {MTL, PLT} implies
		// Wdt(GoLeft(C)) = Wdt(C)
		if (env.cellNature(getHeight(), getWidth() - 1) == Cell.MTL
				|| (env.cellNature(getHeight(), getWidth() - 1) == Cell.PLT) && wdt != getWidth()) {
			throw new PostconditionError(
					"Post-condition error le personnage ne peut jamais rentrer dans une plateforme ou du metal");
		}


		// post :exists Character c in Environment::CellContent(Envi(C),Hgt(C),Wdt(C)-1)
		// implies Wdt(GoLeft(C)) = Wdt(C)

		if (env.cellContent(getHeight(), getWidth() - 1).getCharacter() != null && getWidth() != wdt) {
			throw new PostconditionError("post-condition error  le personnage est bloque par un autre");
		}
		
		
		// Environment::CellNature(Envi(C),Wdt(C),Hgt(C)) not in {LAD, HDR}
		// and Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1) not in {PLT, MTL, LAD }
		// and not exists Character c in Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C))
		// implies Wdt(GoLeft(C)) = Wdt(C)
		if(env.cellNature(hgt, wdt) != Cell.LAD 
				&& env.cellNature(hgt, wdt) != Cell.HDR
				&& env.cellNature(hgt-1, wdt) != Cell.PLT
				&& env.cellNature(hgt-1, wdt) != Cell.MTL
				&& env.cellNature(hgt-1, wdt) != Cell.LAD
				&& env.cellContent(hgt-1, wdt).getCharacter() == null
				&& wdt != getWidth()) {
			throw new PostconditionError("Post condition error: Ne peut aller ‡ gauche en chute libre");
		}
		
		// exists Character c in Environment::CellContent(Envi(C),Wdt(C)-1,Hgt(C))
		// implies Wdt(GoLeft(C)) = Wdt(C)
		if(env.cellContent(hgt, wdt-1).getCharacter() != null
				&& wdt != getWidth()) {
			throw new PostconditionError("Post condition error: Ne peut aller ‡ gauche si un garde y est deja");
		}

		// post :
		// (Wdt(C) != 0) 
		// and Environment::CellNature(Envi(C),Hgt(C),Wdt(C)-1) not in {MTL, PLT}
		// and (Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) in {LAD, HDR}
		// or Environment::CellNature(Envi(C),Hgt(C)-1,Wdt(C)) in {PLT, MTL, LAD}
		// or exists Character c in Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C)) )
		// and not (exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C),Wdt(C)-1))
		// implies Wdt(GoLeft(C)) = Wdt(C)-1

		if (wdt > 0 
			&& env.cellNature(hgt, wdt - 1) != Cell.MTL 
			&& env.cellNature(hgt, wdt - 1) != Cell.PLT
			&& (env.cellNature(hgt - 1, wdt) == Cell.LAD || env.cellNature(hgt - 1, wdt) == Cell.HDR
				|| env.cellNature(hgt - 1, wdt) == Cell.PLT || env.cellNature(hgt - 1, wdt) == Cell.MTL
				|| env.cellNature(hgt - 1, wdt) == Cell.LAD || env.cellContent(hgt - 1, wdt).getCharacter() != null)
			&& env.cellContent(hgt, wdt - 1).getCharacter() == null
			&& getWidth() != wdt - 1) {
			throw new PostconditionError("Le personnage n'est pas aller a gauche");
		}

	}

	@Override
	public void goRight() {

		int hgt = getHeight();
		int wdt = getWidth();
		// post
		checkInvariant();
		super.goRight();
		checkInvariant();

		// post: Hgt(GoRight(C)) = Hgt(C)
		if (getHeight() != hgt) {
			throw new PostconditionError(" Post-condition error la height  a change");
		}

		EnvironnementService env = getEnvi();
		// post :Wdt(C) == Environnement::getWidth()-1 implies Wdt(GoRight(C)) = Wdt(C)

		if (wdt == env.getWidth()-1 && getWidth() != wdt) {
			throw new PostconditionError("Post-condition error la width a change");
		}

		// post :Environment::CellNature(Envi(C),Hgt(C),Wdt(C)+1) in {MTL, PLT } implies
		// Wdt(GoRight(C)) = Wdt(C)

		if (env.cellNature(getHeight(), getWidth() + 1) == Cell.MTL
				|| (env.cellNature(getHeight(), getWidth() + 1) == Cell.PLT) && wdt != getWidth()) {

			throw new PostconditionError(
					"Post-condition error le personnage ne peut jamais rentrer dans une plateforme ou du metal");
		}

		// post :Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) not in {LAD, HDR}
		// and Environment::CellNature(Envi(C),Hgt(C)-1,Wdt(C)) not in {PLT, MTL, LAD }
		// and not exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C))
		// implies Wdt(GoRight(C)) = Wdt(C)

		if(env.cellNature(hgt, wdt) != Cell.LAD 
				&& env.cellNature(hgt, wdt) != Cell.HDR
				&& env.cellNature(hgt-1, wdt) != Cell.PLT
				&& env.cellNature(hgt-1, wdt) != Cell.MTL
				&& env.cellNature(hgt-1, wdt) != Cell.LAD
				&& env.cellContent(hgt-1, wdt).getCharacter() == null
				&& wdt != getWidth()) {
			throw new PostconditionError("Post condition error: Ne peut aller ‡ droite en chute libre");
		}
		
		// post :exists Character c in Environment::CellContent(Envi(C),Hgt(C),Wdt(C)+1)
		// implies Wdt(GoRight(C)) = Wdt(C)

		if (env.cellContent(getHeight(), getWidth() + 1).getCharacter() != null && getWidth() != wdt) {
			throw new PostconditionError("post-condition error  le personnage est bloqu√© par un autre");
		}

		// post : (Wdt(C) < Environnement::getWidth() and Environment::CellNature(Envi(C),Hgt(C),Wdt(C)+1) not
		// in {MTL, PLT } and (Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) in {LAD,HDR}
		// or Environment::CellNature(Envi(C),Hgt(C)-1,Wdt(C)) in {PLT, MTL, LAD}
		// or exists Character c in Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C)) )
		// and not (exists Character c in Environment::CellContent(Envi(C),Hgt(C),Wdt(C)+1))
		// implies Wdt(GoRight(C)) = Wdt(C)+1

		if ((wdt < env.getWidth() - 1 
				&& env.cellNature(hgt, wdt + 1) != Cell.MTL 
				&& env.cellNature(hgt, wdt + 1) != Cell.PLT
				&& (env.cellNature(hgt - 1, wdt) == Cell.LAD || env.cellNature(hgt - 1, wdt) == Cell.HDR
					|| env.cellNature(hgt - 1, wdt) == Cell.PLT || env.cellNature(hgt - 1, wdt) == Cell.MTL
					|| env.cellNature(hgt - 1, wdt) == Cell.LAD || env.cellContent(hgt - 1, wdt).getCharacter() != null)
				&& env.cellContent(hgt, wdt + 1).getCharacter() == null
				&& getWidth() != wdt + 1)) {
			throw new PostconditionError("Le personnage n'est pas aller a droite");
		}

	}

	@Override
	public void goUp() {

		int hgt = getHeight();
		int wdt = getWidth();

		checkInvariant();
		super.goUp();
		checkInvariant();

		// post: wdt(GoUp(C)) = wtd(C)
		if (getWidth() != wdt) {
			throw new PostconditionError(" Post-condition error la width a change");
		}
		
		EnvironnementService env = getEnvi();
		
		// post :Wdt(C) = 0 implies Wdt(GoUp(C)) = Wdt(C)
		if (hgt == env.getHeight()-1 && getHeight() != hgt) {
			throw new PostconditionError("Post-condition error la height a change");
		}

		// post :
		// Environment::CellNature(Envi(C),Hgt(C)+1,Wdt(C)) in {MTL, PLT} or
		// exists C in Environnement::CellContent(Hgt(C)+1,Wdt(C)) implies
		// hgt(GoUp(C)) = hgt(C)
		
		if ((env.cellNature(getHeight() + 1, getWidth()) == Cell.MTL
				|| (env.cellNature(getHeight() + 1, getWidth()) == Cell.PLT) || env.cellContent(getHeight()+1, getWidth()).getCharacter() != null) && hgt != getHeight()) {
			throw new PostconditionError(
					"Post-condition error le personnage ne peut jamais sauter et taper sa tete contre une plateforme ou du metal");
		}

		// post :Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) not in {LAD, HDR}
		// and Environment::CellNature(Envi(C),Hgt(C)-1,Wdt(C)) not in {PLT, MTL, LAD }
		// and not exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C))
		// implies hgt(GoUP(C)) = hgt(C)
		if(env.cellNature(hgt, wdt) != Cell.LAD 
				&& env.cellNature(hgt, wdt) != Cell.HDR
				&& env.cellNature(hgt-1, wdt) != Cell.PLT
				&& env.cellNature(hgt-1, wdt) != Cell.MTL
				&& env.cellNature(hgt-1, wdt) != Cell.LAD
				&& env.cellContent(hgt-1, wdt).getCharacter() == null
				&& wdt != getWidth()) {
			throw new PostconditionError("Post condition error: Ne peut aller ‡ droite en chute libre");
		}

		// post :exists Character c in Environment::CellContent(Envi(C),Hgt(C)+1,Wdt(C))
		// implies hgt(GoUp(C)) = hgt(C)
		if (env.cellContent(getHeight() + 1, getWidth()).getCharacter() != null && getHeight() != hgt) {
			throw new PostconditionError("post-condition error  le personnage est bloque par un autre");
		}

		// post : (hgt(C) != Height(envi(C)-1) and
		// Environment::CellNature(Envi(C),Hgt(C) + 1,Wdt(C)) not in {MTL, PLT}
		// and Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) in {LAD}
		// and not (exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C)+1,Wdt(C)))
		// implies hgt(GoUp(C)) = hgt(C)+1
		if (hgt <= env.getHeight() - 1 && env.cellNature(hgt + 1, wdt) != Cell.MTL
				&& env.cellNature(hgt + 1, wdt) != Cell.PLT && env.cellNature(hgt, wdt) == Cell.LAD
				&& env.cellContent(hgt + 1, wdt).getCharacter() == null) {
			if (getHeight() != (hgt + 1))
				throw new PostconditionError("Le personnage n'est pas monte");
		}
	}

	@Override
	public void goDown() {

		int hgt = getHeight();
		int wdt = getWidth();
		// post
		checkInvariant();
		super.goDown();
		checkInvariant();

		// post: wdt(GoDown(C)) = wtd(C)
		if (getWidth() != wdt)
			throw new PostconditionError(" Post-condition error la width a change");

		// post :hgt(C) = 0 implies hgt(GoDown(C)) = hgt(C)
		if (getHeight() < 0)
			throw new PostconditionError("Post-condition error la height a change");

		// post : Environment::CellNature(Envi(C),Hgt(C)-1,Wdt(C)) in {PLT, MTL}
		// or exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C))
		// implies hgt(GoDown(C)) = hgt(C)
		EnvironnementService env = getEnvi();
		if (((env.cellNature(hgt - 1, getWidth()) == Cell.MTL || env.cellNature(hgt - 1, wdt) == Cell.PLT)
				|| env.cellContent(hgt - 1, wdt).getCharacter() != null) && getHeight() != hgt)
			throw new PostconditionError(
					"Post-condition error le personnage ne peut jamais decendre dans une plateforme ou du metal");
		
		// post :exists Character c in Environment::CellContent(Envi(C),Hgt(C)+1,Wdt(C))
				// implies hgt(GoUp(C)) = hgt(C)
		if (env.cellContent(getHeight() - 1, getWidth()).getCharacter() != null && getHeight() != hgt) {
			throw new PostconditionError("post-condition error  le personnage est bloque par un autre");
		}

		// post :
		// (hgt(C) >= 1)
		// and Environment::CellNature(Envi(C),Hgt(C) - 1,Wdt(C)) not in {MTL, PLT}
		// and (Environment::CellNature(Envi(C),Hgt(C),Wdt(C)) in {LAD}
		// and not (exists Character c in
		// Environment::CellContent(Envi(C),Hgt(C)-1,Wdt(C)))
		// implies hgt(GoDown(C)) = hgt(C)-1
		if (hgt >= 1  && env.cellNature(hgt - 1, wdt) != Cell.MTL && env.cellNature(hgt - 1, wdt) != Cell.PLT
				&& env.cellContent(hgt - 1, wdt).getCharacter() == null)

			if (getHeight() != (hgt - 1))
				throw new PostconditionError("Le personnage n'est pas descendu");
	}

}