package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject;

public interface CardInterface {
	public CardDataObject getTop(int id);
	public CardDataObject getBottom(int id);
	public CardDataObject getData(int id);
}
