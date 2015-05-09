
public class Point {
	
	/*On gere les coordonnees avec des x et des y en double, et ce n'est qu a l affichage que l on recaste les coordonnees en int.
	Mieux vaut jouer avec des doubles pour les vitesses tres basses. Sinon, on ne peut pas ajouter 0.5 a x et ca pose probleme.
	*/
	
	public double x;
	public double y;
	public double z;
	
	public double dx; //Composant horizontal de la vitesse
	public double dy; //Composant vertical de la vitesse
	public double dz;
	
	public double ddx; //Composant horizontal de l acceleration
	public double ddy; //Composant horizontal de l acceleration
	public double ddz;
	
	public Point(double ax, double ay){
		x = ax;
		y = ay;
		z = 0;
		dx = 0;
		dy = 0;
		dz = 0;
		ddx = 0;
		ddy = 0;
		ddz = 0;
	}
	
	public Point(double ax, double ay,double az){
		x = ax;
		y = ay;
		z = az;
		dx = 0;
		dy = 0;
		dz = 0;
		ddx = 0;
		ddy = 0;
		ddz = 0;
	}
	
	public double distance(Point p) {
		return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y) + (z-p.z)*(z-p.z));
	}
	

}
