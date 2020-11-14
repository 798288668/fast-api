#!/usr/bin/env bash

method=$1

param="dev"

if [[ "${method}"x = "prod"x ]]; then
  param="prod"
elif [[ "${method}"x = "test"x ]]; then
  param="test"
else
  echo "Input Is Error."
fi

# $(aws ecr get-login --no-include-email --region ap-east-1)
docker login -u "username" -p "password" registry.cn-hongkong.aliyuncs.com

cd /root/app/fast-api
export CONFIG_ENV=${param}
docker-compose pull
docker-compose up -d
docker image prune -f

if [[ "${method}"x = "prod"x ]]; then
  sh /root/opt/backup-db.sh
fi

# 清空历史记录
# echo > /var/log/wtmp
# echo > /var/log/btmp
# echo > /var/log/lastlog
# echo > /var/log/secure
# echo > /var/log/messages
# echo > /var/run/utmp
# echo > /root/.bash_history
# history -r
# history -cw
# echo clean success
