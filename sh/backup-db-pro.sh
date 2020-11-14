#!/usr/bin/env bash

method=$1

ip=13.115.40.203

if [[ "${method}"x = "init"x ]]; then
  scp backup-db.sh cron-backup.cron root@${ip}:/root/opt
  ssh root@${ip} 'chmod u+x /root/opt/* && crontab /root/opt/cron-backup.cron > ~/log && echo cron success'
fi

ssh root@${ip} 'cd /root/opt && sh backup-db.sh'
