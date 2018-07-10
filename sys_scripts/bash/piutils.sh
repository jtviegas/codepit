#!/bin/sh


TOPIC=TUT
MODEL_FILE=/home/scadmin/sources/TutorialBulkFile/model/model.pamodel
DATA_DIR=/home/scadmin/sources/TutorialBulkFile/data
TS_START=20130516-0000
TS_END=20130602-0000

STREAMS_DIR=/opt/IBM/InfoSphereStreams

function stopPi {
	echo "@stopPi"
	$TASP_HOME/bin/stop.sh -t=$TOPIC
	result=$?
    echo "stopPi@[$result]"
}

function cleanTopicData {
	echo "@cleanTopicData"
	$TASP_HOME/bin/admin.sh cleanup -s -t=$TOPIC
	result=$?
	rm -rf $PI_HOME/BAK*
    echo "cleanTopicData@[$result]"
}

function deployModel {
	echo "@deployModel"
	$TASP_HOME/bin/udm_converter.sh -deploy $MODEL_FILE .
	result=$?
    echo "deployModel@[$result]"
}
function resetData {
	echo "@resetData"
	mv $DATA_DIR/good/* $DATA_DIR/
	mv $DATA_DIR/bad/* $DATA_DIR/
	result=$?
    echo "resetData@[$result]"
}
function startPi {
	echo "@startPi"
	$TASP_HOME/bin/start.sh -t=$TOPIC
	result=$?
    echo "startPi@[$result]"
}
function extract {
	echo "@extract"
	$TASP_HOME/bin/admin.sh run_extractor_instance -s=$TS_START -e=$TS_END -t=$TOPIC
	result=$?
	echo "extract@[$result]"
}

function stopExtract {
	echo "@stopExtract"
	$TASP_HOME/bin/admin.sh stop_extractor_instance
	result=$?
	echo "stopExtract@[$result]"
}
function restartExtraction {
	echo "@restartExtraction"
	$TASP_HOME/bin/admin.sh run_extractor_instance -t=$TOPIC
	result=$?
	echo "restartExtraction@[$result]"
}

function listStreamsInstances {
	$STREAMS_DIR/bin/streamtool lsinstance -a
}

function listStreamsJobs {
	$STREAMS_DIR/bin/streamtool lspe -i=spl
}


function startOver {
	stopPi
	cleanTopicData
	resetData
	deployModel
	startPi
	extract
}

function stopRestart {
	stopExtract
	stopPi
	startPi
	restartExtraction
}





