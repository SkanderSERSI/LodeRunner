package impl;

import enums.ItemType;

public class ItemImpl{

	static int cpt = 0;
	private int height;
	private int width;
	private int id; 
	private ItemType nature;
	
	
	public ItemImpl(int height,int width, ItemType nature) {
		this.height = height;
		this.width = width;
		id = cpt++;
		this.nature = nature;
	}

	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
	}


	
	public int getId() {
		return id ;
	}

	
	public ItemType nature() {
		return nature;
	}

	
	public void setWidth(int x) {
		this.width = x;
		
	}

	
	public void setHeight(int y) {
		this.height = y;
	}
	
	public String toString() {
		
		switch (this.nature) {
		case Treasure:
			return "T";
		case Trap:
			return "W";
		case Bomb:
			return "B";
		case Oil:
			return "~";
		
		}
		return "";
			
		
	}

}
