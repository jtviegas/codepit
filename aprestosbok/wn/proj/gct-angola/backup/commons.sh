#!/bin/bash

#===========================================================
#
#	FILE:	commons.sh
#		
#	USAGE:	
# DESCRIPTION:	general purpose shell script functions
#     OPTIONS:	
#REQUIREMENTS:	
#	 BUGS:	
#      AUTHOR:	jv
#     COMPANY:	Wincor-Nixdorf
#     VERSION:	1.2
#     CREATED:	20080822
#    REVISION:	20080903
#===========================================================


#=== FUNCTION ===================================================
#          NAME	: clearDVDRW
#   DESCRIPTION	: formats a DVD+RW
#		  command.
#   PARAMETER 1	: the device location, e.g., /dev/hdc
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jtv
#================================================================
clearDVDRW()
{
	log "«IN» clearDVDRW"
	local result=-1

	if [ ! -b $1 ];then
		log "!!!$1 is not a block device!!!"
		exit -1
	fi
	local device=$1

	log "going to format dvd+rw $device"
	dvd+rw-format -force $device
	result=$?
	log "format dvd+rw $device returned $result"

	log "«OUT» clearDVDRW - ($result)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: burnDVDRW
#   DESCRIPTION	: appends data to a DVD+RW
#   PARAMETER 1	: the device location, e.g., /dev/hdc
#   PARAMETER 2	: the list of folder/files to burn
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jtv
#================================================================
burnDVDRW()
{
	log "«IN» burnDVDRW"
	local result=-1

	if [ ! -b $1 ];then
		log "!!!$1 is not a block device!!!"
		exit -1
	fi

	local device=$1
	shift
	local data="$@"

	#we are going to assume this is a multisession write
	#and test if things work out ok
	local option="M"	
	growisofs -dry-run -$option $device -R -J $data
	if [ $? -ne 0 ]; then
		#otherwise we assume we're on the initial session
		option="Z"
	fi

	log "going to write [$data]- on $device with growisofs (session type $option)"
	growisofs -$option $device -R -J $data	
	result=$?
	log "growisofs write on $device returned $result"

	log "«OUT» burnDVDRW - ($result)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: isCommand
#   DESCRIPTION	: Checks if the system provides the supplied 
#		  command.
#   PARAMETER 1	: the command name
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jtv
#================================================================
isCommand()
{
	log "«IN» isCommand"
	
	local result=-1

	if [ -z "$1" ]
	then
		log "!!!no command parameter provided!!!"
		return 1
	fi
	
	local command=$1

	#see if whereis brings back some info
	local foo=$(whereis $command)
	debug "whereis $command returned $foo"

	RETURN_VALUE=
	local IFS_old=$IFS
	IFS=":"
	splitString "$foo"
	
	local arrayLength=${#RETURN_VALUE[@]}
	debug "arraylength is $arrayLength"
	IFS=$IFS_old	

	#if whereis returned info then
	#it will concatenated as '<command>: <results>'
	#otherwise we'll get '<command>: ' only.
	if [ "$arrayLength" -gt 1  ]
	then
		result=0
		debug "found $command"
	else
		debug "did not find $command"
	fi

	log "«OUT» isCommand = ($result)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: log
#   DESCRIPTION	: common logging function, prints the message to
#		  STDOUT and expects to find a 
#		  filename in a $LOGFILE variable to write in
#   PARAMETER 1	: the message to log
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jtv
#================================================================
log()
{
	message="<log>$(date +%Y%m%d%H%M%S) - $1"
	echo -e "$message"
	#only executes if LOGFILE variable exists
	#and has some value assigned
	if [ -n "$LOGFILE" ]
	then
		echo -e "$message" >> "$LOGFILE"
	fi
}

#=== FUNCTION ===================================================
#          NAME	: debug
#   DESCRIPTION	: common debuggingfunction, prints the message to
#		  STDOUT
#   PARAMETER 1	: the debug message
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080829
#        AUTHOR	: jtv
#================================================================
debug()
{      
	#only executes if DEBUG variable exists
	#and has some value assigned
	if [ -n "$DEBUG" ]
	then
		echo -e "<debug>$(date +%Y%m%d%H%M%S) - $1"
	fi
}

#=== FUNCTION ===================================================
#          NAME	: splitString
#   DESCRIPTION	: splits a string and assigns its pieces to an 
#		  array to be conveyed in the RETURN_VALUE variable. 
#   PARAMETER 1	: the string to split
#   PARAMETER 2	: the string separator (OPTIONAL), assumes " " as
#		  default separator.
#       CREATED	: 20080828
#        AUTHOR	: jtv
#================================================================
splitString()
{
	log "«IN» splitString"


	if [ -z "$1" ];then
		log "!!!must supply the string!!!"
		exit -1
	fi
	
	if [ -n "$2" ];then
		SEPARATOR=$2
	fi
	
	local string="$@"
	
	debug "string to split is $string"
	debug "separator is $IFS"	

	RETURN_VALUE=

	trimString "$string"
	string=$RETURN_VALUE
	RETURN_VALUE=
	squeezeStringSpaces "$string"
	string="$RETURN_VALUE"
	debug "squeeze string places returned $string to splitString"

	#local array=($string)
	
	index=0
	RETURN_VALUE=
	
	for item in $string
	do
		RETURN_VALUE[$index]=$item
		index=`expr $index + 1`
	done
	
	debug "split created an array with $index elements"

	log "«OUT» splitString = (${RETURN_VALUE[*]})"
}

#=== FUNCTION ===================================================
#          NAME	: trimString
#   DESCRIPTION	: trims a string and assigns it to the 
#		  RETURN_VALUE variable. 
#   PARAMETER 1	: the string to be trimmed
#       CREATED	: 20080828
#        AUTHOR	: jtv
#================================================================
trimString()
{
	log "«IN» trimString"
	if [ -z "$1" ];then
		log "!!!must supply the string!!!"
		exit -1
	fi
	local string=$@
	RETURN_VALUE=$(echo "$string" | sed 's/^[ \t]*//;s/[ \t]*$//')
	log "«OUT» trimString = ($RETURN_VALUE)"
}

#=== FUNCTION ===================================================
#          NAME	: squeezeStringSpaces
#   DESCRIPTION	: squeezes multiple consecutive spaces and tabs 
#		  in a string to a single space, and assigns it to the 
#		  RETURN_VALUE variable.
#   PARAMETER 1	: the string to be squeezed
#       CREATED	: 20080828
#        AUTHOR	: jtv
#================================================================
squeezeStringSpaces()
{
	log "«IN» squeezeStringSpaces"
	if [ -z "$1" ];then
		log "!!!must supply the string!!!"
		exit -1
	fi
	local string="$@"
	RETURN_VALUE=`echo "$string" | sed 's/[ \t]\{2,\}/ /g'`
	log "«OUT» squeezeStringSpaces = ($RETURN_VALUE)"
}

#=== FUNCTION ===================================================
#          NAME	: getFolderSizeOnK
#   DESCRIPTION	: calculates a folder size in Kbytes, if it behaves 
#		  correctly returns 0 and assigns it to the 
#		  RETURN_VALUE variable, otherwise if there was an #			  error, returns -1.
#   PARAMETER 1	: folder, e.g., /dev/hdc
#       CREATED	: 20080828
#        AUTHOR	: jtv
#================================================================
getFolderSizeOnK()
{
	log "«IN» getFolderSizeOnK"
	local result=-1
	
	if [ -z "$1" ]; then
		log "!!!must supply the folder!!!"
		exit -1
	fi
	
	if [ ! -e $1 ]; then
		log "!!!$1 not found!!!"
		exit -1
	fi
	
	local folder=$1
	log "going to execute du on $folder"
	local retval=`du -s $folder 2>>$LOGFILE`
	log "du on folder got->$retval"
	
	if [ -n "$retval" ]; then
		RETURN_VALUE=
		splitString "$retval"
		local myarray=$RETURN_VALUE
		RETURN_VALUE=
		local size=${myarray[0]}
		log "$folder size is $size"
		RETURN_VALUE=$size
		result=0
	fi

	log "«OUT» getFolderSizeOnK = ($RETURN_VALUE)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: mountDevice	
#   DESCRIPTION	: mounts a device
#		  device is already mounted
#   PARAMETER 1	: device, e.g., /dev/hdc
#   PARAMETER 2	: mount point, where to mount it, e.g., folder
#       CREATED	: 20080828
#        AUTHOR	: jtv
#================================================================
mountDevice()
{
	log "«IN» mountDevice"
	local result=-1
	if [ -z "$1" ];then
		log "!!!must supply the device!!!"
		exit -1
	fi
	
	if [ ! -b $1 ];then
		log "!!!$1 is not a block device!!!"
		exit -1
	fi

	if [ -z "$2" ];then
		log "!!!must supply the mount point!!!"
		exit -1
	fi
	
	if [ ! -e $2 ];then
		log "!!!$2 not found!!!"
		exit -1
	fi

	local device=$1
	local mountpoint=$2
	
	local doMount=0	

	local mounts=`cat /proc/mounts | grep "$device"`
	debug "grep $device on mounts got -> $mounts"
	if [ -n "$mounts" ];then	#device already mounted?
		log "$device is mounted"
		local IFS_old=$IFS	#going to find if it is
		IFS=" "			#mounted where we want it
		local index=0		#to be
		for item in $mounts
		do
			debug "$item"
			if [ $index -eq 1 ];then
				if [ "$item" == "$mountpoint"  ];then
					doMount=-1
				fi
			fi
		done
	fi

	if [ $doMount -eq 0 ];then
		mount $device $mountpoint
		if [ $? -eq 0 ];then	#if mount was ok
			result=0	#we return 0 otherwise
		fi			#we return -1
	fi
	IFS=$IFS_old
	log "«OUT» mountDevice = ($result)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: data2iso
#   DESCRIPTION	: creates an iso based on hte data files/folders
#		  provided
#   PARAMETER 1	: the iso file location, e.g., /tmp/file.iso
#   PARAMETER 2	: the label which to name the media
#   PARAMETER 3	: the list of folder/files to burn
#       RETURNS : 0 - ok, else not ok, and if 0 sets 
#		  RETURN_VALUE variable with the burned 
#		  iso correspondent md5sum for later verification
#       CREATED	: 20080902
#        AUTHOR	: jv
#================================================================
data2iso()
{
	log "«IN» data2iso"
	local result=-1

	local temporary_folder="$PWD/data2iso`date '+%Y%m%d%H%M%S'`.tmp"
	mkdir "$temporary_folder"
	
	local iso=$1
	shift
	local label=$1
	shift
	local data=$@
	
	log "going to copy $data to temporary folder $temporary_folder/"
	
	cp -r $data "$temporary_folder/"
	
	local old_pwd=$PWD
	cd $temporary_folder
	
	mkisofs -o"$iso" -J -ldots -rock -v -V$label . 2>>$LOGFILE
	if [ $? -eq 0 ]; then
		result=0
	fi
	
	cd $old_pwd	#goto initial location
	rm -rf "$temporary_folder"

	log "«OUT» data2iso - ($result)"
	return $result
}

#=== FUNCTION ===================================================
#          NAME	: burnDVDRW
#   DESCRIPTION	: appends data to a DVD+RW
#   PARAMETER 1	: the device location, e.g., /dev/hdc
#   PARAMETER 2	: the iso file
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 20080902
#        AUTHOR	: jtv
#================================================================
burnISOonDVD()
{
	log "«IN» burnISOonDVD"
	local result=-1

	if [ ! -b $1 ];then
		log "!!!$1 is not a block device!!!"
		exit -1
	fi
	
	if [ ! -e $2 ]; then
		log "!!!$2 not found!!!"
		exit -1
	fi

	#grab parameters
	local device=$1
	local iso=$2
	
	log "going to write [$iso]- on $device with growisofs"
	growisofs -dvd-compat -Z $device=$iso	
	result=$?
	log "growisofs write on $device returned $result"

	log "«OUT» burnISOonDVD - ($result)"
	return $result
}