# book delivery

![tests](https://github.com/sombriks/bookdelivery/actions/workflows/test.yml/badge.svg)
[![Docker Image Version (latest semver)](https://img.shields.io/docker/v/sombriks/bookdelivery?logo=docker&color=lightblue)](https://hub.docker.com/repository/docker/bookdelivery/general)

sample springboot application

## Requirements

- java 17
- mysql 8
- proper environment configuration

## How to run this project

Make sure the database is available by installing and configuring mysql or by
running the development sidecar:

```bash
docker compose -f src/infrastructure/docker-compose-dev.yml --env-file .env up -d 
```

Then you can start the spring application from your IDE or from command line
using maven:

```bash
./mvnw spring-boot:run
```

## Running the tests

You can use the unit test facilities from your IDE or run them from command line
using manve:

```bash
./mvnw clean package
```

It will produce the repackaged spring boot jar under
`bookdelivery/target/bookdelivery-0.0.1-SNAPSHOT.jar`

## CI/CD pipeline

In order to publish image on docker registry, the github action expects a git
tag creation.

You can either create one with the `git tag` command or use the github releases
page to do that. Example:

```bash
git tag 0.1.0
git push origin 0.1.0
```

Remember to follow semantic version and create te proper repository secrets so 
the action can login on docker hub with success.

## Further information

- how to create secrets on github
- how to use github releases page
- why use only the `latest` tag when tagging images is a very bad practice 
- 