import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Mediador extends UnicastRemoteObject implements InterfaceMediador{
	
	public static JanelaMediador frame;
	public int contador;
	public static Assinante subscriber = new Assinante();
	
	public Mediador() throws RemoteException {
		super();
		System.out.println("Servidor Mediador criado!");
				
	}
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		   
		try {	
			
			Mediador mediador = new Mediador();			
			Naming.rebind("//localhost/mediador", mediador);
			
			
			} catch (RemoteException e1) {				
				e1.printStackTrace();
			} catch (MalformedURLException e1) {				
				e1.printStackTrace();
			}
			subscriber.Go();
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new JanelaMediador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void receberMensagem() throws RemoteException {
		
		contador++;
		
		if (contador==1) {
			frame.btnNotificacao.setText(contador + " Mensagem");
		} else {
			frame.btnNotificacao.setText(contador + " Mensagens");
		}
		
		frame.textArea.append(subscriber.msg + "\r\n");
		
				
		
		
	}

}
