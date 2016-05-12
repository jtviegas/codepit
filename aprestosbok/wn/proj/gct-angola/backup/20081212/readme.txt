GCT Angola - Loja 003 - Huambo
Procedimento de Configuração de gravação em DVD+RW de backup conjunto do tplinux 
e da bd Oracle da RMS duarante o processo de fim de dia.

1. login no servidor tplinux como tux;
2. posicionamento na pasta do user tux  
	cd ~
3. copiar o ficheiro gct-angola-backuppatch.tar.bz2 para essa pasta
	cp <<path>>/gct-angola-backuppatch.tar.bz2 ~/
4. untar dos ficheiros para a pasta do user tux
	tar xjpvf gct-angola-backuppatch.tar.bz2

Nota: a partir desse momento deve-se disponibilizar um dvd+rw diariamente
na drive do servidor para que no fim de dia o backup seja lá gravado.

