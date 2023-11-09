# Kafka
Các ví dụ liên quan đến Kafka từ cơ bản đến nâng cao

Mỗi nhánh trong Repo sẽ là 1 ví dụ/ giải pháp/ project mẫu liên quan đến Kafka

# Môi trường phát triển
- Sử dụng Docker Images để thực hành, đa phần sẽ là 1 Zookeeper + 1 Kafka Broker + nhiều Partition + ... <br />
  - Môi trường dựng dựa trên landoop/fast-data-dev:
    - https://hub.docker.com/r/landoop/fast-data-dev
    - https://github.com/trandungchien1982/docker/tree/05.KafkaSimple1Broker
  - Môi trường dựng dựa trên Conduktor
    - https://github.com/conduktor/kafka-stack-docker-compose
    - https://www.conduktor.io/get-started/

- OS: Ubuntu 20.04 LTS
- Docker Version: 20.10.11
```
    docker version
------------------------------------------------------------
    Version:           20.10.11
    Server: Docker Engine - Community
```

# Folder liên quan trên Windows
```
D:\Projects\kafka
```
==============================================================
# Ví dụ [04.UseMessageKeyToRouting]
==============================================================

**Tham khảo**
- https://viblo.asia/p/003-gui-va-nhan-message-trong-apache-kafka-LzD5dMmzKjY#_22-message-key-3
- https://github.com/trandungchien1982/spring/tree/09.LogServices+RabbitMQ

**Yêu cầu**
- Dựng môi trường Kafka dùng Docker
- Tạo 1 App Spring Boot đóng vai trò Producer (`spring-kafka-producer`)
  - Mở Port 9160
  - Có kết nối đến RabbitMQ+ServiceLog để xử lý logs tập trung
- Tạo 1 App Spring Boot đóng vai trò Consumer (`spring-kafka-consumer`)
  - Mở Port ngẫu nhiên từ 9161 đến 9170
  - Mỗi lần chạy instance của consumer thì nó đã thuộc chung Consumer Group `consumerGroupII` của topic: `use-message-key-topic`
  - Có kết nối đến RabbitMQ+ServiceLog để xử lý logs tập trung
- Theo dõi các messages được Producer sinh ra và đẩy vào Kafka Partitions từ Kafka Server UI
- Theo dõi các messages đã được Consume dựa vào Console Log trên Producer/Consumer/RabbitMQ+ServiceLog

**Kiểm tra kết quả**
- Dùng Docker Compose dựng môi trường Kafka Dev, Kafka Broker giao tiếp ở port 9092
- Truy cập UI của Kafka Server: http://localhost:3030

```shell
docker-compose -f 01.docker-compose-KafkaServer.yml up
------------------------------------------------------------------------------
tdc@tdc:~/kafka$ docker-compose -f 01.docker-compose-KafkaServer.yml up
Creating kafka_fast-dev-kafka_1 ... done
Attaching to kafka_fast-dev-kafka_1
fast-dev-kafka_1  | Starting services.
fast-dev-kafka_1  | This is Lenses.io’s fast-data-dev. Kafka 2.6.2-L0 (Lenses.io's Kafka Distribution).
fast-dev-kafka_1  | You may visit http://127.0.0.1:3030 in about a minute.
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/01-zookeeper.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/02-broker.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/03-schema-registry.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/04-rest-proxy.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/05-connect-distributed.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/06-caddy.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/07-smoke-tests.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/08-logs-to-kafka.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Included extra file "/etc/supervisord.d/99-supervisord-running-sample-data.conf" during parsing
fast-dev-kafka_1  | 2023-11-06 04:18:35,349 INFO Set uid to user 0 succeeded
fast-dev-kafka_1  | 2023-11-06 04:18:35,353 INFO RPC interface 'supervisor' initialized
fast-dev-kafka_1  | 2023-11-06 04:18:35,353 CRIT Server 'unix_http_server' running without any HTTP authentication checking
fast-dev-kafka_1  | 2023-11-06 04:18:35,354 INFO supervisord started with pid 7
fast-dev-kafka_1  | 2023-11-06 04:18:36,356 INFO spawned: 'broker' with pid 179
fast-dev-kafka_1  | 2023-11-06 04:18:36,359 INFO spawned: 'caddy' with pid 180
fast-dev-kafka_1  | 2023-11-06 04:18:36,361 INFO spawned: 'connect-distributed' with pid 181
fast-dev-kafka_1  | 2023-11-06 04:18:36,365 INFO spawned: 'logs-to-kafka' with pid 182
fast-dev-kafka_1  | 2023-11-06 04:18:36,368 INFO spawned: 'rest-proxy' with pid 188
fast-dev-kafka_1  | 2023-11-06 04:18:36,372 INFO spawned: 'running-sample-data-ais' with pid 190
fast-dev-kafka_1  | 2023-11-06 04:18:36,375 INFO spawned: 'running-sample-data-backblaze-smart' with pid 192
fast-dev-kafka_1  | 2023-11-06 04:18:36,380 INFO spawned: 'running-sample-data-taxis' with pid 197
fast-dev-kafka_1  | 2023-11-06 04:18:36,383 INFO spawned: 'running-sample-data-telecom-italia' with pid 205
fast-dev-kafka_1  | 2023-11-06 04:18:36,387 INFO spawned: 'schema-registry' with pid 207
fast-dev-kafka_1  | 2023-11-06 04:18:36,392 INFO spawned: 'smoke-tests' with pid 209
fast-dev-kafka_1  | 2023-11-06 04:18:36,399 INFO spawned: 'zookeeper' with pid 211
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: broker entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: caddy entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: connect-distributed entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: logs-to-kafka entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: rest-proxy entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: running-sample-data-ais entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: running-sample-data-backblaze-smart entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: running-sample-data-taxis entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: running-sample-data-telecom-italia entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: schema-registry entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: smoke-tests entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-06 04:18:37,414 INFO success: zookeeper entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)

```

**Kiểm tra Console Log của Producer**
```shell
./gradlew bootRun
-----------------------------------------------------------------
> Task :compileJava
> Task :processResources UP-TO-DATE
> Task :classes
> Task :bootRunMainClassName

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.16)

[Kafka-Producer] INFO  - Starting KafkaProducerApp using Java 11.0.20.1 on tdc with PID 86671 (/home/tdc/kafka/spring-kafka-producer/build/classes/java/main started by tdc in /home/tdc/kafka/spring-kafka-producer)
[Kafka-Producer] INFO  - No active profile set, falling back to 1 default profile: "default"
[Kafka-Producer] INFO  - Tomcat initialized with port(s): 9160 (http)
[Kafka-Producer] INFO  - Initializing ProtocolHandler ["http-nio-9160"]
[Kafka-Producer] INFO  - Starting service [Tomcat]
[Kafka-Producer] INFO  - Starting Servlet engine: [Apache Tomcat/9.0.80]
[Kafka-Producer] INFO  - Initializing Spring embedded WebApplicationContext
[Kafka-Producer] INFO  - Root WebApplicationContext: initialization completed in 768 ms
[Kafka-Producer] INFO  - AdminClientConfig values: 
        bootstrap.servers = [http://127.0.0.1:9092]
        client.dns.lookup = use_all_dns_ips
        client.id = 
        connections.max.idle.ms = 300000
        default.api.timeout.ms = 60000
        metadata.max.age.ms = 300000
        metric.reporters = []
        metrics.num.samples = 2
        metrics.recording.level = INFO
        metrics.sample.window.ms = 30000
        receive.buffer.bytes = 65536
        reconnect.backoff.max.ms = 1000
        reconnect.backoff.ms = 50
        request.timeout.ms = 30000
        retries = 2147483647
        retry.backoff.ms = 100
        sasl.client.callback.handler.class = null
        sasl.jaas.config = null
        sasl.kerberos.kinit.cmd = /usr/bin/kinit
        sasl.kerberos.min.time.before.relogin = 60000
        sasl.kerberos.service.name = null
        sasl.kerberos.ticket.renew.jitter = 0.05
        sasl.kerberos.ticket.renew.window.factor = 0.8
        sasl.login.callback.handler.class = null
        sasl.login.class = null
        sasl.login.connect.timeout.ms = null
        sasl.login.read.timeout.ms = null
        sasl.login.refresh.buffer.seconds = 300
        sasl.login.refresh.min.period.seconds = 60
        sasl.login.refresh.window.factor = 0.8
        sasl.login.refresh.window.jitter = 0.05
        sasl.login.retry.backoff.max.ms = 10000
        sasl.login.retry.backoff.ms = 100
        sasl.mechanism = GSSAPI
        sasl.oauthbearer.clock.skew.seconds = 30
        sasl.oauthbearer.expected.audience = null
        sasl.oauthbearer.expected.issuer = null
        sasl.oauthbearer.jwks.endpoint.refresh.ms = 3600000
        sasl.oauthbearer.jwks.endpoint.retry.backoff.max.ms = 10000
        sasl.oauthbearer.jwks.endpoint.retry.backoff.ms = 100
        sasl.oauthbearer.jwks.endpoint.url = null
        sasl.oauthbearer.scope.claim.name = scope
        sasl.oauthbearer.sub.claim.name = sub
        sasl.oauthbearer.token.endpoint.url = null
        security.protocol = PLAINTEXT
        security.providers = null
        send.buffer.bytes = 131072
        socket.connection.setup.timeout.max.ms = 30000
        socket.connection.setup.timeout.ms = 10000
        ssl.cipher.suites = null
        ssl.enabled.protocols = [TLSv1.2, TLSv1.3]
        ssl.endpoint.identification.algorithm = https
        ssl.engine.factory.class = null
        ssl.key.password = null
        ssl.keymanager.algorithm = SunX509
        ssl.keystore.certificate.chain = null
        ssl.keystore.key = null
        ssl.keystore.location = null
        ssl.keystore.password = null
        ssl.keystore.type = JKS
        ssl.protocol = TLSv1.3
        ssl.provider = null
        ssl.secure.random.implementation = null
        ssl.trustmanager.algorithm = PKIX
        ssl.truststore.certificates = null
        ssl.truststore.location = null
        ssl.truststore.password = null
        ssl.truststore.type = JKS

[Kafka-Producer] INFO  - Kafka version: 3.1.2
[Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699497056455
[Kafka-Producer] INFO  - App info kafka.admin.client for adminclient-1 unregistered
[Kafka-Producer] INFO  - Metrics scheduler closed
[Kafka-Producer] INFO  - Closing reporter org.apache.kafka.common.metrics.JmxReporter
[Kafka-Producer] INFO  - Metrics reporters closed
[Kafka-Producer] INFO  - Starting ProtocolHandler ["http-nio-9160"]
[Kafka-Producer] INFO  - Tomcat started on port(s): 9160 (http) with context path ''
[Kafka-Producer] INFO  - Started KafkaProducerApp in 1.874 seconds (JVM running for 2.261)
```


**Kiểm tra việc consume các messages trong topic `use-message-key-topic`:** <br/>
(Xem trong Console Log của Consumer) <br/>
- Khi chạy instance `spring-kafka-consumer` thứ nhất, port 9162 <br/>
  (consumerGroupII: partitions assigned: [use-message-key-topic-2])
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9162 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 1.972 seconds (JVM running for 2.317)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Cluster ID: BT7FNGvhRU-iJeyU7LCPjg
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Finished assignment for group at generation 1: {consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324=Assignment(partitions=[use-message-key-topic-0, use-message-key-topic-1, use-message-key-topic-2])}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=1, memberId='consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[use-message-key-topic-0, use-message-key-topic-1, use-message-key-topic-2])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: use-message-key-topic-1, use-message-key-topic-2, use-message-key-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition use-message-key-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition use-message-key-topic-1 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition use-message-key-topic-2 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition use-message-key-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [use-message-key-topic-1, use-message-key-topic-2, use-message-key-topic-0]
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: group is already rebalancing
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Revoke previously assigned partitions use-message-key-topic-1, use-message-key-topic-2, use-message-key-topic-0
[Kafka-Consumer] INFO  - consumerGroupII: partitions revoked: [use-message-key-topic-1, use-message-key-topic-2, use-message-key-topic-0]
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Finished assignment for group at generation 2: {consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324=Assignment(partitions=[use-message-key-topic-2]), consumer-consumerGroupII-1-7ae36d2b-1471-4b71-a378-8364c37807e8=Assignment(partitions=[use-message-key-topic-0, use-message-key-topic-1])}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-902cfcc1-6c92-47e7-a145-4b34ab5f1324', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[use-message-key-topic-2])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: use-message-key-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition use-message-key-topic-2 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [use-message-key-topic-2]
```

- Khi chạy instance `spring-kafka-consumer` thứ hai, port 9163 <br />
  (consumerGroupII: partitions assigned: [use-message-key-topic-1, use-message-key-topic-0]) <br />
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9163 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 1.952 seconds (JVM running for 2.325)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Cluster ID: BT7FNGvhRU-iJeyU7LCPjg
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-7ae36d2b-1471-4b71-a378-8364c37807e8', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-7ae36d2b-1471-4b71-a378-8364c37807e8', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[use-message-key-topic-0, use-message-key-topic-1])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: use-message-key-topic-1, use-message-key-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition use-message-key-topic-1 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition use-message-key-topic-0 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [use-message-key-topic-1, use-message-key-topic-0]
```

**Tiến hành push các message từ index 10 đến 20 (không kèm message key), lặp API 11 lần :)** <br/>
=> Các message sẽ được phân phối đều đặn vào 3 partitions
```shell
GET http://localhost:9160/send-no-key
```
- Xem logs của Producer:
```shell
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_11 ] with partition=[1], offset=[0]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_12 ] with partition=[0], offset=[1]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_13 ] with partition=[2], offset=[0]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_14 ] with partition=[1], offset=[1]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_15 ] with partition=[0], offset=[2]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_16 ] with partition=[1], offset=[2]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_17 ] with partition=[0], offset=[3]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_18 ] with partition=[2], offset=[1]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_19 ] with partition=[0], offset=[4]
[Kafka-Producer] WARN  - SUCCESS - Sent [Message-NO-KEY]=[{Message-NO-KEY} : index_20 ] with partition=[2], offset=[2]
```

- Xem logs của Consumer 1 (port 9162) <br/>
(Chưa rõ tại sao server.port lại biến thành 9165 trong khi ghi logs :) )
```shell
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [use-message-key-topic-2]
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] INFO  - Attempting to connect to: [localhost:56820]
[Kafka-Consumer] INFO  - Created new connection: rabbitConnectionFactory#42373389:0/SimpleConnection@7d76324 [delegate=amqp://admin@127.0.0.1:56820/, localPort= 58572]
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_13 , topic: use-message-key-topic, partition: 2, offset: 0, time: 10:01:58
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_13 , topic: use-message-key-topic, partition: 2, offset: 0, time: 10:01:58 ------> FINISH 
     ( ............. after waiting 9200 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_18 , topic: use-message-key-topic, partition: 2, offset: 1, time: 10:02:07
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_18 , topic: use-message-key-topic, partition: 2, offset: 1, time: 10:02:07 ------> FINISH 
     ( ............. after waiting 7600 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_20 , topic: use-message-key-topic, partition: 2, offset: 2, time: 10:02:15
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NO-KEY} : index_20 , topic: use-message-key-topic, partition: 2, offset: 2, time: 10:02:15 ------> FINISH 
     ( ............. after waiting 8600 millis ... ) 
```

- Xem logs của Consumer 2 (port 9163) <br/>
  (Chưa rõ tại sao server.port lại biến thành 9167 trong khi ghi logs :) )
```shell
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [use-message-key-topic-1, use-message-key-topic-0]
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] INFO  - Attempting to connect to: [localhost:56820]
[Kafka-Consumer] INFO  - Created new connection: rabbitConnectionFactory#188cbcde:0/SimpleConnection@5e62ddac [delegate=amqp://admin@127.0.0.1:56820/, localPort= 58558]
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_10 , topic: use-message-key-topic, partition: 0, offset: 0, time: 10:01:55
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_10 , topic: use-message-key-topic, partition: 0, offset: 0, time: 10:01:55 ------> FINISH 
     ( ............. after waiting 8300 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_11 , topic: use-message-key-topic, partition: 1, offset: 0, time: 10:02:04
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_11 , topic: use-message-key-topic, partition: 1, offset: 0, time: 10:02:04 ------> FINISH 
     ( ............. after waiting 1500 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_14 , topic: use-message-key-topic, partition: 1, offset: 1, time: 10:02:05
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_14 , topic: use-message-key-topic, partition: 1, offset: 1, time: 10:02:05 ------> FINISH 
     ( ............. after waiting 2800 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_16 , topic: use-message-key-topic, partition: 1, offset: 2, time: 10:02:08
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_16 , topic: use-message-key-topic, partition: 1, offset: 2, time: 10:02:08 ------> FINISH 
     ( ............. after waiting 2900 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_12 , topic: use-message-key-topic, partition: 0, offset: 1, time: 10:02:11
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_12 , topic: use-message-key-topic, partition: 0, offset: 1, time: 10:02:11 ------> FINISH 
     ( ............. after waiting 3400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_15 , topic: use-message-key-topic, partition: 0, offset: 2, time: 10:02:14
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_15 , topic: use-message-key-topic, partition: 0, offset: 2, time: 10:02:14 ------> FINISH 
     ( ............. after waiting 5400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_17 , topic: use-message-key-topic, partition: 0, offset: 3, time: 10:02:20
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_17 , topic: use-message-key-topic, partition: 0, offset: 3, time: 10:02:20 ------> FINISH 
     ( ............. after waiting 2900 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_19 , topic: use-message-key-topic, partition: 0, offset: 4, time: 10:02:22
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NO-KEY} : index_19 , topic: use-message-key-topic, partition: 0, offset: 4, time: 10:02:22 ------> FINISH 
     ( ............. after waiting 4800 millis ... ) 
```


**Tiến hành push các message từ index 40 đến 50 (key = null, lặp API 11 lần :)** <br/>
=> Các message sẽ được phân phối đều đặn vào 3 partitions
- Xem logs của Consumer 1 (port 9162)
```shell
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_41 , topic: use-message-key-topic, partition: 2, offset: 3, time: 10:14:14
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_41 , topic: use-message-key-topic, partition: 2, offset: 3, time: 10:14:14 ------> FINISH 
     ( ............. after waiting 4400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_46 , topic: use-message-key-topic, partition: 2, offset: 4, time: 10:14:18
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_46 , topic: use-message-key-topic, partition: 2, offset: 4, time: 10:14:18 ------> FINISH 
     ( ............. after waiting 300 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_50 , topic: use-message-key-topic, partition: 2, offset: 5, time: 10:14:19
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {Message-NULL-KEY} : index_50 , topic: use-message-key-topic, partition: 2, offset: 5, time: 10:14:19 ------> FINISH 
     ( ............. after waiting 5000 millis ... ) 
```
- Xem logs của Consumer 2 (port 9163)
```shell
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_43 , topic: use-message-key-topic, partition: 1, offset: 3, time: 10:14:18
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_43 , topic: use-message-key-topic, partition: 1, offset: 3, time: 10:14:18 ------> FINISH 
     ( ............. after waiting 1200 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_45 , topic: use-message-key-topic, partition: 1, offset: 4, time: 10:14:19
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_45 , topic: use-message-key-topic, partition: 1, offset: 4, time: 10:14:19 ------> FINISH 
     ( ............. after waiting 200 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_47 , topic: use-message-key-topic, partition: 1, offset: 5, time: 10:14:19
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_47 , topic: use-message-key-topic, partition: 1, offset: 5, time: 10:14:19 ------> FINISH 
     ( ............. after waiting 4000 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_49 , topic: use-message-key-topic, partition: 1, offset: 6, time: 10:14:23
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_49 , topic: use-message-key-topic, partition: 1, offset: 6, time: 10:14:23 ------> FINISH 
     ( ............. after waiting 5500 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_42 , topic: use-message-key-topic, partition: 0, offset: 6, time: 10:14:29
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_42 , topic: use-message-key-topic, partition: 0, offset: 6, time: 10:14:29 ------> FINISH 
     ( ............. after waiting 4600 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_44 , topic: use-message-key-topic, partition: 0, offset: 7, time: 10:14:33
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_44 , topic: use-message-key-topic, partition: 0, offset: 7, time: 10:14:33 ------> FINISH 
     ( ............. after waiting 9100 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_48 , topic: use-message-key-topic, partition: 0, offset: 8, time: 10:14:42
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {Message-NULL-KEY} : index_48 , topic: use-message-key-topic, partition: 0, offset: 8, time: 10:14:42 ------> FINISH 
     ( ............. after waiting 7500 millis ... )
```

**Tiến hành push các message từ index 60 đến 70 (key = 'test-key01', lặp API 11 lần :)** <br/>
=> Các message sẽ được điều hướng vào 1 partition cụ thể, ở đây là partition 0
- Xem logs của Consumer 1 (port 9162)
```shell
NO CHANGES ...
```

- Xem logs của Consumer 2 (port 9163)
```shell
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_60 , topic: use-message-key-topic, partition: 0, offset: 9, time: 10:20:45
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_60 , topic: use-message-key-topic, partition: 0, offset: 9, time: 10:20:45 ------> FINISH 
     ( ............. after waiting 1100 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_61 , topic: use-message-key-topic, partition: 0, offset: 10, time: 10:20:46
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_61 , topic: use-message-key-topic, partition: 0, offset: 10, time: 10:20:46 ------> FINISH 
     ( ............. after waiting 9800 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_62 , topic: use-message-key-topic, partition: 0, offset: 11, time: 10:20:56
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_62 , topic: use-message-key-topic, partition: 0, offset: 11, time: 10:20:56 ------> FINISH 
     ( ............. after waiting 9400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_63 , topic: use-message-key-topic, partition: 0, offset: 12, time: 10:21:06
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_63 , topic: use-message-key-topic, partition: 0, offset: 12, time: 10:21:06 ------> FINISH 
     ( ............. after waiting 8100 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_64 , topic: use-message-key-topic, partition: 0, offset: 13, time: 10:21:14
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_64 , topic: use-message-key-topic, partition: 0, offset: 13, time: 10:21:14 ------> FINISH 
     ( ............. after waiting 3400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_65 , topic: use-message-key-topic, partition: 0, offset: 14, time: 10:21:17
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_65 , topic: use-message-key-topic, partition: 0, offset: 14, time: 10:21:17 ------> FINISH 
     ( ............. after waiting 4300 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_66 , topic: use-message-key-topic, partition: 0, offset: 15, time: 10:21:21
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_66 , topic: use-message-key-topic, partition: 0, offset: 15, time: 10:21:21 ------> FINISH 
     ( ............. after waiting 7700 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_67 , topic: use-message-key-topic, partition: 0, offset: 16, time: 10:21:29
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_67 , topic: use-message-key-topic, partition: 0, offset: 16, time: 10:21:29 ------> FINISH 
     ( ............. after waiting 500 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_68 , topic: use-message-key-topic, partition: 0, offset: 17, time: 10:21:30
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_68 , topic: use-message-key-topic, partition: 0, offset: 17, time: 10:21:30 ------> FINISH 
     ( ............. after waiting 7400 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_69 , topic: use-message-key-topic, partition: 0, offset: 18, time: 10:21:37
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_69 , topic: use-message-key-topic, partition: 0, offset: 18, time: 10:21:37 ------> FINISH 
     ( ............. after waiting 8300 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_70 , topic: use-message-key-topic, partition: 0, offset: 19, time: 10:21:45
[Kafka-Consumer] WARN  - ServerPort: [9167] -- Received Message: {MessageWithKey} : test-key01 and index_70 , topic: use-message-key-topic, partition: 0, offset: 19, time: 10:21:45 ------> FINISH 
     ( ............. after waiting 9200 millis ... ) 
```


**Tiến hành push các message từ index 150 đến 160 (key = 'test-keySECOND', lặp API nhiều lần :)** <br/>
=> Các message sẽ được điều hướng vào 1 partition cụ thể, ở đây là partition 2
- Xem logs của Consumer 1 (port 9162)
```shell
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_111 , topic: use-message-key-topic, partition: 2, offset: 6, time: 10:27:13
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_111 , topic: use-message-key-topic, partition: 2, offset: 6, time: 10:27:13 ------> FINISH 
     ( ............. after waiting 5900 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_110 , topic: use-message-key-topic, partition: 2, offset: 7, time: 10:27:42
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_110 , topic: use-message-key-topic, partition: 2, offset: 7, time: 10:27:42 ------> FINISH 
     ( ............. after waiting 1500 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_150 , topic: use-message-key-topic, partition: 2, offset: 8, time: 10:28:22
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_150 , topic: use-message-key-topic, partition: 2, offset: 8, time: 10:28:22 ------> FINISH 
     ( ............. after waiting 3900 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_151 , topic: use-message-key-topic, partition: 2, offset: 9, time: 10:28:26
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_151 , topic: use-message-key-topic, partition: 2, offset: 9, time: 10:28:26 ------> FINISH 
     ( ............. after waiting 4500 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_152 , topic: use-message-key-topic, partition: 2, offset: 10, time: 10:28:30
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_152 , topic: use-message-key-topic, partition: 2, offset: 10, time: 10:28:30 ------> FINISH 
     ( ............. after waiting 6100 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_153 , topic: use-message-key-topic, partition: 2, offset: 11, time: 10:28:36
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_153 , topic: use-message-key-topic, partition: 2, offset: 11, time: 10:28:36 ------> FINISH 
     ( ............. after waiting 5800 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_154 , topic: use-message-key-topic, partition: 2, offset: 12, time: 10:28:42
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_154 , topic: use-message-key-topic, partition: 2, offset: 12, time: 10:28:42 ------> FINISH 
     ( ............. after waiting 6600 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_155 , topic: use-message-key-topic, partition: 2, offset: 13, time: 10:28:49
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_155 , topic: use-message-key-topic, partition: 2, offset: 13, time: 10:28:49 ------> FINISH 
     ( ............. after waiting 7000 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_156 , topic: use-message-key-topic, partition: 2, offset: 14, time: 10:28:56
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_156 , topic: use-message-key-topic, partition: 2, offset: 14, time: 10:28:56 ------> FINISH 
     ( ............. after waiting 8100 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_157 , topic: use-message-key-topic, partition: 2, offset: 15, time: 10:29:04
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_157 , topic: use-message-key-topic, partition: 2, offset: 15, time: 10:29:04 ------> FINISH 
     ( ............. after waiting 5600 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_158 , topic: use-message-key-topic, partition: 2, offset: 16, time: 10:29:09
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_158 , topic: use-message-key-topic, partition: 2, offset: 16, time: 10:29:09 ------> FINISH 
     ( ............. after waiting 9200 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_159 , topic: use-message-key-topic, partition: 2, offset: 17, time: 10:29:18
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_159 , topic: use-message-key-topic, partition: 2, offset: 17, time: 10:29:18 ------> FINISH 
     ( ............. after waiting 2300 millis ... ) 
[Kafka-Consumer] WARN  -  -------------------------------------------------------------------------------------- 
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_160 , topic: use-message-key-topic, partition: 2, offset: 18, time: 10:29:21
[Kafka-Consumer] WARN  - ServerPort: [9165] -- Received Message: {MessageWithKey} : test-keySECOND and index_160 , topic: use-message-key-topic, partition: 2, offset: 18, time: 10:29:21 ------> FINISH 
     ( ............. after waiting 8300 millis ... ) 
```

- Xem logs của Consumer 2 (port 9163)
```shell
NO CHANGES
```

**Tiến hành push các message từ index 200 đến 210 (key = '', lặp API nhiều lần :)** <br/>
=> Có vẻ như nó tương đương vơi TH key = null và đẩy vào partition 0 (không sure lắm)
