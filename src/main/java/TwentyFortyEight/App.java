package TwentyFortyEight;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static int GRID_SIZE; // 4x4 grid
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static int WIDTH;
    public static  int HEIGHT;
    public static final int FPS = 30;
    private boolean gameOver;

    private int startTime;
    private float timer;
    

    private Cell[][] board;

    public static Random random = new Random();

    private PFont font;
    public PImage eight;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.board = new Cell[GRID_SIZE][GRID_SIZE];
        gameOver = false;
        WIDTH = GRID_SIZE * CELLSIZE;
        HEIGHT =  GRID_SIZE * CELLSIZE;
    }

    private void addBlock() {
        ArrayList<Cell> emptyLocations = new ArrayList<Cell>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j].getValue() == 0) {
                    emptyLocations.add(board[i][j]);
                }
            }
        }
        int location = random.nextInt(emptyLocations.size());
        emptyLocations.get(location).place();
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player
     * and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        this.eight = loadImage(this.getClass().getResource("8.png").getPath().replace("%20", ""));
        System.out.println(this.eight);
        // " "));

        // create attributes for data storage, eg board
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2] = new Cell(i2, i);
            }
        }
        addBlock();
        addBlock();

        startTime = millis();
        timer = 0;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (this.keyCode == 82) {
            restartGame();
        } else if (this.keyCode == 37) { //Left
            if (shiftLeft()) {
                addBlock();
            }
        } else if (this.keyCode == 39) { //Right
            if (shiftRight()) {
                addBlock();
            }
        } else if (this.keyCode == 38) { //up
            if (shiftUp()) {
                addBlock();
            }
        } else if (this.keyCode == 40) { //down
            if (shiftDown()) {
                addBlock();
            }
        } 
        gameOver = checkGameOver();
    }

    private void restartGame() {
        gameOver = false;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j].setValue(0);
            }
        }
        addBlock();
        addBlock();

        startTime = millis();
        timer = 0;
    }

    private boolean checkGameOver() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j].getValue() == 0) {
                    return false;
                }
                if (i != 0) {
                    //checks up value
                    if (board[i][j].getValue() == board[i-1][j].getValue()) {
                        return false;
                    }
                } 
                if (j != 0) {
                    if (board[i][j].getValue() == board[i][j-1].getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void resetMerge() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j].setMerge(false);
            }
        }
    }

    private boolean shiftLeft() {
        boolean shifts = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 1; j < GRID_SIZE; j++) {
                if (board[i][j].getValue() != 0) {
                    boolean move = true;
                    int col = j;
                    while (move) {
                        if (col == 0) {
                            move = false;
                        } else {
                            if (board[i][col-1].getValue() == 0) {
                                board[i][col-1].setValue(board[i][col].getValue());
                                board[i][col].setValue(0);
                                col = col-1;
                                shifts = true;
                            } else if (board[i][col-1].getValue() == board[i][col].getValue()) {
                                    if (!board[i][col-1].getMerge() && !board[i][col].getMerge()) {
                                        board[i][col-1].combine();
                                        board[i][col].setValue(0);
                                        col = col-1;
                                        shifts = true;
                                    } else {
                                        move = false;
                                    }
                            } else {
                                move = false;
                            }
                        }
                    }

                }
            }
        }
        resetMerge();
        return shifts;
    }

    private boolean shiftRight() {
        boolean shifts = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = GRID_SIZE - 2; j >= 0; j--) {
                if (board[i][j].getValue() != 0) {
                    boolean move = true;
                    int col = j;

                    while (move) {
                        if (col == GRID_SIZE-1) {
                            move = false;
                        } else {
                            if (board[i][col+1].getValue() == 0) {
                                board[i][col+1].setValue(board[i][col].getValue());
                                board[i][col].setValue(0);
                                col = col+1;
                                shifts = true;
                            } else if (board[i][col+1].getValue() == board[i][col].getValue()) {
                                if (!board[i][col+1].getMerge() && !board[i][col].getMerge()) {
                                    board[i][col+1].combine();
                                    board[i][col].setValue(0);
                                    col = col+1;
                                    shifts = true;
                                } else {
                                        move = false;
                                }
                            } else {
                                move = false;
                            }
                        }
                    }

                }
            }
        }
        resetMerge();
        return shifts;
    }

    private boolean shiftUp() {
        boolean shifts = false;

        for (int i = 1; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j].getValue() != 0) {
                    boolean move = true;
                    int row = i;

                    while (move) {
                        if (row == 0) {
                            move = false;
                        } else {
                            if (board[row-1][j].getValue() == 0) {
                                board[row-1][j].setValue(board[row][j].getValue());
                                board[row][j].setValue(0);
                                row = row-1;
                                shifts = true;
                            } else if (board[row-1][j].getValue() == board[row][j].getValue()) {
                                if (!board[row-1][j].getMerge() && !board[row][j].getMerge()) {
                                    board[row-1][j].combine();
                                    board[row][j].setValue(0);
                                    row = row-1;
                                    shifts = true;
                                } else {
                                    move = false;
                                }
                            } else {
                                move = false;
                            }
                        }
                    }

                }
            }
        }
        resetMerge();
        return shifts;
    }

    private boolean shiftDown() {
        boolean shifts = false;

        for (int i = GRID_SIZE-2; i >= 0; i--) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j].getValue() != 0) {
                    boolean move = true;
                    int row = i;

                    while (move) {
                        if (row == GRID_SIZE-1) {
                            move = false;
                        } else {
                            if (board[row+1][j].getValue() == 0) {
                                board[row+1][j].setValue(board[row][j].getValue());
                                board[row][j].setValue(0);
                                row = row+1;
                                shifts = true;
                            } else if (board[row+1][j].getValue() == board[row][j].getValue()) {
                                if (!board[row+1][j].getMerge() && !board[row][j].getMerge()) {
                                    board[row+1][j].combine();
                                    board[row][j].setValue(0);
                                    row = row+1;
                                    shifts = true;
                                } else {
                                    move = false;
                                }
                            } else {
                                move = false;
                            }
                        }
                    }

                }
            }
        }
        resetMerge();
        return shifts;
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == PConstants.LEFT) {
            Cell current = board[e.getY()/App.CELLSIZE][e.getX()/App.CELLSIZE];
            current.place();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        // draw game board


        this.textSize(40);
        this.strokeWeight(15);
        
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2].draw(this);
            }
        }

        fill(0, 0, 0);
        textSize(30);
        if (timer < 100) {
            text("Time: " + (int)timer, WIDTH - 135, 30);
        } else {
            text("Time: " + (int)timer, WIDTH - 150, 30);
        }

        if (gameOver) {
            if (GRID_SIZE == 2) {
                textSize(30);
                text("GAME OVER", WIDTH / 2 - 85, HEIGHT / 2);
            } else {
                textSize(40);
                text("GAME OVER", WIDTH / 2 - 110, HEIGHT / 2);
            }
        
            // text("R to Restart", WIDTH / 2 - 210, HEIGHT / 2+ 50);
        } else {
            int elapsed = millis() - startTime;
            timer = elapsed / 1000.0f;
        }

        //this.image(this.eight, 0, 0);
    }


    public static void main(String[] args) {
        if (args.length == 1) {
            GRID_SIZE = Integer.parseInt(args[0]);
        } else {
            GRID_SIZE = 4;
        }
        PApplet.main("TwentyFortyEight.App");
    }

}

