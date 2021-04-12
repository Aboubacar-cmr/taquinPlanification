package taquin;

import java.util.*;
public class Main {
    public static void main(String[] args){
        /**
         * Instantiation du monde initial
         */
        State monde = new State(3,3,true);
        monde.showState();
        System.out.println("#############################################################");

        /**
         * Instantiation de l'agent intelligent
         */
        AgentIA ia = new AgentIA(monde);
        List<Action> plan =ia.bfs();
        plan.remove(plan.size()-1);
        System.out.println(plan.size());
       for (Action action : plan) {
           monde = monde.apply(action);
           monde.showState();
           //System.out.println(action);
           System.out.println("-----------------------------------");
       }
        


    }
}
