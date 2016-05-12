#!/bin/sh

#
# Importacao  de artigos e precos para lojas 
#

LOJA=`psql -U tplinux -t -A -F "" -c "select lpad(number, 3, '0') from scf where structmember='gstScfParam.ulLong' and index = 12"`
HOME_DIR=/home/tplinux

export WDIR=$HOME_DIR/comunicacoes/tmp
export LOGDIR=$HOME_DIR/comunicacoes/log
export OUT_DIR="/var/FS/COMP/DBW/GLOJA"

#
# Get Files
#
ftp ftp.elos24.com <<EOF
bin
prompt
lcd $WDIR
cd loja$LOJA/inbox
mget artprec*
#mdel artprec*
EOF

#
# Untar all
#
cd $WDIR
for i in `find . -type f -print | grep artprec | grep tgz `
do
echo $i
tar  zxvf $i
mv $i $LOGDIR
done
mv * $OUT_DIR
exit

