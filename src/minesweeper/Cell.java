package minesweeper;

import java.util.Observable;

public class Cell extends Observable {

	static private final char cHidden = '#';
//	static private final char cFlag = 'P';
//	static private final char cQuestion = '?';
//	static private final char cMineBoom = '¤';
	static private final char cMine = 'x';
	static private final char cEmpty = ' ';
	
	private byte flags;
	
	public Cell() {
		flags = 0;
	}

	@Override
	public String toString() {
		return (
			isReveal() ?
				(isMine() ? String.valueOf(cMine) :
					(getValue() == 0 ? String.valueOf(cEmpty) :
						String.valueOf(getValue())
					)
				)
			: String.valueOf(cHidden)
		);
	}
	
	public void incrValue() {
		++flags;
	}

	public byte getValue() {
		return (byte)(flags & 0b0000_0111);
	}
	
	public void setMine() {
		flags |= 1 << 7;
	}

	public boolean isMine() {
		return (flags & 1 << 7) != 0;
	}

	public boolean canBeReveal() {
		return getState() != State.FLAG && ! isReveal();
	}
	
	public void reveal() {
		flags |= 1 << 6;
		setChanged();
		notifyObservers();
	}

	public boolean isReveal() {
		return (flags & 1 << 6) != 0;
	}
	
	public void setState(byte state) {
		flags = (byte)((flags & 0b1100_0111) | (state << 3));
	}
	
	public byte getState() {
		return (byte)((flags & 0b0011_1000) >> 3);
	}
	
	
	public final class State {
		public static final byte UNMARKED = 0;
		public static final byte FLAG = 1;
		public static final byte QUESTION = 2;
//		public static final byte BOOM = 3;
		
//		char[] symbol = {'#', 'P', '?', '¤'};
		
		private State() {}
	}
}
