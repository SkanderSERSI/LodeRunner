package decorators;

import services.CharacterService;
import services.EnvironnementService;

public class CharacterDecorator implements CharacterService {
	private CharacterService delegate;

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
		delegate.goUp();
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
}
