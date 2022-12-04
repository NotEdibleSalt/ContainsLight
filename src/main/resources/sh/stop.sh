#!/bin/sh

NAME=$1
echo $NAME
tokill=`ps -ef | grep java | grep $NAME | awk '{print $2}'`
kill -9 $tokill

sleep 5s
mv ./$NAME ./back
rm -rf ./server.log
echo "success"
