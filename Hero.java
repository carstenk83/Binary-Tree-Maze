package project5;


/**
* Class that creates a hero with certain number
* of life life points
*
* @author Carsten Kaiser
*/
public class Hero{
    private int activeLifePoints;

    /**
    * Constructor for hero that takes active life points param
    *
    *@param activeLifePoints life points hero has at given time
    */
    public Hero(int activeLifePoints){
        this.activeLifePoints = activeLifePoints;
    }

    /**
    * Returns active life points for specified hero
    *
    * @return activeLifePoints active life points left for hero
    */
    public int getActiveLifePoints(){
        return activeLifePoints;
    }

    /**
    * Sets active life points to specified num
    *
    * @param num new number of life points
    */
    public void setActiveLifePoints(int num){
        activeLifePoints = num;
    }

    /**
    * Decreases the active life points by a specified amount
    *
    * @param amount the amount to decrease the life points
    */
    public void decreaseLifePoints(int amount) {
        if (amount > 0) {
            activeLifePoints -= amount;
        }
    }

    /**
    * Increases the active life points by a specified amount
    *
    * @param amount the amount to increase the life points
    */
    public void increaseLifePoints(int amount) {
        if (amount > 0) {
            activeLifePoints += amount;
        }
    }
}
