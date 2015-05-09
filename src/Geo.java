
public class Geo {
	

	public static Point Obj = new Point(0, 500, -10); //Objectif pour filmer l image
	public static double zP = -30; //la profondeur du plan ecran de la camera
	

	//Renvoie le point correspondant au dessin sur le plan du point d'entrée a une profondeur Z
	public static Point perspectiveP(Point P, double z){
		double xp, yp;
		xp = Obj.x + (zP - Obj.z)*(P.x-Obj.x)/(z - Obj.z); //(zP - Obj.z) est la difference de z entre le plan et l Objectif
		yp = Obj.y + (zP - Obj.z)*(P.y-Obj.y)/(z - Obj.z);
		
		//on affiche le new point en centrant l'écran sur le point objectif
		xp = FenetreDrunk.LARGEUR*0.5 +(Obj.x - xp);
		yp = FenetreDrunk.HAUTEUR*0.5 + (-Obj.y + yp);
		
		return new Point(xp,yp);
	}
	
	public static Point perspectiveP(Point P){
		double xp, yp;
		xp = Obj.x + (zP - Obj.z)*(P.x-Obj.x)/(P.z - Obj.z); //(zP - Obj.z) est la difference de z entre le plan et l Objectif
		yp = Obj.y + (zP - Obj.z)*(P.y-Obj.y)/(P.z - Obj.z);
		
		//on affiche le new point en centrant l'écran sur le point objectif
		xp = FenetreDrunk.LARGEUR*0.5 +(Obj.x - xp);
		yp = FenetreDrunk.HAUTEUR*0.5 + (-Obj.y + yp);
		
		return new Point(xp,yp);
	}
	
	//Ceci renvoie le tableau des points correspondant a ce qu on affiche a l ecran (ces points dependents de la position de la camera)
	public static Point[] perspective (Point[] points){
		Point[] tab = new Point[points.length];
		for(int i =0 ; i < points.length ; i++){
			tab[i] = perspectiveP(points[i],points[i].z);
		}
		return tab;
	}
	
}
