
public class Point {
	
	/*On gere les coordonnees avec des x et des y en double, et ce n'est qu a l affichage que l on recaste les coordonnees en int.
	Mieux vaut jouer avec des doubles pour les vitesses tres basses. Sinon, on ne peut pas ajouter 0.5 a x et ca pose probleme.
	*/
	
	public double x;
	public double y;
	
	public double dx; //Composant horizontal de la vitesse
	public double dy; //Composant vertical de la vitesse
	
	public double ddx; //Composant horizontal de l acceleration
	public double ddy; //Composant horizontal de l acceleration
	
	
	public Point(double ax, double ay){
		x = ax;
		y = ay;
		dx = 0;
		dy = 0;
		ddx = 0;
		ddy = 0;
	}
	

}
