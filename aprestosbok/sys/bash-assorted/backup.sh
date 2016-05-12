#!/bin/bash

#===========================================================
#
#	FILE:	backup.sh
#		
#	USAGE:	backup.sh
# DESCRIPTION:	burns defined files/folders on a dvd+rw disk. 
#		There's a day in the month when it performs
#		a monthly total backup. Then, on a defined
#		day of the week(Mon,Sun,...) it will perform
#		a weekly total backup. On the remaining days
#		it executes an incremental backup related
#		to a timestamp created on the weekly backup
#		creation instant.
#		All these backups are kept in a local folder
#		and then copied to a DVD.
#     OPTIONS:	See usage.
#REQUIREMENTS:	tar, dvd+rw-tools and commons.sh script file
#	 BUGS:	
#      AUTHOR:	jv
#     COMPANY:	Wincor-Nixdorf
#     VERSION:	1.1
#     CREATED:	20080822
#    REVISION:	20080827
#===========================================================

#=== USER DEFINED VARIABLES=================================
DEBUG=0		# remove 0 or comment if you don't want to debug 
LOGFILE="`basename $0`.log"	# remove value or comment if you don't want to log to a file
MONTH_BACKUP_DAY="28"	# define the date for the monthly total backup
WEEK_BACKUP_DAY="Fri"	# define the week day for the weekly total backup
BACKUP_FOLDER=/home/tplinux/scripts/backup_test		#the local backup folder
TO_BE_BACKED_UP="/usr/kerberos /home/tplinux/scripts/backup_test/mpscach013" # define files and folders to back up
DEVICE=/dev/hda		#define the burner system device



#=== CONSTANTS - don't mess with these======================
WEEK="week"	
MONTH_BACKUP_FOLDER=$BACKUP_FOLDER/month
WEEK_BACKUP_FOLDER=$BACKUP_FOLDER/week
DAY_BACKUP_FOLDER=$BACKUP_FOLDER/day
BACKUP_FILE_PREFIX="backup-"
BACKUP_FILE_SUFFIX=".tar.bz2"
WEEK_TIMESTAMP_FILE="WEEK_TIMESTAMP"


#=== INCLUDE - always requires this file====================
source commons.sh


#=== FUNCTION ==============================================
#          NAME	: doPrerequisites
#   DESCRIPTION	: Checks special requisites for backup 
#		  operation
#   PARAMETER 1	: ---
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jv
#===========================================================
doPrerequisites()
{
	log "«IN» doPrerequisites"
	local result=-1
	if [ ! -e "$MONTH_BACKUP_FOLDER" ];then
		log "going to create $MONTH_BACKUP_FOLDER"
		mkdir $MONTH_BACKUP_FOLDER 2>>$LOGFILE
		if [ $? -ne 0 ]; then
			return -1
		fi
		log "created $MONTH_BACKUP_FOLDER"
	fi

	if [ ! -e "$WEEK_BACKUP_FOLDER" ];then
	log "going to create $WEEK_BACKUP_FOLDER"
		mkdir $WEEK_BACKUP_FOLDER 2>>$LOGFILE
		if [ $? -ne 0 ]; then
			return -1
		fi
		log "created $WEEK_BACKUP_FOLDER"
	fi

	if [ ! -e "$DAY_BACKUP_FOLDER" ];then
		log "going to create $DAY_BACKUP_FOLDER"
		mkdir $DAY_BACKUP_FOLDER 2>>$LOGFILE
		if [ $? -ne 0 ]; then
			return -1
		fi
		log "created $DAY_BACKUP_FOLDER"
	fi

	result=0
	log "«OUT» doPrerequisites"
	return $result
}

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

	doPrerequisites
	if [ $? -ne 0 ]; then
		log "!!!failed to doPrerequisites!!!"
		return -1
	fi

	dayOfMonth=`date +%d`
	weekDay=`date +%a`
	log "day of month is $dayOfMonth and week day is $weekDay"
	#if first day of the month
	if [ "$dayOfMonth" == "$MONTH_BACKUP_DAY" ]; then	
		log "do monthly integral backup"
		doMonthlyBackup
	else
		#if first day of the week
		if [ "$weekDay" == "$WEEK_BACKUP_DAY" ]; then
			log "do weekly integral backup"
			doWeeklyBackup
		else
			log "do daily incremental backup based on previous weekly backup"
			doDailyBackup
		fi	
	fi
	result=$?
	log "«OUT» doBackup - ($result)"

	return $result;
}
#=== FUNCTION ==============================================
#          NAME	: doMonthlyBackup
#   DESCRIPTION	: performs total backup of files, storing it
#		  locally and burning it on the DVD
#   PARAMETER 1	: ---
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jv
#===========================================================
doMonthlyBackup()
{
	log "«IN» doMonthlyBackup"
	local result=-1
	
	local filename=$BACKUP_FILE_PREFIX$(date +%Y%m)$BACKUP_FILE_SUFFIX
	log "grab all the data and tar-bzip it in $filename"
	tar --totals -cjpvf $PWD/$filename $TO_BE_BACKED_UP 2>>$LOGFILE
	
	if [ $? -ne 0 ]; then
		return -1
	fi
	log "$filename created"

	log "delete all the files from the $MONTH_BACKUP_FOLDER folder"
	rm -rf $MONTH_BACKUP_FOLDER/* 2>>$LOGFILE
	
	log "move $PWD/$filename into month backup folder $MONTH_BACKUP_FOLDER"
	mv $PWD/$filename $MONTH_BACKUP_FOLDER/ 2>>$LOGFILE

	if [ $? -eq 0 ];then
		log "going to format dvd $DEVICE"
		clearDVDRW $DEVICE
		log "burning data on the $DEVICE"	
		burnDVDRW $DEVICE "$MONTH_BACKUP_FOLDER $WEEK_BACKUP_FOLDER $DAY_BACKUP_FOLDER"
		result=$?
		log "ended burning with return value $result"
	fi
	
	
	log "«OUT» doMonthlyBackup - ($result)"
	return $result
}
#=== FUNCTION ==============================================
#          NAME	: doWeeklyBackup
#   DESCRIPTION	: performs total backup of files, storing it
#		  locally and burning it on the DVD
#   PARAMETER 1	: ---
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jv
#===========================================================
doWeeklyBackup()
{
	log "«IN» doWeeklyBackup"

	local result=-1
	
	local timestamp=`date '+%Y-%m-%d %H:%M:%S'`
	local filename=$BACKUP_FILE_PREFIX$(date +%Y)$WEEK$(date +%W)$BACKUP_FILE_SUFFIX
	log "grab all the data and tar-bzip it on $filename"
	tar cjpvf $PWD/$filename $TO_BE_BACKED_UP 2>>$LOGFILE
	
	if [ $? -ne 0 ]; then
		return -1
	fi
	
	log "delete all the files from the $WEEK_BACKUP_FOLDER and $DAY_BACKUP_FOLDER backup folders"
	rm -rf $WEEK_BACKUP_FOLDER/* 2>>$LOGFILE && rm -rf $DAY_BACKUP_FOLDER/* 2>>$LOGFILE
	
	if [ $? -ne 0 ]; then
		log "!!!something was wrong when deleting obsolete backups on \n $WEEK_BACKUP_FOLDER and $DAY_BACKUP_FOLDER!!!"
	fi

	#record the exact time of backup
	log "creating $WEEK_BACKUP_FOLDER/$WEEK_TIMESTAMP_FILE with timestamp $timestamp"
	echo "$timestamp" > $WEEK_BACKUP_FOLDER/$WEEK_TIMESTAMP_FILE
	
	log "move $PWD/$filename into the $WEEK_BACKUP_FOLDER folder"
	mv $PWD/$filename $WEEK_BACKUP_FOLDER/
	if [ $? -eq 0 ];then
		log "going to format the $DEVICE"
		clearDVDRW $DEVICE
		log "formatted $DEVICE"
		log "going to burn on $DEVICE"	
		burnDVDRW $DEVICE "$MONTH_BACKUP_FOLDER $WEEK_BACKUP_FOLDER"
		result=$?
		log "ended burning with return value $result"
	fi

	log "«OUT» doWeeklyBackup - ($result)"	
	return $result
}
#=== FUNCTION ==============================================
#          NAME	: doDailyBackup
#   DESCRIPTION	: performs incremental backup of files,
#		  storing it locally and burning it 
#		  on the DVD
#   PARAMETER 1	: the command name
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jv
#===========================================================
doDailyBackup()
{
	log "«IN» doDailyBackup"
	
	local filename=$BACKUP_FILE_PREFIX$(date +%Y%m%d)$BACKUP_FILE_SUFFIX
	log "retrieve the week backup date"
	local week_backup_date=`cat $WEEK_BACKUP_FOLDER/$WEEK_TIMESTAMP_FILE`
	log "this week backup date was $week_backup_date"
	log "grab all the new data and tar-bzip it in $filename"
	tar --newer="$week_backup_date" -cjpvf $PWD/$filename $TO_BE_BACKED_UP 2>>$LOGFILE
	if [ $? -ne 0 ]; then
		log "!!!something wrong happened in tar!!!"
		return -1
	fi

	log "move $PWD/$filename into the $DAY_BACKUP_FOLDER folder"
	mv $PWD/$filename $DAY_BACKUP_FOLDER/

	if [ $? -eq 0 ];then
		log "burn it on the $DEVICE"	
		burnDVDRW $DEVICE "$DAY_BACKUP_FOLDER/$filename"
		result=$?
		log "ended burning with return value $result"
	fi

	log "«OUT» doDailyBackup - ($result)"	
	return $result
}



doBackup


