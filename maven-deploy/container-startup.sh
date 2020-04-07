
cd /app/maven-deploy || exit

gpg2 --import gpg-private-flexibleappender.asc  || exit
cp settings.xml /etc/maven/ || exit

cd /app || exit
mvn clean deploy