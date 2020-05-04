library(here)
library(tidyverse)
library(outliers)
dr_here()

Fish1 <-read_tsv(here("Fish1.txt"))


#plot histograms and boxplots for each variable
Windows(6,6)
hist(Fish1$Age)
hist(Fish1$Length)
hist(Fish1$mg)
boxplot(Fish1$Age)
boxplot(Fish1$Length)
boxplot(Fish1$mg)
boxplot(Fish1$Weight)

?pairs
# scatterplots of all pairs of variables
pairs(Fish1)

# create a function called panel.hist that adds histograms 
# to the diagonal of the pairs plot (taken from help)
panel.hist <- function(x, ...)
{
  usr <- par("usr"); on.exit(par(usr))
  par(usr = c(usr[1:2], 0, 1.5) )
  h <- hist(x, plot = FALSE)
  breaks <- h$breaks; nB <- length(breaks)
  y <- h$counts; y <- y/max(y)
  rect(breaks[-nB], 0, breaks[-1], y, col = "cyan", ...)
}

#plot pairs function with histograms along the diagonal using the panel.hist function
windows(10,10)
pairs(Fish1, diag.panel = panel.hist)

# run grubbs test on the variabkles
grubbs.test(Fish1$Age)
grubbs.test(Fish1$Length)
grubbs.test(Fish1$Weight)
grubbs.test(Fish1$mg)

#output a vector containing the data in the weight variable
# where the outlier is replaced with the mean
rm.outlier(Fish1$Weight, fill=TRUE, median=FALSE)

#replace the outlier in the weight variable in the Fish1 data set with the mean 
Fish1$Weight<-rm.outlier(Fish1$Weight, fill=TRUE, median=FALSE)

#Create a vector containing the variance of each
# variable from the Fish1 data set
cov_matrix <- cov(Fish1)#create a vector containing the mean of each variable from the Fish1 data set
center<- colMeans(Fish1)
# Calculate the mahalanobis distance for the data set Fish1
mahalanobis(Fish1, center, cov_matrix)

# the output is
# [1]  4.291035  3.678726  1.591098  1.717256  3.376488  9.190784  1.699392
#[8]  1.244229  3.899651  3.228352  1.680254 10.144276  2.973726  3.284734

#Each value represents the square of the mahalanobis distance (a k-dimensional z-score). 
#We had four different variables so each value represents a 4 dimensional z-score 
#for each observation (there are 14 observations). 
#To check whether these distances are significant from the centroid of the 
#data set we compare them to the critical value obtained from the chi-squared 
# distribution with significance a and k degrees of freedom. We will compare 
#the distances to the critical value from the chi-squared distribution with a=0.05 and k=4.
 
qchisq(0.95,df = 4)

# point 12 is an outlier