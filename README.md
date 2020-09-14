# Travel-Time-Optimizer

```docker
docker run --name tto-postgres -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=postgres -p 5432:5432 -d postgres:alpine
```

Copy paste API Key to application.yml

```shell script
export GPG_TTY=$(tty)
```

```docker

docker run -e TTO_MAPS_API_KEY="" -e SPRING_DATASOURCE_URL="jdbc:postgresql://192.168.178.53:5432/postgres" -e TTO_SCHEDULING="0 0/2 * * * *" -d lukasjusten/tto-backend:latest

```