#!/usr/bin/env bash

# Save cluster name...
CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)
# and create bootstrap server variable
BOOTSTRAP="${CLUSTER_NAME}"-w-0:9092

# Create all topics
# 1) All topics should be co-partitioned (should have the same number of partitions) to the needs of KStream-KTable join
# 2) We use 4 partitions for proper throughput
# 3) Use compact clean policy for permanent static data
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --create --topic inputBicycleResult --partitions 4
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --create --topic inputStationsList --config cleanup.policy=compact --partitions 4
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --create --topic outputAggregation --partitions 4
kafka-topics.sh --bootstrap-server "${BOOTSTRAP}" --create --topic outputAnomaly --partitions 4
