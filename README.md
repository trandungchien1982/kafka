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
# Ví dụ [02.UseConsumerGroup]
==============================================================

**Tham khảo**
- https://viblo.asia/p/tim-hieu-ve-consumer-va-consumer-group-trong-kafka-Qbq5Q77XZD8
- https://vsudo.net/blog/kafka-consumer.html
- https://aithietke.com/tim-hieu-ve-consumer-va-consumer-group-trong-kafka/
- https://hackmd.io/@datbv/r1ub5ZpkF

**Yêu cầu**
- Dựng môi trường Kafka dùng Docker
- Tạo 1 App Spring Boot đóng vai trò Producer (`spring-kafka-producer`)
- Tạo 1 App Spring Boot đóng vai trò Consumer (`spring-kafka-consumer`)
  - Mở Port ngẫu nhiên từ 9121 đến 9130
  - Mỗi lần chạy instance của consumer thì nó đã thuộc chung Consumer Group `consumerGroupII` của topic: `main-consumer-topic`
- Theo dõi các messages được Producer sinh ra và đẩy vào Kafka Partitions từ Kafka Server UI
- Theo dõi các messages đã được Consume dựa vào Console Log

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

- Kiểm tra Console Log của Producer
```shell
15:17:21: Executing ':KafkaProducerApp.main()'...

> Task :compileJava UP-TO-DATE
> Task :processResources
> Task :classes

> Task :KafkaProducerApp.main()

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.16)

[Kafka-Producer] INFO  - Starting KafkaProducerApp using Java 1.8.0_333 on tdc with PID 204346 (/home/tdc/kafka/spring-kafka-producer/build/classes/java/main started by tdc in /home/tdc/kafka/spring-kafka-producer)
[Kafka-Producer] INFO  - No active profile set, falling back to 1 default profile: "default"
[Kafka-Producer] INFO  - Tomcat initialized with port(s): 9117 (http)
[Kafka-Producer] INFO  - Initializing ProtocolHandler ["http-nio-9117"]
[Kafka-Producer] INFO  - Starting service [Tomcat]
[Kafka-Producer] INFO  - Starting Servlet engine: [Apache Tomcat/9.0.80]
[Kafka-Producer] INFO  - Initializing Spring embedded WebApplicationContext
[Kafka-Producer] INFO  - Root WebApplicationContext: initialization completed in 1123 ms
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
  ssl.enabled.protocols = [TLSv1.2]
  ssl.endpoint.identification.algorithm = https
  ssl.engine.factory.class = null
  ssl.key.password = null
  ssl.keymanager.algorithm = SunX509
  ssl.keystore.certificate.chain = null
  ssl.keystore.key = null
  ssl.keystore.location = null
  ssl.keystore.password = null
  ssl.keystore.type = JKS
  ssl.protocol = TLSv1.2
  ssl.provider = null
  ssl.secure.random.implementation = null
  ssl.trustmanager.algorithm = PKIX
  ssl.truststore.certificates = null
  ssl.truststore.location = null
  ssl.truststore.password = null
  ssl.truststore.type = JKS

[Kafka-Producer] INFO  - Kafka version: 3.1.2
[Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699258644437
[Kafka-Producer] INFO  - App info kafka.admin.client for adminclient-1 unregistered
[Kafka-Producer] INFO  - Metrics scheduler closed
[Kafka-Producer] INFO  - Closing reporter org.apache.kafka.common.metrics.JmxReporter
[Kafka-Producer] INFO  - Metrics reporters closed
[Kafka-Producer] INFO  - Starting ProtocolHandler ["http-nio-9117"]
[Kafka-Producer] INFO  - Tomcat started on port(s): 9117 (http) with context path ''
[Kafka-Producer] INFO  - Started KafkaProducerApp in 2.75 seconds (JVM running for 3.21)
[Kafka-Producer] INFO  - Initializing Spring DispatcherServlet 'dispatcherServlet'
[Kafka-Producer] INFO  - Initializing Servlet 'dispatcherServlet'
[Kafka-Producer] INFO  - Completed initialization in 1 ms
[Kafka-Producer] INFO  - ProducerConfig values: 
  acks = -1
  batch.size = 16384
  bootstrap.servers = [http://127.0.0.1:9092]
  buffer.memory = 33554432
  client.dns.lookup = use_all_dns_ips
  client.id = producer-1
  compression.type = none
  connections.max.idle.ms = 540000
  delivery.timeout.ms = 120000
  enable.idempotence = true
  interceptor.classes = []
  key.serializer = class org.apache.kafka.common.serialization.StringSerializer
  linger.ms = 0
  max.block.ms = 60000
  max.in.flight.requests.per.connection = 5
  max.request.size = 1048576
  metadata.max.age.ms = 300000
  metadata.max.idle.ms = 300000
  metric.reporters = []
  metrics.num.samples = 2
  metrics.recording.level = INFO
  metrics.sample.window.ms = 30000
  partitioner.class = class org.apache.kafka.clients.producer.internals.DefaultPartitioner
  receive.buffer.bytes = 32768
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
  ssl.enabled.protocols = [TLSv1.2]
  ssl.endpoint.identification.algorithm = https
  ssl.engine.factory.class = null
  ssl.key.password = null
  ssl.keymanager.algorithm = SunX509
  ssl.keystore.certificate.chain = null
  ssl.keystore.key = null
  ssl.keystore.location = null
  ssl.keystore.password = null
  ssl.keystore.type = JKS
  ssl.protocol = TLSv1.2
  ssl.provider = null
  ssl.secure.random.implementation = null
  ssl.trustmanager.algorithm = PKIX
  ssl.truststore.certificates = null
  ssl.truststore.location = null
  ssl.truststore.password = null
  ssl.truststore.type = JKS
  transaction.timeout.ms = 60000
  transactional.id = null
  value.serializer = class org.apache.kafka.common.serialization.StringSerializer

[Kafka-Producer] INFO  - [Producer clientId=producer-1] Instantiated an idempotent producer.
[Kafka-Producer] INFO  - Kafka version: 3.1.2
[Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699258705795
[Kafka-Producer] INFO  - [Producer clientId=producer-1] Cluster ID: 0Ra5Z1t0S3CQ_tq0fznaIg
[Kafka-Producer] INFO  - [Producer clientId=producer-1] ProducerId set to 0 with epoch 0

```


- Kiểm tra việc consume các messages trong topic `main-consumer-topic`: <br />
(Xem trong Console Log của Consumer) <br/>
  - Khi chạy instance `spring-kafka-consumer` thứ nhất (lúc này Consumer Group có 1 Consumer)
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9128 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 1.519 seconds (JVM running for 1.83)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Cluster ID: zd5a3zr8Q56Ochg0wmt_KQ
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroupII-1-5b0e5da3-3c42-45ca-9f87-b0a3cda23b27', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-0, main-consumer-topic-1, main-consumer-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Found no committed offset for partition main-consumer-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition main-consumer-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition main-consumer-topic-1 to position FetchPosition{offset=1, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Resetting offset for partition main-consumer-topic-2 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-0, main-consumer-topic-1, main-consumer-topic-2]
```

  - Khi chạy instance `spring-kafka-consumer` thứ hai (lúc này Consumer Group có 2 Consumer) <br />
  2 Consumers trong Consumer Group sẽ được này xử lý Rebalance Partition:
    - Consumer 1: [main-consumer-topic-2]
    - Consumer 2: [main-consumer-topic-0, main-consumer-topic-1]
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Consumer] INFO  - Starting ProtocolHandler ["http-nio-9127"]
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9127 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 1.583 seconds (JVM running for 1.907)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Cluster ID: zd5a3zr8Q56Ochg0wmt_KQ
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-47eabe0a-dcb1-4f70-aa53-ec7136343d97', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=2, memberId='consumer-consumerGroupII-1-47eabe0a-dcb1-4f70-aa53-ec7136343d97', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-0, main-consumer-topic-1])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-0, main-consumer-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-0 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-1 to the committed offset FetchPosition{offset=1, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-0, main-consumer-topic-1]
```
  
  - Khi chạy instance `spring-kafka-consumer` thứ ba (lúc này Consumer Group có 3 Consumer) <br />
  3 Consumers trong Consumer Group sẽ được này xử lý Rebalance Partition:
    - Consumer 1: [main-consumer-topic-1]
    - Consumer 2: [main-consumer-topic-0]
    - Consumer 3: [main-consumer-topic-2]
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9129 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 1.623 seconds (JVM running for 1.968)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Cluster ID: zd5a3zr8Q56Ochg0wmt_KQ
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=3, memberId='consumer-consumerGroupII-1-e7cd8437-0208-404a-bc10-7e84b0d3137c', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=3, memberId='consumer-consumerGroupII-1-e7cd8437-0208-404a-bc10-7e84b0d3137c', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-2])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-2 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
[Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-2]
```

  - Tiến hành xóa đi 1 instance `spring-kafka-consumer` (lúc này Consumer Group có 2 Consumer) <br />
    2 Consumers trong Consumer Group sẽ được xử lý Rebalance Partition:
    - Consumer 1: [main-consumer-topic-2]
    - Consumer 2: [main-consumer-topic-0, main-consumer-topic-1]


- Tiến hành produce 10 messages, bắt đầu từ index 100
  - Xem kết quả bên Producer :
    ```shell
    [Kafka-Producer] INFO  - [Producer clientId=producer-1] Instantiated an idempotent producer.
    [Kafka-Producer] INFO  - Kafka version: 3.1.2
    [Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
    [Kafka-Producer] INFO  - Kafka startTimeMs: 1699259801128
    [Kafka-Producer] INFO  - [Producer clientId=producer-1] Cluster ID: 0Ra5Z1t0S3CQ_tq0fznaIg
    [Kafka-Producer] INFO  - [Producer clientId=producer-1] ProducerId set to 1 with epoch 0
    ```
    
  - Xem kết quả bên Consumer 1: <br />
    (Consumer 1 đang được assign cho partition 2: [main-consumer-topic-2])
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-2])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-2
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-2 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-2]
    [Kafka-Consumer] INFO  - ServerPort: [9128] -- Received Message: Message-Type1 : count_106 , topic: main-consumer-topic, partition: 2, offset: 0, time: Mon Nov 06 23:53:07 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9128] -- Received Message: Message-Type1 : count_109 , topic: main-consumer-topic, partition: 2, offset: 1, time: Mon Nov 06 23:53:09 ICT 2023
    ```

  - Xem kết quả bên Consumer 2: <br />
    (Consumer 2 đang được assign cho partition 0, partition 1: [main-consumer-topic-0, main-consumer-topic-1])
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-0, main-consumer-topic-1])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-0, main-consumer-topic-1
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-0 to the committed offset FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-1 to the committed offset FetchPosition{offset=1, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-0, main-consumer-topic-1]
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_100 , topic: main-consumer-topic, partition: 0, offset: 0, time: Mon Nov 06 23:53:03 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_101 , topic: main-consumer-topic, partition: 1, offset: 1, time: Mon Nov 06 23:53:05 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_102 , topic: main-consumer-topic, partition: 0, offset: 1, time: Mon Nov 06 23:53:05 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_103 , topic: main-consumer-topic, partition: 1, offset: 2, time: Mon Nov 06 23:53:06 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_104 , topic: main-consumer-topic, partition: 0, offset: 2, time: Mon Nov 06 23:53:06 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_105 , topic: main-consumer-topic, partition: 1, offset: 3, time: Mon Nov 06 23:53:07 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_107 , topic: main-consumer-topic, partition: 1, offset: 4, time: Mon Nov 06 23:53:08 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_108 , topic: main-consumer-topic, partition: 0, offset: 3, time: Mon Nov 06 23:53:08 ICT 2023
    [Kafka-Consumer] INFO  - ServerPort: [9124] -- Received Message: Message-Type1 : count_110 , topic: main-consumer-topic, partition: 1, offset: 5, time: Mon Nov 06 23:53:09 ICT 2023
    ```

- Ta sẽ tạo thêm vài Consumer mới để (5 consumers > 3 partitions) <br />
=> Sẽ có 2 Consumer rảnh rỗi không được assign vào Partition nào cả.
  - Consumer 1: 
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-2])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-2
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-2 to the committed offset FetchPosition{offset=2, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-2]
    ```
    
  - Consumer 2: 
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-1])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-1
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-1 to the committed offset FetchPosition{offset=6, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-1]
    ```
    
  - Consumer 3: 
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[main-consumer-topic-0])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: main-consumer-topic-0
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Setting offset for partition main-consumer-topic-0 to the committed offset FetchPosition{offset=4, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: [main-consumer-topic-0]
    ```
    
  - Consumer 4:
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: group is already rebalancing
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Revoke previously assigned partitions 
    [Kafka-Consumer] INFO  - consumerGroupII: partitions revoked: []
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=7, memberId='consumer-consumerGroupII-1-7d923c2f-e4a7-4f0f-8570-9ce1a8a68795', protocol='range'}
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=7, memberId='consumer-consumerGroupII-1-7d923c2f-e4a7-4f0f-8570-9ce1a8a68795', protocol='range'}
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: 
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: []
    ```

  - Consumer 5: 
    ```shell
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Request joining group due to: need to re-join with the given member-id
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] (Re-)joining group
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully joined group with generation Generation{generationId=7, memberId='consumer-consumerGroupII-1-bd63dc44-748c-40c6-8562-0f037abda707', protocol='range'}
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Successfully synced group in generation Generation{generationId=7, memberId='consumer-consumerGroupII-1-bd63dc44-748c-40c6-8562-0f037abda707', protocol='range'}
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Notifying assignor about the new Assignment(partitions=[])
    [Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroupII-1, groupId=consumerGroupII] Adding newly assigned partitions: 
    [Kafka-Consumer] INFO  - consumerGroupII: partitions assigned: []
    ```
