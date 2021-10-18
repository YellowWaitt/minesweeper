package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import minesweeper.Grid;

public class GUI extends JFrame {

	private static final long serialVersionUID = 7446192599263749847L;
	private Settings settings;
	private SettingsDialog settingsDialog;
	private GraphicGrid grid;
	
	public GUI() {
		setTitle("Démineur");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initIcon();
		initSettings();
		initMenuBar();
		initGrid();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void initIcon() {
		try {
			Image icon = ImageIO.read(new File("pictures/minesweeper-icon.png"));
			setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initSettings() {
		settings = new Settings();
		settingsDialog = new SettingsDialog(settings, this, "Options", true);
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuGame = new JMenu("Partie"),
				menuAbout = new JMenu("A Propos");
		JMenuItem newGame = new JMenuItem("Nouvelle Partie"),
				options = new JMenuItem("Options"),
				quit = new JMenuItem("Quitter"),
				questionMark = new JMenuItem("?");
		
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.setGrid(new Grid(settings.getDifficulty()));
			}
		});
		
		options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.CTRL_MASK));
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean update = settingsDialog.startDialog();
				if (update) {
					grid.setGrid(new Grid(settings.getDifficulty()));
				}
			}
		});
		
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_MASK));
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		questionMark.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				KeyEvent.CTRL_MASK));
		questionMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(grid,
					"Mon super jeu du démineur !\nCool non ?",
					"A Propos",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
		});
		
		menuGame.add(newGame);
		menuGame.add(options);
		menuGame.addSeparator();
		menuGame.add(quit);
		
		menuAbout.add(questionMark);
		
		menuBar.add(menuGame);
		menuBar.add(menuAbout);
		
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
	}

	private void initGrid() {
		grid = new GraphicGrid(new Grid(), settings);
		add(grid);
	}
}
