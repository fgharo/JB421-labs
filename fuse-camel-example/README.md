Build source code/dependencies into uber/fat jar with maven. Jar should get put in target/

```
$ mvn clean install
```

Build container image with podman (or docker). Name container camel-with-spring-boot to make it easier to manage.

```
$ sudo podman build -t camel-with-spring-boot .
```

Run container locally with podman (or docker)

```
$ sudo podman run -d -p 8080 camel-with-spring-boot
```

Assume container is listening on host port 35156 or was assigned 35156 by podman and
the above command redirects traffic received to port 8080. Then we can send a request to the
java rest service listening on port 8080 inside the container as follows: 


```
$ curl -si http://localhost:35156/camel/hello/Developer
HTTP/1.1 200 OK
...
{
  greeting: Hello, Developer
  server: workstation.lab.example.com
}
```

Note: Default port Spring boot listens on 8080 can be configured in 
application.properties.