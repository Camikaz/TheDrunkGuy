import java.awt.Polygon;


public class ClassMain {

	public static void main(String[] args) {
		
		
		
		Point A = new Point(0,0);
		Point B = new Point(3,0);
		Point C = new Point(3,5);
		Point D = new Point(0,5);
		
		Point[] tablo = {A,B,C,D};
		
		Objet Poly1 = new Objet( tablo , 5);
		
		Point A2 = new Point(4,0);
		Point B2 = new Point(4,5);
		Point C2 = new Point(8,5);
		Point D2 = new Point(8,0);
		
		Point[] tablo2 = {A2,B2,C2,D2};
		
		Objet Poly2 = new Objet( tablo2 , 5);
		
		
		if(Poly1.Intersect(Poly2)){
			System.out.println("Ca coupe");
		}
		else{
			System.out.println("Ca coupe pas");
		}
		
	}

}
