package Gloomhaven.TempStorage;

public interface Cards {
	public CardDataObject getTop(String name);
	public CardDataObject getBottom(String name);
	public CardDataObject getData(int i);
	
}