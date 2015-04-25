
public abstract class EulerMath {
	//----------Decleration of times variables--------------//
	final static double FPS = 100; //Mettre ces deux variables en static non ? Cam. : Reda, Yup
	final static double dt = 1/FPS; 
	double currentTime;
	double timeCounter;
	/* dt is the time period, inverse of the display frequency which is 100 fps
	 * 
	 */
	//-----------------------------------------------------------------------------------//
	
	
	public void Math() {
	}
	
	
	//correction syntaxiques
	public void applyInstantForce(Membre o, Vector force) {
		for(int i=0;i<o.npoints;i++){
			o.points[i].dx += dt*force.x/o.mass;
			o.points[i].dy += dt*force.y/o.mass;
			o.points[i].x += o.points[i].dx*dt;
			o.points[i].y += o.points[i].dy*dt;
		}
	}
	
	/* The torque has to be along the z axis that's why we only need its magnitude i.e the double torque
	 * 
	 */
	
	public void applyInstantTorque(Membre o, double torque) {
		o.angularVelocity += dt*torque/o.inertia;
		o.orientation += o.angularVelocity*dt;
		o.rotate(o.angularVelocity*dt, o.centerOfMass); // Cette ligne reste à voir selon comment Camille définis son angle de rotation pour la méthode rotation, si c'est une variation d'angle alors la ligne est bonne, mais si c'est une rotation par rapport à l'axe des x la ligne est à changer
		//Reda, il faut mettre un point en 2eme entree, et non un objet : C'est r�gl�, Reda.
	}
	
	public double getTorque(Membre o, Vector force) {
		double torque;
		Vector om = new Vector(o.centerOfMass, force.a);
		torque = om.cross2D(force);
		return torque;
	}
	
	
	
}
