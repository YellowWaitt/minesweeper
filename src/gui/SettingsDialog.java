package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import minesweeper.Difficulty;

class SettingsDialog extends JDialog implements WindowListener{

	private static final long serialVersionUID = 8281321276860893898L;
	private Settings settings;
	private boolean update;
	private CancelAction cancelAction = new CancelAction();
	
	private JRadioButton[] diffRadioButton = new JRadioButton[3];

	public SettingsDialog(Settings settings, JFrame parent, String title,
			boolean modal) {
		super(parent, title, modal);
		this.settings = settings;
		
		setSize(150, 290);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		initComponents();
		addWindowListener(this);
	}
	
	private void initComponents() {
		JPanel panDifficulty = new JPanel();
		panDifficulty.setPreferredSize(new Dimension(100, 210));
		panDifficulty.setBorder(BorderFactory.createTitledBorder("Difficulté"));
		
		String[] texts = {"Facile", "Moyen", "Difficile"};
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < texts.length; ++i) {
			Difficulty diff = Difficulty.values()[i];
			diffRadioButton[i] = new JRadioButton(
					String.format("<html>%s<br>%d mines<br>grille %dx%d</html>",
							texts[i], diff.getMine(), diff.getLine(),
							diff.getColumn())
					);
			diffRadioButton[i].setSelected(diff == settings.getDifficulty());
			diffRadioButton[i].setFocusPainted(false);
			bg.add(diffRadioButton[i]);
			panDifficulty.add(diffRadioButton[i]);
		}
		
		
		JPanel content = new JPanel();
		content.add(panDifficulty);
		
		
		JPanel control = new JPanel();
		
		JButton ok = new JButton("Ok");
		ok.setFocusPainted(false);
		ok.addActionListener(new OkAction());
		JButton cancel = new JButton("Cancel");
		cancel.setFocusPainted(false);
		cancel.addActionListener(cancelAction);
		
		control.add(ok);
		control.add(cancel);
		
		
		getContentPane().add(content, BorderLayout.NORTH);
		getContentPane().add(control, BorderLayout.SOUTH);
	}
	
	public boolean startDialog() {
		update = false;
		setLocationRelativeTo(getParent());
		setVisible(true);
		return update;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		cancelAction.actionPerformed(null);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}


	private class OkAction implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			update = true;
			settings.setDifficulty(getDifficulty());
			setVisible(false);
		}
		
		private Difficulty getDifficulty() {
			return diffRadioButton[0].isSelected() ? Difficulty.EASY :
				diffRadioButton[1].isSelected() ? Difficulty.MEDIUM :
				Difficulty.HARD;
		}
	}


	private class CancelAction implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			diffRadioButton[getInd()].setSelected(true);
			setVisible(false);
		}
		
		private int getInd() {
			Difficulty[] diffs = Difficulty.values(); 
			int i;
			for (i = 0; i < diffs.length && diffs[i] != settings.getDifficulty();
					++i);
			return i;
		}
	}
}
