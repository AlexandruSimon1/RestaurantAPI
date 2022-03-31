#!/usr/bin/bash
while ! exec 6<>/dev/tcp/${DATABASE}/3306; do
    echo "Trying to connect to MySQL at Amazon RDS Database..."
    sleep 10
done
echo ">> connected to MySQL database! <<"
java -Dspring.profiles.active=dev -Djasypt.encryptor.password=${PASSWORD} -Djava.security.egd=file:/dev/./urandom -jar /opt/restaurant-spring-boot.jar
