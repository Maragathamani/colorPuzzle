package d2test;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Maragathamani on 3/12/14.
 */
public class D2Test {

    public static TreeMap<Integer, ArrayList<String>> openList = new TreeMap<Integer, ArrayList<String>>();
    public static HashMap<Integer, String> closedList = new HashMap<Integer, String>();
    public static HashMap<String, String> trackList1 = new HashMap<String, String>();
    public static String homeDirectory = System.getProperty("user.home") + "/Desktop";
    public static String filesDirectory = homeDirectory + "/.";
    public static Integer count = 0;
    public static long startTime = 0;
    public static File file = new File(filesDirectory+"\\output.txt");
    public static long time = 0;
    public static String strTime = "";
    public static ArrayList<Character> allMoves = new ArrayList<Character>();

    public static void main(String[] args) throws IOException {

        ArrayList<String> input1 = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(filesDirectory + "\\input.txt"));       //Reads the input text file.

        //Takes all the input in an array
        while (br.ready()) {
            String input = br.readLine();
            if (!input.isEmpty()) {
                input1.add(input);
            }
        }
        br.close();
        operation(input1);
    }

    // Each puzzles in the input file are processed here.
    private static void operation(ArrayList<String> input1) throws IOException {

        int totalMoves = 0;
        // Loop for each puzzle in the input file.
        for (int i = 0; i < input1.size(); i++) {
            openList.clear();
            closedList.clear();
            trackList1.clear();
            allMoves.clear();
            String puzzle = input1.get(i);
            System.out.println("Puzzle: " + puzzle);
            startTime = System.currentTimeMillis();
            ArrayList<Character> puzzleColor = new ArrayList<Character>();
            for (int j = 0; j < puzzle.length(); j++) {
                if (Character.isLetter(puzzle.charAt(j))) {
                    puzzleColor.add(puzzle.charAt(j));
                }
            }
            puzzle = charArrayListToString(puzzleColor);
            ArrayList<String> str = new ArrayList<String>();
            str.add(puzzle);
            openList.put(calculateHeuristicValue(puzzleColor), str);                              // Initial state of the puzzle is inserted into open list with heuristic value.
            trackList1.put(puzzle, "");

            int z = 0;

            while (!validateGoalState(puzzle)) {
                ArrayList<String> firstEntry = openList.firstEntry().getValue();                  // Taking the first entry from the open list.
                int key = openList.firstEntry().getKey();

                if (firstEntry.size() > 1) {                                                      // checks whether the openList have more than one state in the first entry then it takes only the first state from the first entry.
                    puzzle = (firstEntry.get(0));
                    firstEntry.remove(0);
                    openList.put(key, firstEntry);
                } else if (firstEntry.size() == 1) {
                    puzzle = (firstEntry.get(0));
                    openList.pollFirstEntry();
                }

                closedList.put(z++, puzzle);                                                      // Adding the processing state to the closedList.

                int ePosition = puzzle.indexOf('e');
                possibleMoves(ePosition, puzzle);
            }
            System.out.println();
            allMoves.add(charOfTheMove(puzzle.indexOf('e')));
            trackMoves(trackList1, puzzle);
            String move = charArrayListToReverseString(allMoves).substring(1);
            System.out.println("Shortest Path: " + move);
            writeOutputFile(move);
//            writeOutputFile("\n");
            System.out.println("Time: " + strTime);
            writeOutputFile(strTime);
//            writeOutputFile("\n \n");
            totalMoves += move.length();
//            writeOutputFile(strTime);
        }
        System.out.println("Total Moves: " + totalMoves);
        writeOutputFile(String.valueOf(totalMoves));
//        writeOutputFile("\n");
        System.out.println("Total Time: " + totalTimeTaken(0));
        writeOutputFile(String.valueOf(totalTimeTaken(0)) + "ms");
//        writeOutputFile(String.valueOf(totalMoves));
//        writeOutputFile(totalTimeTaken(0) + "ms");
    }

    private static void trackMoves(HashMap<String, String> trackList1, String puzzle) {
        String str1 = trackList1.get(puzzle);
        if (!str1.equalsIgnoreCase("")) {
            allMoves.add(charOfTheMove(str1.indexOf('e')));
            trackMoves(trackList1, str1);
        }
    }

    private static char charOfTheMove(int ePosition) {
        int temp = 65 + ePosition;
        char letterToMove = (char) temp;
        return (letterToMove);
    }

    /**
     * This calculates all the possible states from the current state.
     *
     * @param ePosition
     * @param puzzleColor
     */
    private static void possibleMoves(int ePosition, String puzzleColor) {

        if (ePosition == 0) {
            addToOpenList(ePosition, 1, puzzleColor, count);
            addToOpenList(ePosition, 5, puzzleColor, count);
        } else if (ePosition == 1) {
            addToOpenList(ePosition, 0, puzzleColor, count);
            addToOpenList(ePosition, 2, puzzleColor, count);
            addToOpenList(ePosition, 6, puzzleColor, count);
        } else if (ePosition == 2) {
            addToOpenList(ePosition, 1, puzzleColor, count);
            addToOpenList(ePosition, 3, puzzleColor, count);
            addToOpenList(ePosition, 7, puzzleColor, count);
        } else if (ePosition == 3) {
            addToOpenList(ePosition, 2, puzzleColor, count);
            addToOpenList(ePosition, 4, puzzleColor, count);
            addToOpenList(ePosition, 8, puzzleColor, count);
        } else if (ePosition == 4) {
            addToOpenList(ePosition, 3, puzzleColor, count);
            addToOpenList(ePosition, 9, puzzleColor, count);
        } else if (ePosition == 5) {
            addToOpenList(ePosition, 0, puzzleColor, count);
            addToOpenList(ePosition, 6, puzzleColor, count);
            addToOpenList(ePosition, 10, puzzleColor, count);
        } else if (ePosition == 6) {
            addToOpenList(ePosition, 5, puzzleColor, count);
            addToOpenList(ePosition, 1, puzzleColor, count);
            addToOpenList(ePosition, 7, puzzleColor, count);
            addToOpenList(ePosition, 11, puzzleColor, count);
        } else if (ePosition == 7) {
            addToOpenList(ePosition, 2, puzzleColor, count);
            addToOpenList(ePosition, 6, puzzleColor, count);
            addToOpenList(ePosition, 12, puzzleColor, count);
            addToOpenList(ePosition, 8, puzzleColor, count);
        } else if (ePosition == 8) {
            addToOpenList(ePosition, 3, puzzleColor, count);
            addToOpenList(ePosition, 7, puzzleColor, count);
            addToOpenList(ePosition, 13, puzzleColor, count);
            addToOpenList(ePosition, 9, puzzleColor, count);
        } else if (ePosition == 9) {
            addToOpenList(ePosition, 4, puzzleColor, count);
            addToOpenList(ePosition, 8, puzzleColor, count);
            addToOpenList(ePosition, 14, puzzleColor, count);
        } else if (ePosition == 10) {
            addToOpenList(ePosition, 5, puzzleColor, count);
            addToOpenList(ePosition, 11, puzzleColor, count);
        } else if (ePosition == 11) {
            addToOpenList(ePosition, 6, puzzleColor, count);
            addToOpenList(ePosition, 10, puzzleColor, count);
            addToOpenList(ePosition, 12, puzzleColor, count);
        } else if (ePosition == 12) {
            addToOpenList(ePosition, 11, puzzleColor, count);
            addToOpenList(ePosition, 7, puzzleColor, count);
            addToOpenList(ePosition, 13, puzzleColor, count);
        } else if (ePosition == 13) {
            addToOpenList(ePosition, 12, puzzleColor, count);
            addToOpenList(ePosition, 8, puzzleColor, count);
            addToOpenList(ePosition, 14, puzzleColor, count);
        } else if (ePosition == 14) {
            addToOpenList(ePosition, 13, puzzleColor, count);
            addToOpenList(ePosition, 9, puzzleColor, count);
        }
    }

    /**
     * Adds the possible states to the open list in order of the heuristic value.
     *
     * @param ePosition
     * @param i
     * @param puzzleColor
     * @param count
     */
    private static void addToOpenList(int ePosition, int i, String puzzleColor, Integer count) {
        ArrayList<Character> temp2 = swap(ePosition, i, stringToCharArrayList(puzzleColor));
        int heuristicValue = calculateHeuristicValue(temp2) + count;
        String str = charArrayListToString(temp2);
        if (checkClosedList(str)) {
            if (checkOpenList(str, heuristicValue)) {
                if (openList.get(heuristicValue) == null) {
                    ArrayList<String> open = new ArrayList<String>();
                    open.add(str);
                    openList.put(heuristicValue, open);
                    trackList1.put(str, puzzleColor);
                } else {
                    ArrayList<String> temp3 = openList.get(heuristicValue);
                    temp3.add(str);
                    openList.put(heuristicValue, temp3);
                    trackList1.put(str, puzzleColor);
                }
            }

        }
    }

    /**
     * This method checks whether the state is present in the open list and adds the new heuristic value to the state if its lesser than the old heuristic.
     *
     * @param str
     * @param heuristicValue
     * @return True if the state is not in openList. False if the state is in the openList (either new h(n) is > or < old h(n)).
     */
    private static boolean checkOpenList(String str, int heuristicValue) {
        for (int key : openList.keySet()) {
            ArrayList<String> value1 = openList.get(key);
            for (int v = 0; v < value1.size(); v++) {
                if (value1.get(v).equalsIgnoreCase(str)) {
                    if (heuristicValue < key) {
                        value1.remove(v);
                        openList.put(key, value1);
                        ArrayList<String> temp3 = new ArrayList<String>();
                        temp3.add(str);
                        openList.put(heuristicValue, temp3);
                        return false;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the given state is in the closed list.
     *
     * @param str
     * @return true if the state not present in the closed list.
     */
    private static boolean checkClosedList(String str) {
        for (String value : closedList.values()) {
            if (value.equalsIgnoreCase(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the heuristic value based on the goal state.
     *
     * @param temp2
     * @return heuristicValue
     */
    private static int calculateHeuristicValue(ArrayList<Character> temp2) {
        int heuristicValue = 0;
        if (!temp2.get(0).equals(temp2.get(10))) {
            heuristicValue++;
        }
        if (!temp2.get(1).equals(temp2.get(11))) {
            heuristicValue++;
        }
        if (!temp2.get(2).equals(temp2.get(12))) {
            heuristicValue++;
        }
        if (!temp2.get(3).equals(temp2.get(13))) {
            heuristicValue++;
        }
        if (!temp2.get(4).equals(temp2.get(14))) {
            heuristicValue++;
        }
        int e = temp2.indexOf('e');
        if(e==5||e==6|e==7||e==8||e==9)
            heuristicValue++;
        return heuristicValue;
    }


    /**
     * This method will move the slide to its new position.
     *
     * @param i
     * @param i1
     * @param temp
     * @return Array list of characters that contains the new state.
     */
    private static ArrayList<Character> swap(int i, int i1, ArrayList<Character> temp) {
        char c = temp.get(i);
        char c1 = temp.get(i1);
        temp.set(i, c1);
        temp.set(i1, c);
        return temp;
    }

    /**
     * Converts the array list of characters to the string.
     *
     * @param list
     * @return String
     */
    private static String charArrayListToString(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }


    private static String charArrayListToReverseString(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        builder.reverse();
        return builder.toString();
    }


    /**
     * Converts the string into array list of characters.
     *
     * @param str
     * @return array list of characters.
     */
    private static ArrayList<Character> stringToCharArrayList(String str) {
        ArrayList<Character> a = new ArrayList<Character>();
        for (int i = 0; i < str.length(); i++) {
            a.add(str.charAt(i));

        }
        return a;
    }

    /**
     * validates the given state whether its a goal state or not.
     *
     * @param goal
     * @return True - if its  a goal state. False - Otherwise
     */
    private static boolean validateGoalState(String goal) throws IOException {
        if (goal.charAt(0) == (goal.charAt(10)) && goal.charAt(1) == (goal.charAt(11))
                && goal.charAt(2) == (goal.charAt(12)) && goal.charAt(3) == (goal.charAt(13))
                && goal.charAt(4) == (goal.charAt(14))) {
            System.out.println();
            System.out.println("Goal State reached: ");
            System.out.println(goal);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            totalTimeTaken(totalTime);
            strTime = totalTime + "ms";
            return true;
        }
        return false;
    }


    private static long totalTimeTaken(long totalTime) {
        time = totalTime + time;
        return time;
    }

    private static void writeOutputFile(String str) throws IOException {
        FileUtils.write(file, str + "\r\n", true);
    }
}