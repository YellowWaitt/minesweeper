package minesweeper;

public final class Difficulty {

	public static final Difficulty EASY = new Difficulty(9, 9, 10);
	public static final Difficulty MEDIUM = new Difficulty(16, 16, 40);
	public static final Difficulty HARD = new Difficulty(16, 30, 99);
	private static final Difficulty[] values = {EASY, MEDIUM, HARD};

	private int line;
	private int column;
	private int mine;
	
	public Difficulty(int line, int column, int mine) {
		this.line = line;
		this.column = column;
		this.mine = mine;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public int getMine() {
		return mine;
	}
	
	public static Difficulty[] values() {
		return values;
	}
}
