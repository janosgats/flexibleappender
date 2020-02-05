FROM centos

VOLUME /app

WORKDIR /app/maven-deploy

RUN yum install -y maven pinentry

CMD ["./container-startup.sh"]