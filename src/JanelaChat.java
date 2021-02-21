import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.jini.space.JavaSpace;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.JFormattedTextField;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Color;

public class JanelaChat extends JFrame{

	public static List<String> listaUsuarios = new ArrayList<String>();
	public static int idUsuario = 0;	
	private JPanel contentPane;
	JTextField textMsg = new JTextField();
	public JTextArea textArea = new JTextArea();
	JLabel lblNewLabel = new JLabel("Mensagem");
	JLabel lblNewLabel_1 = new JLabel("Hist\u00F3rico");
	JButton btnEnviar = new JButton("Enviar");
	Mensagem msgChat;
	String nomeUsuario;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		//JanelaChat frame = new JanelaChat();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaChat frame = new JanelaChat();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}

	/**
	 * Create the frame.
	 */
	public JanelaChat() {

		System.out.println("Procurando pelo servico JavaSpace...");
		Lookup finder = new Lookup(JavaSpace.class);
		JavaSpace space = (JavaSpace) finder.getService();
		if (space == null) {
			System.out.println("O servico JavaSpace nao foi encontrado. Encerrando...");
			System.exit(-1);
		} 
		System.out.println("O servico JavaSpace foi encontrado.");
		System.out.println("Espaço Amb " + space);

		nomeUsuario = JOptionPane.showInputDialog(null, "Qual o seu nome?");
		setTitle(nomeUsuario);

		Mensagem msg = new Mensagem();
		msg.nome = nomeUsuario;
		new ReadMessage().start();
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 228);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		textMsg.setBounds(33, 123, 236, 20);
		contentPane.add(textMsg);
		textMsg.setColumns(10);


		textArea.setBounds(33, 32, 182, 55);
		//contentPane.add(textArea);


		lblNewLabel.setBounds(33, 98, 64, 14);
		contentPane.add(lblNewLabel);


		lblNewLabel_1.setBounds(33, 7, 64, 14);
		contentPane.add(lblNewLabel_1);


		btnEnviar.setBounds(195, 154, 74, 23);
		contentPane.add(btnEnviar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 32, 236, 54);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);

		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				msg.nome = nomeUsuario;
				msg.conteudo = textMsg.getText();				

				try {
					space.write(msg, null, 60*1000);
				} catch (RemoteException | TransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				textMsg.setText(" ");


			}
		});

	}

	public class ReadMessage extends Thread {

		public void run() {
			System.out.println("Procurando pelo servico JavaSpace...");
			Lookup finder = new Lookup(JavaSpace.class);
			JavaSpace space = (JavaSpace) finder.getService();
			if (space == null) {
				System.out.println("O servico JavaSpace nao foi encontrado. Encerrando...");
				System.exit(-1);
			} 
			System.out.println("O servico JavaSpace foi encontradoo.");
			System.out.println("Espaço: "+ space);      

			while(true) {	

				Mensagem template = new Mensagem();
				
				try {
					msgChat = (Mensagem) space.read(template, null, 1000);

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnusableEntryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransactionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (msgChat != null) {
					if (!listaUsuarios.contains(msgChat.nome)) {
						listaUsuarios.add(msgChat.nome);
						idUsuario = listaUsuarios.size();
					}

					textArea.append(msgChat.nome +" diz -> "+ msgChat.conteudo + " \r\n");

					try {
						msgChat = (Mensagem) space.take(template, null, 1000);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnusableEntryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransactionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}

	}

}
