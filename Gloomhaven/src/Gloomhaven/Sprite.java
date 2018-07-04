package Gloomhaven;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Sprite {
    Image image;
    int width;
    int height;
    String name;
    
    /* Two constructors for setting up initial values and for easy
     * initialization.
     */
    public Sprite(String file) {
        width = height = 0;
        image = null;
        setImage(file);
    }

  
    /* Setter and getter method for the image attribute.
     * The setter method has the additional task of assigning the width
     * and height variables based on the image.
     */
    public void setImage(String file) {
    	ImageIcon icon = new ImageIcon(file);
    	image = icon.getImage();
        width =image.getWidth(null);
        height = image.getHeight(null);
    }
    
    public void setImage(Image img) {
    	image = img;
        width =image.getWidth(null);
        height = image.getHeight(null);
    }
    
    public void scaleSprites(int w, int h) {
    	Image temp=image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon icon=new ImageIcon(temp);
		image=icon.getImage();
    }
    
    public Image getImage() { return image; }
    public int getWidth() {return width; }
    public int getHeight() { return height; }
}
