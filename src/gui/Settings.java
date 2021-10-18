package gui;

import minesweeper.Difficulty;

class Settings {
	
	private Difficulty difficulty;
	private ThemeManager theme;

	public Settings() {
		difficulty = Difficulty.EASY;
		theme = new ThemeManager("haiku");
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public ThemeManager getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme.setTheme(theme);;
	}
}