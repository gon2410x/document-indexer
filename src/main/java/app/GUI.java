package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import java.util.List;

public class GUI extends JFrame 
{
	private final JTextField textFieldExamine;
	private final JTextField textFieldSearch;
	
	private final JButton buttonExamine;
	private final JButton buttonSearch;
	private final JButton buttonIndex;
	
	private final JTextArea textAreaResult;
	
	private final JList<String> fileTypesJList;
	private static final String[] fileTypesNames = {".txt", ".pdf" };
	private static final Color[] colors = {Color.CYAN, Color.RED};
	
	private final JLabel label;

	private final GridBagLayout layout; // layout of this frame
	private final GridBagConstraints constraints; // layout's constraints
	
	private Indexer indexer;
	
	public GUI(Indexer indexer) {
		super("Final Proyect Information Retrieval");	
		this.indexer = indexer;
		
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		textFieldExamine = new JTextField(5);
		constraints.weightx = 100;
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(textFieldExamine, 0, 0, 2, 1);

		buttonExamine = new JButton("Examine");
		constraints.weightx = 50;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(buttonExamine, 0, 2, 1, 1);

		ButtonHandlerExamine handler2 = new ButtonHandlerExamine();
		buttonExamine.addActionListener(handler2);	
		
		fileTypesJList = new JList<String>(fileTypesNames); // list of file type
		fileTypesJList.setVisibleRowCount(5);	
		fileTypesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		constraints.weightx = 25;
		addComponent(new JScrollPane(fileTypesJList), 1, 0, 1, 1);
		
		fileTypesJList.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				getContentPane().setBackground(colors[fileTypesJList.getSelectedIndex()]);
				System.out.println(fileTypesNames[fileTypesJList.getSelectedIndex()]);
				indexer.changeFileType(fileTypesNames[fileTypesJList.getSelectedIndex()]);
			}
		});
		
		buttonIndex = new JButton("Index");
		constraints.weightx = 50;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(buttonIndex, 1, 2, 1, 1);
		
		ButtonHandlerIndex handlerIndex = new ButtonHandlerIndex();
		buttonIndex.addActionListener(handlerIndex);
			
		textFieldSearch = new JTextField();
		constraints.weightx = 100;
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(textFieldSearch, 3, 1, 1, 1);
		
		buttonSearch = new JButton("Search");
		constraints.weightx = 50;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(buttonSearch, 3, 2, 1, 1);
		
		ButtonHandlerSearch handler3 = new ButtonHandlerSearch();
		buttonSearch.addActionListener(handler3);
				
		textAreaResult = new JTextArea("", 10, 15);
		addComponent(new JScrollPane(textAreaResult), 4, 1, 1, 1);
		
        label = new JLabel("Drag Some File Here !", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setSize(200,200);
        label.setOpaque(true);
        label.setBorder(new LineBorder(Color.MAGENTA));
        label.setPreferredSize(new Dimension(200, 200));
		addComponent(label, 5, 0, 3, 1);
    
        MyDragDropListener myDragDropListener = new MyDragDropListener();
        new DropTarget(label, myDragDropListener);
	}
	
	private void addComponent(Component component,int row, int column, int width, int height) {
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout.setConstraints(component, constraints);
		add(component);
	}
	
	// inner class for button examine event handling
	private class ButtonHandlerExamine implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {		
			JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    
		    FileNameExtensionFilter file = new FileNameExtensionFilter("txt","pdf"); 
		    fileChooser.setFileFilter(file);

		    int result = fileChooser.showOpenDialog(null);

		    if (result != JFileChooser.CANCEL_OPTION) {
		    			    	
		        File fileName = fileChooser.getSelectedFile();

		        if ((fileName == null) || (fileName.getName().equals(""))) {
		            textFieldExamine.setText("...");
		        } else {
		            textFieldExamine.setText(fileName.getAbsolutePath());
		        }
		    }  
		}
	}
	
	private class ButtonHandlerSearch implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event)
		{			
			String resultado = "";
			try {
				resultado = indexer.busqueda(textFieldSearch.getText());
				textAreaResult.setText(resultado);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ButtonHandlerIndex implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {			
			System.out.println("Indexing is executed for the folder : " + textFieldExamine.getText());		 
			System.out.println("For text type : " +fileTypesNames[fileTypesJList.getSelectedIndex()]);	
			try {
				indexer.indexer( textFieldExamine.getText() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private class MyDragDropListener implements DropTargetListener {
	    @Override
	    public void drop(DropTargetDropEvent event) {    	
            try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> droppedFiles = (List<File>) event.getTransferable()
                											.getTransferData(DataFlavor.javaFileListFlavor);

                for (File file : droppedFiles) {
                    System.out.println("\nFile Name : " + file.getName());
                    System.out.println("File Path: " + file.getAbsolutePath());
                    indexer.addIndex(file.getAbsolutePath());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
	    }

		public void dragEnter(DropTargetDragEvent event) { }
		public void dragOver(DropTargetDragEvent event) { }
		public void dropActionChanged(DropTargetDragEvent event) { }
		public void dragExit(DropTargetEvent event ) { }
	}
}