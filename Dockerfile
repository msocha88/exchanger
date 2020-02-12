FROM openjdk:8u201-jdk-alpine3.9
ADD target/exchanger-0.0.1-SNAPSHOT.jar .
EXPOSE 12121
CMD java -jar exchanger-0.0.1-SNAPSHOT.jar
# --security-opt apparmor=unconfined