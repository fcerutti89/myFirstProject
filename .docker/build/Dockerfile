# Dockerfile
FROM tomcat:8.0
MAINTAINER Author f.cerutti@reply.it

CMD pwd

ADD /var/lib/jenkins/workspace/firstDockerPipeline/target/HelloWorld.war /usr/local/tomcat/webapps/

EXPOSE 10080