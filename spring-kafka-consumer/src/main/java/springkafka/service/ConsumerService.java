package springkafka.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import springkafka.config.LogService;

import java.util.Date;

@Service
public class ConsumerService {
	
	private final Log log = LogFactory.getLog(getClass());

	@Value("${server.port}")
	String serverPort;

	@Value(value = "${kafka.topic}")
	private String topicName;

	@Autowired
	LogService logService;

	@KafkaListener(topics = "${kafka.topic}")
	public void listenWithHeaders(
			@Payload String message,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECORD_METADATA) ConsumerRecord record) {
		String msg = "ServerPort: [" + serverPort + "]" +
				" -- Received Message: " + message + ", topic: " + topicName
				+ ", partition: " + record.partition() + ", offset: " + record.offset()
				+ ", time: " + DateFormatUtils.format(new Date(), "HH:mm:ss");
		logService.log(" -------------------------------------------------------------------------------------- ");
		logService.log(msg);

		long randomMillis = Long.valueOf(RandomStringUtils.randomNumeric(2)) * 100;
		try {
			Thread.sleep(randomMillis);
		} catch (Exception ex) {
		}
		logService.log(msg + "" + " ------> FINISH " +
				"\n     ( ............. after waiting " + randomMillis + " millis ... ) ");
	}

}
