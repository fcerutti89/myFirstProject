#Dockerfile
FROM tomcat:8-jre8
#https://github.com/docker-library/tomcat

ENV CATALINA_HOME /usr/local/tomcat

ADD ./target/HelloWorld.war $CATALINA_HOME/webapps/

EXPOSE 10080