FROM fabric8/s2i-java:latest-java11
# Building Fat jar or uber jar
COPY target/*.jar /deployments/app.jar
#Default entry point for fabric8/s2i-java ENTRYPOINT ["./run-java.sh"]
