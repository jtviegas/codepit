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

age<-c(2,2,3,4,4,5,6,7,8,8)
height<-c(4,5,9,11,12,14,17,21,22,24)
plantData<-data.frame(age,height)



### linear regression
height<-plantData$height
age<-plantData$age

model<-lm(height~age)
summary(model)
plot(height~age, col="red", ylab="height", xlab="age", main="plant age vs height"
     , xlim=c(0,10), ylim=c(0,40), pch=5)
abline(model)

## prediction based on the model
new<-data.frame(age=14)
pred<-predict(model,new, interval = "predict")


