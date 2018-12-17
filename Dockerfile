# Dockerfile for example whisk docker action
FROM openwhisk/dockerskeleton 
FROM 99taxis/mini-java8

ENV FLASK_PROXY_PORT 8080

### Add source file(s)
#ADD example.c /action/example.c
ADD ./mleap-spark-action/whisk-action/exec /action/exec
ADD ./mleap-spark-action /action/mleap-spark-action

ADD /mleap-spark-action/target/scala-2.11/mleap.jar /action/jars/mleap.jar

RUN chmod +x /action/exec

# scala environment. Much easier than Java :). 
ENV SCALA_VERSION=2.11.7 \
	SCALA_HOME=/opt/scala

RUN apk add --no-cache --virtual=.build-dependencies wget ca-certificates && \
    apk add --no-cache bash && \
    cd "/tmp" && \
    wget "https://downloads.typesafe.com/scala/${SCALA_VERSION}/scala-${SCALA_VERSION}.tgz" && \
    tar xzf "scala-${SCALA_VERSION}.tgz" && \
    mkdir "${SCALA_HOME}" && \
    rm "/tmp/scala-${SCALA_VERSION}/bin/"*.bat && \
    mv "/tmp/scala-${SCALA_VERSION}/bin" "/tmp/scala-${SCALA_VERSION}/lib" "${SCALA_HOME}" && \
    ln -s "${SCALA_HOME}/bin/"* "/usr/bin/" && \
    apk del .build-dependencies && \
    rm -rf "/tmp/"*

### end of java scala installaiton.

ENV SBT_VERSION=0.13.8

## sbt installation for building
RUN apk add --no-cache --virtual=build-dependencies curl && \
    curl -sL "http://dl.bintray.com/sbt/native-packages/sbt/$SBT_VERSION/sbt-$SBT_VERSION.tgz" | gunzip | tar -x -C /usr/local && \
    ln -s /usr/local/sbt/bin/sbt /usr/local/bin/sbt && \
    chmod 0755 usr/local/bin/sbt && \
    apk del build-dependencies

#RUN cd /action/mleap-spark-action && sbt clean assembly

# ENV CLASSPATH $CLASSPATH:/action/mleap-spark-action/target/scala-2.11/mleap.jar
ENV CLASSPATH $CLASSPATH:/action/jars/mleap.jar
CMD ["/bin/bash", "-c", "cd actionProxy && python -u actionproxy.py"]
