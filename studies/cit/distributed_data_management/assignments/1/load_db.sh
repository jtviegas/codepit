#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

echo "going to import artists and artworks..."

mongoimport --db tate --collection artists --file $this_folder/artists.json
mongoimport --db tate --collection artworks --file $this_folder/artworks.json

echo "...done."