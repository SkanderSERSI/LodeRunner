package services;

import enums.Move;
import impl.ItemImpl;

public interface GuardService extends CharacterService {

	Move behaviour();
	CharacterService getTarget();
	int timeInHole();
	void climbLeft();
	void climbRight();
	void step();
	int getId();
	boolean getCatched();
	void setCatched(boolean c);
	void init(int x, int y, EngineService engineImpl);
	int getPv();
	void decPv();
	ItemImpl getItem();
}
