package taquin;

public class Main {
    public static void main(String[] args){
        State monde = new State(3,3,true);
        monde.showState();
        monde.searcheActions();
        monde.getListActions();
        System.out.println("-----------------suivant------------------------");

        // ----------------------------------
        monde = monde.apply(monde.getlistActions().get(0));
        monde.showState();
    }
}
