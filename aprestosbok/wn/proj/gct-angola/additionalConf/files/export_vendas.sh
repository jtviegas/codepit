#!/bin/sh

#
# Exportacao de vendas 
#

LOJA=`psql -U tplinux -t -A -F "" -c "select lpad(number, 3, '0') from scf where structmember='gstScfParam.ulLong' and index = 12"`
HOME_DIR=/home/tplinux

export IN_DIR=$HOME_DIR/download
export TMP_DIR=$HOME_DIR/comunicacoes/tmp
export LOG_DIR=$HOME_DIR/comunicacoes/log
export FILE="S0$LOJA*\.pnt"
export TS=`date +%Y%m%d%H%M`
cd $IN_DIR

tar zcvf $TMP_DIR/Vend$LOJA-$TS.tgz $FILE
#
# SO DEPOIS DE TUDO OK
# rm $FILE
#

#
# Processo de FTP
#
ftp ftp.elos24.com <<EOF
bin
lcd $TMP_DIR
cd loja$LOJA/outbox
put Vend$LOJA-$TS.tgz
EOF

#
# Arquivar
#
mv $TMP_DIR/Vend$LOJA-$TS.tgz $LOG_DIR




