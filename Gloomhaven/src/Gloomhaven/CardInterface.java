package Gloomhaven;

public interface CardInterface {
	public CardDataObject getTop(String name);
	public CardDataObject getBottom(String name);
	public CardDataObject getData(int i);
}
