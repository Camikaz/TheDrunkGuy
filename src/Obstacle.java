import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Obstacle extends Objet {
	
	
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
		this.z = az;
		
		if( ((az<-5)||(az>100)) && (az*this.getDz()>0) ){
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
	

	

}
