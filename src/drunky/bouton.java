package drunky;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class bouton extends JButton{
	
	public String nom;
	public BufferedImage bkg;
	public Graphics buffer;
	public Image img;
	
	public bouton(String name){
		super(name);
		nom = name;
		
	}

	public void paintComponent(Graphics p){
		bkg = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		buffer = bkg.getGraphics();
		img = getToolkit().getImage("android_logo_low_quality.jpg");
		
		buffer.setColor(Color.LIGHT_GRAY);
		//buffer.fillRect(this.getInsets().left+10, this.getInsets().top+10, this.getWidth() -this.getInsets().right-10, this.getHeight()-this.getInsets().bottom-10);
		buffer.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		p.drawImage(bkg, 0, 0, this);
		
	}
}
