package impl;

public class Hole {
	private int x;
	private int y;
	private double time_since_creation;
	public Hole(int x, int y, int time_since_creation) {
		this.x = x;
		this.y = y;
		this.time_since_creation = time_since_creation;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public double getTsc() {
		return this.time_since_creation;
	}
	
	public void setTsc(double time) {
		this.time_since_creation = time;
	}
}
