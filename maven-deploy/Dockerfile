FROM centos

VOLUME /app
VOLUME /root/.m2/repository

WORKDIR /app/maven-deploy

RUN yum install -y maven pinentry

CMD ["./container-startup.sh"]