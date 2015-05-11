import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Obstacle extends Objet {
	
	
	protected boolean actif; //actif si l'objet est dans la zone de contact, variable a remplacer par un boolean "decor" peut etre
	public double[] limites = new double[6]; // {xmin, xmax, ymin, ymax, zmin, zmax}
	public double vx, vy;
	public double angularSpeed;
	
	
	public Obstacle(Point[] Points, double az, double adz) {
		super(Points);
		vx = 0;
		vy = 0;
		angularSpeed = 0;
		setZ(az);
		setDz(adz);
		double[] lim =  {-10000,10000,0,7000,-9,10000};
		this.limites = lim;
	}

	public Obstacle(Point[] Points, double az, double adz, double[] aLim) {
		super(Points);
		vx = 0;
		vy = 0;
		angularSpeed = 0;
		setZ(az);
		setDz(adz);
		limites = aLim;
	}
	
	public double getZ(){
		return z;
	}
	public double getDz(){
		return dz;
	}
	
	public void setZ(double az){
		this.z = az;

		for(int i = 0; i< this.npoints; i++){
			this.points[i].z = az;
		}
		
	}	
	public void setDz (double adz){
		this.dz = adz;
	}



	public void addVitesse(double dx, double dy){
		vx += dx;
		vy += dy;
	}
	
	public void addAngularSpeed(double theta){
		angularSpeed += theta;
	}

	public Point CenterOfMass() {
		Point resultat = new Point(0,0);
		int i;
		for(i=0; i<points.length-1; i++)
		{
			resultat.x += (points[i].x+points[i+1].x) * (points[i].x*points[i+1].y - points[i+1].x*points[i].y);
			resultat.y += (points[i].y+points[i+1].y) * (points[i].x*points[i+1].y - points[i+1].x*points[i].y);
		}
		// "i" a la valeur points.length-1 maintenant
		resultat.x += (points[i].x+points[0].x) * (points[i].x*points[0].y - points[0].x*points[i].y);
		resultat.y += (points[i].y+points[0].y) * (points[i].x*points[0].y - points[0].x*points[i].y);

		double a = aire();
		resultat.x /= 6*a;
		resultat.y /= 6*a;
		return resultat;
	}

	public double aire()
	{
		double a=0;
		int i;
		for(i=0; i<points.length-1; i++)
			a += points[i].x*points[i+1].y - points[i+1].x*points[i].y;
		// "i" a la valeur points.length-1 maintenant
		a += points[i].x*points[0].y - points[0].x*points[i].y;
		return 0.5*a;
	}

	@Override
	public void move() {
		//gestion limites
		Point cur = this.points[0];
		//gestion xmax
		if(vx>0){
			for(int i =0 ; i<npoints; i++) { //cur devient le point le plus a droite
				if (points[i].x > cur.x) {
					cur = points[i];
				}
			}
		if(cur.x > limites[1]){vx *= -1;}
		}
		//gestion xmin
		if(vx<0){
			for(int i =0 ; i<npoints; i++) { //cur devient le point le plus a gauche
				if (points[i].x < cur.x) {
					cur = points[i];
				}
			}
			if(cur.x < limites[0]){vx *= -1;}
		}
		// gestion ymin, max
		if(vy>0){
			for(int i =0 ; i<npoints; i++) {
				if (points[i].y > cur.y) {
					cur = points[i];
				}
			}
			if(cur.y > limites[3]){vy *= -1;}
		}

		if(vy<0){
			for(int i =0 ; i<npoints; i++) {
				if (points[i].y < cur.y) {
					cur = points[i];
				}
			}
			if(cur.y < limites[2]){vy *= -1;}
		}

		if((dz>0 && z>limites[5])||(dz<0 && z<limites[4])){
				dz*=-1;
		}

		translate(vx,vy);
		rotate(angularSpeed, this.CenterOfMass());
		this.setZ(this.getZ() + this.getDz());

	}
	
}
