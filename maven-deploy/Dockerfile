FROM centos

RUN yum install -y maven pinentry

WORKDIR /app



### NORMAL OPERATION
#VOLUME /app
#VOLUME /root/.m2/repository



### WINDOWS HOTFIX FOR 'docker.exe: Error response from daemon: file does not exist.'
COPY ./pom.xml /app/pom.xml
RUN mvn dependency:go-offline
COPY ./maven-deploy /app/maven-deploy
COPY ./src /app/src

ENTRYPOINT "sh"
CMD ["-c", "/app/maven-deploy/container-startup.sh"]