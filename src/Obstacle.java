
public class Obstacle extends Objet {
	
	public double z;
	public boolean actif; //actif si l'objet est dans la zone de contact
	
	public Obstacle(Point[] apoints) {
		super(apoints);
		z = 100;
		actif = false;
	}

}
