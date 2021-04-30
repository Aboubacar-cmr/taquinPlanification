package taquin;

import java.util.*;

public class Astart{
    private State etatInit;

    public Astart(State init){
        this.etatInit = init;
    }

    /**
     * Cette méthode permet de trouver le nombre de piece male placer dans la grille
     */
    public Float heuristique(State etat){
        int cpt = 0;
        for (int i = 0; i < this.etatInit.getLongueur(); i++) {
            for (int j = 0; j < this.etatInit.getLargeur();j++) {
                if(etat.getGrille()[i][j] != this.etatInit.getGrilleBut()[i][j]){
                    cpt+=1;
                }
            }
        }
        return cpt+0F;
    }


    public List<Action> agentAstart(){
        Map<State,Action> plan = new HashMap<>();
        Map<State,State> father = new HashMap<>();
        Map<State,Float> distance = new HashMap<>();
        PriorityQueue<DistanceSource> value = new PriorityQueue<>();
        List<State> open = new ArrayList<>();
        open.add(this.etatInit);
        father.put(this.etatInit, null);
        distance.put(this.etatInit, 0F);
        value.add(new DistanceSource(this.etatInit, this.heuristique(this.etatInit)));

        while(!open.isEmpty()){
            System.out.println(value.size());
            State instantiation = value.poll().getEtat();
            if(instantiation.ISsatisfie()){
                System.out.println("YESS");
                return this.get_bfs_plan(father,plan,instantiation);
            }else{
                open.remove(instantiation);
                for(Action action : instantiation.searcheActions()){
                    State next = instantiation.apply(action);
                    if(!distance.containsKey(next)){
                        distance.put(next,Float.MAX_VALUE);
                    }
                    if(distance.get(next)> (distance.get(instantiation) + action.getCost())){
                        distance.put(next, (distance.get(instantiation)+ action.getCost()));
                        value.remove(new DistanceSource(next));
                        value.add(new DistanceSource(next, (distance.get(next)+this.heuristique(next)) ));
                        father.put(next, instantiation);
                        plan.put(next,action);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }


    public List<Action> get_bfs_plan(Map<State,State> father,Map<State,Action> plan,State goal){
        List<Action> bfs_plan = new ArrayList<>();
        while(goal != null){
            if(plan.get(goal) != null){
                bfs_plan.add(plan.get(goal));
            }
            goal = father.get(goal);
        }
        return bfs_plan;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etatInit == null) ? 0 : etatInit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Astart other = (Astart) obj;
		if (etatInit == null) {
			if (other.etatInit != null)
				return false;
		} else if (!etatInit.equals(other.etatInit))
			return false;
		return true;
	}
    
    
    
}