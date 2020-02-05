docker build -f Dockerfile -t flexibleappender-maven-deployer:latest ./..
docker run -it -v ${PWD}/..:/app flexibleappender-maven-deployer:latest