install.packages("devtools")
library(devtools)
install_github("jtviegas/myrpack")
library(myrpack)
ls("package:myrpack")
install_if_missing("tidyverse")
library(tidyverse)
install_if_missing("readxl")
library(readxl)
install_if_missing("here")
library(here)
install_if_missing("ggplot2")
library(ggplot2)


data <- read_csv(here("wip/data/data1.csv"),na="?")
head(data)


install_if_missing("VIM")
library(VIM)

# list the datasets available
data()
#...load sleep as a promise/not in memory
data(sleep, package="VIM")
#...force sleep to be loadded in memory
sleep <- sleep
head(sleep)
#...locate missing data
is.na(sleep$Span)

#...excluding missing values
x<-c(4,8,2,NA)
mean(x)
mean(x,na.rm = T)

x<-c(4,8,2,NA,NA,3,7,5)
x<-matrix(x,4,2)
#...find complete rows
complete.cases(x)
x[complete.cases(x),]
x <- na.omit(x)

## visualising missing data
#...the function aggr aggregates missing data
a <- aggr(sleep)
#... the barMiss function creates a barplot highlighting the missing values
x<-sleep[,c("Exp","Sleep")]
#...the sleep missing values based on the various exp values
barMiss(x)
# the histMiss also gives the missing values relationship
y<-sleep[,c("Span","Sleep")]
histMiss(y)

y<-sleep[,c("Span","Dream")]
histMiss(y)
#...marginplot gives more information about the missing values
marginplot(sleep[,c("Span","Sleep")])

#...marginmatrix: creates a scatterplot matrix
z<-sleep[,1:5]
#...transform values to get it in a manageable range
z[,c(1,2,3)]<-log10(z[,c(1,2,3)])
hist(sleep$BodyWgt)
hist(z$BodyWgt)
marginmatrix(z)

##...dealing with missing data

#...in a regression model, lm uses only the complete cases already
model<-lm(z$Sleep~z$BodyWgt)
summary(model)
model<-lm(z$Sleep~z$BodyWgt, data=na.omit(z))
summary(model)
#..other actions:
#   na.fail() - issue an error if the object contains missing values
#   na.exclude() - same as na.omit() but will result in NA predictions for missing values

## if we really need to input missing data, we can use VIM irmi() function
imputed.sleep <- irmi(sleep)
imputed.sleep
summary(sleep)
summary(imputed.sleep)

#..let's see how the model was affected
model.sleep<-lm(sleep$NonD~sleep$BodyWgt)
summary(model.sleep)

model.imputed.sleep<-lm(imputed.sleep$NonD~imputed.sleep$BodyWgt)
summary(model.imputed.sleep)




















