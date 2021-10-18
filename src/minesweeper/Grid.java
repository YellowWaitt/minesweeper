package minesweeper;

import java.util.Observable;
import java.util.Random;

import tools.Tools;

public class Grid extends Observable {

	private int line;
	private int column;
	private int mine;
	private Cell cells[][];
	private int cell;
	private int cellReveal;
	private boolean needGenerate;
	private boolean finish;
	
	public Grid() {
		this(Difficulty.EASY);
	}

	public Grid(Difficulty diff) {
		this(diff.getLine(), diff.getColumn(), diff.getMine());
	}
	
	public Grid(int line, int column, int mine) {
		this.line = line;
		this.column = column;
		this.mine = mine;
		
		cells = new Cell[line][column];
		cell = column * line;
		needGenerate = true;
		finish = false;
		
		for (int lin = 0; lin < line; ++lin) {
			for (int col = 0; col < column; ++col) {
				cells[lin][col] = new Cell();
			}
		}
	}

	@Override
	public String toString() {
		String ret = new String();
		for (Cell line[] : cells) {
			for (Cell c : line) {
				ret += c.toString();
			}
			ret += "\n";
		}
		return ret;
	}

	private void generateGrid(int lin, int col) {
		Random r = new Random();
		int arr[] = new int[cell];
		int nCase, nLine, nColumn, toSwap;
		
		for (int i = 0; i < cell; ++i) {
			arr[i] = i;
		}
		
		Tools.swap(arr, lin * column + col, arr.length - 1);
		
		for (int i = 0; i < mine; ++i) {
			toSwap = r.nextInt(cell - 1 - i);
			nCase = arr[toSwap];
			
			nLine = nCase / column;
			nColumn = nCase % column;
			
			cells[nLine][nColumn].setMine();
			incrAdjCells(nLine, nColumn);
			
			Tools.swap(arr, toSwap, arr.length - 2 - i);
		}
		
	}
	
	private void incrAdjCells(int lin, int col) {
		for (int dl = -1; dl < 2; ++dl) {
			for (int dc = -1; dc < 2; ++dc) {
				if (Tools.isBetween(lin + dl, 0, line - 1) && 
						Tools.isBetween(col + dc, 0, column - 1)) {
					cells[lin + dl][col + dc].incrValue();
				}
			}
		}
	}
	
	public void reveal(int lin, int col) {
		if (! finish && Tools.isBetween(lin, 0, line - 1) &&
				Tools.isBetween(col, 0, column - 1)) {
			if (needGenerate) {
				generateGrid(lin, col);
				needGenerate = false;
			}
			
			revealRec(lin, col);
			Cell c = cells[lin][col];
			if (c.isReveal() && (c.isMine() || cell - cellReveal == mine)) {
//				revealMine();
				finish = true;
				setChanged();
				notifyObservers(! c.isMine());
			}
		}
	}
	
	private void revealRec(int lin, int col) {
		if (Tools.isBetween(lin, 0, line - 1) &&
				Tools.isBetween(col, 0, column - 1) ) {
			Cell c = cells[lin][col];
			if (c.canBeReveal()) {
				++cellReveal;
				c.reveal();
				if (c.getValue() == 0) {
					revealRec(lin - 1, col - 1);
					revealRec(lin - 1, col);
					revealRec(lin - 1, col + 1);
					revealRec(lin, col - 1);
					revealRec(lin, col + 1);
					revealRec(lin + 1, col - 1);
					revealRec(lin + 1, col);
					revealRec(lin + 1, col + 1);
				}
			}
		}
	}

//	private void revealMine() {
//		for (Cell lin[] : cells) {
//			for (Cell c : lin) {
//				if (c.isMine()) {
//					c.reveal();
//				}
//			}
//		}
//	}

//	public String getArray() {
//		String ret = new String();
//		for (Cell lin[] : cells) {
//			for (Cell c : lin) {
//				ret += c.isMine() ? " " + c.toString() + " " :
//					String.format("%2d ", c.getValue());
//			}
//			ret += "\n";
//		}
//		return ret;
//	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public int getMine() {
		return mine;
	}

	public int getCell() {
		return cell;
	}

	public boolean isFinish() {
		return finish;
	}
	
	public Cell getCell(int lin, int col) {
		return cells[lin][col];
	}
}
