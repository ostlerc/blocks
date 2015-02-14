package pt314.blocks.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;

import java.text.ParseException;

import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pt314.blocks.game.Block;
import pt314.blocks.game.Direction;
import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;

/**
 * Simple GUI test...
 */
public class SimpleGUI extends JFrame implements ActionListener {

    private GameBoard board;

    // currently selected block
    private Block selectedBlock;
    private int selectedBlockRow;
    private int selectedBlockCol;

    private GridButton[][] buttonGrid;

    private JMenuBar menuBar;
    private JMenu gameMenu, helpMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem aboutMenuItem;

    public SimpleGUI() {
        super("Blocks");

        initMenus();

        initBoard();

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initMenus() {
        menuBar = new JMenuBar();

        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        newGameMenuItem = new JMenuItem("New game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                initBoard();
            }
        });
        gameMenu.add(newGameMenuItem);

        gameMenu.addSeparator();

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameMenu.add(exitMenuItem);

        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SimpleGUI.this, "Sliding blocks!");
            }
        });
        helpMenu.add(aboutMenuItem);

        setJMenuBar(menuBar);
    }

    private void initBoard() {
        loadBoard();

        buttonGrid = new GridButton[board.height][board.width];
        setLayout(new GridLayout(board.height, board.width));
        for (int row = 0; row < board.height; row++) {
            for (int col = 0; col < board.width; col++) {
                Block block = board.getBlockAt(row, col);
                GridButton cell = new GridButton(row, col, block);
                cell.setPreferredSize(new Dimension(64, 64));
                cell.addActionListener(this);
                cell.setOpaque(true);
                buttonGrid[row][col] = cell;
                add(cell);
            }
        }
        updateUI();
        pack();
    }

    private void loadBoard() {
        try {
            // Load board from file
            board = new GameBoard();
            board.load(new Scanner(new File("res/puzzles/my-puzzle.txt")));
        } catch(FileNotFoundException e) {
            System.out.format("File not found %s%n", e.getMessage());
        } catch(ParseException e) {
            System.out.format("Error: %s at offset %d%n", e.getMessage(), e.getErrorOffset());
        }
    }

    // Update display based on the state of the board...
    // TODO: make this more efficient
    private void updateUI() {
        for (int row = 0; row < board.height; row++) {
            for (int col = 0; col < board.width; col++) {
                Block block = board.getBlockAt(row, col);
                GridButton cell = buttonGrid[row][col];
                cell.update(block);
            }
        }
    }

    /**
     * Handle board clicks.
     *
     * Movement is done by first selecting a block, and then
     * selecting the destination.
     *
     * Whenever a block is clicked, it is selected, even if
     * another block was selected before.
     *
     * When an empty cell is clicked after a block is selected,
     * the block is moved if the move is valid.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle grid button clicks...
        GridButton cell = (GridButton) e.getSource();
        int row = cell.getRow();
        int col = cell.getCol();
        System.out.println("Clicked (" + row + ", " + col + ")");

        if (selectedBlock == null || board.getBlockAt(row, col) != null) {
            selectBlockAt(row, col);
        }
        else {
            moveSelectedBlockTo(row, col);
        }
    }

    /**
     * Select block at a specific location.
     *
     * If there is no block at the specified location,
     * the previously selected block remains selected.
     *
     * If there is a block at the specified location,
     * the previous selection is replaced.
     */
    private void selectBlockAt(int row, int col) {
        Block block = board.getBlockAt(row, col);
        if (block != null) {
            selectedBlock = block;
            selectedBlockRow = row;
            selectedBlockCol = col;
        }
    }

    /**
     * Try to move the currently selected block to a specific location.
     *
     * If the move is not possible, nothing happens.
     */
    private void moveSelectedBlockTo(int row, int col) {

        int vertDist = row - selectedBlockRow;
        int horzDist = col - selectedBlockCol;

        if (vertDist != 0 && horzDist != 0) {
            System.err.println("Invalid move!");
            return;
        }

        Direction dir = getMoveDirection(selectedBlockRow, selectedBlockCol, row, col);
        int dist = Math.abs(vertDist + horzDist);

        if (!board.moveBlock(selectedBlockRow, selectedBlockCol, dir, dist)) {
            System.err.println("Invalid move!");
        }
        else {
            if (col == board.width-1 && (selectedBlock instanceof TargetBlock)) {
                JOptionPane.showMessageDialog(SimpleGUI.this, "Winner winner chicken dinner");
            }

            selectedBlock = null;
            updateUI();
        }
    }

    /**
     * Determines the direction of a move based on
     * the starting location and the destination.
     *
     * @return <code>null</code> if both the horizontal distance
     *         and the vertical distance are not zero.
     */
    private Direction getMoveDirection(int startRow, int startCol, int destRow, int destCol) {
        int vertDist = destRow - startRow;
        int horzDist = destCol - startCol;
        if (vertDist < 0)
            return Direction.UP;
        if (vertDist > 0)
            return Direction.DOWN;
        if (horzDist < 0)
            return Direction.LEFT;
        if (horzDist > 0)
            return Direction.RIGHT;
        return null;
    }
}
