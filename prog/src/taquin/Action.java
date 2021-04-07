package taquin;

public class Action {

    private int value;
    private Position positionStart;
    private Position positionEnd;
    

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

    public int getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return   "+("+this.positionStart.getX()+","+this.positionStart.getY() +")("+this.positionEnd.getX()+","+this.positionEnd.getY()+")";
    }


}
