import java.util.ArrayList;



//Cette classe définit les objets du premier plan qui sont susceptibles de subir des forces, a priori que les membres du drunk guy

public class Membre extends Objet {
	
	public Point centerOfMass;
	public double massDensity; // With this we get the mass + the inertia, for polygones made of the same material the density should remain the same.
	public double inertia;
	public double mass;
	public double angularVelocity;
	public double orientation;
	public Vector velocity;// The velocity, orientation & angular velocity have to be initialised to 0 and the velocity is the dx for the center of mass.
	public ArrayList<Vector> forces;
		

	public Membre(Point[] apoints, double density) {
		super(apoints);
		massDensity = density;
		mass = getMass();
		angularVelocity = 0;
		orientation = 0;
		velocity = new Vector(0,0);
		setMomentOfInertia();
		forces = new ArrayList<Vector>();
	}

	public Membre(double[] tabX, double[] tabY, double amasse) {
		super(tabX, tabY);
		this.mass = amasse;
	}
	
	
	
	public void setCenterOfMass() {
		double A, Cx, Cy;
		A=0;
		Cx=0;
		Cy=0;
		for(int i=0;i<npoints;i++){
			A+=(points[i].x*points[i+1].y-points[i+1].x*points[i].y)/2;
		}
		
		for(int j=0;j<npoints;j++) {
			//J'ai remplacé les i par des j, ça me parait plus logique mais c'est peut etre une erreur ! S. Camille
			Cx+=(points[j].x + points[j+1].x)*(points[j].x*points[j+1].y-points[j+1].x*points[j].y)/(6*A);
			Cy+=(points[j].y + points[j+1].y)*(points[j].x*points[j+1].y-points[j+1].x*points[j].y)/(6*A);
		}
		
		this.centerOfMass = new Point(Cx, Cy);
	}
	
	public Point getCenterOfMass() {
		if(centerOfMass==null){
			getCenterOfMass();
			return centerOfMass;
		} else {
			return centerOfMass;
		}
	}
	
	public double getMass() {
		double A = 0;
		for(int i=0;i<npoints;i++){
			A+=(points[i].x*points[i+1].y-points[i+1].x*points[i].y)/2;
		}
		return A*massDensity;
	}
	
	//Les méthodes avec les triangles sont utilisées pour avoir le moment d'inertie du polygone en le décomposant en autant de triangles que le polygone n'a de sommets, on applique huygens pour obtenir le moment d'inertie total
	
	public double triangleInertia(Point A, Point B, Point C) {
		Vector ba = new Vector(B,A);
		Vector bc = new Vector(B,C);
		double b = bc.norm();
		double a = Math.abs(ba.dot(bc)/bc.norm());
		double h = Math.abs(bc.cross2D(ba)/bc.norm());
		double i = (h*b*b*b + h*a*b*b + h*a*a*b + b*h*h*h)/12;
		return i;
	}
	
	public Point triangleCenterOfMass(Point A, Point B, Point C) {
		Point[] tab = {A,B,C}; //il faut d'abord créer le tab de point puis instancier l objet (ici le membre) avant sinon erreur de compilation
		Membre triangle = new Membre(tab, 1); //J'ai transformé "Objet triangle = new Objet(tab); en Membre triangle = new Membre(tab); mais il faut ajouter une masse (jai mis 1 pour l'instant)
		triangle.setCenterOfMass();
		return triangle.centerOfMass;
	}
	
	
	public void setMomentOfInertia() {
		inertia = 0;
		for(int i=0;i<npoints-1;i++){
			inertia += triangleInertia(centerOfMass, points[i+1], points[i]) + mass*Math.pow(centerOfMass.distance(triangleCenterOfMass(centerOfMass, points[i+1], points[i])),2);
		}
		inertia += triangleInertia(centerOfMass, points[0], points[npoints-1]) + mass*Math.pow(centerOfMass.distance(triangleCenterOfMass(centerOfMass, points[0], points[npoints-1])),2);
	}

	public void move(EulerMath calcul) {
		//Mettre des calculs de nouvelles acceleration, rotation, translation et compagnie
		double torque = 0;
		Vector sum = new Vector(0,0);
		for(int i=0;i<forces.size();i++) {
			sum = sum.add(forces.get(i));
			torque += calcul.getTorque(this ,forces.get(i));
		}
		calcul.applyInstantForce(this, sum);
		calcul.applyInstantTorque(this, torque);
		
	}

}
