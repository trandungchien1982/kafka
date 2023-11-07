package springkafka.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ConsumerService {
	
	private final Log log = LogFactory.getLog(getClass());

	@Value("${server.port}")
	String serverPort;

	@Value(value = "${kafka.topic}")
	private String topicName;

	@KafkaListener(topics = "${kafka.topic}")
	public void listenWithHeaders(
			@Payload String message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECORD_METADATA) ConsumerRecord record) {
		log.info("ServerPort: [" + serverPort + "]" +
				" -- Received Message: " + message + ", topic: " + topicName
						+ ", partition: " + record.partition() + ", offset: " + record.offset()
						+ ", time: " + new Date());
	}

}
