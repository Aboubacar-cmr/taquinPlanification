package taquin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class State implements Comparable<State>{

    private int[][]  grille;
    private int[][] grilleBut = new int[3][3];
    private int longueur;
    private int largeur;
    private int distance_source = 0;
    
    /**
     * 
     * @param longueur longeur de la grille
     * @param largeur largeur de la grille
     * @param init boolean permettant d'initialiser la grille si true 
     */
    public State(int longueur,int largeur,boolean init){
        this.longueur = longueur;
        this.largeur = largeur;
        this.grilleB();
        this.grille = new int[this.longueur][this.largeur];
        if(init){
            this.initP();
        }
    }

    /**
     * Cette méthode permet de verifier si un état satisfait le but
     * @return true si l'état but est attein false sinon
     */
    public boolean ISsatisfie(){
       for (int i = 0; i < this.longueur; i++) {
           for (int j = 0; j < this.largeur; j++) {
                if(this.grille[i][j] != this.grilleBut[i][j])
                    return false;
           }
       }
       return true;
    }

    /**
     * cette méthode permettra d'appliquer une action passée en param un monde courant 
     */
    public State apply(Action action){
        State newState = this.createNewState();
        newState.grille[action.getPositionEnd().getX()][action.getPositionEnd().getY()] = newState.grille[action.getPositionStart().getX()][action.getPositionStart().getY()];
        newState.grille[action.getPositionStart().getX()][action.getPositionStart().getY()] = 0;
        return newState;
    }

    /**
     * Cette méthode permet de créer une copie conforme du monde courrant
     */
    public State createNewState(){
        State newState = new State(this.longueur,this.largeur,false);
        for (int i = 0; i <this.longueur; i++) {
            for (int j = 0; j <this.largeur; j++) {
                newState.grille[i][j] = this.grille[i][j];

            }
        }
        return newState;
    }

    /**
     * Méthode permettant de trouver tous les actions possible sur un monde
     */
    public ArrayList<Action> searcheActions(){
        ArrayList<Action> listeActions = new ArrayList<>();
        for (int i = 0; i < this.longueur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                if( verifG(i, j)){
                    listeActions.add(new Action( new Position(i, j), new Position(i,(j-1) )) );
                }else if(verifD(i, j)){
                    listeActions.add(new Action(new Position(i, j), new Position(i,(j+1))) );
                }else if(verifH(i, j)){
                    listeActions.add(new Action(new Position(i, j), new Position((i-1),j)) );
                }else if(verifB(i,j)){
                    listeActions.add(new Action(new Position(i, j), new Position((i+1),j)) );
                }
            }
        }
        return listeActions;
    }

    /**
     * Méthode permettant de verifier la partie gauche la cellule courrante
     */
    public boolean verifG(int x,int y){
        if((y-1 >=0) && this.grille[x][y-1] == 0){
            return true;
        }
        return false;
    }

    /**
     * Méthode permettant de verifier la partie droite de la cellule courante 
     * @param x
     * @param y
     * @return
     */
    public boolean verifD(int x,int y){
        if( (y+1 <=this.largeur-1) && (this.grille[x][y+1] == 0) ){
            return true;
        }
        return false;
    }

    /**
     * Méthode permettant de verifier la partie haute de la cellule courante 
     * @param x
     * @param y
     * @return
     */
    public boolean verifH(int x,int y){
        if( (x-1>=0) && (this.grille[x-1][y] == 0) ){
            return true;
        }
        return false;
    }

    /**
     * Méthode permettant de verifier la partie base de la cellule courante 
     * @param x
     * @param y
     * @return
     */
    public boolean verifB(int x,int y){
        if( (x+1 <= this.longueur-1) && (this.grille[x+1][y] == 0)){
            return true;
        } 
        return false;
    }

    /**
     * Méthode d'affichage du monde
     */
    public void showState(){
        for(int i = 0;i < this.longueur;i++){
            for(int j = 0;j < this.largeur;j++){
                if(this.grille[i][j]<10){
                    System.out.print("| "+this.grille[i][j]+" ");
                }else{
                    System.out.print("| "+this.grille[i][j]);
                }
            }
            System.out.println();
        }

    }

    /**
     * Méthode permettant d'initialiser le monde
     */
    public void init(){
        ArrayList<Integer> entiers = this.entiers();
        for(int i = 0;i < this.longueur;i++){
            for(int j = 0;j < this.largeur;j++){
                Random rand = new Random();
                int ent = rand.nextInt(entiers.size());
                this.grille[i][j] = entiers.get(ent);
                entiers.remove(ent);
            }
        }
    }

    /**
     * etat initial pour le teste
     */
    public void initP(){
        this.grille[0][0] = 4; //1
        this.grille[0][1] = 1; //2
        this.grille[0][2] = 0; //3
        this.grille[1][0] = 7; //4
        this.grille[1][1] = 6; //5
        this.grille[1][2] = 3; //6
        this.grille[2][0] = 5; //7
        this.grille[2][1] = 2; //8
        this.grille[2][2] = 8; //9
    }

    /**
     * Etat but
     */
    public void grilleB(){
        this.grilleBut[0][0] = 1;
        this.grilleBut[0][1] = 2;
        this.grilleBut[0][2] = 3;
        this.grilleBut[1][0] = 4;
        this.grilleBut[1][1] = 5;
        this.grilleBut[1][2] = 6;
        this.grilleBut[2][0] = 7;
        this.grilleBut[2][1] = 8;
        this.grilleBut[2][2] = 0;
    }

    /**
     * [util] : Une méthode à qui je delegue une tache
     * cette méthode permet de remplir une liste d'entier d'une taille de l*L
     * @return : la liste contenant les entiers de 1 à l*L
     */
    public ArrayList<Integer> entiers(){
        ArrayList<Integer> entier = new ArrayList<>();
        for(int i = 0;i < (this.longueur * this.largeur);i++){
            entier.add(i);
        }
        return entier;
    }


    public int getLongueur(){
        return this.longueur;
    }

    public int getLargeur(){
        return this.largeur;
    }

    public int[][] getGrille(){
        return this.grille;
    }

    public int[][] getGrilleBut(){
        return this.grilleBut;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(grille);
		return result;
	}

    /**
     * la redefinition de cette méthode permet de comparer si deux monde sont pareil
     * Deux monde son egaux si leur grillle sont égaux
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.deepEquals(grille, other.grille))
			return false;
		return true;
	}
    
    

    public int getDistance_source(){
        return this.distance_source;
    }


    @Override
    public int compareTo(State etat){
        if(this.getDistance_source() > etat.getDistance_source())
            return 1;
        else if(this.getDistance_source() < etat.getDistance_source())
            return -1;
        return 0;

    }

    
}