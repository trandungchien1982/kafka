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
# Ví dụ [01.HelloWorld]
==============================================================

**Tham khảo**
- https://www.baeldung.com/spring-kafka

**Yêu cầu**
- Dựng môi trường Kafka dùng Docker
- Tạo 1 App Spring Boot đóng vai trò Producer (`spring-kafka-producer`)
- Tạo 1 App Spring Boot đóng vai trò Consumer (`spring-kafka-consumer`)
- Theo dõi các messages được Producer sinh ra và đẩy vào Kafka Partitions
- Theo dõi các messages đã được Consume

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

- Produce nhiều messages vào topic `hello-world-topic` thông qua endpoint lặp lại nhiều lần :) : <br />
(Từ count_10 đến count_30)
```shell
http://localhost:9117/send

```

- Kiểm tra việc consume các messages trong topic `hello-world-topic`: <br />
  - (Xem trong Console Log của Consumer) <br/>
  - Có vẻ như offset của các partition được assign vào topic đã được gán về cuối cùng nên Consumer không thấy record nào mới cả ...

```shell
15:30:11: Executing ':KafkaConsumerApp.main()'...

> Task :compileJava
> Task :processResources UP-TO-DATE
> Task :classes

> Task :KafkaConsumerApp.main()

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.16)

[Kafka-Consumer] INFO  - Starting KafkaConsumerApp using Java 1.8.0_333 on tdc with PID 211985 (/home/tdc/kafka/spring-kafka-consumer/build/classes/java/main started by tdc in /home/tdc/kafka/spring-kafka-consumer)
[Kafka-Consumer] INFO  - No active profile set, falling back to 1 default profile: "default"
[Kafka-Consumer] INFO  - Tomcat initialized with port(s): 9118 (http)
[Kafka-Consumer] INFO  - Initializing ProtocolHandler ["http-nio-9118"]
[Kafka-Consumer] INFO  - Starting service [Tomcat]
[Kafka-Consumer] INFO  - Starting Servlet engine: [Apache Tomcat/9.0.80]
[Kafka-Consumer] INFO  - Initializing Spring embedded WebApplicationContext
[Kafka-Consumer] INFO  - Root WebApplicationContext: initialization completed in 1003 ms
[Kafka-Consumer] INFO  - ConsumerConfig values: 
  allow.auto.create.topics = true
  auto.commit.interval.ms = 5000
  auto.offset.reset = latest
  bootstrap.servers = [http://localhost:9092]
  check.crcs = true
  client.dns.lookup = use_all_dns_ips
  client.id = consumer-consumerGroup1s-1
  client.rack = 
  connections.max.idle.ms = 540000
  default.api.timeout.ms = 60000
  enable.auto.commit = false
  exclude.internal.topics = true
  fetch.max.bytes = 52428800
  fetch.max.wait.ms = 500
  fetch.min.bytes = 1
  group.id = consumerGroup1s
  group.instance.id = null
  heartbeat.interval.ms = 3000
  interceptor.classes = []
  internal.leave.group.on.close = true
  internal.throw.on.fetch.stable.offset.unsupported = false
  isolation.level = read_uncommitted
  key.deserializer = class org.apache.kafka.common.serialization.StringDeserializer
  max.partition.fetch.bytes = 1048576
  max.poll.interval.ms = 300000
  max.poll.records = 500
  metadata.max.age.ms = 300000
  metric.reporters = []
  metrics.num.samples = 2
  metrics.recording.level = INFO
  metrics.sample.window.ms = 30000
  partition.assignment.strategy = [class org.apache.kafka.clients.consumer.RangeAssignor, class org.apache.kafka.clients.consumer.CooperativeStickyAssignor]
  receive.buffer.bytes = 65536
  reconnect.backoff.max.ms = 1000
  reconnect.backoff.ms = 50
  request.timeout.ms = 30000
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
  session.timeout.ms = 45000
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
  value.deserializer = class org.apache.kafka.common.serialization.StringDeserializer

[Kafka-Consumer] INFO  - Kafka version: 3.1.2
[Kafka-Consumer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Consumer] INFO  - Kafka startTimeMs: 1699259418234
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Subscribed to topic(s): hello-world-topic
[Kafka-Consumer] INFO  - Starting ProtocolHandler ["http-nio-9118"]
[Kafka-Consumer] INFO  - Tomcat started on port(s): 9118 (http) with context path ''
[Kafka-Consumer] INFO  - Started KafkaConsumerApp in 2.3 seconds (JVM running for 2.769)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Cluster ID: 0Ra5Z1t0S3CQ_tq0fznaIg
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Request joining group due to: need to re-join with the given member-id
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] (Re-)joining group
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroup1s-1-b08ce700-0613-4ab0-be40-71af4063c124', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Finished assignment for group at generation 1: {consumer-consumerGroup1s-1-b08ce700-0613-4ab0-be40-71af4063c124=Assignment(partitions=[hello-world-topic-0, hello-world-topic-1, hello-world-topic-2])}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Successfully synced group in generation Generation{generationId=1, memberId='consumer-consumerGroup1s-1-b08ce700-0613-4ab0-be40-71af4063c124', protocol='range'}
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Notifying assignor about the new Assignment(partitions=[hello-world-topic-0, hello-world-topic-1, hello-world-topic-2])
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Adding newly assigned partitions: hello-world-topic-0, hello-world-topic-2, hello-world-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-0
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-2
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Found no committed offset for partition hello-world-topic-1
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Resetting offset for partition hello-world-topic-0 to position FetchPosition{offset=7, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Resetting offset for partition hello-world-topic-2 to position FetchPosition{offset=8, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - [Consumer clientId=consumer-consumerGroup1s-1, groupId=consumerGroup1s] Resetting offset for partition hello-world-topic-1 to position FetchPosition{offset=6, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Consumer] INFO  - consumerGroup1s: partitions assigned: [hello-world-topic-0, hello-world-topic-2, hello-world-topic-1]

```


- Tiến hành produce thêm 10 messages nữa, bắt đầu từ index 100
  - Xem kết quả bên Producer :
```shell
[Kafka-Producer] INFO  - [Producer clientId=producer-1] Instantiated an idempotent producer.
[Kafka-Producer] INFO  - Kafka version: 3.1.2
[Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699259801128
[Kafka-Producer] INFO  - [Producer clientId=producer-1] Cluster ID: 0Ra5Z1t0S3CQ_tq0fznaIg
[Kafka-Producer] INFO  - [Producer clientId=producer-1] ProducerId set to 1 with epoch 0
```

  - Xem kết quả bên Consumer : 
```shell
[Kafka-Consumer] INFO  - consumerGroup1s: partitions assigned: [hello-world-topic-0, hello-world-topic-2, hello-world-topic-1]
[Kafka-Consumer] INFO  - 1699260010665 -- Received Message: Message-Type1 : count_103 , topic: hello-world-topic, partition: 0, offset: 7, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_105 , topic: hello-world-topic, partition: 0, offset: 8, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_109 , topic: hello-world-topic, partition: 0, offset: 9, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_101 , topic: hello-world-topic, partition: 2, offset: 8, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_104 , topic: hello-world-topic, partition: 2, offset: 9, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_106 , topic: hello-world-topic, partition: 2, offset: 10, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_108 , topic: hello-world-topic, partition: 2, offset: 11, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_110 , topic: hello-world-topic, partition: 2, offset: 12, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_100 , topic: hello-world-topic, partition: 1, offset: 6, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_102 , topic: hello-world-topic, partition: 1, offset: 7, time: Mon Nov 06 15:40:10 ICT 2023
[Kafka-Consumer] INFO  - 1699260010666 -- Received Message: Message-Type1 : count_107 , topic: hello-world-topic, partition: 1, offset: 8, time: Mon Nov 06 15:40:10 ICT 2023

```
