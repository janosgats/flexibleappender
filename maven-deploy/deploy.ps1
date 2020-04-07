mkdir ${PSScriptRoot}/maven_repository_of_container

docker build -f ${PSScriptRoot}/Dockerfile -t flexibleappender-maven-deployer:latest ${PSScriptRoot}/..



### NORMAL OPERATION
#docker run -it -v ${PSScriptRoot}/..:/app -v ${PSScriptRoot}/maven_repository_of_container:/root/.m2/repository flexibleappender-maven-deployer:latest

### WINDOWS HOTFIX FOR 'docker.exe: Error response from daemon: file does not exist.'
docker run -it flexibleappender-maven-deployer:latest
