import tables.TAccount;
import tables.TTime;
import tables.TProcess;
import panels.*;
import others.Colors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;

public class FrameServer extends JFrame{

	Titles titles;
	JPanel header, left, right;
	JScrollPane scrollAccount, scrollTime, scrollProcess, scrollComputer;
	Colors color = new Colors();

	public FrameServer(TAccount tAccount, TTime tTime, TProcess tProcess, Buttons buttons, JLabel lThread){
		titles = new Titles();
		header = new JPanel();
		header.setLayout(null);
		header.setBounds(0, 0, 750, 180);
		header.add(titles);
		header.add(buttons);
		
		scrollAccount = new JScrollPane(tAccount.table);
		scrollAccount.setBounds(0, 0, 400, 385);
		scrollAccount.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollAccount.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		left = new JPanel();
		left.setLayout(null);
		left.setBounds(0, 180, 400, 390);
		left.add(scrollAccount);
		
		scrollTime = new JScrollPane(tTime.table);
		scrollTime.setBounds(85, 10, 170, 66);
		scrollTime.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTime.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		scrollProcess = new JScrollPane(tProcess.table);
		scrollProcess.setBounds(15, 100, 318, 102);
		scrollProcess.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollProcess.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		lThread.setBounds(0, 208, 350, 20);
		lThread.setForeground(color.red);
		lThread.setFont(new Font("DejaVu Sans", 1, 10));
		/*
		scroll = new JScrollPane(comparacion.table);
		scroll.setBounds(0, 0, 400, 385);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		*/
		right = new JPanel();
		right.setLayout(null);
		right.setBounds(400, 180, 350, 400);
		right.add(scrollTime);
		right.add(scrollProcess);
		//right.add(scroll);
		right.add(lThread);


		add(header);
		add(left);
		add(right);

		setTitle("Proyecto - 3er parcial");
		setLayout(null);
        setSize(750,600);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
}