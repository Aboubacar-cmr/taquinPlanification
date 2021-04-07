package taquin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class State{

    private int[][]  grille;
    private int longueur;
    private int largeur;
    private ArrayList<Action> listeActions = new ArrayList<>();

    public State(int longueur,int largeur,boolean init){
        this.longueur = longueur;
        this.largeur = largeur;
        this.grille = new int[this.longueur][this.largeur];
        if(init){
            this.init();
        }
    }

    /**
     * Cette méthode permet de verifier si un état satisfait le but
     * @return
     */
    public boolean satisfie(){
        int x = 1;
        for (int i = 0; i < this.longueur; i++) {
            for (int j = 0; j <this.largeur;j++) {
                if(this.grille[i][j] == x){
                    x++;
                }else{
                    return false;
                }
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
    public void searcheActions(){
        for (int i = 0; i < this.longueur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                if( verifG(i, j)){
                    this.listeActions.add(new Action( new Position(i, j), new Position(i,(j-1) )) );
                }else if(verifD(i, j)){
                    this.listeActions.add(new Action(new Position(i, j), new Position(i,(j+1))) );
                }else if(verifH(i, j)){
                    this.listeActions.add(new Action(new Position(i, j), new Position((i-1),j)) );
                }else if(verifB(i,j)){
                    this.listeActions.add(new Action(new Position(i, j), new Position((i+1),j)) );
                }
            }
        }
    }

    /**
     * Méthode permettat d'affcher la liste des actions
     */
    public void getListActions(){
        for (int i = 0; i < this.listeActions.size(); i++) {
            System.out.println(this.listeActions.get(i));
        }
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

    public ArrayList<Action> getlistActions(){
        return this.listeActions;
    }
}