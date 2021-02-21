import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher{
	
 	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String topicName = "TOPICO_SUSPEITO";
	//public String msg;
	
	public Publisher(String msg) throws JMSException{
		//this.msg = msg;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();	
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
		Destination dest = session.createTopic(topicName);
		MessageProducer publisher = session.createProducer(dest);
		TextMessage message = session.createTextMessage();
		message.setText(msg);
		publisher.send(message);       
		publisher.close();
		session.close();
		connection.close();
		
	}

}
