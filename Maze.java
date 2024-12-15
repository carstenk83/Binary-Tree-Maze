package project5;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that represents a Maze 
 * Inherits from BST<MazeNode> and provides functionality specific 
 * to the maze, such as finding
 * valid paths and checking life point requirements
 *
 * @author Carsten Kaiser
 */
public class Maze extends BST<MazeNode>{
    /**
    * Adds node to BST using add method from super class
    *
    * @return true if this tree did not already contain the specified element, false if so
    */
    @Override
    public boolean add(MazeNode node) {
        return super.add(node);  // Call the parent BST add method, return type matches
    }

    /**
     * Prints all valid paths from the root to the leaf nodes
     * where the nodes are at the last level and life points are sufficient
     */
    public void printAvailablePaths() {
        List<MazeNode> currentPath = new ArrayList<>();
        List<String> allPaths = new ArrayList<>();

        // Start the recursive collection of valid paths
        collectPaths(this.root, currentPath, 0, allPaths, this.root.height);

        if (!allPaths.isEmpty()) {
            for (String path : allPaths) {
                System.out.println(path);
            }
        }
    }

    /**
     * Recursive helper to collect valid paths from root to leaves
     *
     * @param node the current node in the tree
     * @param currentPath the list of MazeNodes in the current path
     * @param lifePoints accumulated life points
     * @param allPaths the list of all valid paths as strings
     * @param currentHeight the current height of the node
     */
    private void collectPaths(BST.Node node, List<MazeNode> currentPath, 
    int lifePoints, List<String> allPaths, int currentHeight) {
        if (node == null) {
            return;
        }

        MazeNode mazeNode = (MazeNode) node.data;
        lifePoints += mazeNode.getLifePoints();
        currentPath.add((MazeNode) node.data);

        if (lifePoints <= 0 ) {
            currentPath.remove(currentPath.size() - 1);
            return;
        }

        //check if this is a leaf node and if the current height is 1
        if (node.left == null && node.right == null) {
            if (currentHeight == 1) {
                allPaths.add(formatPath(currentPath));
            }
        } else {
            //recursive call into left and right children, passing the updated height
            collectPaths(node.left, currentPath, lifePoints - 1, allPaths, currentHeight - 1);
            collectPaths(node.right, currentPath, lifePoints - 1, allPaths, currentHeight - 1);
        }

        //backtrack
        currentPath.remove(currentPath.size() - 1);
    }

    /**
    * Formats a list of MazeNodes into a string representation of the path
    *
    * @param path the list of MazeNodes in the path
    * @return a formatted string of the path
    */
    private String formatPath(List<MazeNode> path) {
        String result = "";
        for (MazeNode node : path) {
            result += node.getLabel() + " ";
        }
    
        return result;
    }
}
