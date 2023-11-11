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
# Ví dụ [05.UseKafkaConnect]
==============================================================

**Tham khảo**
- https://github.com/trandungchien1982/docker/tree/01.MySQL
- https://github.com/trandungchien1982/docker/tree/03.PostgreSQL
- https://debezium.io/documentation/reference/stable/connectors/mysql.html
- https://debezium.io/documentation/reference/stable/connectors/postgresql.html

**Yêu cầu**
- Dựng môi trường Kafka dùng Docker
- Tạo ra các KafkaConnector cần thiết và config để sync data từ MySQL sang PostgreSQL
  - MySQL, port : 3306
  - PostgreSQL, port : 5432

- Theo dõi các messages được MySQL Connector / PostgreSQL Connector sinh ra và đẩy vào Kafka Partitions từ Kafka Server UI

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

**Khởi động MySQL DB và biến nó thành Source trong KafkaConnect**
- Dựng MySQL DB Server với file `02.docker-compose-MySQL.yml`
- Tạo DB schema `tdchien` và sau đó thêm các tables: users, students, users01, orders, ...
- Trong Kafka UI, ta thêm 1 Connector mới với config của MySQLConnector như trong file `02.mysql-connector-config.properties`
```shell
name=MySQL-DB-Connector
connector.class=io.debezium.connector.mysql.MySqlConnector
tasks.max=1
database.user=root
database.password=root
database.hostname=MySQL-for-kafka-connect

database.history.kafka.topic=transfer-data-kafka-connect-topic
database.history.kafka.bootstrap.servers=localhost:9092
database.server.name=MySQL-DB-Server_SOURCE
database.port=3306
include.schema.changes=true

database.include.list=tdchien
table.include.list=students,users
table.exclude.list=orders

# Optional properties
database.protocol=jdbc:mysql
database.jdbc.driver=com.mysql.cj.jdbc.Driver
```

**Kiểm tra việc tạo MySQLConnector đã thành công hay chưa :)**
- Kiểm tra trong Kafka UI, mục Connectors 
- Vào xem chi tiết Connector mới tạo ra

**Kiểm tra data của MySQL DB đã được sync vào các topic như configs**
- Tiến hành open các topic với prefix đã chỉ định trong configuration
- Xem các topic = {prefix}
- Xem các topic = {prefix}_{table-name}

**Kiểm tra tính real-time của KafkaConnect bằng cách thêm rows vào DB Tables: students, users, ...**
- Thêm data row vào MySQL DB 
- Kiểm tra sự hiện của bản sao data đó trong topic tương ứng

**Tiếp tục tạo PostgreSQL Connector để consume data từ topic hiện tại (của MySQL)**
- TODO: Còn tiếp ...
