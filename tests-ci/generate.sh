#!/bin/bash

callApi() {
  local file="$1"
  local api="$2"
  curl -X POST \
    -H "accept: */*" \
    -H "Content-Type: application/json" -d @$file \
    "http://localhost:7471"$api
}

callApi beer.json "/api/projects/init"
callApi beer.json "/api/build-tools/maven"
callApi beer.json "/api/servers/java/base"
callApi beer.json "/api/servers/java/jacoco-minimum-coverage"
callApi beer.json "/api/servers/spring-boot"
callApi beer.json "/api/servers/spring-boot/banner/jhipster-v7"
callApi beer.json "/api/servers/spring-boot/mvc/web/tomcat"
callApi beer.json "/api/servers/spring-boot/mvc/security/jwt"
callApi beer.json "/api/servers/spring-boot/databases/postgresql"
callApi beer.json "/api/servers/spring-boot/databases/migration/liquibase"

echo "Waiting 5sec..."
sleep 5
