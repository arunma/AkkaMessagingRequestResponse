# Akka Kamon

For a detail description read [the blog post](http://mukis.de/pages/monitoring-akka-with-kamon/).

# Run it

Run the docker container with all the statsd backend/frontend stuff

```bash
docker run -v /etc/localtime:/etc/localtime:ro -p 80:80 -p 8125:8125/udp -p 8126:8126 -p 8083:8083 -p 8086:8086 -p 8084:8084 --name kamon-grafana-dashboard muuki88/grafana_graphite:latest
```

In another terminal start the application

```
sbt run
```

Go to [localhost](http://localhost) and configure your dashboard.

# Setup Activator

1. [Download Typesafe Activator](http://typesafe.com/platform/getstarted) (or copy it over from a USB)
2. Extract the zip and run the `activator` or `activator.bat` script from a non-interactive shell
3. Your browser should open to the Activator UI: [http://localhost:8888](http://localhost:8888)

