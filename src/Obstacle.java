import java.awt.Graphics;


public class Obstacle extends Objet {
	
	protected double z;
	protected double dz;
	
	protected boolean actif; //actif si l'objet est dans la zone de contact
	
	
	public Obstacle(Point[] apoints) {
		super(apoints);
		setZ(100);
		dz = -0.0001;
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
		if(az<0){
			this.setZ(20);
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
		Point P = new Point(0,0);
		Point[] tab = new Point[this.npoints];
		double xp, yp;
		for(int i =0 ; i < this.npoints ; i++){
			if(!P.equals(this.points[i])){
				xp = P.x +  (this.points[i].x-P.x)/(1+this.getZ());
				yp = P.y +  (this.points[i].y-P.y)/(1+this.getZ());
				xp = FenetreDrunk.LARGEUR*0.5 + xp/(1+this.getZ());
				yp = FenetreDrunk.HAUTEUR*0.5 - (yp-500)/(1+this.getZ());
				tab[i] = (new Point(xp,yp) );
			}
			else{tab[i] = this.points[i];}
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

}
