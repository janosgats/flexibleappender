docker build -f $PSScriptRoot/Dockerfile -t flexibleappender-maven-deployer:latest $PSScriptRoot/..
docker run -it -v $PSScriptRoot/..:/app flexibleappender-maven-deployer:latest