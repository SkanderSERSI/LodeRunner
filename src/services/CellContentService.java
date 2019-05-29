package services;

import impl.ItemImpl;

public interface CellContentService {
	
	CharacterService getCharacter();
	ItemImpl getItem();
	boolean isEmpty();
	
	void setCharacter(CharacterService c);
	void setItem(ItemImpl item);
}
