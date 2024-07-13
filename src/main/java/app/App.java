package app;

import java.io.IOException;
import javax.swing.JFrame;

public class App {

	public static void main(String[] args) throws IOException {

		System.out.println("Lucene Interface!");
		
		Indexer index = new Indexer();	
		GUI indexGUI = new GUI(index);
		indexGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		indexGUI.setSize(500, 600);
		indexGUI.setVisible(true);
	}
}

