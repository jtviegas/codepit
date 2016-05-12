#!/bin/sh

for var in "$@"
do
	echo "going to compile less file: $var"
	recess bootstrap/less/$var.less --compress > css/$var.css 
done	
echo "done"


