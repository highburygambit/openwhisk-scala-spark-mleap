# openwhisk-scala-spark-mleap
An openWhisk docker action written in scala which communicates with IBM cloudant database. It's using a docker container to spin off a scala container which downloads the action builds and invokes the function.

# Pre-requisites
* openWhisk - https://github.com/openwhisk/openwhisk
* openwhisk CLI
* java 1.8+
* scala 2.11.6+ and sbt 0.13

# Run The Example



# Write Your Own Example

* git clone https://github.com/sanjeevghimire/openwhisk-scala-cloudant-action.git
* cd scala-cloudant-action/

* `sbt clean assembly`
* `cd ../` and Run `./buildAndPush.sh sanjeevghimire/scalaexample`
* Create the scala action using following script.

  `wsk action create --docker scalaexample sanjeevghimire/scalaexample`
* Invoke the action with necessary parameters:

  `wsk action invoke --blocking --result scalaexample --param cmd sendChat --param message ‘How are you?’`
  
  ```
    {
        "chatTS": 1.485286534412e+12,
        "from": "openwhisk",
        "text": "I am good!",
        "to": "user"
    }
  ```
* To get chat that you have sent use the following script:

  `wsk action invoke --blocking --result scalaexample --param command getChat`
  
  
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
  
