#!/bin/sh

_pwd=`pwd`

cd /tmp/
wget http://cran.r-project.org/src/base/R-3/R-3.1.2.tar.gz
tar xzpvf R-3.1.2.tar.gz

sudo mkdir -p /app
sudo -R chown notroot.notroot /app

mv R-3.1.2 /app/R
cd /app/R
make clean && ./configure --with-x=no --with-readline=no --enable-R-shlib --with-tcltk && make
cd ..
tar cjpvf R.tar.bz2 R

