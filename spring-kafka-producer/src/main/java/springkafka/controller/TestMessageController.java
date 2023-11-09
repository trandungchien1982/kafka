package springkafka.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springkafka.config.LogService;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestMessageController {
	
	private final Log log = LogFactory.getLog(getClass());

	// Giá trị bắt đầu COUNT để dễ quản lý giá trị index của message :) ...
	public static final long COUNT_INITIAL = 200;

	@Value(value = "${kafka.topic}")
	private String topicName;

	@Autowired
	LogService logService;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		kafkaTemplate.send(topicName, msg);
	}

	private static AtomicLong count = new AtomicLong(COUNT_INITIAL);

	@GetMapping(path="/send-no-key")
	public String testSendMsgAndResponse() {
		String message = "{Message-NO-KEY} : index_" + count.getAndIncrement() + " ";
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				String msg = "SUCCESS - Sent [Message-NO-KEY]=[" + message + "] with " +
						"partition=[" + result.getRecordMetadata().partition() + "], " +
						"offset=[" + result.getRecordMetadata().offset() + "]";
				// ~> RabbitMQ Logs
				logService.log(msg);

			}
			@Override
			public void onFailure(Throwable ex) {
				String msg = "FAIL - Unable to send [Message-NO-KEY]=[" + message + "] due to : " + ex.getMessage();
				log.info(msg);
				// ~> RabbitMQ Logs
				logService.log(msg);
			}
		});

		return message;
	}

	@GetMapping(path="/send-null-key")
	public String testSendMsgNullKeyAndResponse() {
		String message = "{Message-NULL-KEY} : index_" + count.getAndIncrement() + " ";
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, null, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				String msg = "SUCCESS - Sent [Message-NULL-KEY]=[" + message + "] with " +
						"partition=[" + result.getRecordMetadata().partition() + "], " +
						"offset=[" + result.getRecordMetadata().offset() + "]";
				// ~> RabbitMQ Logs
				logService.log(msg);

			}
			@Override
			public void onFailure(Throwable ex) {
				String msg = "FAIL - Unable to send [Message-NULL-KEY]=[" + message + "] due to : " + ex.getMessage();
				log.info(msg);
				// ~> RabbitMQ Logs
				logService.log(msg);
			}
		});

		return message;
	}

	@GetMapping(path="/send-with-key")
	public String testSendMsgWithKeyAndResponse(@RequestParam(name = "specificKey", required = false) String specificKey) {
		String key = UUID.randomUUID() + "_" + count.get();
		if (specificKey != null) {
			key = specificKey;
		}
		String message = "{MessageWithKey} : " + key + " and index_" + count.getAndIncrement() + " ";
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, key, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				String msg = "SUCCESS - Sent [Message-With-Key]=[" + message + "] with " +
						"partition=[" + result.getRecordMetadata().partition() + "], " +
						"offset=[" + result.getRecordMetadata().offset() + "]";
				logService.log(msg);
			}
			@Override
			public void onFailure(Throwable ex) {
				String msg = "FAIL - Unable to send [Message-With-Key]=["
						+ message + "] due to : " + ex.getMessage();
				logService.log(msg);
			}
		});

		return message;
	}

}
