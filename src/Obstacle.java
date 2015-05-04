import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Obstacle extends Objet {


	protected boolean actif; //actif si l'objet est dans la zone de contact, variable a remplacer par un boolean "decor" peut etre
	public double[] limites = new double[6]; // {xmin, xmax, ymin, ymax, zmin, zmax}
	public double vx, vy;
	public double angularSpeed;
	
	
	/*public Obstacle(Point[] apoints) {
		super(apoints);
		setZ(100);
		dz = -0.01;
		actif = false;
	}*/

	/**
	 *
	 * @param apoints
	 * @param az
	 * @param adz
	 */
	public Obstacle(Point[] apoints, double az, double adz) {
		super(apoints);
		vx = 0;
		vy = 0;
		angularSpeed = 0;
		setZ(az);
		setDz(adz);
		double[] lim =  {-10000,10000,0,7000,-9,10000};
		this.limites = lim;
	}

	public Obstacle(Point[] apoints, double az, double adz, double[] aLim) {
		super(apoints);
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
	}
	public void setDz (double adz){
		this.dz = adz;
	}

	/*public void setVelocity (double dx, double dy){
		velocity.x = dx;
		velocity.y = dy;
	}*/

	public void addVitesse(double dx, double dy){
		for(int i=0; i<this.npoints; i++){
			this.points[i].dx += dx;
			this.points[i].dy += dy;
		}
	}

	public Point CenterOfMass() {
		double A, Cx, Cy;
		A=0;
		Cx=0;
		Cy=0;
		for(int i=0;i<npoints-1;i++){
			A+=(points[i].x*points[i+1].y-points[i+1].x*points[i].y)/2;
		}


		for(int j=0;j<npoints-1;j++) {
			Cx+=(points[j].x + points[j+1].x)*(points[j].x*points[j+1].y-points[j+1].x*points[j].y)/(6*A);
			Cy+=(points[j].y + points[j+1].y)*(points[j].x*points[j+1].y-points[j+1].x*points[j].y)/(6*A);
		}

		return new Point(Cx, Cy);
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
