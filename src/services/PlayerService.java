package services;

public interface PlayerService extends CharacterService {

	EngineService getEngine();
	void step();
	public int getScore();
	public void setScore(int score);
	public int getTresorsTrouves();
	public void incTresors() ;
	public void init(int height, int width, EngineService eng);
}
