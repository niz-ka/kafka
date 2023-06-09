#!/usr/bin/env bash

# Save cluster name...
CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)
# and create bootstrap server variable
BOOTSTRAP="${CLUSTER_NAME}"-w-0:9092

# Reset kafka streams application
/usr/lib/kafka/bin/kafka-streams-application-reset.sh --bootstrap-servers "${BOOTSTRAP}" --application-id kafka-streams-app \
--input-topics inputBicycleResult,inputStationsList --force

# Remove kafka streams directory
rm -rf /tmp/kafka-streams/kafka-streams-app

# Delete all topics
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --delete --topic inputBicycleResult
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --delete --topic inputStationsList
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --delete --topic outputAggregation
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --delete --topic outputAnomaly

# Wait for deletion
echo "Wait..."
sleep 3

# Remove files related to kafka connect
rm -rf connect-jdbc-sink-aggregation.properties \
connect-jdbc-sink-anomaly.properties \
connect-standalone.properties \
kafka-connect-jdbc-10.7.0.jar \
mysql-connector-j-8.0.33.jar

# Remove database container
docker container rm -f mymysql
docker volume prune -f

# Remove kafka connect plugin directory
sudo rm -rf /usr/lib/kafka/plugin
