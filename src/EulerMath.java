
public abstract class EulerMath {
	//----------Decleration of times variables--------------//
	final double FPS = 100;
	final double dt = 1/FPS; 	
	double currentTime;
	double timeCounter;
	/* dt is the time period, inverse of the display frequency which is 100 fps
	 * 
	 */
	//-----------------------------------------------------------------------------------//
	
	public Math() {
	}
	
	public void applyInstantForceToPoint(double pointMass, Vector force, point p) {
		p.dx += dt*force.x/pointMass;
		p.dy += dt*force.y/pointMass;
		
	}
	
	
	public void applyInstantForceToObjet(Objet o, Vector force) {
		for(int i=0;i<o.npoints;i++){
			npoints[i].dx += dt*force.x/o.mass;
			npoints[i].dy += dt*force.y/o.mass;
			npoints[i].x += npoints[i].dx*dt;
			npoints[i].y += npoints[i].dy*dt;
		}
	}
	
	/* The torque has to be along the z axis that's why we only need its magnitude i.e the double torque
	 * 
	 */
	
	public void applyInstantTorque(Objet o, double torque) {
		o.angularVelocity += dt*torque/o.inertia;
		o.orientation += o.angularVelocity*dt;
		o.rotate(o.angularVelocity*dt, o); // Cette ligne reste à voir selon comment Camille définis son angle de rotation pour la méthode rotation, si c'est une variation d'angle alors la ligne est bonne, mais si c'est une rotation par rapport à l'axe des x la ligne est à changer
	}
	
	
	
}
