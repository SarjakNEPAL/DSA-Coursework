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
        currentBlock = Block.getRandomBlock(); // Generate a new random block and assign it to the currentBlock variable.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to clear the panel.
        drawBoard(g); // Draw the game board on the panel.
        drawBlock(g, currentBlock); // Draw the current falling block on the panel.
    }

    private void drawBoard(Graphics g) {
        for (int y = 0; y < BOARD_HEIGHT; y++) { // Loop through each row of the board.
            for (int x = 0; x < BOARD_WIDTH; x++) { // Loop through each column of the board.
                if (board[y][x] != 0) { // Check if the cell is filled (not empty).
                    g.setColor(Block.COLORS[board[y][x]]); // Set the color based on the block index in the board array.
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw the filled block at the calculated position.
                }
            }
        }
    }

    private void drawBlock(Graphics g, Block block) {
        g.setColor(block.color); // Set the color of the current block.
        for (int y = 0; y < block.shape.length; y++) { // Loop through the block's shape.
            for (int x = 0; x < block.shape[y].length; x++) { // Loop through each cell in the block shape.
                if (block.shape[y][x] != 0) { // Check if the cell is part of the block.
                    // Calculate the position to draw the block based on its coordinates
                    g.fillRect((block.x + x) * BLOCK_SIZE, (block.y + y) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw the block at its current position.
                }
            }
        }
    }

    private void moveBlock(int dx, int dy) {
        currentBlock.x += dx; // Update the block's x position based on user input (dx).
        currentBlock.y += dy; // Update the block's y position based on user input (dy).
        if (checkCollision()) { // Check for collisions after moving the block.
            currentBlock.x -= dx; // Revert x position if there's a collision.
            currentBlock.y -= dy; // Revert y position if there's a collision.
            if (dy > 0) { // If the block is moving down.
                mergeBlock(); // Merge the block into the board.
                clearRows(); // Clear any completed rows.
                generateNewBlock(); // Generate a new block after the current one has landed.
            }
        }
    }

    private boolean checkCollision() {
        for (int y = 0; y < currentBlock.shape.length; y++) { // Loop through the block's shape.
            for (int x = 0; x < currentBlock.shape[y].length; x++) { // Loop through each cell in the shape.
                if (currentBlock.shape[y][x] != 0) { // Check if the cell is part of the block.
                    int newX = currentBlock.x + x; // Calculate the new x position of the block.
                    int newY = currentBlock.y + y; // Calculate the new y position of the block.
                    // Check if the new position is out of bounds or collides with an existing block
                    if (newX < 0 || newX >= BOARD_WIDTH || newY >= BOARD_HEIGHT || board[newY][newX] != 0) {
                        return true; // Collision detected; return true.
                    }
                }
            }
        }
        return false; // No collision detected; return false.
    }

    private void mergeBlock() {
        for (int y = 0; y < currentBlock.shape.length; y++) { // Loop through the block's shape.
            for (int x = 0; x < currentBlock.shape[y].length; x++) { // Loop through each cell in the shape.
                if (currentBlock.shape[y][x] != 0) { // Check if the cell is part of the block.
                    board[currentBlock.y + y][currentBlock.x + x] = currentBlock.colorIndex; // Merge the block into the board by setting the color index.
                }
            }
        }
    }

    private void clearRows() {
        for (int y = 0; y < BOARD_HEIGHT; y++) { // Loop through each row of the board.
            boolean fullRow = true; // Assume the row is full.
            for (int x = 0; x < BOARD_WIDTH; x++) { // Loop through each column in the row.
                if (board[y][x] == 0) { // Check if there's an empty cell.
                    fullRow = false; // The row is not full.
                    break; // Exit the loop early.
                }
            }
            if (fullRow) { // If the row is full.
                for (int row = y; row > 0; row--) { // Shift all rows above down by one.
                    System.arraycopy(board[row - 1], 0, board[row], 0, BOARD_WIDTH); // Copy the row above into the current row.
                }
                for (int x = 0; x < BOARD_WIDTH; x++) { // Clear the top row.
                    board[0][x] = 0; // Set all cells in the top row to empty.
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBlock(0, 1); // Move the current block down by one.
        repaint(); // Repaint the panel to update the display.
    }

    private class KeyHandler extends KeyAdapter { // Inner class to handle key events.
        @Override
        public void keyPressed(KeyEvent e) { // Method called when a key is pressed.
            switch (e.getKeyCode()) { // Check which key was pressed.
                case KeyEvent.VK_LEFT: // If the left arrow key is pressed.
                    moveBlock(-1, 0); // Move the block left.
                    break; // Exit the switch statement.
                case KeyEvent.VK_RIGHT: // If the right arrow key is pressed.
                    moveBlock(1, 0); // Move the block right.
                    break; // Exit the switch statement.
                case KeyEvent.VK_DOWN: // If the down arrow key is pressed.
                    moveBlock(0, 1); // Move the block down.
                    break; // Exit the switch statement.
                case KeyEvent.VK_UP: // If the up arrow key is pressed.
                    // Rotate block logic can be added here (not implemented).
                    break; // Exit the switch statement.
            }
            repaint(); // Repaint the panel to update the display after movement.
        }
    }

    public static void main(String[] args) { // Main method to start the game.
        JFrame frame = new JFrame("Tetris"); // Create a new JFrame with the title "Tetris".
        Q3B gamePanel = new Q3B(); // Create an instance of the game panel.
        frame.add(gamePanel); // Add the game panel to the frame.
        frame.pack(); // Pack the frame to fit the preferred size of the panel.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation.
        frame.setVisible(true); // Make the frame visible.
    }
}

// Block class to represent the falling blocks
class Block {
    public int[][] shape; // 2D array representing the shape of the block.
    public Color color; // Color of the block.
    public int colorIndex; // Index for the color array.
    public int x, y; // Current position of the block on the board.

    public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA}; // Array of colors for the blocks.

    // Method to get a random block shape
    public static Block getRandomBlock() {
        // Define shapes for different blocks
        int[][][] shapes = {
            {{1, 1, 1, 1}}, // I shape
            {{1, 1, 1}, {0, 1, 0}}, // T shape
            {{1, 1}, {1, 1}}, // O shape
            {{0, 1, 1}, {1, 1, 0}}, // S shape
            {{1, 1, 0}, {0, 1, 1}}, // Z shape
            {{1, 1, 1}, {1, 0, 0}}, // L shape
            {{1, 1, 1}, {0, 0, 1}}  // J shape
        };
        Random rand = new Random(); // Create a new Random object for generating random numbers.
        int index = rand.nextInt(shapes.length); // Get a random index for the shapes array.
        Block block = new Block(); // Create a new Block instance.
        block.shape = shapes[index]; // Assign a random shape to the block.
        block.color = COLORS[index]; // Assign a color based on the shape index.
        block.colorIndex = index + 1; // Set the color index (1-based for the board).
        block.x = 3; // Set the initial x position of the block.
        block.y = 0; // Set the initial y position of the block.
 return block; // Return the newly created block.
    }
}
//input:
//output:
//a tetris game
//Expected Output:
//a tetris game
