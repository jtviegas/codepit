#!/bin/bash

#===========================================================
#
#	FILE:	gct_angola_config.sh
#		
#	USAGE:	
# DESCRIPTION:	
#     OPTIONS:	
#REQUIREMENTS:	
#	 BUGS:	
#      AUTHOR: joao viegas
#     COMPANY:	
#     VERSION:	0.1
#     CREATED: Wed Oct  1 06:02:51 WEST 2008
#    REVISION:	
#===========================================================
FILES_DIR=files

IMPORT_ARTPREC_SCRIPT=import_artprec.sh
EXPORT_VENDAS_SCRIPT=export_vendas.sh
LOGFILE="$0.log"
HOME_DIR=/home/tplinux

COMUNICACOES_DIR=$HOME_DIR/comunicacoes
COM_TMP_DIR=$HOME_DIR/comunicacoes/tmp
COM_LOG_DIR=$HOME_DIR/comunicacoes/log

NETRC_FILE=/root/.netrc

FTP_SERVER=ftp.elos24.com
FTP_USER=angola
FTP_PASS=angola2008

EXPORT_CRON_ENTRY="0 8,10,11 * * * $COMUNICACOES_DIR/$EXPORT_VENDAS_SCRIPT"
IMPORT_CRON_ENTRY="0,30 8-20 * * * $COMUNICACOES_DIR/$IMPORT_ARTPREC_SCRIPT"


#=== FUNCTION ===================================================
#          NAME	: create_netrc_config
#   DESCRIPTION	: add netrc config in order to perform ftp 
#			without supplying ayuthentication 
#			attributes. 
#   PARAMETER 1	: 
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 
#        AUTHOR	: 
#================================================================
create_netrc_config()
{
	log "«IN» create_netrc_config"
	local result=-1

	if [ -e $NETRC_FILE ]
	then
		grep -i "$FTP_SERVER" $NETRC_FILE
		local netrc_entry=$?
	
		if [ "$netrc_entry" == "0" ]
		then
			log ".netrc file already has ftp authentication info"
			result=0
		else
			cat >> $NETRC_FILE <<EOF
machine $FTP_SERVER
login $FTP_USER
password $FTP_PASS
EOF
			result=$?
		fi
	else
			cat > $NETRC_FILE <<EOF
machine $FTP_SERVER
login $FTP_USER
password $FTP_PASS
EOF
		result=$?
	fi	

	log "«OUT» create_netrc_config($result)"
	return $result;	
}

#=== FUNCTION ===================================================
#          NAME	: write_crontab_entries
#   DESCRIPTION	: add crontab entries to perform import and
#			export scripts
#   PARAMETER 1	: 
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 
#        AUTHOR	: 
#================================================================
write_crontab_entries()
{
	log "«IN» write_crontab_entries"
	local result=-1

	crontab -l | grep -i $IMPORT_ARTPREC_SCRIPT 1>/dev/null
	local import_already_in_cron=$?
	crontab -l | grep -i $EXPORT_VENDAS_SCRIPT 1>/dev/null
	local export_already_in_cron=$?
	
	local timestamp=`date '+%d%m%Y%H%M%S'`
	local cron_tmp_file="cron-$timestamp.tmp"	
	local do_cron_update="no"

	crontab -l > $cron_tmp_file

	if [ "$import_already_in_cron" != "0" ]
	then
		echo "$IMPORT_CRON_ENTRY" >> $cron_tmp_file
		do_cron_update="yes"
	fi

	if [ "$export_already_in_cron" != "0" ]
	then
		echo "$EXPORT_CRON_ENTRY" >> $cron_tmp_file
		do_cron_update="yes"
	fi

	if [ $do_cron_update == "yes" ]
	then
		cat $cron_tmp_file | crontab
		result=$?
	else
		result=0
	fi
	
	rm -f $cron_tmp_file 2>/dev/null

	log "«OUT» write_crontab_entries($result)"
	return $result;	
}

#=== FUNCTION ===================================================
#          NAME	: copy_import_export_scripts
#   DESCRIPTION	: copies the import export scripts while 
#			assigning the correct 
#			LOJA variables values 
#   PARAMETER 1	: 
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 
#        AUTHOR	: 
#================================================================
copy_import_export_scripts()
{
	log "«IN» copy_import_export_scripts"
	local result=-1

	cp $FILES_DIR/$IMPORT_ARTPREC_SCRIPT $COMUNICACOES_DIR/$IMPORT_ARTPREC_SCRIPT && cp $FILES_DIR/$EXPORT_VENDAS_SCRIPT $COMUNICACOES_DIR/$EXPORT_VENDAS_SCRIPT	
	
	result=$?
	log "«OUT» copy_import_export_scripts($result)"
	return $result;
}

#=== FUNCTION ===================================================
#          NAME	: create_comunicacoes_folder
#   DESCRIPTION	: creates comunicacoes folder structure, with log
#			and tmp folder. 
#   PARAMETER 1	: 
#       RETURNS : 0 - ok, else not ok
#       CREATED	: 
#        AUTHOR	: 
#================================================================
create_comunicacoes_folder()
{
	log "«IN» create_comunicacoes_folder"
	local result=-1

	if [ -e $COMUNICACOES_DIR ]
	then
		log "$COMUNICACOES_DIR exists already"
		result=0
	else
		mkdir $COMUNICACOES_DIR
		result=$?
	fi

	if [ -e $COM_TMP_DIR ]
	then
		log "$COM_TMP_DIR exists already"
		result=0
	else
		mkdir $COM_TMP_DIR
		result=$?
	fi

	if [ -e $COM_LOG_DIR ]
	then
		log "$COM_LOG_DIR exists already"
		result=0
	else
		mkdir $COM_LOG_DIR
		result=$?
	fi

	log "«OUT» create_comunicacoes_folder($result)"
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


#===============================================================
#			EXECUTION
#===============================================================

if [ `whoami` != "root" ]
then
	log "!!!you must be root to run this script!!!"
	exit -1
fi

log "$0 started..."

create_comunicacoes_folder
copy_import_export_scripts
write_crontab_entries
create_netrc_config

log "...$0 done"
