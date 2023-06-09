#!/usr/bin/env bash

# Run containerized MySQL database
docker run --name mymysql -v sqldata:/var/lib/mysql -p 6033:3306 \
-e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:debian

# Wait for dabatase
echo "Wait..."
sleep 40

# Create user streamuser and database streamdb
docker exec mymysql mysql -uroot -pmy-secret-pw --execute="CREATE
USER 'streamuser'@'%' IDENTIFIED BY 'stream';CREATE DATABASE IF NOT
EXISTS streamdb CHARACTER SET utf8;GRANT ALL ON streamdb.* TO
'streamuser'@'%';"

# Create aggregation_table and anomaly_table
docker exec mymysql mysql -u streamuser --password=stream --database=streamdb \
--execute="create table aggregation_table (day
varchar(200), station varchar(200), startCount int, stopCount int,
temperatureAverage double, primary key(day,station));create table
anomaly_table (windowStart varchar(200), windowEnd varchar(200),
stationName varchar(200), stopRedundancy int, startRedundancy int,
docksInService int, ratioN double);"

# Download MySQL connector
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/\
8.0.33/mysql-connector-j-8.0.33.jar

# Move MySQL connector
sudo cp mysql-connector-j-8.0.33.jar /usr/lib/kafka/libs

# Download Kafka JDBC connect
wget https://packages.confluent.io/maven/io/confluent/kafka-connect-jdbc/\
10.7.0/kafka-connect-jdbc-10.7.0.jar

# Move Kafka JDBC connect
sudo mkdir /usr/lib/kafka/plugin
sudo cp kafka-connect-jdbc-10.7.0.jar /usr/lib/kafka/plugin

# Create config for connect standalone
cat << EOF > connect-standalone.properties
plugin.path=/usr/lib/kafka/plugin
bootstrap.servers=localhost:9092
key.converter=org.apache.kafka.connect.storage.StringConverter
value.converter=org.apache.kafka.connect.json.JsonConverter
key.converter.schemas.enable=false
value.converter.schemas.enable=true
offset.storage.file.filename=/tmp/connect.offsets
offset.flush.interval.ms=10000
EOF

# Create connect jdbc sink config for aggregation_table
cat << EOF > connect-jdbc-sink-aggregation.properties
connection.url=jdbc:mysql://localhost:6033/streamdb
connection.user=streamuser
connection.password=stream
tasks.max=1
name=kafka-to-mysql-task1
connector.class=io.confluent.connect.jdbc.JdbcSinkConnector
topics=outputAggregation
table.name.format=aggregation_table
pk.mode=record_value
pk.fields=day,station
insert.mode=UPSERT
EOF

# Create connect jdbc sink config for anomaly_table
cat << EOF > connect-jdbc-sink-anomaly.properties
connection.url=jdbc:mysql://localhost:6033/streamdb
connection.user=streamuser
connection.password=stream
tasks.max=1
name=kafka-to-mysql-task2
connector.class=io.confluent.connect.jdbc.JdbcSinkConnector
topics=outputAnomaly
table.name.format=anomaly_table
pk.mode=none
insert.mode=INSERT
EOF

# Fix logger
sudo cp /usr/lib/kafka/config/tools-log4j.properties \
 /usr/lib/kafka/config/connect-log4j.properties
echo "log4j.logger.org.reflections=ERROR" | \
sudo tee -a /usr/lib/kafka/config/connect-log4j.properties

# Run connector
/usr/lib/kafka/bin/connect-standalone.sh connect-standalone.properties \
connect-jdbc-sink-aggregation.properties \
connect-jdbc-sink-anomaly.properties
