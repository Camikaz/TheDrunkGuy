
public class Obstacle extends Objet {
	
	private double z; // Remarque : un seul z pour tout les points de l'obstacles. Logique, on affiche que des trucs plats
	private double dz;
	
	public boolean actif; //actif si l'objet est dans la zone de contact
	
	//Constructeur simple
	public Obstacle(Point[] apoints) {
		super(apoints);
		z = 100; //100 est ici arbitraire
		dz = -5;
		actif = false;
	}
	
	//Constructeur avec z et dz
	public Obstacle(Point[] apoints, double az, double adz) {
		super(apoints);
		z = az;
		dz = adz;
	}
	
	public double getZ(){
		return z;
	}
	
	public void setZ(double az){
		
		this.grossir(this.z , az); //on grossit le polygone à chaque avancée
		z = az;
		
		//on voit si le polygone devient actif
		if((actif == false)&&(az <= 10)){
			actif = true;
		}
		if((actif == true) && (az >=10)){
			actif = false;
		}
	}

	public void move() {
		for(int i = 0; i <= this.npoints-1 ; i++){
			
			this.grossir(this.z , this.z + this.dz );
			
			this.points[i].dx += this.points[i].ddx;
			this.points[i].dy += this.points[i].ddy;
			
			this.points[i].x += this.points[i].dx;
			this.points[i].y += this.points[i].dy;
			this.z += this.dz;
			
		}
	}
	
	//doit grossir en fonction du changement de profondeur
	public void grossir(double z1, double z2){
		
	}

}
