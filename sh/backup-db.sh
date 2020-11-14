#!/usr/bin/env bash

db_user="root"
db_passwd="root"
db_name="fast-api"

# 容器中备份
docker exec -i mysql bash <<EOF
if [ ! -d "/backups/mysql" ]; then
  mkdir -p /backups/mysql
fi
/usr/bin/mysqldump --user=${db_user} --password=${db_passwd} --databases ${db_name} | gzip > /backups/mysql/backups_$(date +%Y%m%d).sql.gz
rm -f /backups/mysql/backups_$(date -d -5day +%Y%m%d).sql.gz
exit
EOF

# 创建目录
if [[ ! -d "/backups/mysql" ]]; then
  mkdir -p /backups/mysql
fi

# 将docker中的备份的数据拷贝到宿主机上。
docker cp mysql:/backups/mysql/backups_$(date +%Y%m%d).sql.gz /backups/mysql
# 删除超过5天的数据
rm -f /backups/mysql/backups_$(date -d -5day +%Y%m%d).sql.gz
