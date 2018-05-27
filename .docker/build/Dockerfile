# Dockerfile
FROM tomcat:8.0
MAINTAINER Author f.cerutti@reply.it

ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

ADD ./target/*.war $CATALINA_HOME/webapps/

EXPOSE 10080