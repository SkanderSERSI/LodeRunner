package impl;

import contracts.GuardContract;
import contracts.PlayerContract;
import enums.Cell;
import enums.ItemType;
import enums.Move;
import services.CharacterService;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;

public class GuardImpl extends CharacterImpl implements GuardService {
	
	static int cpt = 0;
	private int id;
	private EngineService eng;
	private int timeInHole;
	private boolean catched = false;
	private ItemImpl item = null;
	private int pv = 2;
	public GuardImpl() {
		super();
	}
	public void init(int height, int width, EngineService eng) {
		this.setHeight(height);
		this.setWidth(width);
		
		this.id = cpt++;
		this.eng = eng;
		setEnvironnement(eng.getEnvironnement());
		timeInHole = 0;
	}

	
	@Override
	public Move behaviour() {
		EnvironnementService env = eng.getEnvironnement();
		
		if (env.cellNature(getHeight(), getWidth()) == Cell.LAD) {
			if (env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD) {
				if (Math.abs(getTarget().getHeight() - getHeight()) < Math.abs(getTarget().getWidth() - getWidth())) {
					if (eng.getPlayer().getHeight()<getHeight()) {
						return Move.Down;
					} else if (getTarget().getHeight() == getHeight()) {
						if(eng.getPlayer().getWidth()>getWidth()) {
							return Move.Right;
						} else if(eng.getPlayer().getWidth()<getWidth()){
							return Move.Left;
						}
					} else {
						return Move.Up;
					}
				} else {
					if(eng.getPlayer().getWidth()>getWidth()) {
						return Move.Right;
					} else if(eng.getPlayer().getWidth()<getWidth()){
						return Move.Left;
					} else {
						return Move.Up;
					}
				}
			} else if (env.cellNature(getHeight() - 1, getWidth()) == Cell.PLT
					|| env.cellNature(getHeight() - 1, getWidth()) == Cell.MTL
					|| env.cellContent(getHeight() - 1, getWidth()) instanceof GuardContract) {
				
				if(env.cellNature(getHeight() + 1, getWidth()) == Cell.LAD) {
					if(eng.getPlayer().getHeight()>getHeight()) {
						return Move.Up;
					}else if (eng.getPlayer().getHeight()<getHeight()) {
						if(eng.getPlayer().getWidth()>getWidth()) {
							
							return Move.Right;
						} else if(eng.getPlayer().getWidth()<getWidth()){
							return Move.Left;
						}
					}else {
						if(eng.getPlayer().getWidth()>getWidth()) {
							
							return Move.Right;
						} else if(eng.getPlayer().getWidth()<getWidth()){
							return Move.Left;
						}
					}
				}
				
			} else if (env.cellNature(getHeight()-1, getWidth()) == Cell.EMP 
					|| env.cellNature(getHeight()-1, getWidth()) == Cell.HOL
					|| env.cellNature(getHeight()-1, getWidth()) == Cell.HDR) {
				if(eng.getPlayer().getHeight()<getHeight()){
					return Move.Down;
				} else if (eng.getPlayer().getHeight()>getHeight()) {
					return Move.Up;
				} else if(eng.getPlayer().getWidth()>getWidth()) {
					return Move.Right;
				} else if(eng.getPlayer().getWidth()<getWidth()){
					return Move.Left;
				}
			} else {
				if(eng.getPlayer().getWidth()>getWidth()) {
					return Move.Right;
				} else if(eng.getPlayer().getWidth()<getWidth()){
					return Move.Left;
				} else if (eng.getPlayer().getHeight()>getHeight()) {
					return Move.Up;
				}
			}
			
			
		} else if(env.cellNature(getHeight(), getWidth()) == Cell.EMP) {
			
			if (env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD && env.cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract) {
				if(eng.getPlayer().getWidth()>getWidth()) {
					return Move.Right;
				} else if(eng.getPlayer().getWidth()<getWidth()){
					return Move.Left;
				} else if(eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()+1) != Cell.EMP) {
					return Move.Right;
				} else {
					return Move.Left;
				}
				
			}
			if (env.cellNature(getHeight() - 1, getWidth()) == Cell.LAD) {
				if(eng.getPlayer().getHeight()<getHeight()){
					return Move.Down;
				} else if(eng.getPlayer().getWidth()>getWidth()) {
					return Move.Right;
				} else if(eng.getPlayer().getWidth()<getWidth()){
					return Move.Left;
				} else if(eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()+1) != Cell.EMP) {
					return Move.Right;
				} else {
					return Move.Left;
				}
				
			} else if (env.cellNature(getHeight()-1, getWidth()) == Cell.PLT || env.cellNature(getHeight()-1, getWidth()) == Cell.MTL) {
				if(eng.getPlayer().getWidth()>getWidth()) {
					return Move.Right;
				} else if(eng.getPlayer().getWidth()<getWidth()){
					return Move.Left;
				} else if(getWidth() >= 1) {
					return Move.Left;
				} else if(eng.getEnvironnement().cellNature(getHeight() - 1, getWidth()+1) != Cell.EMP) {
					return Move.Right;
				} else {
					return Move.Left;
				}
			}
			
		} else if (env.cellNature(getHeight(), getWidth()) == Cell.HDR || env.cellNature(getHeight(), getWidth()) == Cell.HOL
				|| (env.cellNature(getHeight(), getWidth()) == Cell.EMP && (env.cellNature(getHeight()-1, getWidth()) == Cell.PLT 
				|| env.cellNature(getHeight()-1, getWidth()) == Cell.MTL)
				|| (env.cellNature(getHeight(), getWidth()) == Cell.EMP && env.cellContent(getHeight()-1, getWidth()).getCharacter() != null)
				|| (env.cellNature(getHeight()-1, getWidth()) == Cell.HOL && env.cellContent(getHeight()-1, getWidth()).getCharacter() != null))){
			if(eng.getPlayer().getWidth()>getWidth()) {
				return Move.Right;
			} else if(eng.getPlayer().getWidth()<getWidth()){
				return Move.Left;
			}
			
		} else if (env.cellNature(getHeight(), getWidth()) == Cell.HDR
				&& env.cellNature(getHeight()-1, getWidth()) != Cell.PLT 
				&& env.cellNature(getHeight()-1, getWidth()) != Cell.MTL 
				&& env.cellContent(getHeight()-1, getWidth()).getCharacter() == null) {
			if(eng.getPlayer().getWidth()>getWidth()) {
				return Move.Right;
			} else if(eng.getPlayer().getWidth()<getWidth()){
				return Move.Left;
			} if(eng.getPlayer().getHeight()<getHeight()){
				return Move.Down;
			} else if (eng.getPlayer().getHeight()>getHeight()) {
				return Move.Up;
			}
		
		} else if (env.cellNature(getHeight(), getWidth()) == Cell.LAD
				&& ((env.cellNature(getHeight()-1, getWidth()) == Cell.MTL || env.cellNature(getHeight()-1, getWidth()) == Cell.PLT)
				|| (env.cellNature(getHeight()-1, getWidth()) != Cell.MTL && env.cellNature(getHeight()-1, getWidth()) != Cell.PLT
				&& env.cellContent(getHeight()-1, getWidth()).getCharacter() != null ))&& getCatched() == false)
		{
			int val_width  =  Math.abs(getWidth() - eng.getPlayer().getWidth());
			int val_height =  Math.abs(getHeight() - eng.getPlayer().getHeight());
			if (val_width < val_height) {
				if (getWidth() - eng.getPlayer().getWidth()<0) {
					return Move.Right;
				}
				if (getWidth() - eng.getPlayer().getWidth()>=0) {
					return Move.Left;
				}
			}
			if (val_width > val_height) {
				if (getHeight() - eng.getPlayer().getHeight()<0) {
					return Move.Up;
				}
				if (getHeight() - eng.getPlayer().getHeight()>=0) {
					return Move.Down;
				}
			}
			if(val_height ==0) {
				if (getWidth() - eng.getPlayer().getWidth()<0) {
					return Move.Right;
				}
				if (getWidth() - eng.getPlayer().getWidth()>=0) {
					return Move.Left;
				}
			}
			if(val_width ==0) {
				if (getHeight() - eng.getPlayer().getHeight()<0) {
					return Move.Up;
				}
				if (getHeight() - eng.getPlayer().getHeight()>=0) {
					return Move.Down;
				}
			}
		}
		return Move.Neutral;
		
	}

	@Override
	public CharacterService getTarget() {
		return eng.getPlayer();
	}

	@Override
	public int timeInHole() {
		return 0;
	}

	@Override
	public void climbLeft() {
		int hgt = getHeight();
		int wdt = getWidth();
		if (getEnvi().cellNature(hgt, wdt) == Cell.HOL
				&&(getEnvi().cellNature(hgt+1, wdt-1) != Cell.MTL
				&& getEnvi().cellNature(hgt+1, wdt-1) != Cell.PLT)) {
			
			CharacterService c = env.cellContent(hgt, wdt).getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			wdt = wdt - 1;
			hgt = hgt + 1;
			super.setHeight(hgt);
			super.setWidth(wdt);
			env.cellContent(hgt, wdt).setCharacter(c);
			if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
				if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
					this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
				}else if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
					decPv();
				}
				
				if (pv == 0) {
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(this.item);
					eng.removeGuard(this);
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
				}	
				
			}
			
		}
	}

	@Override
	public void climbRight() {
		int hgt = getHeight();
		int wdt = getWidth();
		if (getEnvi().cellNature(hgt, wdt) == Cell.HOL
				&&(getEnvi().cellNature(hgt+1, wdt+1) != Cell.MTL
				&& getEnvi().cellNature(hgt+1, wdt+1) != Cell.PLT)) {
			
			CharacterService c = env.cellContent(hgt, wdt).getCharacter();
			env.cellContent(hgt, wdt).setCharacter(null);
			wdt = wdt + 1;
			hgt = hgt + 1;
			super.setHeight(hgt);
			super.setWidth(wdt);
			env.cellContent(hgt, wdt).setCharacter(c);
			
			if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
				if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
					this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
				}else if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
					decPv();
				}
				
				if (pv == 0) {
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(this.item);
					eng.removeGuard(this);
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
				}	
				
			}
		}
			
			
		}
	

	@SuppressWarnings("incomplete-switch")
	@Override
	public void step() {
		if ((eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.HDR 
				&& eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.LAD 
				&& eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.HOL) 
				&& (eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.EMP
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HDR 
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HOL
				&& eng.getEnvironnement().cellContent(getHeight()-1,getWidth()).getCharacter() == null)) {
					goDown();
					
					
					if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof PlayerContract) {
						
						System.out.println("Vous perdez une vie...");
						setCatched(true);
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
						eng.setNextCommand(null);
					}
					
					if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
							this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
							eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
						}
						
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
							eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
							decPv();
						}
						
						if (pv == 0) {
							eng.removeGuard(this);
							eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
						}	
						
					}
					
					if (eng.getEnvironnement().cellNature(getHeight(),getWidth()) == Cell.HOL && this.item != null) {
						this.item.setHeight(getHeight()+1);
						this.item.setWidth(getWidth());
						
						// repop le tresor au dessus du garde
						eng.getEnvironnement().cellContent(getHeight()+1,getWidth()).setItem(this.item);
						this.item =null;
					}
			
		} else {
			Move m = behaviour();
			if(eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HOL) {
				
				if(timeInHole<5) timeInHole++;
				if(timeInHole==4) {
					System.out.println("Time In Hole "+timeInHole);
					switch (m) {
					case Left:
						if(eng.getEnvironnement().cellContent(getHeight()+1, getWidth()-1).getCharacter() instanceof PlayerContract) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
						}else {
							climbLeft();
						}
						break;
					case Right:
						if(eng.getEnvironnement().cellContent(getHeight()+1, getWidth()+1).getCharacter() instanceof PlayerContract) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
						}else {
							climbRight();
						}
						break;
					case Neutral:
						break;
					}
					timeInHole = 0;
				}
			}else {
				switch (m) {
				case Left:
					if(getWidth() == 0)
						break;
					if (!(eng.getEnvironnement().cellContent(getHeight(), getWidth()-1).getCharacter() instanceof GuardContract)) {
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()-1).getCharacter() instanceof PlayerContract) {
						
						System.out.println("Vous perdez une vie...");
						setCatched(true);
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
						eng.setNextCommand(null);
						
					} else if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()-1).getCharacter() instanceof PlayerContract
							&& eng.getEnvironnement().cellNature(getHeight()-1, getWidth()-1) == Cell.HOL) {
						
						System.out.println("Vous perdez une vie...");
						setCatched(true);
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
					}
					
					else {
						goLeft();

						if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
							if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
								this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
								eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
							}else if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
								eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
								decPv();
							}
							
							if (pv == 0) {
								eng.removeGuard(this);
								eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
							}	
							
						}
					}
					}
					break;
				case Right:
					
					if (!(eng.getEnvironnement().cellContent(getHeight(), getWidth()+1).getCharacter() instanceof GuardContract)) {
						if(eng.getEnvironnement().cellContent(getHeight(), getWidth()+1).getCharacter() instanceof PlayerContract) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
							
						} else if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()+1).getCharacter() instanceof PlayerContract
								&& eng.getEnvironnement().cellNature(getHeight()-1, getWidth()+1) == Cell.HOL) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
							
						} else {
							goRight();
							if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
								if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
									this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
								}else if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
									decPv();
								}
								
								
								
							}
						}
					}
					break;
				case Up:
					
					if (!(eng.getEnvironnement().cellContent(getHeight()+1, getWidth()).getCharacter() instanceof GuardContract)) {
						if(eng.getEnvironnement().cellContent(getHeight()+1, getWidth()).getCharacter() instanceof PlayerContract) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
							
						} else {
							goUp();
							if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
								if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
									this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
								}else if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
									decPv();
								}
								
								if (pv == 0) {
									eng.removeGuard(this);
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
								}	
								
							}
						}
					}
					
					break;
				case Down:
					if(getHeight() - 1 < 0)
						break;
					if(eng.getEnvironnement().cellNature(getHeight(), getWidth()) == Cell.HDR && eng.getPlayer().getWidth() == getWidth()) {
						System.out.println("Vous perdez une vie...");
						setCatched(true);
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
						eng.setNextCommand(null);
					} else if (!(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof GuardContract)) {
						if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof PlayerContract) {
							
							System.out.println("Vous perdez une vie...");
							setCatched(true);
							eng.setPv(eng.getPv() - 1);
							eng.restart(eng.getLvl());
							eng.setNextCommand(null);
							
						} else {
							goDown();
							if (eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
								if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Treasure) {
									this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
								}
								
								if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem().nature() == ItemType.Bomb) {
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
									decPv();
								}
								
								if (pv == 0) {
									eng.removeGuard(this);
									eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
								}	
								
							}
						}
					}
					break;
				
				case Neutral:
					
					break;
				}
				if(pv ==0) {
					System.out.println("item "+this.item);
					if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
						this.item.setHeight(getHeight());
						this.item.setWidth(getWidth());
					}
					
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(this.item);
					eng.removeGuard(this);
					eng.getEnvironnement().cellContent(getHeight(), getWidth()).setCharacter(null);
					
				}
			}
		}
		
		if(eng.getEnvironnement().cellNature(getHeight(), getWidth()) != Cell.HOL && eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) != Cell.HOL && eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof PlayerContract) {
			
			System.out.println("Vous perdez une vie...");
			setCatched(true);
			eng.setPv(eng.getPv() - 1);
			eng.restart(eng.getLvl());
			eng.setNextCommand(null);
		}
		
		if ((eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.HDR 
				&& eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.LAD 
				&& eng.getEnvironnement().cellNature(getHeight(),getWidth()) != Cell.HOL 
				&& (eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.EMP
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HDR 
				|| eng.getEnvironnement().cellNature(getHeight()-1, getWidth()) == Cell.HOL
				&& eng.getEnvironnement().cellContent(getHeight()-1,getWidth()).getCharacter() == null))) {
					goDown();
					
					if(eng.getEnvironnement().cellContent(getHeight()-1, getWidth()).getCharacter() instanceof PlayerContract) {
						
						System.out.println("Vous perdez une vie...");
						setCatched(true);
						eng.setPv(eng.getPv() - 1);
						eng.restart(eng.getLvl());
						eng.setNextCommand(null);
					}
					
					if(eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem() != null) {
						this.item =  eng.getEnvironnement().cellContent(getHeight(), getWidth()).getItem();
						eng.getEnvironnement().cellContent(getHeight(), getWidth()).setItem(null);
					}
					
					if (eng.getEnvironnement().cellNature(getHeight(),getWidth()) == Cell.HOL && this.item != null) {
						this.item.setHeight(getHeight()+1);
						this.item.setWidth(getWidth());
						
						eng.getEnvironnement().cellContent(getHeight()+1,getWidth()).setItem(this.item);
						this.item =null;
					}
		}
		
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	
	@Override
	public String toString() {
		return "G" + getId();
		}
		
	@Override
	public boolean getCatched() {
		return catched;
	}
	@Override
	public void setCatched(boolean c) {
		this.catched = c;
	}
	@Override
	public int getPv() {
		return pv;
	}
	
	@Override
	public void decPv() {
		this.pv--;
	}
	@Override
	public ItemImpl getItem() {
		return this.item;
	}
	
}
