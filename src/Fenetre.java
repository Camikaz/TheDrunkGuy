import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements ActionListener{
		
	private JPanel affichage = new JPanel();
	private double[] x = new double[4]; //les tableaux de double gardent plus de précisions, ensuite on arrondi 
	private double[] y = new double[4];
	private int[] x2 = new int[x.length]; // tableau de int pour transférer les doubles au Polygon
	private int[] y2 = new int[y.length];
	
	private Graphics buffer;
	private BufferedImage ArrierePlan;
	
	
		public Fenetre(String nom){
			this.setTitle(nom);
			
			this.setSize(1000, 700);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
			
			ArrierePlan = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			buffer = ArrierePlan.getGraphics();
			
			this.setContentPane(affichage);
			initial(); //coordonnées en example, le polygon commence au centre (tout petit)
			go();
			
		}
		
		public void paint(Graphics g){
			buffer.setColor(Color.GRAY);
			buffer.fillRect(0, 0, affichage.getWidth(), affichage.getHeight());
			
			buffer.setColor(Color.white);
			buffer.drawLine(0,((int)(affichage.getHeight()*0.5)),affichage.getWidth(), (int)(affichage.getHeight()*0.5));
			for(int i = 0; i <= 10; i++){
				buffer.drawLine(((int)(affichage.getWidth()*0.5)),((int)(affichage.getHeight()*0.5)),((int)(affichage.getWidth()*i*0.1)),this.getHeight());
			}
			buffer.setColor(Color.red);
			buffer.drawLine(0,affichage.getHeight(), affichage.getWidth(), affichage.getHeight());
			if(x != null){
				buffer.fillPolygon(x2, y2, x2.length);
			}
			
			g.drawImage(ArrierePlan, 0,0, this);
			
		}

		
		private void initial() {
			// obstacle initial au centre de l'écran
			
			x[0]= (984*0.5) - 2;
			x[1] = (984*0.5) + 2;
			x[3]= (984*0.5) - 2;
			x[2] = (984*0.5) + 2;
			y[0]= (660*0.5) - 2;
			y[1] = (660*0.5) - 2;
			y[3]= (660*0.5) + 2;
			y[2] = (660*0.5) + 2;
			
		}


		public void actionPerformed(ActionEvent e) { //déclenché avec le bouton 

			go();
			//grossissement(x,y);
			
				
			}
		
		/*private void grossissement(double[] x, double[] y) { // pas prêt
			System.out.println("lol");
			System.out.println(affichage.getHeight());
			double coefGro = 1.1;	// valeur du grossissement à chaque itération
			for(int s = 0; s < 4; s++){
				x[s] = coefGro*x[s];
				y[s] = coefGro*y[s];
			}	
			for(int u = 0; u < x.length; u++){
				x2[u] = (int) x[u];
				y2[u] = (int) y[u];
			}
				
				affichage.receivePoly(x2,y2);
				affichage.paintComponent(affichage.getGraphics());
				
			
			
		}
		*/

		public void go(){
			// parcours le chemin sur une des lignes blanches de manière random
			int direction = (int)(Math.random()*10)+1;
			
			System.out.println(direction); 
			
			// mouvement de l'obstacle vers le premier plan !

			for(int distance = 0; distance <= distance+1; distance++){
				
				// arrondi des coordonnées
				for(int u = 0; u < x.length; u++){
					x2[u] = (int) x[u];
					y2[u] = (int) y[u];
				}
				
				// envoi les new coord et repaint
				repaint();
				
				
				try {
			      Thread.sleep(10);
			    } catch (InterruptedException e1) {
			      e1.printStackTrace();
			    }
			    
				
				
				if(direction < 5){
					x[2] = x[2] -1; // point de référence 
					x[0] = x[2] - 4 - distance*0.5;
					x[1] = x[2];
					x[3] = x[0];
				}else if(direction > 5){
					x[2] = x[2] +1; // point de référence
					x[0] = x[2] - 4 + distance*0.5;
					x[1] = x[2]; 
					x[3] = x[0]; 
				} else if (direction == 5){
					x[0] = x[2] - 4 - distance*0.5;
					x[1] = x[2]; 
					x[3] = x[0];
				}
				
				
				if(direction < 5){
					y[2] = (y[2] + affichage.getHeight()*(1/(affichage.getWidth()*(1-2*direction*0.1))));
					y[3] = y[2];
					y[1]= y[2] - 0.5*distance;
					y[0] = y[1];
				}else if(direction > 5){
					y[2] = (y[2] + affichage.getHeight()*(1/(affichage.getWidth()*(2*direction*0.1-1))));
					y[3] = y[2];
					y[1]= y[2] + 0.5*distance;
					y[0] = y[1];
				}else if(direction == 5){
					y[2] = (y[2] + 1);
					y[3] = y[2];
					y[1]= y[2] + 0.5*distance;
					y[0] = y[1];
				}
				
				if(y[2] >= affichage.getHeight()){
					System.out.println("J'aime le paté"+ affichage.getHeight());
					break;
				}
				
				
				
			}
			
		}
}
