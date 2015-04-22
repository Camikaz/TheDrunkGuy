import java.awt.Image;

/*Classe cree par Camille le 21/04. Elle va servir a designer les polygones dont le bonhomme est constitue
 *  et peut etre aussi les obstacles quand ils seront au premier plan.
 *  Je n'ai pas utilise la classe Polygon existante car je veux utiliser mes propres points avec une vitesse et une acceleration
 *  
 *  Cette classe devra atre transformee en abstract !
 */
 


public class Objet{
	
	public int npoints; //nombre de points du polygone (souvent 4)
	public Point[] points; //tableau contenant les points sommet du polygone
	
	public Image pic;
	
	
	
	//Constructeur avec un tab de points et une masse
	public Objet(Point[] apoints){
		points = apoints;
		npoints = apoints.length;
	}
	
	
	//constructeur avec deux tableaux de double designant les positions X et Y de chaque points
	public Objet(double[] tabX, double[] tabY){ 
		points = new Point[tabX.length];
		npoints = tabX.length;
		for(int i = 0; i<= points.length-1 ; i++){
			points[i] = new Point( tabX[i], tabY[i]);
		}
	}
	
	
	//public abstract void move();
	


	public void translate (double DX, double DY){
		for(int i = 0; i <= this.npoints - 1 ; i++ ){
			this.points[i].x = this.points[i].x + DX;
			this.points[i].y = this.points[i].y + DY;
		}
	}
	
	/*Translation autoure d'un point O */
	public void rotate (double angle, Point O){
		for(int i = 0; i <= this.npoints -1 ; i++){
			if((this.points[i].x != O.x)||(this.points[i].y != O.y)){
				
				double r = Math.sqrt( Math.pow(this.points[i].x - O.x, 2) + Math.pow(this.points[i].y - O.y, 2) );
				
				double xb = O.x + r*( ((this.points[i].x - O.x)/r)*Math.cos(Math.toRadians(angle)) - ((this.points[i].y - O.y)/r)*Math.sin(Math.toRadians(angle)) );
				double yb = O.y + r*( ((this.points[i].y - O.y)/r)*Math.cos(Math.toRadians(angle)) + ((this.points[i].x - O.x)/r)*Math.sin(Math.toRadians(angle)) );
				
				this.points[i].x = xb;
				this.points[i].y = yb;
			}
		}
		
	}
	
	/*test intersection retourne vrai ou faux
	 * Si le polygone est a l interieur de l autre, il n y a pas intersection mais osef*/
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
	
	/*test de l'intersection de 2 segments [AB] et [CD]. Methode servant uniquement pour Intersect*/
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
		
		/*
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
		*/
		
		double Y1,Y2;
		boolean var;
		
		double yG1, yG2;
		if(B.x == A.x) { yG1 = A.y ;}
		else{ yG1 = B.y + (xG - B.x) * ((B.y - A.y)/(B.x - A.x)); }

		if(C.x == D.x) { yG2 = C.y ;}
		else{  yG2 = D.y + (xG - D.x) * ((D.y - C.y)/(D.x - C.x)); }
		
		if(yG1<yG2){var = true;}
		else{var =false;}
		
		
		for(double X = xG; X <= xD ; X++){
			if(B.x == A.x) { Y1 = B.y ;}
			else{ Y1 = B.y + (X - B.x) * ((B.y - A.y)/(B.x - A.x)); }

			if(C.x == D.x) { Y2 = D.y;}
			else{ Y2 = D.y + (X - D.x) * ((D.y - C.y)/(D.x - C.x)); }
			
			if((Y1>=Y2)&&(var == true)){
				return true;
			}
			if((Y1<=Y2)&&(var == false)){
				return true;
			}
			
		}
		
		return false;
	}
	
	/*test intersection retournant 1 point d intersection
	 * Le mieux est qu il n y ait qu une seule intersection (la plupart des cas) car cette methode n en recherche que un
	 * Si le polygone est a l interieur de l autre, il n y a pas intersection*/
		public Point IntersectP(Objet poly2){
			Point A;
			for(int i= 0; i<= this.npoints -1; i++){
				for(int j = 0; j<= poly2.npoints -1 ; j++){
					if(i!=this.npoints-1){
						if(j!= poly2.npoints-1){
							if(IntersectSegm( this.points[i], this.points[i+1], poly2.points[j], poly2.points[j+1])){
								return IntersectSegmP( this.points[i], this.points[i+1], poly2.points[j], poly2.points[j+1]);
							}
						}
						else{
							if(IntersectSegm( this.points[i], this.points[i+1], poly2.points[j], poly2.points[0])){
								return IntersectSegmP( this.points[i], this.points[i+1], poly2.points[j], poly2.points[0]);
							}
						}
					}
					else{
						if(j!= poly2.npoints-1){
							if(IntersectSegm( this.points[i], this.points[0], poly2.points[j], poly2.points[j+1])){
								return IntersectSegmP( this.points[i], this.points[0], poly2.points[j], poly2.points[j+1]);
							}
						}
						else{
							if(IntersectSegm( this.points[i], this.points[0], poly2.points[j], poly2.points[0])){
								return IntersectSegmP( this.points[i], this.points[0], poly2.points[j], poly2.points[0]);
							}
						}
						
					}
				}//fin des j
			}//fin des i
			return (new Point(-10,-1)); //si il n'y a pas d'intersection, on retourne false
		}
		
	/*Test intersection entre segment [AB] et [CD], retourne le point d'intersection*/
	static Point IntersectSegmP(Point A, Point B, Point C, Point D){
		
		//Traitement des cas de verticalite
		if((B.x == A.x)&&(C.x!=D.x)){ //AB vertical, pas CD
			if((D.y + (A.x - D.x) * ((D.y - C.y)/(D.x - C.x)) <= Math.max(A.y, B.y)) && (D.y + (A.x - D.x) * ((D.y - C.y)/(D.x - C.x)) >= Math.min(A.y, B.y))){
				return (new Point(A.x, D.y + (A.x - D.x) * ((D.y - C.y)/(D.x - C.x)) ));
			}
			else{return (new Point(-1,-1));}
		}
		
		if((C.x == D.x)&&(A.x!=B.x)){ //CD vertical, pas AB
			if( (B.y + (C.x - B.x) * ((B.y - A.y)/(B.x - A.x)) <= Math.max(C.y, D.y)) && (B.y + (C.x - B.x) * ((B.y - A.y)/(B.x - A.x)) >= Math.min(C.y, D.y)) ) {
				return (new Point(C.x,B.y + (C.x - B.x) * ((B.y - A.y)/(B.x - A.x)) ));
			}
			else{ return (new Point(-1,-1));}
		}
		
		if((B.x == A.x)&&(C.x == D.x)){ //AB et CD verticaux
			if(B.x == C.x){
				return A;
			}
			else{ return (new Point(-1,-1));}
		}
		
		
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
		
		if((bout1D.x < bout2G.x)||(bout1G.x > bout2D.x)){ //test si les bouts sont eloignes horizontalement
			return (new Point(-1,-1));
		}
		
		
		double xG = Math.max(bout1G.x, bout2G.x);
		double xD = Math.min(bout1D.x, bout2D.x);
		
		double yG1 = B.y + (xG - B.x) * ((B.y - A.y)/(B.x - A.x));
		double yG2 = D.y + (xG - D.x) * ((D.y - C.y)/(D.x - C.x));
		
		double Y1,Y2;
		boolean var;
		
		if(yG1<yG2){var = true;}
		else{var =false;}
		
		for(double X = xG; X <= xD ; X = X + 0.1){ //On parcourt sur la zone susceptible de croisement
			Y1 = B.y + (X - B.x) * ((B.y - A.y)/(B.x - A.x));
			Y2 = D.y + (X - D.x) * ((D.y - C.y)/(D.x - C.x));
			
			if((Y1>=Y2)&&(var == true)){
				return (new Point(X,((Y1+Y2)/2)));
			}
			
			if((Y1<=Y2)&&(var == false)){
				return (new Point(X,((Y1+Y2)/2)));
			}
			
		}
		
		return (new Point(-1,-1));
	}
	
	
}
