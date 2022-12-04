#!/bin/sh

IMAGE_FULL_NAME=contains-light
PROJECT=ContainsLight

docker build -t $IMAGE_FULL_NAME .

sleep 5s

CONTAINER_NAME=$(docker ps -aq --filter name=^/$PROJECT$)
echo $CONTAINER_NAME

sleep 5s

if [[ -n "$CONTAINER_NAME" ]]; then
docker rm -f $CONTAINER_NAME;
fi
docker run -d --name $PROJECT -p 8099:8099 $IMAGE_FULL_NAME

sleep 5s

echo "start success"
