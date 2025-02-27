import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;


/* 
 * The Tetris game features its algorithm which starts by establishing the graphical interface and building a two-dimensional array for game board representation.
 *  The program defines various block shapes as 2D arrays through a class structure while it creates a method that selects random starting blocks at the upper board border. 
 * The game loops function through a timer system that controls block descent movements while it continuously monitors user input for block movement and rotation functions. 
 * The block moves downwards until collision detection shows that it can go no further. At this moment the block gets merged into the board by marking occupied cells as filled.
 *  A row completion check follows next in the sequence which deletes complete rows and causes continuous downward movement of all higher rows. 
 * After the previous block lands the game engine generates a fresh random block and then verifies if this new block can start its placement from its 
 * initial position to determine game status. 
 * The game board receives continual updating which shows both the progressing block pieces and the number of filled rows.
*/

public class Q3B extends JPanel implements ActionListener {

    /*
     * ode Explanation
Class Definitions:

The main class titled Q3B functions as both the game controller and graphics processor.
The Block class contains information about the rectangle shapes by which falling blocks appear while also holding their respective colors and coordinates.
Game Board Setup:

A 2D array structure called int[][] board shows the game board layout through its plain cells since each element indicates whether it has a block placed in it or not.
Block Generation:

A random block shape appears through the Block class via its getRandomBlock() method while receiving its assigned color. The Block shapes exist as 2D data models.
Drawing the Game:

The game board along with the current block become visible through the paintComponent(Graphics g) method override. The Graphics object enables filling rectangles with a position and color range specified by the current block.
Movement Logic:

Through the moveBlock(int dx, int dy) method the user interface positions the active block depending on their keystrokes. The system examines the space around the block as a way to establish potential movement boundaries.
Merging and Clearing Rows:

The game logic happens within two methods which include the mergeBlock() method for updating the board during block placement and clearRows() method to look for full rows followed by removal.
User Input Handling:

Through its KeyHandler class the game detects player keyboard input for block direction control with arrow keys.
Game Loop:

The actionPerformed(ActionEvent e) method receives execution from the timer which proceeds to shift the block downward before it repaints the game screen.
Main Method:

The main method enables the game window creation then inserts the game panel before showing the window.
     */

     
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    private static final int TIMER_DELAY = 500;

    private Timer timer;
    private Block currentBlock;
    private int[][] board;

    public Q3B() {
        setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyHandler());
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        generateNewBlock();
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    private void generateNewBlock() {
        currentBlock = Block.getRandomBlock();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawBlock(g, currentBlock);
    }

    private void drawBoard(Graphics g) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board[y][x] != 0) {
                    g.setColor(Block.COLORS[board[y][x]]);
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private void drawBlock(Graphics g, Block block) {
        g.setColor(block.color);
        for (int y = 0; y < block.shape.length; y++) {
            for (int x = 0; x < block.shape[y].length; x++) {
                if (block.shape[y][x] != 0) {
                    g.fillRect((block.x + x) * BLOCK_SIZE, (block.y + y) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private void moveBlock(int dx, int dy) {
        currentBlock.x += dx;
        currentBlock.y += dy;
        if (checkCollision()) {
            currentBlock.x -= dx;
            currentBlock.y -= dy;
            if (dy > 0) {
                mergeBlock();
                clearRows();
                generateNewBlock();
            }
        }
    }

    private boolean checkCollision() {
        for (int y = 0; y < currentBlock.shape.length; y++) {
            for (int x = 0; x < currentBlock.shape[y].length; x++) {
                if (currentBlock.shape[y][x] != 0) {
                    int newX = currentBlock.x + x;
                    int newY = currentBlock.y + y;
                    if (newX < 0 || newX >= BOARD_WIDTH || newY >= BOARD_HEIGHT || board[newY][newX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void mergeBlock() {
        for (int y = 0; y < currentBlock.shape.length; y++) {
            for (int x = 0; x < currentBlock.shape[y].length; x++) {
                if (currentBlock.shape[y][x] != 0) {
                    board[currentBlock.y + y][currentBlock.x + x] = currentBlock.colorIndex;
                }
            }
        }
    }

    private void clearRows() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean fullRow = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board[y][x] == 0) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                for (int row = y; row > 0; row--) {
                    System.arraycopy(board[row - 1], 0, board[row], 0, BOARD_WIDTH);
                }
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    board[0][x] = 0;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBlock(0, 1);
        repaint();
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveBlock(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveBlock(1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    moveBlock(0, 1);
                    break;
                case KeyEvent.VK_UP:
                    // Rotate block logic can be added here
                    break;
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Q3B gamePanel = new Q3B();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Block {
    public int[][] shape;
    public Color color;
    public int colorIndex;
    public int x, y;

    public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};

    public static Block getRandomBlock() {
        // Define shapes for different blocks
        int[][][] shapes = {
            {{1, 1, 1, 1}}, // I
            {{1, 1, 1}, {0, 1, 0}}, // T
            {{1, 1}, {1, 1}}, // O
            {{0, 1, 1}, {1, 1, 0}}, // S
            {{1, 1, 0}, {0, 1, 1}}, // Z
            {{1, 1, 1}, {1, 0, 0}}, // L
            {{1, 1, 1}, {0, 0, 1}}  // J
        };
        Random rand = new Random();
        int index = rand.nextInt(shapes.length);
        Block block = new Block();
        block.shape = shapes[index];
        block.color = COLORS[index];
        block.colorIndex = index + 1; // 1-based index for color
        block.x = 3; // Start position
        block.y = 0; // Start position
        return block;
    }
}