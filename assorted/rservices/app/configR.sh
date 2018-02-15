#!/bin/sh

R --save <<RPROG
	r <- getOption("repos")
	r["CRAN"] <- "http://cran.ma.imperial.ac.uk"
	options(repos=r)
	install.packages("reader")
	install.packages("rkafka")
	install.packages("syuzhet")
	install.packages("mongolite")
	install.packages("jsonlite")
	print('R is ready!')
RPROG