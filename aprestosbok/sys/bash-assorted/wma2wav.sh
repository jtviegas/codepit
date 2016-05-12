#!/bin/bash



temp_folder=WAVs
echo 'going convert wma to wav...'
mkdir $temp_folder
cp *.wma $temp_folder/
cd $temp_folder

for file in *.wma
do
	mplayer -ao pcm "$file" # | sox -t raw -r 44100 -s -w -c2 - `echo $file | sed 's/wma/wav/'`
	mv audiodump.wav `echo $file | sed 's/wma/wav/'`
done
rm -rf *.wma
cd ..
echo ...done!
exit 0

