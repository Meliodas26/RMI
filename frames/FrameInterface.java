package frames;

import tables.TInterface;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class FrameInterface extends JFrame{
	
	JScrollPane scroll;

	public FrameInterface(TInterface tInterface){

		scroll = new JScrollPane(tInterface.table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(scroll);
		
		setTitle("Interfaces");
        setSize(500,177);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

	}
	/*
	public void add(String type, String os, String user, String ip, String status){
		tableInterface.addInterface(type,os,user,ip,status);
	}
	*/
	/*
	public void updateStatus(int row, int column, String value){
		tableInterface.updateStatus(row,column,value);
	}
	*/
}