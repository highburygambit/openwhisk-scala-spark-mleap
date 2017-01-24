# openwhisk-scala-cloudant-action
An openWhisk docker action written in scala which communicates with IBM cloudant database. It's using a docker container to spin off a scala container which downloads the action builds and invokes the function.

# Pre-requisites
* openWhisk - https://github.com/openwhisk/openwhisk
* openwhisk CLI
* java 1.8+
* scala 2.11.6+ and sbt 0.13

# Quick Start

* git clone 
* cd scala-cloudant-action/
* Make sure you have IBM cloudant setup. Create a database and change credentials `src/main/resources/config.properties` 
    ```    
    username=<username> 
    password=<password> 
    host=<bluemix host> 
    port=443 
    url=<url> 
    dbname=<database that you just created> 
    key=<key provided by the cloudant db server> 
    passcode=<password>
    ```
    
* `sbt clean assembly`
* `cd ../` and Run `./buildAndPush.sh sanjeevghimire/scalaexample`
* Create the scala action using following script.

  `wsk action create --docker scalaexample sanjeevghimire/scalaexample`
* Invoke the action with necessary parameters:

  `wsk action invoke --blocking --result example --param cmd sendChat --param message ‘How are you?’`
  
  ```
    {
        "chatTS": 1.485286534412e+12,
        "from": "openwhisk",
        "text": "I am good!",
        "to": "user"
    }
  ```
* To get chat that you have sent use the following script:

  `wsk action invoke --blocking --result example --param command getChat`
  
  
  ```
      {
        "chatSessionId": "ZozbiUPzWiy7xPhA",
        "sessionStart": 1.485286490545e+12,
        "text": [
            {
                "chatTS": 1.485220172e+12,
                "from": "user",
                "text": "None",
                "to": "watson"
            },
            {
                "chatTS": 1.485281432e+12,
                "from": "user",
                "text": "how are you?",
                "to": "watson"
            },
            {
                "chatTS": 1.485220958e+12,
                "from": "user",
                "text": "hi",
                "to": "watson"
            },
            {
                "chatTS": 1.485220894e+12,
                "from": "user",
                "text": "hi",
                "to": "watson"
            }
        ]
    }
```
  
* To update the Docker action, run buildAndPush.sh to upload the latest image to Docker Hub. This will allow the system to pull your new Docker image the next time it runs the code for your action. 

  `./buildAndPush.sh sanjeevghimire/scalaexample`
  
  `wsk action update --docker scalaexample scala/example`
