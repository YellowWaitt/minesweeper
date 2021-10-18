package main;

import javax.swing.SwingUtilities;

import gui.GUI;

public class Main {

	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		Grid g = new Grid(5, 5, 5);
//		int lin, col;
//		
//		while (! g.isFinish()) {
//			System.out.println(g.getArray());
//			System.out.println(g.toString());
//			
//			System.out.println("Choisir une case : ");
//			lin = sc.nextInt();
//			col = sc.nextInt();
//			
//			g.reveal(lin, col);
//		}
//		
//		System.out.println(g.getArray());
//		System.out.println(g.toString());
//		
//		sc.close();
		
		SwingUtilities.invokeLater(GUI::new);
	}

}
