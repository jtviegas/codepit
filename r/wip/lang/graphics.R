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
library(graphics)


data <- read_csv(here("wip/data/data1.csv"),na="?")
head(data)

## barplots - prints a vector of frequencies
x<-c(12,24,15,10,16)
barplot(x)

# contingency table
ct<-table(data$Gender)
barplot(ct, main="gender barchart", ylab="frequency", xlab="gender")

# other way of setting up the frequencies...
male<-subset(data, Gender=="Male")
female<-subset(data, Gender=="Female")

fm<-apply(male[2],2,length)
ff<-apply(female[2],2,length)
frequency<-c(fm,ff)
barplot(frequency)

## pie chart ...with colors => c(5,6)
pie(ct, col=c(5,6))
#...with legend
percentlabels<-100*frequency/sum(frequency)
pielabels<-paste(percentlabels,"% ","")
pie(frequency, main="pie chart for gender", col=c(5,6), labels=pielabels,cex=1.5)
legend("topleft", c("male", "female"),cex=1.5,fill = c(5,6))

## Histograms
freq <- c(12,34,23,67,44,23,77,83,48,23,87)
hist(freq)
# specifying the classes
hist(freq, breaks = 8)
hist(freq, br=c(10,30,50,70,90))

## box and whisker plots
boxplot(data$Maths, ylab="result %")
boxplot(data[,4:6], ylab="result %", xlab="subject")
boxplot(data$Maths~data$Gender)

## scatterplots

age<-c(2,2,3,4,4,5,6,7,8,8)
height<-c(4,5,9,11,12,14,17,21,22,24)
plantData<-data.frame(age,height)
#...add labels
plot(plantData$height~plantData$age, col="red", ylab="height", xlab="age", main="plant age vs height"
     , xlim=c(0,10), ylim=c(0,40), pch=5)
colors()
##...add points in different color based on categorical variable
#...create a blank plot
plot(NULL, xlim=c(0,100), ylim=c(0,100), xlab="math results %", ylab = "programming result %")
male<-subset(data,Gender=="Male")
female<-subset(data,Gender=="Female")
#..add males as red triangles
points(x=male$Maths,y=male$Programming,pch=17,col="red")
#...add females as orange circles
points(x=female$Maths,y=female$Programming,pch=16,col="orange")
##...add legend
legend("topleft", legend=c("male", "female"), col=c("red", "orange"), pch=c(17,16))

## shape and size of the graphics window
#...default size of graphics window is 7 inches per 7 inches

#...open new window of 5x5
#windows(5,5)
dev.new(width=5,height=5)
#..put 2 graphs in one row
par(mfrow=c(1,2))
plot(age~height)

#...scatterplots relationship between multiple variables
panel.hist <- function(x, ...)
{
  usr <- par("usr"); on.exit(par(usr))
  par(usr = c(usr[1:2], 0, 1.5) )
  h <- hist(x, plot = FALSE)
  breaks <- h$breaks; nB <- length(breaks)
  y <- h$counts; y <- y/max(y)
  rect(breaks[-nB], 0, breaks[-1], y, col = "cyan", ...)
}
pairs(data, diag.panel = panel.hist)








