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
# Ví dụ [03.ApplyPubSub]
==============================================================

**Tham khảo**
- https://viblo.asia/p/tim-hieu-ve-consumer-va-consumer-group-trong-kafka-Qbq5Q77XZD8
- https://vsudo.net/blog/kafka-consumer.html
- https://aithietke.com/tim-hieu-ve-consumer-va-consumer-group-trong-kafka/
- https://hackmd.io/@datbv/r1ub5ZpkF

**Yêu cầu**
- Dựng môi trường Kafka dùng Docker
- Tạo 1 App Spring Boot đóng vai trò Producer (`spring-kafka-producer`)
- Tạo 1 App Spring Boot đóng vai trò Subscriber (`spring-kafka-subscriber`)
  - Mở Port ngẫu nhiên từ 9150 đến 9160
  - Mỗi lần chạy instance của consumer thì nó sẽ thuộc Consumer Group `consumerGroupX_{1-10}` của topic: `pub-sub-topic`
- Theo dõi các messages được Producer sinh ra và đẩy vào Kafka Partitions từ Kafka Server UI
- Theo dõi các messages đã được Consume/Subscribe dựa vào Console Log

**Kiểm tra kết quả**
- Dùng Docker Compose dựng môi trường Kafka Dev, Kafka Broker giao tiếp ở port 9092
- Truy cập UI của Kafka Server: http://localhost:3030
```shell
docker-compose -f 01.docker-compose-KafkaServer.yml up
------------------------------------------------------------------------------
tdc@tdc:~/kafka$ docker-compose -f 01.docker-compose-KafkaServer.yml up
Creating kafka_fast-dev-kafka_1 ... done
Attaching to kafka_fast-dev-kafka_1
fast-dev-kafka_1  | Setting advertised host to 127.0.0.1.
fast-dev-kafka_1  | Starting services.
fast-dev-kafka_1  | This is Lenses.io’s fast-data-dev. Kafka 2.6.2-L0 (Lenses.io's Kafka Distribution).
fast-dev-kafka_1  | You may visit http://127.0.0.1:3030 in about a minute.
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/01-zookeeper.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/02-broker.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/03-schema-registry.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/04-rest-proxy.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/05-connect-distributed.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,593 INFO Included extra file "/etc/supervisord.d/06-caddy.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,594 INFO Included extra file "/etc/supervisord.d/07-smoke-tests.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,594 INFO Included extra file "/etc/supervisord.d/08-logs-to-kafka.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,594 INFO Included extra file "/etc/supervisord.d/99-supervisord-sample-data.conf" during parsing
fast-dev-kafka_1  | 2023-11-07 03:18:42,594 INFO Set uid to user 0 succeeded
fast-dev-kafka_1  | 2023-11-07 03:18:42,604 INFO RPC interface 'supervisor' initialized
fast-dev-kafka_1  | 2023-11-07 03:18:42,604 CRIT Server 'unix_http_server' running without any HTTP authentication checking
fast-dev-kafka_1  | 2023-11-07 03:18:42,605 INFO supervisord started with pid 7
fast-dev-kafka_1  | 2023-11-07 03:18:43,609 INFO spawned: 'broker' with pid 180
fast-dev-kafka_1  | 2023-11-07 03:18:43,639 INFO spawned: 'caddy' with pid 182
fast-dev-kafka_1  | 2023-11-07 03:18:43,645 INFO spawned: 'connect-distributed' with pid 183
fast-dev-kafka_1  | 2023-11-07 03:18:43,652 INFO spawned: 'logs-to-kafka' with pid 185
fast-dev-kafka_1  | 2023-11-07 03:18:43,659 INFO spawned: 'rest-proxy' with pid 186
fast-dev-kafka_1  | 2023-11-07 03:18:43,666 INFO spawned: 'sample-data' with pid 187
fast-dev-kafka_1  | 2023-11-07 03:18:43,670 INFO spawned: 'schema-registry' with pid 189
fast-dev-kafka_1  | 2023-11-07 03:18:43,676 INFO spawned: 'smoke-tests' with pid 192
fast-dev-kafka_1  | 2023-11-07 03:18:43,681 INFO spawned: 'zookeeper' with pid 195
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: broker entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: caddy entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: connect-distributed entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: logs-to-kafka entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: rest-proxy entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: sample-data entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: schema-registry entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,797 INFO success: smoke-tests entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
fast-dev-kafka_1  | 2023-11-07 03:18:44,798 INFO success: zookeeper entered RUNNING state, process has stayed up for > than 1 seconds (startsecs)
```

**Kiểm tra Console Log của Producer**
```shell
./gradlew bootRun
--------------------------------------------------
> Task :compileJava UP-TO-DATE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE
> Task :bootRunMainClassName

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.16)

[Kafka-Producer] INFO  - Starting KafkaProducerApp using Java 11.0.20.1 on tdc with PID 41761 (/home/tdc/kafka/spring-kafka-producer/build/classes/java/main started by tdc in /home/tdc/kafka/spring-kafka-producer)
[Kafka-Producer] INFO  - No active profile set, falling back to 1 default profile: "default"
[Kafka-Producer] INFO  - Tomcat initialized with port(s): 9140 (http)
[Kafka-Producer] INFO  - Initializing ProtocolHandler ["http-nio-9140"]
[Kafka-Producer] INFO  - Starting service [Tomcat]
[Kafka-Producer] INFO  - Starting Servlet engine: [Apache Tomcat/9.0.80]
[Kafka-Producer] INFO  - Initializing Spring embedded WebApplicationContext
[Kafka-Producer] INFO  - Root WebApplicationContext: initialization completed in 821 ms
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
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699327386067
[Kafka-Producer] INFO  - App info kafka.admin.client for adminclient-1 unregistered
[Kafka-Producer] INFO  - Metrics scheduler closed
[Kafka-Producer] INFO  - Closing reporter org.apache.kafka.common.metrics.JmxReporter
[Kafka-Producer] INFO  - Metrics reporters closed
[Kafka-Producer] INFO  - Starting ProtocolHandler ["http-nio-9140"]
[Kafka-Producer] INFO  - Tomcat started on port(s): 9140 (http) with context path ''
[Kafka-Producer] INFO  - Started KafkaProducerApp in 2.211 seconds (JVM running for 2.654)
```


**Kiểm tra việc consume các messages trong topic `pub-sub-topic`:**
(Xem trong Console Log của Consumer) <br/>
- Khi chạy instance `spring-kafka-subscriber` thứ nhất (thuộc Consumer Group `consumerGroupX_9` và chỉ có 1 Consumer) <br />
  (partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Sub] INFO  - Kafka version: 3.1.2
[Kafka-Sub] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Sub] INFO  - Kafka startTimeMs: 1699327774531
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Subscribed to topic(s): pub-sub-topic
[Kafka-Sub] INFO  - Starting ProtocolHandler ["http-nio-9152"]
[Kafka-Sub] INFO  - Tomcat started on port(s): 9152 (http) with context path ''
[Kafka-Sub] INFO  - Started KafkaPubSubApp in 1.554 seconds (JVM running for 1.89)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Cluster ID: a5DqnTTaQkqzVtCIK97jgQ
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Request joining group due to: need to re-join with the given member-id
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroupX_9-1-071b91f0-f856-4e2a-bdfa-4fadfa93266c', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Finished assignment for group at generation 1: {consumer-consumerGroupX_9-1-071b91f0-f856-4e2a-bdfa-4fadfa93266c=Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Successfully synced group in generation Generation{generationId=1, memberId='consumer-consumerGroupX_9-1-071b91f0-f856-4e2a-bdfa-4fadfa93266c', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Notifying assignor about the new Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Adding newly assigned partitions: pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Resetting offset for partition pub-sub-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Resetting offset for partition pub-sub-topic-1 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_9-1, groupId=consumerGroupX_9] Resetting offset for partition pub-sub-topic-2 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - consumerGroupX_9: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
```

- Khi chạy instance `spring-kafka-subscriber` thứ hai (lúc này Consumer Group `consumerGroupX_3` cũng chỉ có 1 Consumer) <br />
  (partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Sub] INFO  - Kafka version: 3.1.2
[Kafka-Sub] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Sub] INFO  - Kafka startTimeMs: 1699328100210
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Subscribed to topic(s): pub-sub-topic
[Kafka-Sub] INFO  - Starting ProtocolHandler ["http-nio-9155"]
[Kafka-Sub] INFO  - Tomcat started on port(s): 9155 (http) with context path ''
[Kafka-Sub] INFO  - Started KafkaPubSubApp in 1.575 seconds (JVM running for 1.901)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Cluster ID: a5DqnTTaQkqzVtCIK97jgQ
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Request joining group due to: need to re-join with the given member-id
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroupX_3-1-e1f5dc07-06eb-46eb-ab46-ae6df9e8f947', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Finished assignment for group at generation 1: {consumer-consumerGroupX_3-1-e1f5dc07-06eb-46eb-ab46-ae6df9e8f947=Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Successfully synced group in generation Generation{generationId=1, memberId='consumer-consumerGroupX_3-1-e1f5dc07-06eb-46eb-ab46-ae6df9e8f947', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Notifying assignor about the new Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Adding newly assigned partitions: pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Resetting offset for partition pub-sub-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Resetting offset for partition pub-sub-topic-1 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_3-1, groupId=consumerGroupX_3] Resetting offset for partition pub-sub-topic-2 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - consumerGroupX_3: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
```


- Khi chạy instance `spring-kafka-subscriber` thứ ba (lúc này Consumer Group `consumerGroupX_6` cũng chỉ có 1 Consumer) <br />
  (partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
./gradlew bootRun
--------------------------------------------------
[Kafka-Sub] INFO  - Kafka version: 3.1.2
[Kafka-Sub] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Sub] INFO  - Kafka startTimeMs: 1699328187999
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Subscribed to topic(s): pub-sub-topic
[Kafka-Sub] INFO  - Starting ProtocolHandler ["http-nio-9159"]
[Kafka-Sub] INFO  - Tomcat started on port(s): 9159 (http) with context path ''
[Kafka-Sub] INFO  - Started KafkaPubSubApp in 1.461 seconds (JVM running for 1.773)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Cluster ID: a5DqnTTaQkqzVtCIK97jgQ
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Discovered group coordinator 127.0.0.1:9092 (id: 2147483647 rack: null)
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Request joining group due to: need to re-join with the given member-id
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] (Re-)joining group
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Successfully joined group with generation Generation{generationId=1, memberId='consumer-consumerGroupX_6-1-f619ce95-bb1d-47e7-b8c9-d8ab7b3ab18d', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Finished assignment for group at generation 1: {consumer-consumerGroupX_6-1-f619ce95-bb1d-47e7-b8c9-d8ab7b3ab18d=Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Successfully synced group in generation Generation{generationId=1, memberId='consumer-consumerGroupX_6-1-f619ce95-bb1d-47e7-b8c9-d8ab7b3ab18d', protocol='range'}
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Notifying assignor about the new Assignment(partitions=[pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Adding newly assigned partitions: pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-0
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-1
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Found no committed offset for partition pub-sub-topic-2
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Resetting offset for partition pub-sub-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Resetting offset for partition pub-sub-topic-1 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - [Consumer clientId=consumer-consumerGroupX_6-1, groupId=consumerGroupX_6] Resetting offset for partition pub-sub-topic-2 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[127.0.0.1:9092 (id: 0 rack: null)], epoch=0}}.
[Kafka-Sub] INFO  - consumerGroupX_6: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
```


**Tiến hành produce 11 messages, bắt đầu từ index 100 đến 110**
- Xem kết quả bên Producer :
```shell
[Kafka-Producer] INFO  - [Producer clientId=producer-1] Instantiated an idempotent producer.
[Kafka-Producer] INFO  - Kafka version: 3.1.2
[Kafka-Producer] INFO  - Kafka commitId: f8c67dc3ae0a3265
[Kafka-Producer] INFO  - Kafka startTimeMs: 1699328293563
[Kafka-Producer] INFO  - [Producer clientId=producer-1] Cluster ID: a5DqnTTaQkqzVtCIK97jgQ
[Kafka-Producer] INFO  - [Producer clientId=producer-1] ProducerId set to 0 with epoch 0
```
    
- Xem kết quả bên Subscriber 1: <br />
(consumerGroupX_9: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
[Kafka-Sub] INFO  - consumerGroupX_9: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_100 , topic: pub-sub-topic, partition: 2, offset: 0, time: Tue Nov 07 10:38:13 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_101 , topic: pub-sub-topic, partition: 0, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_102 , topic: pub-sub-topic, partition: 1, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_103 , topic: pub-sub-topic, partition: 2, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_104 , topic: pub-sub-topic, partition: 1, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_105 , topic: pub-sub-topic, partition: 0, offset: 1, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_106 , topic: pub-sub-topic, partition: 1, offset: 2, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_107 , topic: pub-sub-topic, partition: 0, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_108 , topic: pub-sub-topic, partition: 2, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_109 , topic: pub-sub-topic, partition: 0, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9154] -- Received Message: Message-Type1 : count_110 , topic: pub-sub-topic, partition: 1, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
```

- Xem kết quả bên Subscriber 2: <br />
(consumerGroupX_3: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
[Kafka-Sub] INFO  - consumerGroupX_3: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_100 , topic: pub-sub-topic, partition: 2, offset: 0, time: Tue Nov 07 10:38:13 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_101 , topic: pub-sub-topic, partition: 0, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_102 , topic: pub-sub-topic, partition: 1, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_103 , topic: pub-sub-topic, partition: 2, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_104 , topic: pub-sub-topic, partition: 1, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_105 , topic: pub-sub-topic, partition: 0, offset: 1, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_106 , topic: pub-sub-topic, partition: 1, offset: 2, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_107 , topic: pub-sub-topic, partition: 0, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_108 , topic: pub-sub-topic, partition: 2, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_109 , topic: pub-sub-topic, partition: 0, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9156] -- Received Message: Message-Type1 : count_110 , topic: pub-sub-topic, partition: 1, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
```

- Xem kết quả bên Subscriber 3: <br />
  (consumerGroupX_6: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2])
```shell
[Kafka-Sub] INFO  - consumerGroupX_6: partitions assigned: [pub-sub-topic-0, pub-sub-topic-1, pub-sub-topic-2]
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_100 , topic: pub-sub-topic, partition: 2, offset: 0, time: Tue Nov 07 10:38:13 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_101 , topic: pub-sub-topic, partition: 0, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_102 , topic: pub-sub-topic, partition: 1, offset: 0, time: Tue Nov 07 10:38:15 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_103 , topic: pub-sub-topic, partition: 2, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_104 , topic: pub-sub-topic, partition: 1, offset: 1, time: Tue Nov 07 10:38:16 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_105 , topic: pub-sub-topic, partition: 0, offset: 1, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_106 , topic: pub-sub-topic, partition: 1, offset: 2, time: Tue Nov 07 10:38:17 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_107 , topic: pub-sub-topic, partition: 0, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_108 , topic: pub-sub-topic, partition: 2, offset: 2, time: Tue Nov 07 10:38:18 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_109 , topic: pub-sub-topic, partition: 0, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
[Kafka-Sub] INFO  - ServerPort: [9152] -- Received Message: Message-Type1 : count_110 , topic: pub-sub-topic, partition: 1, offset: 3, time: Tue Nov 07 10:38:19 ICT 2023
```
