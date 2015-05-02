import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/*Classe cree par Camille le 21/04. Elle va servir a designer les polygones dont le bonhomme est constitue
 *  et peut etre aussi les obstacles quand ils seront au premier plan.
 *  
 *  Cette classe est abstraite et ses filles sont Membre et Obstacle
 */
 


public abstract class Objet{
	
	public int npoints; //nombre de points du polygone (souvent 4)
	public Point[] points; //tableau contenant les points sommet du polygone
	
	public Image pic;
	
	protected double z; //profondeur : z positif: devant, z negatif : derriere
	protected double dz;
	

	
	public static Point Obj = new Point(0, 100); //Objectif pour filmer l image
	public static double zP = -20; //la profondeur du plan ecran de la camera
	public static double zOb = -10; //profondeur de l'objectif
	
	
	
	//Constructeur avec un tab de points 
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
	
	// Pourquoi la méthode est abstraite si la classe l'est déjà? Je la mets en commentaire en attendant - Reda
	public abstract void move();
	
	//la methode est abstraite, cela signifie que chaque classes filles devra avoir sa classe move, et donc son propre deplacement
	//Si tu veux, on peut faire un mouvement standard avec une classe non abstraite et completer ce mouvement dans chaque fille mais c'est pas tres interessant


	public void translate (double DX, double DY){
		for(int i = 0; i <= this.npoints - 1 ; i++ ){
			this.points[i].x = this.points[i].x + DX;
			this.points[i].y = this.points[i].y + DY;
		}
	}
	
	/*Rotation d'une variation d angle (en degres) autour d'un point O */
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
	
	/*test intersection retournant vrai ou faux. Si le polygone est a l interieur de l autre, il n y a pas intersection*/
	public boolean Intersect(Objet poly2){
		if(Math.abs(this.z - poly2.z)>=2){return false;}
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
	
	/*test intersection retournant une liste des points d intersections. Si le polygone est a l interieur de l autre, il n y a pas intersection*/
	public LinkedList<Point> IntersectList(Objet poly2){
		if(Math.abs(this.z - poly2.z) >= 2){
			return null;}
		LinkedList<Point> ListeP = new LinkedList<Point>();
		Point A;
		for(int i= 0; i<= this.npoints -1; i++){
			for(int j = 0; j<= poly2.npoints -1 ; j++){
				if(i!=this.npoints-1){ //Pour ne pas dépasser la taille du tableau
					if(j!= poly2.npoints-1){
						A = IntersectSegmP( this.points[i], this.points[i+1], poly2.points[j], poly2.points[j+1]);
						if(A!=null){
							ListeP.add(A);
						}
					}
					else{
						A = IntersectSegmP( this.points[i], this.points[i+1], poly2.points[j], poly2.points[0]);
						if(A!=null){
							ListeP.add(A);
						}
					}
				}
				else{
					if(j!= poly2.npoints-1){
						A = IntersectSegmP( this.points[i], this.points[0], poly2.points[j], poly2.points[j+1]);
						if(A!=null){
							ListeP.add(A);
						}
					}
					else{
						A = IntersectSegmP( this.points[i], this.points[0], poly2.points[j], poly2.points[0]);
						if(A!=null){
							ListeP.add(A);
						}
					}
				}
			}//fin des j
		}//fin des i
		if(ListeP.size() == 0){return null;}
		else{return ListeP;}
	}
		
	
	/*test de l'intersection de 2 segments [AB] et [CD] retournant un booleen. Methode servant uniquement pour Intersect*/
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
		
		double yG1, yG2, yD1, yD2;
		if(A.x == B.x){
			yG1 = A.y;
			yD1 = B.y;
		}
		else{
			yG1 = B.y + (xG - B.x) * ((B.y - A.y)/(B.x - A.x));
			yD1 = B.y + (xD - B.x) * ((B.y - A.y)/(B.x - A.x));
		}
		
		if(C.x == D.x){
			yG2 = C.y;
			yD2 = D.y;
		}
		else{
			yG2 = D.y + (xG - D.x) * ((D.y - C.y)/(D.x - C.x));
			yD2 = D.y + (xD - D.x) * ((D.y - C.y)/(D.x - C.x));
		}
		
		if((yG1>=yG2)&&(yD1<=yD2)){
			return true;
		}
		if((yG1<=yG2)&&(yD1>=yD2)){
			return true;
		}
		
		return false;
	}
	
	
	/*test intersection entre [AB] et [CD] retournant le point d'intersection ou null*/
	static Point IntersectSegmP(Point A, Point B, Point C, Point D){
		
		//Traitement des cas de verticalite
		if((B.x == A.x)&&(C.x!=D.x)){ //AB vertical, pas CD
			if( (A.x <= Math.max(C.x, D.x)) && (A.x >= Math.min(C.x, D.x)) ) {
				double yI = D.y + (A.x - D.x) * ((D.y - C.y)/(D.x - C.x));
				if( (yI <= Math.max(A.y, B.y)) && (yI >= Math.min(A.y, B.y)) ){
					return (new Point(A.x,yI));
				}
				return null;
			}
			return null;
		}
		
		
		if((C.x == D.x)&&(A.x!=B.x)){ //CD vertical, pas AB
			if((C.x <= Math.max(A.x, B.x)) && (C.x >= Math.min(A.x, B.x))){
				double yI = B.y + (C.x - B.x) * ((B.y - A.y)/(B.x - A.x));
				if( (yI <= Math.max(C.y, D.y)) && (yI >= Math.min(C.y, D.y)) ) {
					return (new Point(C.x,yI));
				}
				return null;
			}
			return null;
		}
		
		if((B.x == A.x)&&(C.x == D.x)){ //AB et CD verticaux
			if(B.x == C.x){
				if ( (A.y <= Math.max(C.y, D.y)) && (A.y >= Math.min(C.y, D.y))){
					return A;
				}
				if ( (B.y <= Math.max(C.y, D.y)) && (B.y >= Math.min(C.y, D.y))){
					return B;
				}
				return null;
			}
			return null;
		} //fin des cas verticaux
		
		double p1 = (B.y - A.y) / (B.x - A.x);
		double p2 = (D.y - C.y) / (D.x - C.x);
		double xI = 0;
		
		if(p1 == p2){ //Cas des pentes égales
			if( C.y == A.y + p1 * (C.x-A.x) ){
				if( (C.x>=Math.min(A.x,B.x))&&(C.x<=Math.max(A.x, B.x)) ){
					return C;
				}
				if( (D.x>=Math.min(A.x,B.x))&&(D.x<=Math.max(A.x, B.x)) ){
					return D;
				}
				return null;
			}
			return null;
		}//fin pentes egales
		
		//cas general : ca prend 2 lignes :'(
		xI = (C.y-C.x*p2-A.y+A.x*p1)/(p1-p2); //Resultat d un calcul sur feuille, c est l abcisse du point d intersection
		
		if( (xI>=Math.min(A.x,B.x))&&(xI<=Math.max(A.x, B.x))&&(xI>=Math.min(C.x,D.x))&&(xI<=Math.max(C.x, D.x)) ){
			return (new Point(xI, B.y + (xI - B.x)*p1));
		}
		return null;
	}
	
	public Point[] perspective (){
		Point[] tab = new Point[this.npoints];
		double xp, yp;
		for(int i =0 ; i < this.npoints ; i++){
			
			xp = Obstacle.Obj.x + (Obstacle.zP - Obstacle.zOb)*(this.points[i].x-Obstacle.Obj.x)/(this.z - Obstacle.zOb); //(Obstacle.zP - Obstacle.zOb) est la difference de z entre le plan et l Obstacle.Objectif
			yp = Obstacle.Obj.y + (Obstacle.zP - Obstacle.zOb)*(this.points[i].y-Obstacle.Obj.y)/(this.z - Obstacle.zOb);
			
			xp = FenetreDrunk.LARGEUR*0.5 +(Obstacle.Obj.x - xp);
			yp = FenetreDrunk.HAUTEUR*0.5 + (-Obstacle.Obj.y + yp);
			tab[i] = new Point(xp,yp);
		}
		return tab;
	}
	

	public void draw(Graphics buffer) {
		
		buffer.setColor(Color.getHSBColor((float)(this.z*0.01), 1, 1));
		Point[] tab = this.perspective();
		
		int n = tab.length;
		int[] tabY = new int[n];
		int[] tabX = new int[n];
		for(int i = 0 ; i<= tab.length-1 ; i++){
			tabX[i] = (int) tab[i].x;
			tabY[i] = (int) tab[i].y;
		}
		buffer.fillPolygon(tabX, tabY, n);
		
		buffer.setColor(Color.BLACK);
		for(int j = 0; j <= tab.length -2 ; j++){ //On parcourt les points du polygones pour tracer ses arretes
			buffer.drawLine((int) tab[j].x, (int) tab[j].y, (int) tab[j+1].x, (int) tab[j+1].y);
		}
		buffer.drawLine((int) tab[this.npoints-1].x, (int) tab[this.npoints-1].y, (int) tab[0].x, (int) tab[0].y);
		
	}

	
	//Renvoie un tableau dont l'index correspond au numero des Objet de liste, classe du plus proche au plus eloigne
	public static int[] tri (LinkedList<Objet> liste){ 
		int[] tab = new int[liste.size()];
		
		for(int i = 0 ; i< liste.size() ;i++){
			tab[i] = i;
		}
		
		LinkedList<Objet> listCur = new LinkedList<Objet>();
		listCur.addAll(liste);
		
		Objet cur;
		
		int j;
		for(int i = 1; i < listCur.size() ;i++){
			cur = listCur.get(i);
			j = i;
			while((j>0)&&(listCur.get(j-1).z< cur.z)){
				listCur.set(j, listCur.get(j-1));
				tab[j] = tab[j-1];
				j = j-1;
			}
			listCur.set(j, cur);
			tab[j] = i;
		}
		return tab;
	}
	
}
