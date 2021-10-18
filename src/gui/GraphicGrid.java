package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minesweeper.Cell;
import minesweeper.Grid;
import tools.Tools;

class GraphicGrid extends JPanel implements Observer {

	private static final long serialVersionUID = 8172103856055699660L;
	private Settings settings;
	private GraphicCell lastClicked;
	private Grid grid;
	
	public GraphicGrid(minesweeper.Grid grid, Settings settings) {
		this.settings = settings;
		setGrid(grid);
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
		lastClicked = null;
		grid.addObserver(this);
		
		removeAll();
		setLayout(new GridLayout(grid.getLine(), grid.getColumn()));
		for (int lin = 0; lin < grid.getLine(); ++lin) {
			for (int col = 0; col < grid.getColumn(); ++col) {
				add(new GraphicCell(lin, col, grid.getCell(lin, col)));
			}
		}
		
		validate();
	}
	
	@Override
	public void update(Observable obs, Object win) {
		for (Component c : getComponents()) {
			((GraphicCell) c).update(null, null);
		}
		
		int choice;
		Object[] options = {"Nouvelle Partie", "Quitter"};
		if ((boolean)win) {
			choice = JOptionPane.showOptionDialog(this,
				"Félicitation, vous avez gagnez !",
				"Victoire !",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				null
			);
		}
		else {
			choice = JOptionPane.showOptionDialog(this,
				"Boum ! Perdu ...\nN'hésitez pas à retentez votre chance :)",
				"Défaite",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				null
			);
		}
		
		if (choice == 0) {
			setGrid(new Grid(grid.getLine(), grid.getColumn(), grid.getMine()));
		}
		else if (choice == 1) {
			System.exit(0);
		}
	}
	
	private void revealAdj(int line, int column) {
		Grid grid = this.grid;
		int nbMineWithFlags = 0;
		for (int dl = -1; dl < 2; ++dl) {
			for (int dc = -1; dc < 2; ++dc) {
				if (Tools.isBetween(line + dl, 0, grid.getLine() - 1) && 
						Tools.isBetween(column + dc, 0, grid.getColumn() - 1) &&
						grid.getCell(line + dl, column + dc).getState() ==
							Cell.State.FLAG) {
					++nbMineWithFlags;
				}
			}
		}
		if (nbMineWithFlags == grid.getCell(line, column).getValue()) {
			for (int dl = -1; dl < 2; ++dl) {
				for (int dc = -1; dc < 2; ++dc) {
					if (grid == this.grid && ! grid.isFinish()) {
						grid.reveal(line + dl, column + dc);;
					}
				}
			}
		}
	}
	
	
	private class GraphicCell extends JButton implements Observer, MouseListener {
		
		private static final long serialVersionUID = -4863257463242576990L;
		private int line, column;
		private Image img;
		private Cell cell;
		
		public GraphicCell(int line, int column, Cell cell) {
			this.line = line;
			this.column = column;
			this.cell = cell;
			
			img = settings.getTheme().cellState[Cell.State.UNMARKED];
			
			cell.addObserver(this);
			addMouseListener(this);
		}

		@Override
		public void update(Observable obs, Object arg) {
			if (obs == cell) {
				if (cell.isMine()) {
					img = settings.getTheme().cellMineBoom;
				}
				else {
					img = settings.getTheme().cellNumbers[cell.getValue()];
				}
			}
			else if (cell.isMine()) {
				if (isEnabled() && cell.getState() != Cell.State.FLAG) {
					img = settings.getTheme().cellMine;
				}
			}
			else if (cell.getState() == Cell.State.FLAG) {
				img = settings.getTheme().cellFlagError;
			}
			setEnabled(false);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {
			if (isEnabled()) {
				img = settings.getTheme().cellState[cell.getState()];
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (isEnabled() && cell.getState() != Cell.State.FLAG) {
				img = settings.getTheme().cellDown;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (isEnabled()) {
				if (Tools.isBetween(e.getY(), 0, getHeight()) &&
						Tools.isBetween(e.getX(), 0, getWidth())) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						grid.reveal(line, column);
						lastClicked = this;
					}
					else {
						if (e.getButton() == MouseEvent.BUTTON3) {
							cell.setState((byte)((cell.getState() + 1) % 3));
						}
						img = settings.getTheme().cellState[cell.getState()];
					}
				}
				else {
					img = settings.getTheme().cellState[cell.getState()];
				}
				repaint();
			}
			else if (Tools.isBetween(e.getY(), 0, getHeight()) &&
					Tools.isBetween(e.getX(), 0, getWidth())) {
				if (lastClicked == this) {
					revealAdj(line, column);
				}
				else {
					lastClicked = this;
				}
			}
		}
	}
}
