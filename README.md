# akka-examples
A collection of samples using Akka


# How to run this samples

```bash 
git clone git@github.com:6za/akka-examples.git
cd akka-examples

docker run -it -w /app \
    --name scala-sandbox \
    -v $PWD:/app -p 0.0.0.0:8080:8080 \
    --rm sbtscala/scala-sbt:eclipse-temurin-jammy-8u352-b08_1.8.2_2.12.17 \
    /bin/bash

```

inside the container you can run: 

```bash 
cd intellij/akka-101/

sbt "runMain Main"
sbt "runMain JediValuesInAkkaStreams"
sbt "runMain JediValuesInAkkaStreamsSummingSink"
sbt "runMain AsynchSynch"
sbt "runMain AsynchSynchSimpleActor"
sbt "runMain AsynchSynchActor"

sbt "runMain TypedAkka"
sbt "runMain TypedAkkaMutable"
sbt "runMain TypedAkkaHierarchy"
sbt "runMain AkkaHttpServer1"

```

To test the AkkHTTPServer:
```bash 
 curl http://localhost:9090
```

```bash 
sbt "runMain api.MySimpleAPI"
curl http://localhost:9090
``` 

```bash
sbt "runMain api.PostAPI"
curl http://localhost:9090/test -X POST  -H "Content-Type: application/json"  -d '{"userId":"hhgtg","offer":42}'
```