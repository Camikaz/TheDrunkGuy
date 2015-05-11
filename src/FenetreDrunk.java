import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.Timer;



public class FenetreDrunk extends JFrame implements MouseListener,
		MouseMotionListener, ActionListener, KeyListener {

	private Timer timer;
	private LinkedList<Objet> Liste; //La Liste de tout les objets a afficher

	private boolean admin = false;

	private Graphics buffer; //On dessine la dedans, vous connaissez le principe d'un buffer
	private BufferedImage ArrierePlan;

	public static int temps;

	//Les valeurs initiales de la position de la souris, modifi�s par le MouseListener
	private double sourx = LARGEUR*0.5;
	private double soury = HAUTEUR* 0.5;

	//Hauteur et Largeur initiale de la fenetre (pour l'instant j'ai fait en sorte que �a soir resizable)
	public static double LARGEUR = 1000;
	public static double HAUTEUR = 700;

	//2 objets utiles pour la musique
	public static Mixer mixer;
	public static Clip clip, sonCollision;

	Obstacle Guy;

	public FenetreDrunk(){

		if(MenuJeu.sound){

			//Tout ceci sert � configurer le son
			Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();

			mixer = AudioSystem.getMixer(mixInfos[0]); //ok you got it

			DataLine.Info dataInfo = new DataLine.Info(Clip.class ,  null); // choisi un type de ligne (ici un clip)
			try{ clip = (Clip) mixer.getLine(dataInfo);} // l'instance clip devient la ligne de mixer qui r�pond au crit�re DataLina.Info
			catch(LineUnavailableException lue) { lue.printStackTrace(); }

			try {
				URL soundURL = FenetreDrunk.class.getResource("Rank2.wav"); //va chercher le son
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
				clip.open(audioStream);
			}
			catch(LineUnavailableException lue) { lue.printStackTrace(); }
			catch(UnsupportedAudioFileException uafe)  {uafe.printStackTrace(); }
			catch(IOException ioe) { ioe.printStackTrace();}

			//config du son collision "bounce.wav"
			try{ sonCollision = (Clip) mixer.getLine(dataInfo);}
			catch(LineUnavailableException lue) { lue.printStackTrace(); }
			try {
				URL soundURL = FenetreDrunk.class.getResource("bounce.wav"); //va chercher le son
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
				sonCollision.open(audioStream);
			}
			catch(LineUnavailableException lue) { lue.printStackTrace(); }
			catch(UnsupportedAudioFileException uafe)  {uafe.printStackTrace(); }
			catch(IOException ioe) { ioe.printStackTrace();}
			sonCollision.setLoopPoints(2000,22000);

			//Place this wherever you want - Appuyer sur M pour arrêter la musique
			clip.loop(Clip.LOOP_CONTINUOUSLY); //or c lip.loop(0); clip.loop(LOOP_CONTINUOUSLY);
			//Fin de la configuration du son
		}

		//Initialisation de la fenetre
		setSize((int)LARGEUR+this.getInsets().left + this.getInsets().right,(int)HAUTEUR+this.getInsets().top + this.getInsets().bottom);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Drunk Guy");

		//Declaration de la liste des objets
		Liste = new LinkedList<Objet>();

		//Rectangle qui définit le DrunkGuy - dimensions
		Point dg1 = new Point(-150,0);
		Point dg2 = new Point(-150,700);
		Point dg3 = new Point(150,700);
		Point dg4 = new Point(150,0);
		Point[] tabGuy = {dg1, dg2, dg3, dg4};
		Guy = new Obstacle(tabGuy, 10, 1);
		Liste.add(Guy);
		
		
		/*Creation des polygones*/
		Point A = new Point(200,0);
		Point B = new Point(300,0);
		Point C = new Point(400,600);
		Point D = new Point(300,600);
		Point E = new Point(0,300);

		Point[] tablo = {A,B,C,D,E};
		Objet Poly1 = new Obstacle(tablo,10,0.7); //On rentre dans l'objet un tablo de point, un z, et une vitesse dz

		Point AA = new Point(0,0);
		Point BB = new Point(100,0);
		Point CC = new Point(100,100);
		Point DD = new Point(0,100);

		Point[] tabloo = {AA,BB,CC,DD};
		Obstacle Poly11 = new Obstacle(tabloo,5,0.015);

		Point A2 = new Point(0,400);
		Point B2 = new Point(0,600);
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
		Point B4 = new Point(-300,000);
		Point C4 = new Point(-100,000);
		Point D4 = new Point(-100,300);

		Point[] tablo4 = {A4,B4,C4,D4};
		Obstacle Poly4 = new Obstacle(tablo4,5,0.01);

		//On ajoute les objets cr�es � la liste
		Liste.add(Poly1);
		Liste.add(Poly11);
		Liste.add(Poly2);
		Liste.add(Poly3);
		Liste.add(Poly4);

		//Des maisons
		for(int i = 0; i< 100 ; i++){
			double positionLaterale = 10000*Math.random();
			A = new Point(positionLaterale-500,0);
			B = new Point(positionLaterale+500,0);
			C = new Point(positionLaterale+500,1500);
			D = new Point(positionLaterale,2000);
			E = new Point(positionLaterale-500,1500);
			Point[] tabloCo = {A,B,C,D,E};
			Obstacle Maison = new Obstacle(tabloCo, i*25 , 0);
			//Maison.angularSpeed = 10*Math.sin(0.01*i); //vitesse angulaire random, juste pour le fun
			Liste.add(Maison);
		}

		//Encore des maisons
		for(int i = 0; i<100 ; i++){
			double positionLaterale = -10000*Math.random();
			A = new Point(positionLaterale-500,0);
			B = new Point(positionLaterale+500,0);
			C = new Point(positionLaterale+500,1500);
			D = new Point(positionLaterale,2000);
			E = new Point(positionLaterale-500,1500);
			Point[] tabloCo = {A,B,C,D,E};
			Objet Maison = new Obstacle(tabloCo, i*25 , 0);
			Liste.add(Maison);
		}

		// voiture
		double[] limvoiture = {-3000,3000, 0, 1000, 0,10000};
		A = new Point(-3000,0);
		B = new Point(-3000,500);
		C = new Point(-2000,500);
		D = new Point(-2000,0);
		Point[] tabvoiture = {A,B,C,D};
		Obstacle Voiture = new Obstacle(tabvoiture,300,0,limvoiture);
		Voiture.vx = 5;
		Voiture.vy = 20;
		Voiture.angularSpeed = 5;
		Liste.add(Voiture);



		//fin de la creation des objets

		//On ajoute des Listener en toute sorte
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);



		//Initialisation du timer
		timer = new Timer(30,this);
		timer.start();

	}

	public void paint(Graphics g){
		//Initialisation du buffer a chaque fois car c'est resizable (pas utile � terme mais pratique pour les differente resolution de nos PCs). 1 resize ==> 1 paint !
		ArrierePlan = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		buffer = ArrierePlan.getGraphics();

		// On change les variables de dimensions quand on resize : 
		LARGEUR = this.getWidth();
		HAUTEUR = this.getHeight();

		// affichage des lignes du sol

		//Le fond
		buffer.setColor(Color.PINK);
		buffer.fillRect(0, 0, this.getWidth(), this.getHeight());

		//ces variables vont nous servir pour toutes les lignes
		Point A,B;
		//Dessin de l'horizon
		A = new Point(-100000000,0,10000);
		B = new Point(100000000,0,10000);
		A = Geo.perspectiveP(A);
		B = Geo.perspectiveP(B);

		buffer.setColor(Color.white);
		buffer.drawLine((int) A.x,((int)A.y),(int) B.x,(int) B.y);


		//pour tous �a il faut comprendre comment j ai fait la perspective : un point sera dessine en projetant son x et
		// son y sur un plan en passant par l objectif, on s y perd je l avoue mais j ai le truc en tete
		for(int i = -100; i <= 100; i++){ //Lignes de fuites
			//1er point
			A = new Point(100*i,0);
			A = Geo.perspectiveP(A,10000);

			//idem pour le 2eme point
			B = new Point(100*i,0);
			B = Geo.perspectiveP(B,(int) (Geo.Obj.z+2));

			//on trace la ligne
			buffer.setColor(Color.getHSBColor((float) (i*0.1), 1, 1));
			buffer.drawLine(((int)A.x),(int)A.y,(int)B.x,(int) B.y);
		}

		for(int i = 0; i <= 1000; i +=10){ //Lignes horizontales
			//1er point
			double z = Geo.Obj.z+11 - (Geo.Obj.z%10);
			A = new Point(-10000,0);
			A = Geo.perspectiveP(A, z + i);
			//idem pour le 2eme point
			B = new Point(10000,0);
			B = Geo.perspectiveP(B, z + i);

			buffer.setColor(Color.getHSBColor((float) ((z+i)*0.001), 1,0.8f));
			buffer.drawLine(((int)A.x),(int)A.y,(int)B.x,(int) B.y);
		}

		//Dessin des polygones
		int[] ordre = new int[Liste.size()];
		ordre = Objet.tri(Liste); //voir methode tri dans Objet
		for(int i = 0; i<= Liste.size() -1 ; i++){ //On parcourt la liste de polygones et les dessine
			Liste.get(ordre[i]).draw(buffer);
		}

		//Petit test d'intersection
		buffer.setColor(Color.BLACK);
		LinkedList<Point> PtInter = new LinkedList<Point>(); //Liste qui recupere les points d'intersection
		LinkedList<Objet> ZInter = new LinkedList<Objet>(); //liste qui recupere les objet associes a ces intersection (oui c est un peu con mais j ai rien trouve de mieux pour avoir le z de l intersection !)
		for(int i = 0; i<= Liste.size()-1; i++){
			for(int j = i ; j <= Liste.size()-1 ; j++){
				if((i!=j)&&(Liste.get(i).z>Geo.Obj.z)&&(Liste.get(j).z>Geo.Obj.z)){ // on evite l intersection avec soi meme
					if(Liste.get(i).Intersect(Liste.get(j)) ){
						PtInter.addAll(Liste.get(i).IntersectList(Liste.get(j))); //On ajoute a la liste d intersections la liste d intersection entre i et j
						for(int k = 0; k < Liste.get(i).IntersectList(Liste.get(j)).size() ; k++) {
							ZInter.add(Liste.get(i));
							if(MenuJeu.sound){
								sonCollision.setFramePosition(0); //douce mélodie (qui joue pas toujours)
								sonCollision.loop(0);
							}
						}
					}
				}
			}
		}
		buffer.drawString("Ca touche "+ PtInter.size() +" fois !", 20, 50);


		for(int l = 0 ; l <= PtInter.size()-1 ; l++){
			//Calcul de la position a l ecran des pt d'intersection pour ecrire "�a touche'
			Point A1 = new Point(PtInter.get(l).x,PtInter.get(l).y);
			A1 = Geo.perspectiveP(A1, ZInter.get(1).z);
			buffer.drawString("Ca touche ",(int) A1.x, (int) A1.y);
		}

		// petits tests pour un HUD - on ajoutera du texte en fct de l'évolution du jeu :)
		int score =(int)(temps*0.03);

		Font police = new Font("Courier", Font.BOLD, 24); //mettez any font you like !
		buffer.setFont(police);
		buffer.setColor(Color.WHITE);
		buffer.drawString("Score : " + score, 20, (int)HAUTEUR-20);
		buffer.drawString("Vies : "+ 3, (int)LARGEUR-150, (int)HAUTEUR-20); // 3 vies, on verra comment on gère ça hein

		g.drawImage(ArrierePlan,0,0,this);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {

		//Evolution de chaque objet
		for(int i = 0; i<= Liste.size() -1 ; i++){
			Liste.get(i).move();
		}

		// juste pour test un peu de deplacement elementaire
		Liste.get(2).translate(Math.cos(temps*0.01), 0);
		Liste.get(2).rotate(5*Math.cos(temps*0.01), Liste.get(0).points[2]);
		Liste.get(1).rotate(temps*0.01, Liste.get(1).points[3]);


		//Camera qui bouge

		if (Geo.Obj.y +  0.1*(HAUTEUR*0.5 - soury) >=0){
			if(admin){
				Geo.Obj.x += -0.1*(LARGEUR*0.5 - sourx);
				Geo.Obj.y += 0.1*(HAUTEUR*0.5 - soury);

			}
			else{
				//on suit le Guy
				Geo.Obj.z = Guy.z - 50;
				Geo.zP = Geo.Obj.z - 60;

				Geo.Obj.x = Guy.CenterOfMass().x;
				Geo.Obj.y= Guy.CenterOfMass().y + 200;
				// balancement
				Geo.Obj.x += 10*Math.cos(temps*0.1);
				Geo.Obj.y += -20*Math.sin(temps*0.2);
			}
		}


		repaint();
		temps ++;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		sourx = arg0.getX();
		soury = arg0.getY();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

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
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_LEFT:
				if(Guy.vx>30){
					Guy.vx*=.5;
				} else {
					Guy.vx-=2;
				}
				System.out.println(Guy.vx);
				break;
			case KeyEvent.VK_RIGHT:
				if(Guy.vx<-30){
					Guy.vx*=.5;
				} else {
					Guy.vx+=2;
				}
				System.out.println(Guy.vx);
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		switch(arg0.getKeyChar()){
			case '-':
				if(Geo.zP<Geo.Obj.z-1){
					Geo.zP +=1;
				} else {
					Geo.zP=Geo.Obj.z-1;
				}
				break;
			case '+':
				Geo.zP -=1;
				break;
			case 'm':
				if(clip.isActive()){clip.stop();}
				else {clip.start();}
				break;
			case 'a':
				if(admin){admin = false;}
				else {admin = true;}
				break;
		}
	}

}
