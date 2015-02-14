package pt314.blocks.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import java.text.ParseException;

/**
 * JUnit tests for <code>GameBoard</code>.
 */
public class GameBoardTest {
    
    private GameBoard board;
    private static String board_1 = "5 7\n" +
        "..HV...\n" +
        "..VHHHH\n" +
        "..TVVVV\n" +
        "VHVHHHH\n" +
        "..V....";

    @Before
    public void setUp() throws ParseException {
        board = new GameBoard();
        board.load(new Scanner(board_1));
    }

    @Test
    public void testBoardSize() {
        assertTrue(board.height == 5);
        assertTrue(board.width == 7);
    }

    @Test
    public void testHorizontalMove() {
        int y = 0;
        int x = 2;

        assertTrue (board.moveBlock(y, x,   Direction.LEFT,  1));
        assertFalse(board.moveBlock(y, x,   Direction.LEFT,  1));
        assertTrue (board.moveBlock(y, x-1, Direction.RIGHT, 1));
        assertFalse(board.moveBlock(y, x-1, Direction.RIGHT, 1));

        assertTrue (board.moveBlock(y, x,   Direction.LEFT,  2));
        assertFalse(board.moveBlock(y, x,   Direction.LEFT,  2));
        assertTrue (board.moveBlock(y, x-2, Direction.RIGHT, 2));
        assertFalse(board.moveBlock(y, x-2, Direction.RIGHT, 2));
    }

    @Test
    public void testVerticalMove() {
        int y = 3;
        int x = 0;

        assertTrue (board.moveBlock(y,    x, Direction.UP,   1));
        assertFalse(board.moveBlock(y,    x, Direction.UP,   1));
        assertTrue (board.moveBlock(y-1,  x, Direction.DOWN, 1));
        assertFalse(board.moveBlock(y-1,  x, Direction.DOWN, 1));

        assertTrue (board.moveBlock(y,    x, Direction.UP,   2));
        assertFalse(board.moveBlock(y,    x, Direction.UP,   2));
        assertTrue (board.moveBlock(y-2,  x, Direction.DOWN, 2));
        assertFalse(board.moveBlock(y-2,  x, Direction.DOWN, 2));
    }

    @Test
    public void testInvalidMoveDirection() {
        assertFalse(board.moveBlock(3, 1, Direction.UP,    1));
        assertFalse(board.moveBlock(3, 1, Direction.DOWN,  1));

        assertFalse(board.moveBlock(4, 2, Direction.LEFT,  1));
        assertFalse(board.moveBlock(4, 2, Direction.RIGHT, 1));
    }

    @Test
    public void testOutOfBoundsMove() {
        assertFalse(board.moveBlock(4, 2, Direction.DOWN,  1));
        assertFalse(board.moveBlock(0, 3, Direction.UP,    1));
        assertFalse(board.moveBlock(0, 2, Direction.LEFT,  3));
        assertFalse(board.moveBlock(1, 6, Direction.RIGHT, 1));
    }

    @Test
    public void testBlockedPath() {
        assertFalse(board.moveBlock(2, 3, Direction.UP,    1));
        assertFalse(board.moveBlock(2, 3, Direction.DOWN,  1));
        assertFalse(board.moveBlock(2, 3, Direction.UP,    2));
        assertFalse(board.moveBlock(2, 3, Direction.DOWN,  2));

        assertFalse(board.moveBlock(1, 3, Direction.LEFT,  1));
        assertFalse(board.moveBlock(1, 3, Direction.RIGHT, 1));
    }
}
