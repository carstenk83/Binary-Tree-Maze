package project5;

/**
* Class that represents MazeNodes which have
* a label and number of life points
* implements Comparable<E> to be able to compare MazeNodes based
* off of their labels
*
* @author Carsten Kaiser
*/
public class MazeNode implements Comparable<MazeNode>{
    private String label;
    private int lifePoints;

    /**
    * Constructs MazeNode with parameters label and life points
    *
    * @param label label of MazeNode
    * @param lifePoints number of lifepoints MazeNode has
    */
    public MazeNode(String label, int lifePoints){
        this.label = label;
        this.lifePoints = lifePoints;
    }

    /**
    * Returns label of this MazeNode
    *
    * @return label label of MazeNode
    */
    public String getLabel(){
        return label;
    }

    /**
    * Returns life points of this MazeNode
    *
    * @return lifePoints number of life points
    */
    public int getLifePoints(){
        return lifePoints;
    }

    /**
    * Compares MazeNodes based off of their label
    *
    * @param other MazeNode being compared to
    * @return if less than 0 then other label is 
    * greater, greater than 0 than other label is smaller,
    * 0 means they are equal
    */
    @Override
    public int compareTo(MazeNode other){
        return this.label.compareTo(other.label);
    }

    /**
    * Returns a string representation of this MazeNode
    *
    * @return a string representation of a MazeNode with its label and life points
    */
    @Override
    public String toString() {
        return label + " " + lifePoints;
    }
    
}
