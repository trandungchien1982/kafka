package springkafka.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestMessageController {
	
	private final Log log = LogFactory.getLog(getClass());

	// Giá trị bắt đầu COUNT để dễ quản lý giá trị index của message :) ...
	public static final long COUNT_INITIAL = 100;

	@Value(value = "${kafka.topic}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		kafkaTemplate.send(topicName, msg);
	}

	private static AtomicLong count = new AtomicLong(COUNT_INITIAL);
		
	@GetMapping(path="/send")
	public String testSendMsg() {
		String message = "Message-Type1 : count_" + count.getAndIncrement() + " ";
		kafkaTemplate.send(topicName, message);
		;
		return message;
	}

	@GetMapping(path="/send-response")
	public String testSendMsgAndResponse() {
		String message = "Message-Type2 : index_" + count.getAndIncrement() + " ";
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("SUCCESS - Sent message=[" + message + "] with " +
						"partition=[" + result.getRecordMetadata().partition() + "], " +
						"offset=[" + result.getRecordMetadata().offset() + "]");
			}
			@Override
			public void onFailure(Throwable ex) {
				log.info("FAIL - Unable to send message=["
						+ message + "] due to : " + ex.getMessage());
			}
		});

		return message;
	}

}
