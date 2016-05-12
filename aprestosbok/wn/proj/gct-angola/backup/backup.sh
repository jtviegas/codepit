#!/bin/bash

#===========================================================
#
#	FILE:	backup.sh
#		
#	USAGE:	backup.sh
# DESCRIPTION:	burns defined files/folders on a dvd+rw disk. 
#     OPTIONS:	See usage.
#REQUIREMENTS:	dvd+rw-tools, mkisofs packages and commons.sh script file
#	 BUGS:	
#      AUTHOR:	jv
#     COMPANY:	Wincor-Nixdorf
#     VERSION:	1.2
#     CREATED:	20080822
#    REVISION:	20080903
#===========================================================

#=== USER DEFINED VARIABLES=================================
#DEBUG=0	# remove 0 or comment if you don't want to debug 
TO_BE_BACKED_UP="/home/tplinux/download" # define files and folders, separated by a space, to back up
DEVICE="/dev/hda"	#define the system burner device, where the dvd+rw is located


#=== CONSTANTS do not mess with these=======================
TEMP_FOLDER="/home/tplinux/tmp"		# folder to handle temporary files being processed
LOGFILE="/home/tplinux/log/backup.log" # remove value or comment if you don't want to log to a file


#=== INCLUDE - always requires this file====================
source /home/tplinux/text/commons.sh


#=== FUNCTION ==============================================
#          NAME	: doBackup
#   DESCRIPTION	: you guessed it!
#   PARAMETER 1	: ---
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jv
#===========================================================
doBackup()
{
	log "«IN» doBackup"
	local result=-1
	
	
	local timestamp=`date +%Y%m%d%H%M%S`
	local isofile="$TEMP_FOLDER/$timestamp.iso"

	data2iso $isofile $timestamp $TO_BE_BACKED_UP
	if [ ! $? -eq 0 ]; then
		log "!!!something wrong in data2iso!!!"
		return -1
	fi
	
	log "going to format dvd $DEVICE"
	clearDVDRW $DEVICE
	if [ ! $? -eq 0 ]; then
		log "!!!something wrong when formating the dvd!!!"
		return -1
	fi

	log "burning data on the $DEVICE"
	burnISOonDVD $DEVICE $isofile	
	if [ $? -eq 0 ];then
		result=0
		log "ended burning with return value $result"
	else
		log "!!!something has gone wrong when burning data on dvd!!!"
	fi
	
	rm -rf "$isofile"	#delete the temporary iso file

	log "«OUT» doBackup - ($result)"
	return $result;
}


doBackup


