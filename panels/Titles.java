package panels;

import others.Colors;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class Titles extends JPanel{

	Colors color = new Colors();

	JLabel title, subTitleSecuencial, subTitleConcurrente, vs, subTitleParalelo, vs2;

	public Titles(){
		setLayout(null);
		setBounds(0, 0, 750, 95);
		setBackground(color.black);

		title = new JLabel("Ordenamiento de saldos");
		title.setFont(new Font("DejaVu Sans", Font.BOLD, 25));
		title.setForeground(color.white);
		title.setBounds(200, 20, 400, 25);

		subTitleSecuencial = new JLabel("Secuencial");
		subTitleSecuencial.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		subTitleSecuencial.setForeground(color.red);
		subTitleSecuencial.setBounds(130, 57, 130, 20);
		
		vs = new JLabel("vs");
		vs.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		vs.setForeground(color.white);
		vs.setBounds(260, 65, 15, 10);
		
		subTitleConcurrente = new JLabel("Concurrente");
		subTitleConcurrente.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		subTitleConcurrente.setForeground(color.yellow);
		subTitleConcurrente.setBounds(280, 57, 150, 20);
		
		vs2 = new JLabel("vs");
		vs2.setFont(new Font("DejaVu Sans", Font.BOLD, 10));
		vs2.setForeground(color.white);
		vs2.setBounds(425, 65, 15, 10);
		
		subTitleParalelo = new JLabel("Paralelo - Concurrente");
		subTitleParalelo.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
		subTitleParalelo.setForeground(color.green);
		subTitleParalelo.setBounds(445, 57, 300, 20);
		
		add(title);
		add(subTitleSecuencial);
		add(vs);
		add(subTitleConcurrente);
		add(vs2);
		add(subTitleParalelo);

	}
}