
public class Obstacle extends Objet {
	
	public double z;
	public boolean actif; //actif si l'objet est dans la zone de contact
	
	
	public Obstacle(Point[] apoints) {
		super(apoints);
		z = 100;
		actif = false;
		System.out.println(refPosition(apoints));
	}

	private int refPosition(Point[] apoints) {
		Point pRef = new Point(0, 0);
		int optimal = -1;
		for(int s = 0; s < apoints.length; s++){
			if(apoints[s].y > pRef.y){
				if(apoints[s].x > pRef.x){
					pRef = apoints[s];
					optimal = s;
				}
			}
		}
		return optimal;
		
		
		
	}

}
