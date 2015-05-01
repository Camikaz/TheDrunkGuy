import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Obstacle extends Objet {
	
	protected double z; //profondeur : z positif: devant, z negatif : derriere
	protected double dz;
	
	public static Point Obj = new Point(0, 100); //Objectif pour filmer l image
	public static double zP = -20; //la profondeur du plan ecran de la camera
	public static double zOb = -10; //profondeur de l'objectif
	
	protected boolean actif; //actif si l'objet est dans la zone de contact
	
	
	public Obstacle(Point[] apoints) {
		super(apoints);
		setZ(100);
		dz = -0.01;
		actif = false;
	}
	
	//Constructeur avec z et dz
	public Obstacle(Point[] apoints, double az, double adz) {
		super(apoints);
		setZ(az);
		setDz(adz);
	}
	
	public double getZ(){
		return z;
	}
	public double getDz(){
		return dz;
	}
	
	public void setZ(double az){
		//on voit si le polygone devient actif
		
		if((actif == false)&&(az <= 10)){
			actif = true;
		}
		if((actif == true) && (az >=10)){
			actif = false;
		}
		this.z = az;
		if((az<-9)||(az>100)){
			this.setDz(-this.getDz());
		}
	}	
	public void setDz (double adz){
		this.dz = adz;
	}

//Pour trouver le point de ref de l'objet (en bas a gauche). J'ai un peu modif la formule. Cam.
	//Pourquoi ne pas retourner directement le point de reference ?
	protected int refPosition() {
		Point pRef = this.points[0];
		int optimal = 0;
		for(int s = 0; s < this.npoints; s++){
			if(this.points[s].y < pRef.y){
				if(this.points[s].x < pRef.x){
					pRef = this.points[s];
					optimal = s;
				}
			}
		}
		return optimal;
	}

	@Override
	public void move() {
		for(int i = 0; i< this.npoints ; i++){
			
			this.points[i].dx += this.points[i].ddx;
			this.points[i].dy += this.points[i].ddy;
			
			this.points[i].x += this.points[i].dx;
			this.points[i].y += this.points[i].dy;
			
			this.setZ(this.getZ() + this.getDz());
		}
	}
	
	/* Cette méthode doit renvoyer un tableau de point selon la position (surtout la distance) de l'obstacle 
	 * Le tableau de point doit correspondre aux points sur l ecran*/
	public Point[] perspective (){
		Point[] tab = new Point[this.npoints];
		double xp, yp;
		for(int i =0 ; i < this.npoints ; i++){
			xp = Obj.x + (zP - zOb)*(this.points[i].x-Obj.x)/(this.z - zOb); //(zP - zOb) est la difference de z entre le plan et l objectif
			yp = Obj.y + (zP - zOb)*(this.points[i].y-Obj.y)/(this.z - zOb);
			
			xp = FenetreDrunk.LARGEUR*0.5 - xp;
			yp = FenetreDrunk.HAUTEUR*0.5 + yp;
			tab[i] = new Point(xp,yp);
		}
		return tab;
	}

	@Override
	public void draw(Graphics buffer) {
		Point[] tab = this.perspective();
		for(int j = 0; j <= tab.length -2 ; j++){ //On parcourt les points du polygones pour tracer ses arretes
			buffer.drawLine((int) tab[j].x, (int) tab[j].y, (int) tab[j+1].x, (int) tab[j+1].y);
		}
		buffer.drawLine((int) tab[this.npoints-1].x, (int) tab[this.npoints-1].y, (int) tab[0].x, (int) tab[0].y);
		
	}
	

	public LinkedList<Point> IntersectListOb(Obstacle Ob){
		if(Math.abs(this.z-Ob.z) <= 10){
			return super.IntersectList(Ob);
		}
		return null;
	}

}
