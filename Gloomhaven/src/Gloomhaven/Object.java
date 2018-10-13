package Gloomhaven;

public class Object {
	
	int id;
	String name;
	
	public Object(int id) {
		
		this.id=id;
		
		switch(id) {
			case 1:
				name="Door";
				break;
		}
	}
}
