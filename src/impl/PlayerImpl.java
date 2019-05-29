package impl;

import java.util.ArrayList;
import contracts.GuardContract;
import enums.Cell;
import enums.Command;
import enums.ItemType;
import services.EngineService;
import services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService {

	private EngineService eng;
	private int score;
	private int tresors_captures;
	
	public PlayerImpl() {
		super();
	}
	
	public void init(int height, int width, EngineService eng) {
		setHeight(height);
		setWidth(width);
		this.eng = eng;
		score = 0;
		tresors_captures = 0;
	}

	@Override
	public EngineService getEngine() {
		return eng;
	}

	@Override
	public void step() {
		if ((eng.getEnvironnement().cellNature(getHeight(), getWidth()) != Cell.HDR
				&& eng.getEnvironnement().cellNature(getHeight(), getWidth()) != Cell.LAD)
				&& (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.EMP
						|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.HDR
						|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.HOL && 
						eng.getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() == null)) {
			goDown();
			
			if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract
					&& eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) != Cell.HOL) {
				
				System.out.println("Vous perdez une vie...");
				eng.restart(eng.getLvl());
				eng.setPv(eng.getPv() - 1);
				eng.setNextCommand(null);
				
			}
			
			if(env.cellContent(getHeight(), getWidth()).getItem() != null) {
				if(env.cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
					setScore(getScore() + 20);
					env.cellContent(getHeight(), getWidth()).setItem(null);
					incTresors();
				}
			}

		} else {
			Command cmd = eng.nextCommand();
			if(cmd != null) {
				switch (cmd) {
				case Up:
					if(getHeight() + 1 > eng.getEnvironnement().getHeight())
						break;
					goUp();
					if(env.cellContent(getHeight(), getWidth()).getItem() != null) {
						if(env.cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
							setScore(getScore() + 20);
							env.cellContent(getHeight(), getWidth()).setItem(null);
							incTresors();
						}
					}
					break;
				case Left:
					if(getWidth() - 1 < 0)
						break;
					if(eng.getEnvironnement().cellContent(getHeight(), getWidth()-1).getCharacter() instanceof GuardContract) {
						System.out.println("Vous perdez une vie...");
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
						eng.setNextCommand(null);
					} else {
						goLeft();
						if(eng.getEnvironnement().cellContent(getHeight(),getWidth()).getItem() != null) {
							if(env.cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
								setScore(getScore() + 20);
								env.cellContent(getHeight(), getWidth()).setItem(null);
								incTresors();
							}
						}
					}
					break;
				case Right:
					if(getWidth() + 1 >= eng.getEnvironnement().getWidth())
						break;
					if(eng.getEnvironnement().cellContent(getHeight(), getWidth()+1).getCharacter() instanceof GuardContract) {
						
						System.out.println("Vous perdez une vie...");
						eng.restart(eng.getLvl());
						eng.setPv(eng.getPv() - 1);
						eng.setNextCommand(null);
						
					} else {
						goRight();
						if(env.cellContent(getHeight(), getWidth()).getItem() != null) {
							if(env.cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
								setScore(getScore() + 20);
								env.cellContent(getHeight(), getWidth()).setItem(null);
								incTresors();
							}
						}
					}
					break;
				case Down:
					if(getHeight() - 1 < 0)
						break;
					goDown();
					
					if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract
							&& eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) != Cell.HOL) {
						
						System.out.println("Vous perdez une vie...");
						eng.restart(eng.getLvl());
						eng.setPv(eng.getPv() - 1);
						eng.setNextCommand(null);
						
					}
					
					if(env.cellContent(getHeight(), getWidth()).getItem() != null) {
						if(env.cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
							setScore(getScore() + 20);
							env.cellContent(getHeight(), getWidth()).setItem(null);
							incTresors();
						}
					}
					break;
				case DigL:
					if(getWidth() == 0) break;
					if (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
							|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
							|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
							|| eng.getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null) {
						if (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth() - 1) == Cell.PLT) {
							eng.getEnvironnement().dig(getHeight() - 1, getWidth() - 1);
							Hole hole = new Hole(getHeight() - 1, getWidth() - 1, 0);
							ArrayList<Hole> holes = eng.getHoles();
							holes.add(hole);
						}
					}
					break;
				case DigR:
					if(getWidth() == eng.getEnvironnement().getWidth()-1) break;
					if (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
							|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
							|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
							|| eng.getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null) {
						if (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth() + 1) == Cell.PLT) {
							eng.getEnvironnement().dig(getHeight() - 1, getWidth() + 1);
							Hole hole = new Hole(getHeight() - 1, getWidth() + 1, 0);
							ArrayList<Hole> holes = eng.getHoles();
							holes.add(hole);
						}
					}
					break;
				case BombL:
					if(getWidth() == 0) break;
					if ((eng.getNbBombs() >0) &&(eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
					|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
					|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
					|| eng.getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null)
					&& (eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR || eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
					&& (eng.getEnvironnement().cellNature(getHeight()-1, getWidth()-1) != Cell.EMP && eng.getEnvironnement().cellNature(getHeight()-1, getWidth()-1) != Cell.HOL)
					&& (eng.getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.PLT && eng.getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.MTL && eng.getEnvironnement().cellNature(getHeight(), getWidth()-1) != Cell.LAD
					)) {
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()-1).getItem()== null) {
							eng.getEnvironnement().cellContent(getHeight(), getWidth()-1).setItem(new ItemImpl(getHeight(), getWidth()-1, ItemType.Bomb));
							eng.decBombs();
						}
						
					}
					break;
				
				case BombR:
					if(getWidth() == eng.getEnvironnement().getWidth()-1) break;
					if ( (eng.getNbBombs() >0) && (eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.MTL
					|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.PLT
					|| eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()) == Cell.LAD
					|| eng.getEnvironnement().cellContent(getHeight() - 1, getWidth()).getCharacter() != null)
					&& (eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR || eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.EMP)
					&& (eng.getEnvironnement().cellNature(getHeight()-1, getWidth()+1) != Cell.EMP && eng.getEnvironnement().cellNature(getHeight()-1, getWidth()+1) != Cell.HOL)
					&& (eng.getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.PLT && eng.getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.MTL && eng.getEnvironnement().cellNature(getHeight(), getWidth()+1) != Cell.LAD
					)) {
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()+1).getItem()== null) {
							eng.getEnvironnement().cellContent(getHeight(), getWidth()+1).setItem(new ItemImpl(getHeight(), getWidth()+1, ItemType.Bomb));
							eng.decBombs();
						}
						
					}
					break;
				}
			}
		}
		
		if ((eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.HDR 
				&& eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.LAD) 
				&& (eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.EMP
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HDR 
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HOL	&&
				eng.getEnvironnement().cellContent(getHeight()-1,getWidth()).getCharacter() == null)) {
					goDown();
					
					if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract
							&& eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) != Cell.HOL) {
						
						System.out.println("Vous perdez une vie...");
						eng.restart(eng.getLvl());
						eng.setPv(eng.getPv() - 1);
						eng.setNextCommand(null);
						
					}
					
					if(env.cellContent(getHeight(), getWidth()).getItem() != null) {
						setScore(getScore() + 20);
						env.cellContent(getHeight(), getWidth()).setItem(null);
						incTresors();
					}
		}
		
		if (getTresorsTrouves() == eng.getPointsTreasures().size()) {
			System.out.println("BRAVO ! Passage au niveau superieur...");
			eng.setPv(eng.getPv() + 1);
			eng.setLvl(eng.getLvl()+1);
			eng.restart(eng.getLvl());
			
		}

	}

	@Override
	public String toString() {
		return "O";

	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public int getTresorsTrouves() {
		return tresors_captures;
	}
	
	public void incTresors() {
		tresors_captures++;
	}

}
