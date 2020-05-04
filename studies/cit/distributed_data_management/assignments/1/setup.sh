#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
#. $this_folder/VARS.sh

echo "going to import artists and artworks..."

#set mongo_dir="C:\Program Files\MongoDB\Server\4.0\bin"
#cd %mongo_dir%
mongoimport --db tate --collection artists --file $this_folder/artists.json
mongoimport --db tate --collection artworks --file $this_folder/artworks.json
#cd %thisdir%

echo "...done."
