package taquin;

public class DistanceSource implements Comparable<DistanceSource> {
    private State etat;
    private Float distance;

    public DistanceSource(State etat,Float dist){
        this.etat = etat;
        this.distance = dist;
    }

    public DistanceSource(State etat){
        this(etat,0F);
    }

    @Override
    public int compareTo(DistanceSource distSrc){
        if(this.getDistance() > distSrc.getDistance())
            return 1;
        else if(this.getDistance() < distSrc.getDistance())
            return -1;
        return 0;
    }

    public Float getDistance(){
        return this.distance;
    }

    public State getEtat(){
        return this.etat;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
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
		DistanceSource other = (DistanceSource) obj;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		return true;
	}
    
    
}
