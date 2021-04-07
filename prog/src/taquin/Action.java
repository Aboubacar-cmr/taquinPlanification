package taquin;

public class Action {

    private Position positionStart;
    private Position positionEnd;
    private int cost = 1;
    

    public Action(Position posStart, Position posEnd){
        this.positionStart = posStart;
        this.positionEnd = posEnd;
    }

    public Position getPositionStart(){
        return this.positionStart;
    }

    public Position getPositionEnd(){
        return this.positionEnd;
    }

    public int getCost(){
        return this.cost;
    }

    @Override
    public String toString(){
        return   "+("+this.positionStart.getX()+","+this.positionStart.getY() +")("+this.positionEnd.getX()+","+this.positionEnd.getY()+")";
    }


}
