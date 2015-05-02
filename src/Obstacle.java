import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Obstacle extends Objet {
	
	
	protected boolean actif; //actif si l'objet est dans la zone de contact, variable a remplacer par un boolean "decor" peut etre
	
	
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
		
		if( ((az<-9)||(az>100)) && (az*this.getDz()>0) ){
			this.setDz(-this.getDz());
		}
		
	}	
	public void setDz (double adz){
		this.dz = adz;
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
	
}
