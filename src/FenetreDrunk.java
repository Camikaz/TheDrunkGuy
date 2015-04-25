import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.Timer;


public class FenetreDrunk extends JFrame implements MouseListener,
		MouseMotionListener, ActionListener {
	
	private Timer timer;
	private LinkedList<Objet> Liste; //La Liste de tout les objets a afficher
	private Graphics buffer;
	private BufferedImage ArrierePlan;
	private int temps;
	private double sourx;
	private double soury;
	
	public FenetreDrunk(){
		setSize(1400,1000);
		setResizable(false);
		setLocation(400,000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Drunk Guy");
		
		ArrierePlan = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		buffer = ArrierePlan.getGraphics();
		
		Liste = new LinkedList<Objet>();
		
		/*Creation de polygone*/
		Point A = new Point(100,100);
		Point B = new Point(300,100);
		Point C = new Point(600,600);
		Point D = new Point(200,600);
		Point E = new Point(250,500);
		
		Point[] tablo = {A,B,E,C,D};
		Obstacle Poly1 = new Obstacle(tablo);
		
		Point A2 = new Point(900,400);
		Point B2 = new Point(900,600);
		Point C2 = new Point(1100,600);
		Point D2 = new Point(1100,400);
		
		Point[] tablo2 = {A2,B2,C2,D2};
		
		Obstacle Poly2 = new Obstacle(tablo2);
		
		Point A3 = new Point(800,300);
		Point B3 = new Point(800,700);
		Point C3 = new Point(1200,700);
		Point D3 = new Point(1200,300);
		Point E3 = new Point(650,500);
		
		Point[] tablo3 = {A3,E3,B3,C3,D3};
		Obstacle Poly3 = new Obstacle(tablo3);

		//fin de creation
		
		Liste.add(Poly1);
		Liste.add(Poly2);
		Liste.add(Poly3);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		timer = new Timer(30,this);
		timer.start();
		
	}
	
	public void paint(Graphics g){
		//Dessin du fond
		buffer.setColor(Color.PINK);
		buffer.fillRect(0,0,this.getWidth(),this.getHeight());
		
		
		//Dessin des polygones
		buffer.setColor(Color.BLACK);
		for(int i = 0; i<= Liste.size() -1 ; i++){ //On parcourt la liste de polygones
			for(int j = 0; j <= Liste.get(i).npoints -2 ; j++){ //On parcourt les points du polygones pour tracer ses arretes
				buffer.drawLine((int) Liste.get(i).points[j].x, (int) Liste.get(i).points[j].y, (int) Liste.get(i).points[j+1].x, (int) Liste.get(i).points[j+1].y);
			}
			buffer.drawLine((int) Liste.get(i).points[Liste.get(i).npoints-1].x, (int) Liste.get(i).points[Liste.get(i).npoints-1].y, (int) Liste.get(i).points[0].x, (int) Liste.get(i).points[0].y);
		}
		
		//Petit test d'intersection
		LinkedList<Point> PtInter = new LinkedList<Point>();
		int k = 0;
		for(int i = 0; i<= Liste.size()-1; i++){
			for(int j = i ; j <= Liste.size()-1 ; j++){
				if((Liste.get(i).Intersect(Liste.get(j))&&(i!=j))){
					k++;
					PtInter.add(Liste.get(i).IntersectP(Liste.get(j)));
				}
			}
		}
		buffer.drawString("Ca touche "+ k +" fois !", 20, 50);
		
		for(int l = 0 ; l <= PtInter.size()-1 ; l++){
			buffer.drawString("Ca touche ",(int) (PtInter.get(l).x), (int) (PtInter.get(l).y));
		}
		
		
		g.drawImage(ArrierePlan,0,0,this);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//Evolution de chaque objet a remplacer par la methode move de chaque objet
		for(int i = 0; i<= Liste.size() -1 ; i++){
			for(int j = 0; j <= Liste.get(i).npoints -1 ; j++){
				Liste.get(i).points[j].x = Liste.get(i).points[j].x + Liste.get(i).points[j].dx;
				Liste.get(i).points[j].y = Liste.get(i).points[j].y + Liste.get(i).points[j].dy;
				Liste.get(i).points[j].dx = Liste.get(i).points[j].dx + Liste.get(i).points[j].ddx;
				Liste.get(i).points[j].dy = Liste.get(i).points[j].dy + Liste.get(i).points[j].ddy;
			}
		}
		
		// juste pour test
		Liste.get(0).translate(5, 0);
		Liste.get(0).rotate(3, Liste.get(0).points[2]);

		
		repaint();
		temps ++;
		

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		sourx = arg0.getX();
		soury = arg0.getY();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
