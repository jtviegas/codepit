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

#...we will use two-sample t-tests to determine there are statistically significant differences 
#...between the sample means

#...let's generate data
set.seed(13)
#...setup graph window to grid 2x2
par(mfrow=c(2,2))

## case 1: n=10/10000 m=0/0.1 sd=1/1
A<-rnorm(10, 0,1)
B<-rnorm(10, 0.1,1)
Ax<-rnorm(10000, 0,1)
Bx<-rnorm(10000, 0.1,1)
#...print histograms based on frequencies
hist(A)
hist(B)
hist(Ax)
hist(Bx)
#...print histograms based on density
hist(A, freq = F)
hist(B, freq = F)
hist(Ax, freq = F)
hist(Bx, freq = F)
#...t.test on the mean
t.test(A,B)   # p-value = 0.07113
t.test(Ax,Bx) # p-value = 5.088e-14
#...the smaller sample still manages to not reject the null hypothesis
#...the bigger sample clearly does not see any significant difference between the two samples

## case 2: n=10/10000 m=0/1 sd=1/1
A<-rnorm(10, 0,1)
B<-rnorm(10, 1,1)
Ax<-rnorm(10000, 0,1)
Bx<-rnorm(10000, 1,1)
#...print histograms based on frequencies
hist(A)
hist(B)
hist(Ax)
hist(Bx)
#...print histograms based on density
hist(A, freq = F)
hist(B, freq = F)
hist(Ax, freq = F)
hist(Bx, freq = F)
#...t.test on the mean
t.test(A,B)   # p-value = 0.04098
t.test(Ax,Bx) # p-value < 2.2e-16
#...the smaller sample rejects the null hypothesis

## case 3: n=10/10000 m=0/0.1 sd=1/4
A<-rnorm(10, 0,1)
B<-rnorm(10, 0.1,4)
Ax<-rnorm(10000, 0,1)
Bx<-rnorm(10000, 0.1,4)
#...print histograms based on frequencies
hist(A)
hist(B)
hist(Ax)
hist(Bx)
#...print histograms based on density
hist(A, freq = F)
hist(B, freq = F)
hist(Ax, freq = F)
hist(Bx, freq = F)
#...t.test on the mean
t.test(A,B)   # p-value = 0.7106
t.test(Ax,Bx) # p-value = 0.09621
#...in both cases we don't reject the null hypothesis


## case 4: n=10/10000 m=0/1 sd=1/4
A<-rnorm(10, 0,1)
B<-rnorm(10, 1,4)
Ax<-rnorm(10000, 0,1)
Bx<-rnorm(10000, 1,4)
#...print histograms based on frequencies
hist(A)
hist(B)
hist(Ax)
hist(Bx)
#...print histograms based on density
hist(A, freq = F)
hist(B, freq = F)
hist(Ax, freq = F)
hist(Bx, freq = F)
#...t.test on the mean
t.test(A,B)   # p-value = 0.2402
t.test(Ax,Bx) # p-value < 2.2e-16
#...we reject the null hypothesis on the bigger sample case

## case 5: n=10/10000 m=0/0.2 sd=10/10
A<-rnorm(10, 0,10)
B<-rnorm(10, 0.2,10)
Ax<-rnorm(10000, 0,10)
Bx<-rnorm(10000, 0.2,10)
#...print histograms based on frequencies
hist(A)
hist(B)
hist(Ax)
hist(Bx)
#...print histograms based on density
hist(A, freq = F)
hist(B, freq = F)
hist(Ax, freq = F)
hist(Bx, freq = F)
#...t.test on the mean
t.test(A,B)   # p-value = 0.2427
t.test(Ax,Bx) # p-value = 0.348
#...in both cases we don't reject the null hypothesis

par(mfrow=c(1,1))
