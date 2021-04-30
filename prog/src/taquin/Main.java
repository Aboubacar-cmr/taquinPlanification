package taquin;

import java.util.*;
public class Main {
    public static void main(String[] args){
    
        System.out.println("--------------------Init--------------------------");
        State init = new State(3,3,true);
        init.showState();

        Astart agent = new Astart(init);
        List<Action> plan = agent.agentAstart();
        System.out.println(plan);

        for(int i = plan.size()-1;i>=0;i--){
            System.out.println("------------------------");
            System.out.println(plan.get(i));
            init = init.apply(plan.get(i));
            init.showState();
        }

    }
}
