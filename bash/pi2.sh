#!/bin/bash

RETURN_VALUE=
STREAMS_DIR=$STREAMS_INSTALL
OMNIBUS_DIR=/opt/IBM/tivoli/netcool/omnibus
ANALYTICS_DIR=/opt/IBM/scanalytics/analytics
ADMIN_COM=$ANALYTICS_DIR/bin/admin.sh
LOG_FOLDER=$ANALYTICS_DIR/spl/instances/log
DEFAULT_INSTANCE=spl@scadmin

TOPIC=ALL

SYS_AGGREG_INTERVAL=15
DATA_DIR=/home/scadmin/Cron/data
MODEL_FILE=/home/scadmin/Cron/cron1.pamodel
TS_START=20161020-0000
TS_END=20161127-0000



function stopExtraction {
        echo "@stopExtraction"
        $TASP_HOME/bin/admin.sh stop_extractor_instance
        result=$?
        echo "stopExtraction@[$result]"
}

function stopPi {
        echo "@stopPi"
        $TASP_HOME/bin/stop.sh -t=$TOPIC
        result=$?
        echo "stopPi@[$result]"
}

function cleanTopic {
        echo "@cleanTopic"
        $TASP_HOME/bin/admin.sh cleanup -s -t=$TOPIC
        result=$?
        rm -rf $PI_HOME/BAK*
        echo "cleanTopic@[$result]"
}

function resetDataFiles() {
        echo "@resetDataFiles"
        mv $DATA_DIR/good/*.csv $DATA_DIR/
        result=$?
        echo "resetDataFiles@[$result]"
}

function deployModel {
        echo "@deployModel"
        $TASP_HOME/bin/udm_converter.sh -deploy $MODEL_FILE
        result=$?
        echo "deployModel@[$result]"
}

function startPi {
        echo "@startPi"
        $TASP_HOME/bin/start.sh -t=$TOPIC
        result=$?
        echo "startPi@[$result]"
}

function runExtract {
        echo "@runExtract"
        $TASP_HOME/bin/admin.sh run_extractor_instance -s=$TS_START -e=$TS_END -t=$TOPIC -l=$SYS_AGGREG_INTERVAL
        result=$?
        echo "runExtract@[$result]"
}

function restartExtraction {
        echo "@restartExtraction"
        $TASP_HOME/bin/admin.sh run_extractor_instance -t=$TOPIC
        result=$?
        echo "restartExtraction@[$result]"
}


usage() 
{

        echo
        echo "usage: ./pi.sh <option> [args]"
        echo options:
        echo 10	: stop extraction
        echo 11 : stop PI
        echo 12 : clean topic
        echo 13 : reset data files
        echo 14 : deploy model
        echo 15 : start PI 
        echo 16 : run extraction of topic data
        echo 17 : restart extraction from where it has stopped
        echo 

}

# ===================================== EXECUTION AREA ====================================

argument=$1

case "$argument" in
        "10")
                stopExtraction
                ;;
        "11")
                stopPi
                ;;
        "12")
                cleanTopic
                ;;
        "13")
                resetDataFiles
                ;;
        "14")
                deployModel
                ;;
        "15")
                startPi
                ;;
        "16")
                runExtract
                ;;
        "17")
                restartExtraction
                ;;
		*)
                usage
                ;;
esac
