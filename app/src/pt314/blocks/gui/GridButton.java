package pt314.blocks.gui;

import pt314.blocks.game.Block;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;

import java.awt.Color;

import javax.swing.JButton;

public class GridButton extends JButton {

	private int row;
	private int col;

	public GridButton(int row, int col, Block block) {
		super(block == null ? "" : block.name());
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public void update(Block block) {
	    setText(block == null ? "" : block.name());
        if (block == null)
            setBackground(Color.LIGHT_GRAY);
        else if (block instanceof TargetBlock)
            setBackground(Color.BLACK);
        else if (block instanceof HorizontalBlock)
            setBackground(Color.BLUE);
        else if (block instanceof VerticalBlock)
            setBackground(Color.RED);
    }
}
