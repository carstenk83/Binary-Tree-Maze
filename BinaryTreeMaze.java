package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
* Class that reads file input and creates maze from given input
* Finds all available paths and prints them, then closes Scanner
*
* @author Carsten Kaiser
*/
public class BinaryTreeMaze{
    public static void main(String[] args){
        /**
        * Code borrowed from project 3 reading input file
        */
        //verifies that the command line argument exists
        if (args.length == 0 ) {
            System.err.println("Usage Error: the program expects file name as an argument.\n");
            System.exit(1);
        }

        //verifies that command line argument contains a name of an existing file
        File mazeFile = new File(args[0]);
        if (!mazeFile.exists()) {
            System.err.println("Error: the file "+mazeFile.getAbsolutePath()+" does not exist.\n");
            System.exit(1);
        }
        if (!mazeFile.canRead()) {
            System.err.println("Error: the file "+mazeFile.getAbsolutePath()+
                               " cannot be opened for reading.\n");
            System.exit(1);
        }

        //open the file for reading
        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner (mazeFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: the file "+mazeFile.getAbsolutePath()+
                               " cannot be opened for reading.\n");
            System.exit(1);
        }

        //create maze object
        Maze maze = new Maze();

        //reading and saving maze data
        String line;
        Scanner lineScanner;
        String label;
        int lifePoints;
        MazeNode node;

        while (fileScanner.hasNextLine()) {
            line = fileScanner.nextLine();
            lineScanner = new Scanner(line);

            try {
            label = lineScanner.next().trim(); //get label
            lifePoints = Integer.parseInt(lineScanner.next().trim()); //get life point
            
            node = new MazeNode(label, lifePoints);
            
            maze.add(node);
    
            }
            catch (NoSuchElementException ex ) {
                //caused by an incomplete or miss-formatted line in the input file
                System.err.println(line);
                continue;
            }
            catch (IllegalArgumentException ex ) {
                //ignore this exception and skip to the next line
            }
        }


        maze.printAvailablePaths();

        fileScanner.close();
    }
}
