cd /efs/hsms/hsms-boot
kill -15 `ps -ef | grep hsms-boot-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
sh ./mvnw clean package  -Dmaven.test.skip=true
java -jar ./target/hsms-boot-0.0.1-SNAPSHOT.jar > hsms.log &