package taquin;

import java.util.*;


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
    


    public List<Action> djisktra(){
        Map<State,Action> plan = new HashMap<>();

        Map<State,Integer> distance_source = new HashMap<>();
        distance_source.put(this.state,0);

        Map<State,State> father = new HashMap<>();
        father.put(this.state, null);

        List<State> open = new ArrayList<>();
        open.add(this.state);

        List<State> goals = null;

        while(!open.isEmpty()){

            //prendre le noeud qui a la plus petite distance source  dans la map des distance
            State instantiation = this.argmin(distance_source);
            open.remove(instantiation);
            if(instantiation.satisfie()){
                goals.add(instantiation);
            }
            /**Pour toutes les actions de l'état initial */
            for (Action  action : this.state.searcheActions()) {
                State next  = this.state.apply(action);
                if(!distance_source.containsKey(next)){
                    distance_source.put(next, Integer.MAX_VALUE);
                }
                if(distance_source.get(next) > (distance_source.get(instantiation) + action.getCost())){
                    distance_source.put(next,(distance_source.get(instantiation)  + action.getCost()));
                    father.put(next, instantiation);
                    plan.put(next, action);
                    open.add(next);
                }
            }
        }
        if(goals.isEmpty()){
            return null;
        }else{
            return dijkstra_plan(father,plan,goals,distance_source);
        }
    }

    public List<Action> dijkstra_plan(Map<State,State> father,Map<State,Action> plan,List<State> goals,Map<State,Integer> distance_sr){
        List<Action> actions = new ArrayList<>();
        State goal = argmin(distance_sr);
        while(goal != null){
            if(plan.get(goal)!= null){
                actions.add(plan.get(goal));
                goal = father.get(goal);
            }else{
                break;
            }
           
        }
        System.out.println("yesss");
        return actions;
    }

    /**
     * Méthode permettant de trouver l'état qui à la plus petite distance source
     * @param distance la liste des états avec leurs distance source
     * @return Le sommet qui à la plus pétite distance source entre les sommets
     */
    public State argmin(Map<State,Integer> distance){
        State instance = null;
        Integer dist = Integer.MAX_VALUE;
        for (Map.Entry mapentry : distance.entrySet()) {

            Integer val = (Integer)mapentry.getValue();
            if(val<dist){
                dist = val;
                instance = (State)mapentry.getKey();
            } 

        }
        return instance;
    }

    
}
