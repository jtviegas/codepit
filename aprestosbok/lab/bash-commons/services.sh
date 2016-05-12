#!/bin/bash

#never forget to supply commons.sh with this file
. commons.sh

#===========================================================
#
#	FILE:	services.sh
#		
#	USAGE:	
# DESCRIPTION:	services dept. general purpose shell script functions
#     OPTIONS:	
#REQUIREMENTS:	
#	 BUGS:	
#      AUTHOR:	
#     COMPANY:	Wincor-Nixdorf
#     VERSION:	0.1
#     CREATED:	20091112
#    REVISION:	
#===========================================================



#=== FUNCTION ===================================================
#          NAME	: getFolderSizeInM
#   DESCRIPTION	: calculates the folder size in megabytes
#					and puts it in the RETURN_VALUE variable
#	PARAMETER 1 : the folder to monitor
#       RETURNS : the size of the folder in megabytes
#       CREATED	: 20091112
#        AUTHOR	: 
#================================================================
getFolderSizeInM()
{
	log "«IN» getFolderSizeInM"

	if [ -z "$1" ];then
		log "!!!must supply the folder!!!"
		exit -1
	fi
	
	_folder=$1

	#call the du command on the folder
	_v=`du -ms $_folder` 
	
	#initialize the variable which is going to hold the result
	RETURN_VALUE=
	#split the du command result in parts
	#and put it in the RETURN_VALUE as an array
	splitString "$_v"
	
	#the first part of the du command result is the size
	#which must be in the first index of the array
	_size=${RETURN_VALUE[0]}
	
	echo "folder $_folder has a size of $_size megas"
	
	RETURN_VALUE=$_size
		
	log "«OUT» getFolderSizeInM - ($RETURN_VALUE)"
}


#=== FUNCTION ===================================================
#          NAME	: cleanOraDbLogs
#   DESCRIPTION	: cleans oracle db logs based on its size
#       RETURNS : 0 for success, other value for failure
#       CREATED	: 20091112
#        AUTHOR	: 
#================================================================
cleanOraDbLogs()
{
	log "«IN» cleanOraDbLogs"
	local result=0

		#PUT THE CLEANING COMMAND HERE
	
	log "«OUT» cleanOraDbLogs - ($result)"
	return $result
}


#================================================================
#=================		EXECUTION	=============================
#================================================================

MEGABYTES_THRESHOLD=300
FOLDER_TO_MONITOR=/var/log
COMMAND="echo"

RETURN_VALUE=
getFolderSizeInM $FOLDER_TO_MONITOR


if [ "$RETURN_VALUE" -ge "$MEGABYTES_THRESHOLD" ]
then
	cleanOraDbLogs
fi
