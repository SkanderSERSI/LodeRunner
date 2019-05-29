package impl;

import services.CellContentService;
import services.CharacterService;


public class CellContent implements CellContentService{
	
	private CharacterService car=null;
	private ItemImpl item=null;
	

	@Override
	public CharacterService getCharacter() {
		return car;
	}

	@Override
	public ItemImpl getItem() {
		return item;
	}

	@Override
	public boolean isEmpty() {
		return car == null && item == null;
	}

	@Override
	public void setCharacter(CharacterService c) {
		this.car = c;
		
	}

	@Override
	public void setItem(ItemImpl item) {
		this.item = item;
		
	}
	

}
