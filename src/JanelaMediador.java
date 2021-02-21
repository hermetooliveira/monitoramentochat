import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Color;

public class JanelaMediador extends JFrame {
	
	public JTextArea textArea = new JTextArea();
	public JButton btnNotificacao = new JButton("0 Mensagem");
	private JPanel contentPane;
	//Subscriber subscriber = new Subscriber();
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaMediador frame = new JanelaMediador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/


	public JanelaMediador() {
		
		setTitle("Mediador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 424, 238);
		contentPane = new JPanel();
		contentPane.setBackground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(54, 97, 318, 153);		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 47, 350, 127);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);		
		btnNotificacao.setBounds(142, 11, 131, 23);
		contentPane.add(btnNotificacao);
	}
}
