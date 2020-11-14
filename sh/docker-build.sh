#!/usr/bin/env bash

cd ..

mvn clean package -DskipTests -q

# $(aws ecr get-login --no-include-email --region ap-east-1)
docker login -u "username" -p "password" registry.cn-hongkong.aliyuncs.com

export CONFIG_ENV=dev
docker-compose build --compress
docker-compose push
docker image prune -f
