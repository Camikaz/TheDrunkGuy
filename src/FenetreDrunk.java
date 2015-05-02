import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.Timer;


public class FenetreDrunk extends JFrame implements MouseListener,
		MouseMotionListener, ActionListener, KeyListener {
	
	private Timer timer;
	private LinkedList<Objet> Liste; //La Liste de tout les objets a afficher
	private Graphics buffer;
	private BufferedImage ArrierePlan;
	private int temps;
	private double sourx;
	private double soury;
	
	public static double LARGEUR = 1000;
	public static double HAUTEUR = 1000;
	
	public FenetreDrunk(){
		setSize((int)LARGEUR+this.getInsets().left + this.getInsets().right,(int)HAUTEUR+this.getInsets().top + this.getInsets().bottom);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Drunk Guy");
		
		
		
		Liste = new LinkedList<Objet>();
		
		/*Creation de polygone*/
		Point A = new Point(200,00);
		Point B = new Point(300,000);
		Point C = new Point(400,600);
		Point D = new Point(300,600);
		Point E = new Point(000,300);
		
		Point[] tablo = {A,B,C,D,E};
		Objet Poly1 = new Obstacle(tablo,10,-0.01);
		
		Point AA = new Point(00,00);
		Point BB = new Point(100,00);
		Point CC = new Point(100,100);
		Point DD = new Point(0000,100);
		
		Point[] tabloo = {AA,BB,CC,DD};
		Obstacle Poly11 = new Obstacle(tabloo,5,0.01);
		
		Point A2 = new Point(000,400);
		Point B2 = new Point(000,600);
		Point C2 = new Point(300,600);
		Point D2 = new Point(300,400);
		
		Point[] tablo2 = {A2,B2,C2,D2};
		
		Obstacle Poly2 = new Obstacle(tablo2,0,0.01);
		
		Point A3 = new Point(800,300);
		Point B3 = new Point(800,700);
		Point C3 = new Point(1200,700);
		Point D3 = new Point(1200,300);
		Point E3 = new Point(650,500);
		
		Point[] tablo3 = {A3,E3,B3,C3,D3};
		Obstacle Poly3 = new Obstacle(tablo3,5,0.02);
		
		Point A4 = new Point(-300,300);
		Point B4 = new Point(-300,-300);
		Point C4 = new Point(-100,-200);
		Point D4 = new Point(-100,300);
		
		Point[] tablo4 = {A4,B4,C4,D4};
		Obstacle Poly4 = new Obstacle(tablo4,5,0.01);
		
		

		//fin de creation
		
		Liste.add(Poly1);
		Liste.add(Poly11);
		Liste.add(Poly2);
		Liste.add(Poly3);
		Liste.add(Poly4);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		timer = new Timer(30,this);
		timer.start();
		
		System.out.println(Poly1.getClass());
		
	}
	
	public void paint(Graphics g){
		
		ArrierePlan = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		buffer = ArrierePlan.getGraphics();
		
		LARGEUR = this.getWidth();
		HAUTEUR = this.getHeight();
		
		// la merde d'Alestair pour faire des jolis petits traits
		buffer.setColor(Color.GRAY);
		buffer.fillRect(0, 0, this.getWidth(), this.getHeight());
				
		buffer.setColor(Color.white);
		double xO = FenetreDrunk.LARGEUR*0.5 - Obstacle.Obj.x;
		double yO = FenetreDrunk.HAUTEUR*0.5 + Obstacle.Obj.y;
		buffer.drawLine(0,((int)(yO)),this.getWidth(), (int)(yO));
		double x1;
		double y1;
		
		for(int i = -10; i <= 10; i++){
			
			xO = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(LARGEUR*0.05*i -Obstacle.Obj.x)/(10000 - Obstacle.zOb); //-9 est la profondeur des lignes
			yO = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(0-Obstacle.Obj.y)/(10000 - Obstacle.zOb);
			xO = LARGEUR*0.5 - xO;
			yO = HAUTEUR*0.5 + yO;
			
			x1 = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(LARGEUR*0.05*i -Obstacle.Obj.x)/(-9 - Obstacle.zOb); //-9 est la profondeur des lignes
			y1 = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(0-Obstacle.Obj.y)/(-9 - Obstacle.zOb);
			x1 = LARGEUR*0.5 - x1;
			y1 = HAUTEUR*0.5 + y1;
			
			buffer.setColor(Color.getHSBColor((float) (i*0.1), 1, 1));
			buffer.drawLine(((int)(xO)),(int)(yO),(int)(x1),(int) y1);
		}
		for(int i = -9; i <= 500; i++){
			xO = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(-LARGEUR*0.5 -Obstacle.Obj.x)/(i - Obstacle.zOb); //i est la profondeur des lignes
			
			yO = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(0-Obstacle.Obj.y)/(i - Obstacle.zOb);
			xO = LARGEUR*0.5 - xO;
			
			yO = HAUTEUR*0.5 + yO;
			
			x1 = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(LARGEUR*0.5 -Obstacle.Obj.x)/(i - Obstacle.zOb);
			y1 = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(0-Obstacle.Obj.y)/(i - Obstacle.zOb);
			x1 = LARGEUR*0.5 - x1;
			y1 = HAUTEUR*0.5 + y1;
			buffer.setColor(Color.getHSBColor((float) (i*0.01), 1,(float) ((500-i)*0.002)));
			buffer.drawLine(((int)(xO)),(int)(yO),(int)(x1),(int) y1);
		}
		
		//Dessin des polygones
		
		int[] ordre = new int[Liste.size()];
		ordre = Objet.tri(Liste);
		for(int i = 0; i<= Liste.size() -1 ; i++){ //On parcourt la liste de polygones et les dessine
			Liste.get(ordre[i]).draw(buffer);
		}
		
		//Petit test d'intersection
		buffer.setColor(Color.BLACK);
		LinkedList<Point> PtInter = new LinkedList<Point>();
		LinkedList<Objet> ZInter = new LinkedList<Objet>();
		for(int i = 0; i<= Liste.size()-1; i++){
			for(int j = i ; j <= Liste.size()-1 ; j++){
				if(i!=j){
					if(Liste.get(i).Intersect(Liste.get(j)) ){
						PtInter.addAll(Liste.get(i).IntersectList(Liste.get(j)));
						for(int k = 0; k < Liste.get(i).IntersectList(Liste.get(j)).size() ; k++) {
							ZInter.add(Liste.get(i));
						}
					}
				}
			}
		}
		buffer.drawString("Ca touche "+ PtInter.size() +" fois !", 20, 50);
		
		for(int l = 0 ; l <= PtInter.size()-1 ; l++){
			x1 = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(PtInter.get(l).x -Obstacle.Obj.x)/(ZInter.get(l).z - Obstacle.zOb);
			y1 = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(PtInter.get(l).y-Obstacle.Obj.y)/(ZInter.get(l).z - Obstacle.zOb);
			x1 = LARGEUR*0.5 - x1;
			y1 = HAUTEUR*0.5 + y1;
			buffer.drawString("Ca touche ",(int) (x1), (int) (y1));
		}
		
		
		g.drawImage(ArrierePlan,0,0,this);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//Evolution de chaque objet a remplacer par la methode move de chaque objet
		for(int i = 0; i<= Liste.size() -1 ; i++){
			for(int j = 0; j <= Liste.get(i).npoints -1 ; j++){
				Liste.get(i).move();
			}
		}
		
		// juste pour test
		Liste.get(0).translate(5*Math.cos(temps*0.01), 0);
		Liste.get(0).rotate(Math.cos(temps*0.01), Liste.get(0).points[2]);
		
		Liste.get(1).rotate(temps*0.01, Liste.get(1).points[2]);
		
		Obstacle.Obj.x = -LARGEUR*0.5 + sourx;
		Obstacle.Obj.y = HAUTEUR*0.5 - soury;
		
		
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	if(arg0.getKeyChar()=='-'){
		Obstacle.zP +=1;
	}
	if(arg0.getKeyChar()=='+'){
		Obstacle.zP -=1;
	}
		
	}

}
