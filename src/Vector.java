public class Vector {
	// points are used to define a bound vector, coordinates for a free vector
	Point a, b;
	double x, y;
	
	public Vector(Point a, Point b) {
		this.a = a;
		this.b = b;
		this.x = b.x - a.x;
		this.y = b.y - a.y;
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double cross2D(Vector v) {
		return (x*v.y - y*v.x);
	}
	
	public double dot(Vector v) {
		return x*v.x + y*v.y;
	}
	
	// If z is positive, the rotation is counter-clockwise & vice-versa, this method is used to turn angular velocity into linear velocity
	public Vector crossWithZ(double z) {
		return new Vector(-z*y, z*x);
	}
	
	public double norm() {
		return Math.sqrt(x*x+y*y);
	}
}
