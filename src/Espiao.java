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
import javax.jms.JMSException;
import javax.swing.Icon;
import javax.swing.JFormattedTextField;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JList;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;

public class Espiao extends JFrame{	
	
	String pessoa;
	String frase;
	JLabel lblMostraEnvio = new JLabel("");
	public static List<String> listaUsuarios = new ArrayList<String>();	
	public static int idUsuario = 0;
	public static int flag = 0;
	private JPanel contentPane;
	public JTextArea textArea = new JTextArea();
	JLabel lblNewLabel_1 = new JLabel("Hist\u00F3rico");
	Mensagem msgChat;
	String nomeUsuario;
	String[] palavras = new String[10];	
	JComboBox comboBox_1 = new JComboBox();	
	private final JButton btnNewButton = new JButton("Inserir");
	InterfaceMediador interfaceMediador;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Espiao frame = new Espiao();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Espiao() {
		
		setTitle("Espi\u00E3o");		
		palavras[0] = "dano";
		palavras[1] = "Explosão";
		palavras[2] = "dinamite";
		palavras[3] = "bomba";		
		palavras[4] = "jokey";
		palavras[5] = "jokey";
		palavras[6] = "jokey";
		palavras[7] = "jokey";
		palavras[8] = "jokey";
		palavras[9] = "jokey";
		comboBox_1.setBackground(Color.LIGHT_GRAY);
		
		comboBox_1.addItem(palavras[0]);
		comboBox_1.addItem(palavras[1]);
		comboBox_1.addItem(palavras[2]);
		comboBox_1.addItem(palavras[3]);		
		
		try {
			interfaceMediador = (InterfaceMediador)Naming.lookup("//localhost/mediador");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Procurando pelo servico JavaSpace...");
		Lookup finder = new Lookup(JavaSpace.class);
		JavaSpace space = (JavaSpace) finder.getService();
		if (space == null) {
			System.out.println("O servico JavaSpace nao foi encontrado. Encerrando...");
			System.exit(-1);
		} 
		
		System.out.println("O servico JavaSpace foi encontrado.");
		System.out.println("Espaço Amb " + space);
		Mensagem msg = new Mensagem();		
		new ReadMessage().start();
		setVisible(true);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 330, 240);
		contentPane = new JPanel();
		contentPane.setBackground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setBounds(33, 32, 182, 55);
		lblNewLabel_1.setBounds(33, 7, 64, 14);
		contentPane.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 32, 236, 107);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);
		comboBox_1.setBounds(33, 168, 98, 22);
		
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel("Suspeitas");
		lblNewLabel.setBounds(33, 150, 64, 14);
		contentPane.add(lblNewLabel);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String palavraSuspeita = JOptionPane.showInputDialog(null,"Qual palavra suspeita deseja adicionar? ");
				adicionarPalavraSuspeita(palavraSuspeita);
				
			}
		});
		btnNewButton.setBounds(150, 168, 72, 23);
		
		contentPane.add(btnNewButton);
		
		
		lblMostraEnvio.setBounds(97, 7, 207, 14);
		contentPane.add(lblMostraEnvio);
		
		
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
					pessoa = msgChat.nome;
					frase = msgChat.conteudo;
										
					try {
						verificarPalavraSuspeita(pessoa, frase);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

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
	public void adicionarPalavraSuspeita(String palavra){
		int k = -1;	
		for(int i=0; i < palavras.length; i++){
			if(!palavras[i].equals("jokey")){
				k += 1;
			}
		}
		palavras[k] = palavra;
		comboBox_1.addItem(palavras[k]);
	}
	
	public void verificarPalavraSuspeita(String pessoa, String frase) throws RemoteException {
		
		for (int i = 0; i < palavras.length; i++) {
			if (frase.indexOf(palavras[i])!=-1) {
				lblMostraEnvio.setText("Enviando MSG SUSPEITA...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Publisher publisher = new Publisher(pessoa +" "+ " escreveu: "+ frase);
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				interfaceMediador.receberMensagem();					
				
				lblMostraEnvio.setText(" ");
				
			}
		}
		
	}
}
