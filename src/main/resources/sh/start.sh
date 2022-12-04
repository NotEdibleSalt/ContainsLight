#!/bin/sh

tokill=`ps -ef | grep java | head -n 1 | awk '{print $2}'`
kill -9 $tokill

sleep 5s
rm -rf ./server.log
echo "stop success"

NAME=$1  # 获取执行脚本时的第一个参数，并赋给变量APP_NAME
echo $NAME
nohup java  -jar $NAME  >> ./server.log 2>&1 &
echo "start success"
