package services;

public interface CharacterService {

	public int getHeight();
	public int getWidth();
	void goLeft();
	void goRight();
	void goUp();
	void goDown();
	EnvironnementService getEnvi();
	public void setHeight(int hgt);
	public void setWidth(int wdt);
	public void setEnvironnement(EnvironnementService es);
	
	
}
