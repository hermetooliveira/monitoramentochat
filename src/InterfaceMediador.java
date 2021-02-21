import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceMediador extends Remote{
	
	public void receberMensagem()  throws RemoteException;
	
}
