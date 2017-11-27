#!/bin/bash

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


usage()
{
	cat <<EOM
    	Usage:
	$(basename $0) Explain options here
	usage: $programname [-abch] [-f infile] [-o outfile]
              -a              turn on feature a
              -b              turn on feature b
              -c              turn on feature c
              -h              display help
              -f infile       specify input file infile
              -o outfile      specify output file outfile

EOM
        exit 1
}

#[ -z $1 ] && { usage; }




ipToint32()
{
	result=$(ip=`hostname -I` && ip=( ${ip//\./ } ) && echo "(${ip[0]} * (2^24)) + (${ip[1]}*(2^16)) + (${ip[2]}*(2^8)) + ${ip[3]}" | bc)
	echo "$result"
}

#=== FUNCTION ===================================================
#          NAME	: getSecret	
#   DESCRIPTION	: reads a secret filethat should be kept private
#   PARAMETER 1	: secret to get, should be the name prefix of the secret file to read
#	OUTCOME		: returns the content of the secret file, for instance, a password, 
#					as in: 
#						mysecret=$(getSecret secretPrefix)
#================================================================

getSecret()
{	
	local SECRET_SUFFIX=".SECRET"
	if [ -z $1 ] 
	then
		echo "!!! no secret to inspect !!! ...leaving."
		return 1
	fi
	secret="$1"
	folder=$(dirname $(readlink -f $0))\\
	secretfile="$folder/$secret$SECRET_SUFFIX"
	#echo "secret file is $secretfile"
	if [ ! -e $secretfile ]
	then
		echo "!!! no secret file [$secretfile] !!! ...leaving."
		return -1
	fi
	result=`cat $secretfile`
	echo "$result"
}

#=== FUNCTION ===================================================
#          NAME	: resetUsbPorts	
#   DESCRIPTION	: due to power volatility some usb ports are 
#					sometimes faulty in my laptop, this is a fix.
#================================================================

resetUsbPorts()
{	
	echo "resetting usb ports...."
	sudo modprobe -r usbhid
	sudo modprobe usbhid
	echo "...reset usb ports...done. [$?]"
}

# you can also use 
#	LOCALDIR=$(dirname $(readlink -f $0))

#getScriptFolder()#{
#	SOURCE="${BASH_SOURCE[0]}"
#	while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
#  		DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
# 		SOURCE="$(readlink "$SOURCE")"
#  		[[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
#	done
#	DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
#	echo "$DIR"
#}
getPswd()
{	
	folder=$(dirname $(readlink -f $0))
	pswdfile="$folder/$(basename $0)$PSWD_SUFFIX"
	#echo "pswd file is $pswdfile"
	if [ ! -e $pswdfile ]
	then
		#echo -n "Please, provide a password:" 
		read pswd
		echo "$pswd" > $pswdfile 
	fi
	result=`cat $pswdfile`
	echo "$result"
}

ipToint32()
{
	result=$(ip=`hostname -I` && ip=( ${ip//\./ } ) && echo "(${ip[0]} * (2^24)) + (${ip[1]}*(2^16)) + (${ip[2]}*(2^8)) + ${ip[3]}" | bc)
	echo "$result"
}