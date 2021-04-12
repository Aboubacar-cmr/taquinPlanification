package taquin;

import java.util.*;
import java.util.Stack;

/**
 * Cette à pour but d'implementer les algorithme d'intelligence arttificielle pour
 * resoudre le problème de taquin
 * 
 */
public class AgentIA {

    private State state;

    public AgentIA(State state){
        this.state = state;
    }
    /**
     * Algorithme de parcour en profondeur 
     * @return Un ensemble d'action (plan d'action ) pour atteindre le but
     */
    public List<Action> dfs(){
        List<Action> plan = new ArrayList<>();
        ArrayList<State> closed = new ArrayList<>();
        closed.add(this.state);
        return dfss(this.state,plan,closed);
    }

    public List<Action> dfss(State instantiation,List<Action> plan,ArrayList<State> closed){
        if(instantiation.satisfie()){
            return plan;
        }else{

            for(Action action: instantiation.searcheActions()){

                State next = instantiation.apply(action);
                //verifier le next(nouveau etat) n'est pas dans la liste de closed
                if(!closed.contains(next)){
                    plan.add(action);
                    closed.add(next); //ajout de l'état dans la liste des fermes
                    //apl recurssif
                    List<Action>  subplan = dfss(next,plan,closed);
                    if(!(subplan == null)){
                        return subplan;
                    }else {
                        plan.remove(plan.size()-1);
                    }
                }

            }
            return null;
        }
    }

    /**
     * Cette méthode permet de verifier si un existe déja dans la liste états
     * @param next : l'état à verifier
     * @param closed : liste des états
     * @return un boolean (true si l'état existe sion false)
     */
    public boolean existe(State next,ArrayList<State> closed){
        
        for(State etat : closed){
            if(next.equals(etat)){
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public List<Action> bfs(){
        Map<State,State> father = new HashMap<>();
        father.put(this.state, null); 
        Map<State,Action> plan = new HashMap<>();
        List<State> open = new ArrayList<>();
        List<State> closed = new ArrayList<>();
        open.add(this.state);
        closed.add(this.state);

        if(this.state.satisfie()){
            return new ArrayList<Action>();
        }

        while(open.size() != 0){
            System.out.println("....");
            State instantiation = open.get(0);
            for (Action action : this.state.searcheActions()) {
                State next = this.state.apply(action);
                if( (!closed.contains(next)) &&  (!open.contains(next))){
                    father.put(next,instantiation);
                    plan.put(next, action);
                    if(next.satisfie()){
                        return get_bfs_plan(father,plan,next);
                    }else{
                        open.add(next);
                    }
                }
                
            }
        }
        return null;
    }

    public List<Action> get_bfs_plan(Map<State,State> father,Map<State,Action> plan,State instance){
        List<Action> actions = new ArrayList<>();

        while(instance != null){
            actions.add(plan.get(instance));
            instance = father.get(instance);
        }
        return actions;
    }
    

    
}
