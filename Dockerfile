FROM maven:3.3.9-jdk-8

MAINTAINER BLF2 blf20822@126.com

RUN mkdir /javatest
WORKDIR /javatest
COPY . /javatest
RUN wget http://115.159.107.50:32768/apache-tomcat-8.5.5.tar.gz
RUN tar -zxvf apache-tomcat-8.5.5.tar.gz
RUN mvn package
RUN rm -r ./apache-tomcat-8.5.5/webapps/*
RUN mv ./target/QL.war ./apache-tomcat-8.5.5/webapps/ROOT.war

CMD /javatest/apache-tomcat-8.5.5/bin/catalina.sh run
EXPOSE 8080
