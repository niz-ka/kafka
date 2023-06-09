#!/usr/bin/env bash 

# Show first 30 rows of tables
docker exec -t mymysql mysql -uroot -pmy-secret-pw --database=streamdb --execute="SELECT * FROM aggregation_table" | head -n 30;
docker exec -t mymysql mysql -uroot -pmy-secret-pw --database=streamdb --execute="SELECT * FROM anomaly_table" | head -n 30;
