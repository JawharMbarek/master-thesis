# Google+ platform Java Command Line Sample #

This project requires:

 - Java 1.6
 - Maven <http://maven.apache.org/>

## Setup Authentication ##

Google supports a recent draft of the OAuth 2.0 protocol for authorizing access to
private user data. You can learn more about OAuth 2.0 at Google here:
http://code.google.com/apis/accounts/docs/OAuth2.html

### Register your Application ###

 - Visit https://code.google.com/apis/console/?api=plus to create a new project
   with access to the Google+ API or to add the Google+ API to an existing project.
 - Click on "API Access" in the left column
 - Click the button labeled "Create an OAuth2 client ID"
 - Give your application a name and click "Next"
 - Select "Installed Application" as the "Application type" and click "Create client ID"

### Configure the Starter ####

Edit the file src/main/resources/config.properties, and enter the values
for the following properties that you retrieved from the API Console:

 - oauth_client_id
 - oauth_client_secret
 - google_api_key

Compile using the command:

    mvn compile

That may take a while if you've never run maven before.  Then use this command to run the sample

    mvn exec:java


## OPTIONAL - Setup Project in Eclipse 3.5/3.6 ##

Prerequisites:
 - Eclipse <http://www.eclipse.org/downloads/>
 - Maven plugin <http://m2eclipse.sonatype.org/installing-m2eclipse.html>

### Setup Eclipse Preferences ###

 - Window > Preferences... (or on Mac, Eclipse > Preferences...)
 - Select Maven
 - check on "Download Artifact Sources"
 - check on "Download Artifact JavaDoc"

Import google-plus-java-cli-starter project
 
 - File > Import...
 - Select "General > Existing Project into Workspace" and click "Next"
 - Click "Browse" next to "Select root directory", find .../path/to/google-plus-java-starter and click "Next"
 - Click "Finish"

Run

 - Click on the run configuration shortcut drop down (the little black down arrow next to the green 
   circle with a white arrow) and select this run configuration: google-plus-java-cli-starter
