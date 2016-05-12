#!/bin/bash

temp_folder=WAVs
echo 'going convert mp3 to wav...'
mkdir $temp_folder

for file in *.mp3 ; do

	echo converting $file ...
	wav_file=${file//\ /_}
	wav_file=${wav_file/%mp3/wav}
	echo wav file name is $wav_file
	mpg123 -w $temp_folder/$wav_file $file

done

echo ...done!
exit 0

