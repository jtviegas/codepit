############################################################################
#####   Worksheet 7 Sample Size, Effect Size and Significance ##############

###########################################################################

set.seed(1)

#####  Case 1 ######
#create a sample of size 10 with a mean of 0 and a standard deviation of 4  
A_10<-rnorm(10, 0, 4)
#create a sample of size 10 with a mean of 1 and a standard deviation of 4  
B_10<-rnorm(10, 1, 4)
#test to see if we can detect a significant difference between 
# the mean of sample A and the mean of sample B at the 5% level
t.test(A_10,B_10)

#create samples of size 20, 30, 50, 100, 1000, 10000 
A_20<-rnorm(20, 0, 4)
B_20<-rnorm(20, 1, 4)
A_30<-rnorm(30, 0, 4)
B_30<-rnorm(30, 1, 4)
A_50<-rnorm(50, 0, 4)
B_50<-rnorm(50, 1, 4)
A_100<-rnorm(100, 0, 4)
B_100<-rnorm(100, 1, 4)
A_1000<-rnorm(1000, 0, 4)
B_1000<-rnorm(1000, 1, 4)
A_10000<-rnorm(10000, 0, 4)
B_10000<-rnorm(10000, 1, 4)

#test each pair of samples
t.test(A_20,B_20)
t.test(A_30,B_30)
t.test(A_50,B_50)
t.test(A_100,B_100)
t.test(A_1000,B_1000)
t.test(A_10000,B_10000)

#plot histograms
windows(10,20)
par(mfrow=c(5,2))
hist(A_10,freq=FALSE, xlim=c(-15,15))
hist(B_10,freq=FALSE, xlim=c(-15,15))
hist(A_20,freq=FALSE, xlim=c(-15,15))
hist(B_20,freq=FALSE, xlim=c(-15,15))
hist(A_50,freq=FALSE, xlim=c(-15,15))
hist(B_50,freq=FALSE, xlim=c(-15,15))
hist(A_100,freq=FALSE, xlim=c(-15,15))
hist(B_100,freq=FALSE, xlim=c(-15,15))
hist(A_1000,freq=FALSE, xlim=c(-15,15))
hist(B_1000,freq=FALSE, xlim=c(-15,15))

#####  Case 2 ######
#create a sample of size 10 with a mean of 0 and a standard deviation of 4  
A_10<-rnorm(10, 0, 4)
#create a sample of size 10 with a mean of 0.1 and a standard deviation of 4  
B_10<-rnorm(10, 0.1, 4)
#test to see if we can detect a significant difference between 
# the mean of sample A and the mean of sample B at the 5% level
t.test(A_10,B_10)

#create samples of size 20, 30, 50, 100, 1000, 10000 
A_20<-rnorm(20, 0, 4)
B_20<-rnorm(20, 0.1, 4)
A_30<-rnorm(30, 0, 4)
B_30<-rnorm(30, 0.1, 4)
A_50<-rnorm(50, 0, 4)
B_50<-rnorm(50, 0.1, 4)
A_100<-rnorm(100, 0, 4)
B_100<-rnorm(100, 0.1, 4)
A_1000<-rnorm(1000, 0, 4)
B_1000<-rnorm(1000, 0.1, 4)
A_10000<-rnorm(10000, 0, 4)
B_10000<-rnorm(10000, 0.1, 4)
A_100000<-rnorm(10000, 0, 4)
B_100000<-rnorm(10000, 0.1, 4)

#test each pair of samples
t.test(A_20,B_20)
t.test(A_30,B_30)
t.test(A_50,B_50)
t.test(A_100,B_100)
t.test(A_1000,B_1000)
t.test(A_10000,B_10000)
t.test(A_100000,B_100000)

#plot histograms
windows(10,20)
par(mfrow=c(5,2))
hist(A_10,freq=FALSE, xlim=c(-15,15))
hist(B_10,freq=FALSE, xlim=c(-15,15))
hist(A_20,freq=FALSE, xlim=c(-15,15))
hist(B_20,freq=FALSE, xlim=c(-15,15))
hist(A_50,freq=FALSE, xlim=c(-15,15))
hist(B_50,freq=FALSE, xlim=c(-15,15))
hist(A_100,freq=FALSE, xlim=c(-15,15))
hist(B_100,freq=FALSE, xlim=c(-15,15))
hist(A_1000,freq=FALSE, xlim=c(-15,15))
hist(B_1000,freq=FALSE, xlim=c(-15,15))

#####  Case 3 ######
#create a sample of size 10 with a mean of 0 and a standard deviation of 1  
A_10<-rnorm(10, 0, 1)
#create a sample of size 10 with a mean of 0.1 and a standard deviation of 1  
B_10<-rnorm(10, 0.1, 1)
#test to see if we can detect a significant difference between 
# the mean of sample A and the mean of sample B at the 5% level
t.test(A_10,B_10)

#create samples of size 20, 30, 50, 100, 1000, 10000 
A_20<-rnorm(20, 0, 1)
B_20<-rnorm(20, 0.1, 1)
A_30<-rnorm(30, 0, 1)
B_30<-rnorm(30, 0.1, 1)
A_50<-rnorm(50, 0, 1)
B_50<-rnorm(50, 0.1, 1)
A_100<-rnorm(100, 0, 1)
B_100<-rnorm(100, 0.1, 1)
A_1000<-rnorm(1000, 0, 1)
B_1000<-rnorm(1000, 0.1, 1)
A_10000<-rnorm(10000, 0, 1)
B_10000<-rnorm(10000, 0.1, 1)
A_100000<-rnorm(10000, 0, 1)
B_100000<-rnorm(10000, 0.1, 1)

#test each pair of samples
t.test(A_20,B_20)
t.test(A_30,B_30)
t.test(A_50,B_50)
t.test(A_100,B_100)
t.test(A_1000,B_1000)
t.test(A_10000,B_10000)
t.test(A_100000,B_100000)

#plot histograms
windows(10,20)
par(mfrow=c(5,2))
hist(A_10,freq=FALSE, xlim=c(-15,15))
hist(B_10,freq=FALSE, xlim=c(-15,15))
hist(A_20,freq=FALSE, xlim=c(-15,15))
hist(B_20,freq=FALSE, xlim=c(-15,15))
hist(A_50,freq=FALSE, xlim=c(-15,15))
hist(B_50,freq=FALSE, xlim=c(-15,15))
hist(A_100,freq=FALSE, xlim=c(-15,15))
hist(B_100,freq=FALSE, xlim=c(-15,15))
hist(A_1000,freq=FALSE, xlim=c(-15,15))
hist(B_1000,freq=FALSE, xlim=c(-15,15))

#####  Case 4 ######
#create a sample of size 10 with a mean of 0 and a standard deviation of 10  
A_10<-rnorm(10, 0, 10)
#create a sample of size 10 with a mean of 0.2 and a standard deviation of 10  
B_10<-rnorm(10, 0.2, 10)
#test to see if we can detect a significant difference between 
# the mean of sample A and the mean of sample B at the 5% level
t.test(A_10,B_10)

#create samples of size 20, 30, 50, 100, 1000, 10000 
A_20<-rnorm(20, 0, 10)
B_20<-rnorm(20, 0.2 , 10)
A_30<-rnorm(30, 0, 10)
B_30<-rnorm(30, 0.2, 10)
A_50<-rnorm(50, 0, 10)
B_50<-rnorm(50, 0.2, 10)
A_100<-rnorm(100, 0, 10)
B_100<-rnorm(100, 0.2, 10)
A_1000<-rnorm(1000, 0, 10)
B_1000<-rnorm(1000, 0.2, 10)
A_10000<-rnorm(10000, 0, 10)
B_10000<-rnorm(10000, 0.2 , 10)
A_100000<-rnorm(10000, 0, 10)
B_100000<-rnorm(10000, 0.2, 10)
A_1000000<-rnorm(100000, 0, 10)
B_1000000<-rnorm(100000, 0.2, 10)

#test each pair of samples
t.test(A_20,B_20)
t.test(A_30,B_30)
t.test(A_50,B_50)
t.test(A_100,B_100)
t.test(A_1000,B_1000)
t.test(A_10000,B_10000)
t.test(A_100000,B_100000)
t.test(A_1000000,B_1000000)


#################   LOOP   ################
##           (Just for fun!)             ##
###########################################

## create a vector containing sample sizes
s1<-c(10,20,30,40,50,100,1000,10000,100000,1000000)
## create a vector to store the p-values generated from each t test
pvalues<-rep(0,length(s1))

# create a function that creates two vectors of data sampled from the normal 
# distribution with specified means and sd and performs a t test. The input 
# is the sample size and the output is the p-value
ttest <- function(size)
{
  A <- rnorm(size,0,1)
  B <- rnorm(size,1,1)
  t.test(A,B)$p.value
}

# loop through each sample size specified in s1 and store the result of the test 
# in the p-values vector
for (i in 1:length(s1))
{  size <- s1[i]
   pvalues[i] <- ttest(size)
} 






