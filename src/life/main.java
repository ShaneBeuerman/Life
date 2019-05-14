package life;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

public class main {

    /*
     Main class
    */
    public static void main(String[] args) throws Exception{    
        setupGUI();
    }
    
    /*
     setUpGui() creates a 515 by 538 pixel gui that is updated 193
     times every 100 milliseconds to showcase how each generation
     changes.
    */
    public static void setupGUI() throws Exception{
        DrawGame game = new DrawGame();
        JFrame box = new JFrame("Game");
        box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        box.setSize(515, 538);
        box.setPreferredSize(new Dimension(515, 538));
        box.add(game);
        box.setLocationRelativeTo(null);
        box.pack();
        box.setVisible(true);
        
        //This loop influences how many times the program updates
        for (int i = 0; i < 193; i++) {
            game.animate();
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    /*
        DrawGame creates the graphics and updates every time animate()
        is called.
    */
    private static class DrawGame extends JPanel{
        Color t = Color.RED;
        int[][] board = new int[50][50];
        int counter = 0;
        
        /*
            paintComponent uses an 2d array called board to paint 
            rectangles on the GUI. If the array contains a 1, then
            it is alive. If the array contains a 0, then it is dead.
        */
        protected void paintComponent(Graphics g) {
            g.setColor(t);
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    if (board[i][j] == 1) {
                        g.setColor(Color.RED);
                    }
                    if (board[i][j] == 0) {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(j * 10, i * 10, 10, 10);
                }
            }

        }

        /*
         animate is called to repaint the GUI. If it is called for the
        first time, it initializes the GUI. Otherwise, it updates before
        it repaints.
        */
        public void animate() throws InterruptedException {
            if (counter == 1) {
                board = update(board);
            } else {
                board = initialize(board);
            }
            counter = 1;
            repaint();
        }

        /*
         The initial state is a simple glider that moves from the top left
         corner of the screen to the bottom right corner.
        */
        public static int[][] initialize(int[][] board) {
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    board[i][j] = 0;
                }
            }
            board[0][0] = 1;
            board[1][1] = 1;
            board[1][2] = 1;
            board[2][0] = 1;
            board[2][1] = 1;
            
            return board;
        }

        /*
         The Game of Life follows four rules when updating. Update follows
         these four rules every time it is called.
        */
        public static int[][] update(int[][] board) {
            int totalNeighbors = 0;
            boolean dead;
            int[][] newBoard = new int[50][50];
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    if (board[i][j] == 0) {
                        dead = true;
                    } else {
                        dead = false;
                    }
                    totalNeighbors = neighborCount(board, i, j);
                    //Rule 1
                    if (dead == false && totalNeighbors < 2) {
                        dead = true;
                    }
                    //Rule 2
                    if (dead == false && totalNeighbors == 2 && totalNeighbors == 3) {
                        dead = false;
                    }
                    //Rule 3
                    if (dead == false && totalNeighbors > 3) {
                        dead = true;
                    }
                    //Rule 4
                    if (dead == true && totalNeighbors == 3) {
                        dead = false;
                    }
                    // 0 means dead, 1 means alive
                    // totalNeighbors is reset to zero;
                    if (dead) {
                        newBoard[i][j] = 0;
                        totalNeighbors = 0;
                    } else {
                        newBoard[i][j] = 1;
                        totalNeighbors = 0;
                    }

                }
            }
            return newBoard;
        }
       
        /*
         This counts the number of neighbors a given value in a 2d array has.
         This is important to determine in order to know whether an organism
         livs or dies.
        */
        public static int neighborCount(int[][] board, int x, int y) {
            int totalNeighbors = 0;
            if ((x - 1) >= 0) {
                if ((y - 1) >= 0) {
                    if (board[x - 1][y - 1] == 1) {
                        totalNeighbors++;
                    }
                }
                if ((y + 1) < 50) {
                    if (board[x - 1][y + 1] == 1) {
                        totalNeighbors++;
                    }
                }
                if (board[x - 1][y] == 1) {
                    totalNeighbors++;
                }
            }
            if ((x + 1) < 50) {
                if ((y - 1) >= 0) {
                    if (board[x + 1][y - 1] == 1) {
                        totalNeighbors++;
                    }
                }
                if ((y + 1) < 50) {
                    if (board[x + 1][y + 1] == 1) {
                        totalNeighbors++;
                    }
                }
                if (board[x + 1][y] == 1) {
                    totalNeighbors++;
                }
            }
            if ((y + 1) < 50) {
                if (board[x][y + 1] == 1) {
                    totalNeighbors++;
                }
            }
            if ((y - 1) >= 0) {
                if (board[x][y - 1] == 1) {
                    totalNeighbors++;
                }
            }
            return totalNeighbors;
        }
    }
}
