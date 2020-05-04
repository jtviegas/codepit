# zookeeper

## zookeeper docker image
```
hostname: zookeeper 
container name: zookeeper 
ports:
	2181 - client
	2888, 3888 - follower and election
```
### usage:

- edit VARS.sh accordingly;
- ``` scripts/build.sh ``` - build docker image and push it to dockerHub (/kakicode/zookeeper);
- ``` scripts/run.sh ``` - run on local docker engine;

