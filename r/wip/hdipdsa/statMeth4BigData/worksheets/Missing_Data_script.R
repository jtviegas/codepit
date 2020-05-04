#install VIM package
install.packages("VIM")
library(VIM)
library(tidyverse)


data()  #view different datasets available
data(sleep, package="VIM") #load sleep data (promise)
sleep<-sleep    #load data in directly so it in the working memory
summary(sleep)
is.na(sleep$Span)   #locate missing data

x<-c(4,8,2,NA)  # vector contains missing data
mean(x)   # returns NA
mean(x, na.rm=TRUE) #excludes missing data before calculating the mean

x<-c(4,8,2,NA,NA,3,7,5)  #create a vector containing missing values
x<-matrix(x,4,2)    # reshape the vector into a matrix with 4 rows and two columns
x

complete.cases(x)  #displays which rows are complete

x[complete.cases(x),]  #removes the rows of x with missing data

windows(10,10)
#shows the amount of missing data for each variable 
# and the frequency of combinations of missing values
a <- aggr(sleep, prop = FALSE, number=TRUE) 
a

#creates a barchart showing the values of the variable EXp 
# that occur when the variable Sleep is missing
x <- sleep[,c("Exp", "Sleep")]
x<-as.data.frame(x)
barMiss(x)

x <- sleep[, c("Exp", "Span")]
barMiss(x)

y <- sleep[, c("Span", "Sleep")]
histMiss(y)

y2 <- sleep[, c("Sleep", "Span")]
histMiss(y2)

y3<- sleep[, c("Span", "Dream")]
histMiss(y3)

# Creates a scatterplot between two variables
#with information about missing values in the margins
marginplot(sleep[,c("Span", "Sleep")])



 data(sleep)
z <- sleep[, 1:5]
z[,c(1,2,3)] <- log10(z[,c(1,2,3)])


windows(10,10)
marginmatrix(z)


model1<-lm(z$Sleep~z$BodyWgt,data=na.omit(z))
summary(model1)
plot(model1)

model2<-lm(z$Sleep~z$BodyWgt)
summary(model2)


imputed.sleep<-irmi(sleep)


summary(sleep)
summary(imputed.sleep)

model.sleep<-lm(sleep$NonD~sleep$BodyWgt)
model.imputed.sleep<-lm(imputed.sleep$NonD~imputed.sleep$BodyWgt)

summary(model.sleep)
summary(model.imputed.sleep)

sleep =  11.4377 -1.8262 * Bodyweight
