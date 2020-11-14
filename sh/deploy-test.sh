#!/usr/bin/env bash

method=$1

sh docker-build.sh

ip=13.114.76.26

if [[ "${method}"x = "init"x ]]; then
  scp docker-compose.yml start.sh root@${ip}:/root/app/fast-api
fi

ssh root@${ip} 'cd /root/app/fast-api && sh start.sh test'
