# Dockerfile for example whisk docker action
FROM openwhisk/dockerskeleton 

ENV FLASK_PROXY_PORT 8080
# Install Java 8
RUN apk add openjdk8

### Add source file(s)
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
    mkdir -p "${SCALA_HOME}" && \
    rm "/tmp/scala-${SCALA_VERSION}/bin/"*.bat && \
    mv "/tmp/scala-${SCALA_VERSION}/bin" "/tmp/scala-${SCALA_VERSION}/lib" "${SCALA_HOME}" && \
    ln -s "${SCALA_HOME}/bin/"* "/usr/bin/" && \
    apk del .build-dependencies && \
    rm -rf "/tmp/"*

ENV CLASSPATH $CLASSPATH:/action/mleap-spark-action/target/scala-2.11/mleap.jar
ENV CLASSPATH $CLASSPATH:/action/jars/mleap.jar
CMD ["/bin/bash", "-c", "cd actionProxy && python -u actionproxy.py"]
