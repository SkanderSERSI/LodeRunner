package impl;

import enums.Cell;
import services.CellContentService;
import services.CharacterService;
import services.EnvironnementService;

public class CharacterImpl implements CharacterService {
	private int hgt;
	private int wdt;
	protected EnvironnementService env;
	
	
	public CharacterImpl() {
		
	}
	public void init( EnvironnementService env,int height, int width) {
		this.hgt = height;
		this.wdt = width;
		this.env = env;
	}


	
	@Override
	public void goLeft() {
		if ((wdt < env.getWidth() 
			&& env.cellNature(hgt,wdt - 1) != Cell.MTL
			&& env.cellNature(hgt,wdt - 1) != Cell.PLT)) {
			
			CharacterService c = env.cellContent(hgt, wdt).getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			wdt = wdt - 1;
			env.cellContent(hgt, wdt).setCharacter(c);
			
		}
	}

	@Override
	public void goRight() {
		
		if (wdt >= 0 
			&& env.cellNature(hgt,wdt + 1) != Cell.MTL
			&& env.cellNature(hgt,wdt + 1) != Cell.PLT) {
			
			CharacterService c = env.cellContent(hgt, wdt).getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			wdt = wdt + 1;
			env.cellContent(hgt, wdt).setCharacter(c);
						
		}
	}

	@Override
	public void goUp() {
		
		if (hgt != env.getHeight() - 1
			&& env.cellNature(hgt + 1,wdt) != Cell.MTL
			&& env.cellNature(hgt + 1,wdt) != Cell.PLT
			&& env.cellNature(hgt,wdt) == Cell.LAD
			&& env.cellContent(hgt + 1,wdt).getCharacter() == null) {
			
			CellContentService ccs = env.cellContent(hgt, wdt); 
			CharacterService c = ccs.getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			hgt = hgt + 1;
			env.cellContent(hgt, wdt).setCharacter(c);
		}
	}

	@Override
	public void goDown() {
		if (hgt != 0 
			&& env.cellNature(hgt - 1,wdt) != Cell.MTL
			&& env.cellNature(hgt - 1,wdt) != Cell.PLT
			&& env.cellContent(hgt - 1,wdt).getCharacter() == null) {
				
			CellContentService ccs = env.cellContent(hgt, wdt); 
			CharacterService c = ccs.getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			hgt = hgt - 1;
			env.cellContent(hgt, wdt).setCharacter(c);		
		}
	}

	@Override
	public EnvironnementService getEnvi() {
		return env;
	}

	@Override
	public int getHeight() {
		return hgt;
	}

	@Override
	public int getWidth() {
		return wdt;
	}

	

	@Override
	public void setHeight(int hgt) {
		this.hgt = hgt;
	}

	@Override
	public void setWidth(int wdt) {
		this.wdt = wdt;
	}

	@Override
	public void setEnvironnement(EnvironnementService es) {
		this.env = es;
		
	}

	
	

}
