/*

public abstract class EulerMath {
	//----------Decleration of times variables--------------//
	final static double FPS = 30; //Mettre ces deux variables en static non ? Cam. : Reda, Yup
	final static double dt = 1/FPS; 
	double currentTime;
	double timeCounter;
	*/
/* dt is the time period, inverse of the display frequency which is 100 fps
	 * 
	 *//*

	//-----------------------------------------------------------------------------------//
	
	
	public void Math() {
	}
	
	
	public static void applyInstantForce(Membre o, Vector force) {
		for(int i=0;i<o.npoints;i++){
			o.points[i].dx += dt*force.x/o.mass;
			o.points[i].dy += dt*force.y/o.mass;
			o.points[i].x += o.points[i].dx*dt; //Maybe it would be more clear not to put the variation of x and y here
			o.points[i].y += o.points[i].dy*dt;
		}
	}
	
	*/
/* The torque has to be along the z axis that's why we only need its magnitude i.e the double torque
	 * 
	 *//*

	
	public static void applyInstantTorque(Membre o, double torque) {
		o.angularVelocity += dt*torque/o.inertia;
		o.orientation += o.angularVelocity*dt;
		o.rotate(o.angularVelocity*dt, o.centerOfMass);
	}
	
	public static double getTorque(Membre o, Vector force) {
		double torque;
		Vector om = new Vector(o.centerOfMass, force.a);
		torque = om.cross2D(force);
		return torque;
	}
	
	
	
}
*/
