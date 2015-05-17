package drunky;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class panel extends JPanel{
	
	public Image image;
	public BufferedImage ArrierePlan;
	public Graphics buffer;
	
	public panel(){
		
		this.setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g){
		ArrierePlan = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		buffer = ArrierePlan.getGraphics();
		image = getToolkit().getImage("android_logo_low_quality.jpg");
		g.setColor(Color.GREEN);
		buffer.drawImage(image, this.getWidth()-100, this.getHeight()-100, this);
		buffer.fillRect(0, 0, 700, 348);
		g.drawImage(ArrierePlan,0,0,this);
	}
	
}
