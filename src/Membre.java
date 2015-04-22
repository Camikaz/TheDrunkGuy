
public class Membre extends Objet {
	
	public double masse;

	public Membre(Point[] apoints, double amasse) {
		super(apoints);
		masse = amasse;
	}

	public Membre(double[] tabX, double[] tabY, double amasse) {
		super(tabX, tabY);
		masse = amasse;
	}

}
