package frames;
import tables.TComputer;
import tables.TThread;
import others.Colors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FrameStatus extends JFrame{
	Colors color = new Colors();

	JScrollPane scrollComputer, scrollThread;
	JButton add,rm;
	public int nThread;
	public FrameStatus(TComputer tComputer, JLabel computers, TThread tThread, JLabel lThreads, int threads, String user){
		setLayout(null);
		nThread = threads;
		scrollComputer = new JScrollPane(tComputer.table);
		scrollComputer.setBounds(0, 0, 184, 172);
		scrollComputer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollComputer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		computers.setBounds(32, 185, 120, 25);
		computers.setForeground(color.celeste);
		computers.setFont(new Font("DejaVu Sans", 1, 20));

		scrollThread = new JScrollPane(tThread.table);
		scrollThread.setBounds(215, 0, 184, 174);
		scrollThread.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollThread.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		lThreads.setBounds(250, 185, 150, 25);
		lThreads.setForeground(color.celeste);
		lThreads.setFont(new Font("DejaVu Sans", 1, 20));

		add = new JButton("+");
		add.setBounds(257, 215, 50, 30);
		add.setBackground(color.darkGreen);
		add.setForeground(color.white);
		add.setFont(new Font("DejaVu Sans", 1, 20));
		add.setBorder(BorderFactory.createLineBorder(color.black, 2));

		rm = new JButton("-");
		rm.setBounds(320, 215, 50, 30);
		rm.setBackground(color.darkRed);
		rm.setForeground(color.white);
		rm.setFont(new Font("DejaVu Sans", 1, 20));
		rm.setBorder(BorderFactory.createLineBorder(color.black, 2));
		
		add(scrollComputer);
		add(computers);
		add(scrollThread);
		add(lThreads);
		add(add);
		add(rm);
		
		setTitle(user);
        setSize(399, 324);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        add.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("Thread agregado...");
  				nThread++;
  				lThreads.setText("Threads: "+nThread);
				System.out.println("---------------------------------");
  			}	
		});

		rm.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("Hilo eliminado...");
  				nThread--;
  				lThreads.setText("Threads: "+nThread);
				System.out.println("---------------------------------");
  			}	
		});
	}

	
	public int getThreads(){
		return nThread;
	}
	
}