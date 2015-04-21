//Classe cree par Camille le 21/04. Elle va servir a designer les polygones dont le bonhomme est constitue et peut etre aussi les obstacles quand ils seront au premier plan.
//Je n'ai pas utilise la classe Polygon existante car je veux utiliser mes propres points avec une vitesse et une acceleration

public class Objet{
	
	public int npoints; //nombre de points du polygone (souvent 4)
	public Point[] points; //tableau contenant les points sommet du polygone
	
	public double masse;
	
	
	
	//Constructeur avec un tab de points et une masse
	public Objet(Point[] apoints, double amasse){
		points = apoints;
		masse = amasse;
		npoints = apoints.length;
	}
	
	
	//constructeur avec deux tableaux de double designant les positions X et Y de chaque points
	public Objet(double[] tabX, double[] tabY, double amasse){ 
		points = new Point[tabX.length];
		npoints = tabX.length;
		for(int i = 0; i<= points.length-1 ; i++){
			points[i] = new Point( tabX[i], tabY[i]);
		}
		masse = amasse;
	}
	
	//test de l'intersection de 2 polygones
	public boolean Intersect(Objet poly2){
		
		for(int i= 0; i<= this.npoints -1; i++){
			for(int j = 0; j<= poly2.npoints -1 ; j++){
				if(i!=this.npoints-1){
					if(j!= poly2.npoints-1){
						if(IntersectSegm( this.points[i], this.points[i+1], poly2.points[j], poly2.points[j+1])){
							return true;
						}
					}
					else{
						if(IntersectSegm( this.points[i], this.points[i+1], poly2.points[j], poly2.points[0])){
							return true;
						}
					}
				}
				else{
					if(j!= poly2.npoints-1){
						if(IntersectSegm( this.points[i], this.points[0], poly2.points[j], poly2.points[j+1])){
							return true;
						}
					}
					else{
						if(IntersectSegm( this.points[i], this.points[0], poly2.points[j], poly2.points[0])){
							return true;
						}
					}
					
				}
			}//fin des j
		}//fin des i
		return false; //si il n'y a pas d'intersection, on retourne false
	}
	
	//test de l'intersection de 2 segments [AB] et [CD]. Methode servant uniquement pour Intersect
	static boolean IntersectSegm(Point A, Point B, Point C, Point D){
		
		Point bout1G = new Point(0,0); //extremite gauche du premier segment
		Point bout1D = new Point(0,0); //extremite droite du premier segment
		if(A.x < B.x){
			bout1G = A;
			bout1D = B;
		}
		else{
			bout1G = B;
			bout1D = A;
		}
		
		Point bout2G = new Point(0,0); //extremite gauche du deuxieme segment
		Point bout2D = new Point(0,0); //extremite droite du deuxieme segment
		if(C.x < D.x){
			bout2G = C;
			bout2D = D;
		}
		else{
			bout2G = D;
			bout2D = C;
		}
		
		if((bout1D.x < bout2G.x)||(bout1G.x > bout2D.x)){
			return false;
		}
		
		double xG = Math.max(bout1G.x, bout2G.x);
		double xD = Math.min(bout1D.x, bout2D.x);
		
		
		double yG1 = B.y + (xG - B.x) * ((B.y - A.y)/(B.x - A.x));
		double yD1 = B.y + (xD - B.x) * ((B.y - A.y)/(B.x - A.x));
		
		double yG2 = D.y + (xG - D.x) * ((D.y - C.y)/(D.x - C.x));
		double yD2 = D.y + (xD - D.x) * ((D.y - C.y)/(D.x - C.x));
		
		
		if(A.x == B.x){
			yG1 = A.y;
			yD1 = B.y;
		}
		
		if(C.x == D.x){
			yG2 = C.y;
			yD2 = D.y;
		}
		
		if((yG1>=yG2)&&(yD1<=yD2)){
			return true;
		}
		if((yG1<=yG2)&&(yD1>=yD2)){
			return true;
		}
		
		return false;
	}

}
