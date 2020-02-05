# Deploying to nexus staging repo
- Place the private gpg key in this directory with the name `gpg-private-flexibleappender.asc`
- Place maven's `settings.xml` file in this dir with the configured sonatype nexus repo user-token 
- Run `./deploy.ps1`
- Enter the passphrase for the gpg key two times