package rps;

import java.util.*;

/**
 * 	The pattern matching algorithm that runs this game has two 
 * parts.
 * 	First, every time the user makes a move, every possible 
 * pattern that ends right before the most recent move is stored 
 * into a hashmap. A pattern merely denotes a series of moves. The 
 * value is an array of size 3 in which each entry represents the 
 * frequency of rock, paper, and scissors respectively that follows 
 * the pattern key. For each of the computer’s moves, the algorithm 
 * starts with the pattern ranging from the second move to the most 
 * recent move by the user and checks the hashmap for a match. If 
 * there is no match the pattern is shorted by cutting off the 
 * oldest move in the pattern and the hashmap is checked again. If a 
 * match is found, then a weighted probability distribution is 
 * created for rock, paper, and scissors based on the stored 
 * frequency array and a move is made. If the pattern is shortened 
 * such that it  contains only two moves, then no pattern has been 
 * successfully matched. This leads to the second part of the 
 * algorithm. 
 * 	There is another array of size three which contains 
 * the frequency of all rocks, papers, and scissors played so far. A 
 * weighted probability distribution is created from this holistic 
 * frequency array and a move is made if no pattern has been 
 * matched. 
 * 	Therefore, the success of this algorithm relies wholly 
 * on how predictable the human player can be. Therefore, I make no 
 * guarantee that the computer will necessarily beat you. Of course, 
 * when matched against a computer opponent this game wins around 
 * half of the time. While other rock, paper, scissors algorithms 
 * may make decisions based on large databases of moves, I think 
 * that doing so neglects the variable behavior of individual 
 * persons. 
 */

public class RPS {
    //number of moves in total include ties

    public int numMoves;
    //string representing the history of moves so far
    private String moves;
    //number of wins by the computer
    private int wins;
    //freq holds the patterns as the key and an array of frequencies as the key
    private HashMap freq;
    //keeps track of the winrate of the computer
    public double winRate;
    //keeps track of moves that arent ties
    public int validMoves;
    //keeps track of all moves played so far
    private int[] allMoves;

    //initializes all of the variables
    public RPS() {
        allMoves = new int[]{1, 1, 1};
        freq = new HashMap();
        numMoves = 0;
        wins = 0;
        winRate = 0.000;
        validMoves = 0;
        moves = "";
    }

    //add move to the hashmap and update the allMoves array
    private void addMove(char move) {
        moves = moves + move;
        if (numMoves != 0) {
            for (int i = 0; i < (moves.length() - 1); i++) {
                this.addToHM(moves.substring(i, moves.length() - 1), move);
            }
        }
        numMoves++;
        if (move == 'r') {
            allMoves[0] = allMoves[0] + 1;
        }
        if (move == 'p') {
            allMoves[1] = allMoves[1] + 1;
        }
        if (move == 's') {
            allMoves[2] = allMoves[2] + 1;
        }
    }
    //add a win and modify values

    private void addWin() {
        wins++;
        validMoves++;
        winRate = (double) wins / validMoves;
    }

    private void addLoss() {
        validMoves++;
        winRate = (double) wins / validMoves;
    }

    //modify the HM to add a new pattern
    private void addToHM(String pattern, char move) {
        if (freq.get(pattern) == null) {
            //create new freq array if one does not exist yet   
            int[] frequencies = new int[3];
            freq.put(pattern, frequencies);
        }
        //0 = rock, 1 = paper, 2 = scissors
        int[] f = (int[]) freq.get(pattern);
        if (move == 'r') {
            f[0] = f[0] + 1;
        } else if (move == 'p') {
            f[1] = f[1] + 1;
        } else if (move == 's') {
            f[2] = f[2] + 1;
        } else {
            throw new IllegalArgumentException();
        }
    }
    //calculate your move for the next round and also returns the decision for the current round
    //[char of your next move][w,l, or t depending on whether you won, lost, or tied]

    public String makeOppMove(char yourMove, char oppMove) {
        //add the new move
        this.addMove(oppMove);
        char yourNextMove = 'n';
        //calculate a move based on past history
        if (moves.length() != 1 && moves.length() != 0) {
            for (int i = 1; i < (moves.length() - 1); i++) {
                if (freq.get(moves.substring(i, moves.length())) != null) {
                    yourNextMove = this.getWinner(this.calculateMove((int[]) freq.get(moves.substring(i, moves.length()))));

                    break;
                }
            }
        }
        //if this pattern does not yet exist make a random move 
        if (yourNextMove == 'n') {
            yourNextMove = makeHolisticMove();
        }
        char decision = 'n';
        int eval = this.evaluateWinner(yourMove, oppMove);
        if (eval == -1) {
            decision = 't';
        }
        if (eval == 0) {
            decision = 'w';
            this.addWin();
        }
        if (eval == 1) {
            decision = 'l';
            this.addLoss();
        }

        char[] charArr = {yourNextMove, decision};
        String out = new String(charArr);
        return out;
    }
    //takes a userMove
    //makes a move based on past history and checks whether comp wins
    //returns a string with the following format [char of compMove][w,l, or t depending on whether the comp won, lost, or tied]

    public String makeMove(char userMove) {
        char compMove = 'n';
        char decision = 'n';
        //first, calculate a move based on past history
        //the frequency array
        if (moves.length() != 1 && moves.length() != 0) {
            for (int i = 1; i < (moves.length() - 1); i++) {
                if (freq.get(moves.substring(i, moves.length())) != null) {
                    compMove = this.getWinner(this.calculateMove((int[]) freq.get(moves.substring(i, moves.length()))));
                    break;
                }
            }
        }
        //if this pattern does not yet exist make a random move 
        if (compMove == 'n') {
            compMove = makeHolisticMove();
        }
        //second, check who won
        int eval = this.evaluateWinner(userMove, compMove);
        if (eval == -1) {
            decision = 't';
        } //player won
        else if (eval == 0) {
            decision = 'l';
            this.addLoss();
        } //comp won
        else if (eval == 1) {
            decision = 'w';
            this.addWin();
        }
        this.addMove(userMove);
        char[] charArr = {compMove, decision};
        String out = new String(charArr);
        return out;
    }

    //resets the game
    public void reset() {
        allMoves = new int[]{1, 1, 1};
        freq = new HashMap();
        numMoves = 0;
        wins = 0;
        winRate = 0.000;
        validMoves = 0;
        moves = "";
    }
    //calculate a move based on a frequency array

    private char calculateMove(int[] f) {
        double total = f[0] + f[1] + f[2];
        double r = f[0] / total;
        double p = r + (f[1] / total);
        double rand = Math.random();
        if (rand < r) {
            return 'r';
        } else if (rand < p) {
            return 'p';
        } else {
            return 's';
        }
    }

    //calculates a move based on all moves played by the user so far
    public char makeHolisticMove() {
        double tot = allMoves[0] + allMoves[1] + allMoves[2];
        double r = (double) allMoves[0] / tot;
        double p = (double) (allMoves[1] / tot) + r;
        double rand = Math.random();
        if (rand < r) {
            return 'r';
        } else if (rand < p) {
            return 'p';
        } else {
            return 's';
        }
    }

    //returns the string name of the move
    public static String getName(char move) {
        if (move == 'r') {
            return "Rock";
        }
        if (move == 'p') {
            return "Paper";
        }
        if (move == 's') {
            return "Scissors";
        }
        return "invalid";
    }

    //get the move that will beat a move
    private char getWinner(char move) {
        if (move == 'r') {
            return 'p';
        }
        if (move == 'p') {
            return 's';
        }
        if (move == 's') {
            return 'r';
        }
        return 'n';
    }

    //return 0 if first parameter is winner, 1 if second parameter is winner and -1 if there is a tie
    //return 2 if error
    private int evaluateWinner(char zero, char one) {
        if (zero == one) {
            return -1;
        }
        if (zero == 'r') {
            if (one == 'p') {
                return 1;
            }
            if (one == 's') {
                return 0;
            }
        }
        if (zero == 'p') {
            if (one == 'r') {
                return 0;
            }
            if (one == 's') {
                return 1;
            }
        }
        if (zero == 's') {
            if (one == 'r') {
                return 1;
            }
            if (one == 'p') {
                return 0;
            }
        }
        return 2;
    }
    
    /**
     * @param args the command line arguments
     */
    /**
    public static void main(String[] args) throws Exception {
        RPS comp = new RPS();
        while (true) {
            System.out.println("Please enter a move");
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(in);
            String moveString = reader.readLine();
            char move = 'n';
            boolean validMove;
            if (moveString.equals("r")) {
                move = 'r';
                validMove = true;
            } else if (moveString.equals("p")) {
                move = 'p';
                validMove = true;
            } else if (moveString.equals("s")) {
                move = 's';
                validMove = true;
            } else {
                validMove = false;
                System.out.println("Invalid move");
            }

            //if the user enters a valid move we can generate a computer move
            if (validMove) {
                String result = comp.makeMove(move);
                char decision = result.charAt(1);
                System.out.println("You chose " + RPS.getName(move) + " and the computer chose " + RPS.getName(result.charAt(0)));
                if (decision == 'w') {
                    System.out.println("You lost!");
                }
                if (decision == 'l') {
                    System.out.println("You won!");
                }
                if (decision == 't') {
                    System.out.println("You tied!");
                }


                System.out.println("Your win rate is " + (1.0 - comp.winRate));
                //System.out.println("The size of the hm is " + comp.freq.size());
            }

        }
    
        **/
}
