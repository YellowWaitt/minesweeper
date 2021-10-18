package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class ThemeManager {

	private String theme = null;
	
	public Image cellNumbers[] = new Image[9];
	public Image cellState[] = new Image[3];
	public Image cellMine, cellMineBoom, cellFlagError, cellDown;
	
	public ThemeManager(String theme) {
		setTheme(theme);
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
		for (int i = 0; i < 9; ++i) {
			cellNumbers[i] = getImage("mine" + i);
		}
		cellState[0] = getImage("unmarked");
		cellState[1] = getImage("flag");
		cellState[2] = getImage("question");
		cellDown = getImage("down");
		cellMine = getImage("mine");
		cellMineBoom = getImage("hit");
		cellFlagError = getImage("notmine");
	}
	
	public String getTheme() {
		return theme;
	}
	
	private Image getImage(String name) {
		Image img = null;
		try {
			img = ImageIO.read(new File("pictures/" +theme +"/" +name +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
