package decorators;

import enums.Move;
import impl.ItemImpl;
import services.CharacterService;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;

public class GuardDecorator implements GuardService {
	
	private final GuardService delegate;
	
	@Override
	public void init(int x, int y, EngineService engineImpl) {
		delegate.init(x, y, engineImpl);
		
	}
	
	public GuardDecorator(GuardService guard) {
		this.delegate = guard;
	}

	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public void goLeft() {
		delegate.goLeft();
	}

	@Override
	public void goRight() {
		delegate.goRight();
		
	}

	@Override
	public void goUp() {
		delegate.goUp();
	}

	@Override
	public void goDown() {
		delegate.goDown();
	}

	@Override
	public EnvironnementService getEnvi() {
		return delegate.getEnvi();
	}

	@Override
	public void setHeight(int hgt) {
		delegate.setHeight(hgt);
	}

	@Override
	public void setWidth(int wdt) {
		delegate.setWidth(wdt);
	}

	@Override
	public void setEnvironnement(EnvironnementService es) {
		delegate.setEnvironnement(es);
	}

	@Override
	public Move behaviour() {
		return delegate.behaviour();
	}

	@Override
	public CharacterService getTarget() {
		return delegate.getTarget();
	}

	@Override
	public int timeInHole() {
		return delegate.timeInHole();
	}

	@Override
	public void climbLeft() {
		delegate.climbLeft();
	}

	@Override
	public void climbRight() {
		delegate.climbRight();
	}

	@Override
	public void step() {
		delegate.step();
	}

	@Override
	public int getId() {
		return delegate.getId();
	}

	@Override
	public boolean getCatched() {
		return delegate.getCatched();
	}

	@Override
	public void setCatched(boolean c) {
		delegate.setCatched(c);
	}

	@Override
	public int getPv() {
		return delegate.getPv();
	}

	@Override
	public void decPv() {
		delegate.decPv();
	}
	
	@Override
	public ItemImpl getItem() {
		return delegate.getItem();
	}
}