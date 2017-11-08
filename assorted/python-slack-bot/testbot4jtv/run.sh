#!/bin/sh

base_folder=$(dirname $(readlink -f $0))
src_folder=$base_folder/src

    export SLACK_BOT_TOKEN='xoxb-197630200977-FVEQvTakWt6pP9EwHDkmBTxv'
    export BOT_ID='U5TJJ5WUR'
python3 $src_folder/bot.py


