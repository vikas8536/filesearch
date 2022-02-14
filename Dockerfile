FROM openjdk:8u121-jre-alpine

WORKDIR /var/rokt/filesearch/

ADD target/filesearch-1.0-SNAPSHOT.jar /var/rokt/filesearch/filesearch-1.0-SNAPSHOT.jar
ADD environment/prod_config.yaml /var/rokt/filesearch/config.yml

EXPOSE 9090 9091

ENTRYPOINT ["java", "-jar", "filesearch-1.0-SNAPSHOT.jar", "server", "config.yml"]