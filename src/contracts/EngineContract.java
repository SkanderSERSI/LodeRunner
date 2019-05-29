package contracts;

import java.awt.Point;
import java.util.ArrayList;
import decorators.EngineDecorator;
import enums.Cell;
import exceptions.InvariantError;
import exceptions.PreconditionError;
import impl.ItemImpl;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;


public class EngineContract extends EngineDecorator {

	public EngineContract(EngineService delegate) {
		super(delegate);
	}
	
	@Override
	public void init(EnvironnementService escreen, ArrayList<Point> guards, ArrayList<Point> treasures) {
		// pre:
		// \forall x,y \in guards:List<Point> {
		//		cellNature(x,y) = EMP }
		for (Point p : guards) {
			if(escreen.cellNature(p.x, p.y) != Cell.EMP) {
				throw new PreconditionError("Pre condition error : Un guard ne situe pas dans une cell EMP");
			}
		}
		
		// pre:
		// \forall x,y \in treasures:List<Point> {
		//		cellNature(x,y) = EMP }
		for (Point p : treasures) {
			if(escreen.cellNature(p.x, p.y) != Cell.EMP) {
				throw new PreconditionError("Pre condition error : Un treasure ne situe pas dans une cell EMP");
			}
		}
		
		
		// pre: 
		// \forall x,y \in treasures:List<Point> {
		//		cellNature(x-1,y) in {PLT,MTL}
		for (Point p : treasures) {
			if(escreen.cellNature(p.x-1, p.y) != Cell.PLT && escreen.cellNature(p.x-1, p.y) != Cell.MTL) {
				throw new PreconditionError("Pre condition error : Un treasure ne situe pas sur une cell PLT ou MTL");
			}
		}
		
		super.init(escreen, guards, treasures);
		
	}
	
	public void checkInvariant() {
		// inv :
		// \forall x,y \in guards:List<Point> {
		//		getEnvironnement().cellContent(x,y).getCharacter() = guard(x,y) }
		for (GuardService g : getGuards()) {
			if(getEnvironnement().cellContent(g.getHeight(),g.getWidth()).getCharacter() != g) {
				throw new InvariantError("Environnement non synchro avec Guards");
			}
		}
		
		// inv :
		// \forall x,y \in treasures:List<Point> {
		//		getEnvironnement().cellContent(x,y).getItem() = treasure(x,y) }
		for (ItemImpl t : getTreasures()) {
			if(getEnvironnement().cellContent(t.getHeight(),t.getWidth()).getItem() != t) {
				throw new InvariantError("Environnement non synchro avec Treasures");
			}
		}
		
		// inv :
		//		 x,y \in player:Point {
		//				getEnvironnement().cellContent(x,y).getCharacter().wdt,
		//		      getEnvironnement().cellContent(x,y).getCharacter().hgt
		//		      = player }
		if(getEnvironnement().cellContent(getPlayer().getHeight(),getPlayer().getWidth()).getCharacter() != getPlayer()) {
			throw new InvariantError("Environnement non synchro avec Player");
		}
		
		// inv  
		// getEnvironnement.cellContent(x,y).getItem() != null
		// implies  getEnvironnement.cellContent(x,y).getItem() == null
		
			if (getEnvironnement().cellContent(getPlayer().getHeight(),getPlayer().getWidth()).getItem() != null){
				if (getEnvironnement().cellContent(getPlayer().getHeight(),getPlayer().getWidth()) != null){
					throw new InvariantError("Invariant error: le joueur n'a pas recuper� l item");
				}
			}
		}
}
