import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Assinante implements MessageListener {	

	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String topicName = "TOPICO_SUSPEITO";
	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
	MessageConsumer subscriber;
	public String msg = "vazia";
	
	public Assinante() {
		super();		
	}
	
	public void Go(){	
		
		try{
			
			connectionFactory = new ActiveMQConnectionFactory(url);			
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			dest = session.createTopic(topicName);
			subscriber = session.createConsumer(dest);
			subscriber.setMessageListener(this);

		}catch(Exception e){
			e.printStackTrace();
		}       
	}   

	public void onMessage(Message message){

		if(message instanceof TextMessage){
			
				try {
					System.out.println( ((TextMessage)message).getText());
					this.msg = ((TextMessage)message).getText();					
				} catch (JMSException e) {					
					e.printStackTrace();
				}				
		}
	} 

}
