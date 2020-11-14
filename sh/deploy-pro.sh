#!/usr/bin/env bash

method=$1

ip=13.115.40.203

if [[ "${method}"x = "init"x ]]; then
  scp docker-compose.yml start.sh root@${ip}:/root/app/fast-api
fi

ssh root@${ip} 'cd /root/app/fast-api && sh start.sh prod'
