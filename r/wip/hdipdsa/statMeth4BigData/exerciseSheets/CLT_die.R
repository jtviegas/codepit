#########################################################
########        Exercise Sheet 2 Q.5            #########
########  The Central Limit Theorem in Action   #########

########################################################


n_samples <- 10000   ### the number of samples we take
n <-1                ### the size of each sample


## create a vector containing the results of one roll of the die 
## repeated 10,000 times. This shows the distribution of the random variable X
x1 <- as.integer(runif(n_samples,1,7) )    

## plot the results in histogram
windows(15,20)
par(mfrow=c(3,2))
hist(x1,main=paste( " Disribution of the mean of", n, "roll"),breaks=seq(0.5,6.5, 1), freq = FALSE)  



## create a vector containing the results of the average of two rolls of the 
## die repeated 10,000 times. This shows the distribution of the sample mean when the 
## sample is of size 2

n <-2 

sample <-rep(0,n_samples)        #create a vector of zeros of length n_samples

for (k  in 0:n_samples)            
{ sample[k] <- mean(as.integer(runif(n,1,7))) 
}

hist(sample,main=paste( " Disribution of the mean of", n, "rolls"),breaks=seq(0.5,6.5, 0.5), freq = FALSE)  


## create a vector containing the results of the average of two rolls of the 
## die repeated 10,000 times. This shows the distribution of the sample mean when the 
## sample is of size 5

n <-5 

sample=rep(0,n_samples)        #create a vector of zeros of length n_samples

for (k  in 0:n_samples)            
{ sample[k] <- mean(as.integer(runif(n,1,7))) 
}

hist(sample,main=paste( " Disribution of the mean of", n, "rolls"),breaks=seq(0.5,6.5, 0.2), freq = FALSE)  


## create a vector containing the results of the average of two rolls of the 
## die repeated 10,000 times. This shows the distribution of the sample mean when the 
## sample is of size 10

n <-10 

sample=rep(0,n_samples)        #create a vector of zeros of length n_samples

for (k  in 0:n_samples)            
{ sample[k] <- mean(as.integer(runif(n,1,7))) 
}

hist(sample,main=paste( " Disribution of the mean of", n, "rolls"),breaks=seq(0.5,6.5, 0.1), freq = FALSE)  



## create a vector containing the results of the average of two rolls of the 
## die repeated 10,000 times. This shows the distribution of the sample mean when the 
## sample is of size 100

n <-100 

sample=rep(0,n_samples)        #create a vector of zeros of length n_samples

for (k  in 0:n_samples)            
{ sample[k] <- mean(as.integer(runif(n,1,7))) 
}

hist(sample,main=paste( " Disribution of the mean of", n, "rolls"),breaks=seq(0.5,6.5, 0.06), freq = FALSE)  

## create a vector containing the results of the average of two rolls of the 
## die repeated 10,000 times. This shows the distribution of the sample mean when the 
## sample is of size 1000

n <-1000 

sample=rep(0,n_samples)        #create a vector of zeros of length n_samples

for (k  in 0:n_samples)            
{ sample[k] <- mean(as.integer(runif(n,1,7))) 
}

hist(sample,main=paste( " Disribution of the mean of", n, "rolls"),breaks=seq(0.5,6.5, 0.05), freq = FALSE)  

## Loop ## 

## create a vector containing the sample sizes we would like to plot
n <- c(1, 2,5,10,100,1000)
## create a matrix of zeros with 10000 rows and 6 columns
X <- matrix(0, n_samples, length(n))


windows(15,20)
par(mfrow=c(3,2))

for (i in n)
{ j = which(n == i)
  
  for (k  in 1:n_samples)  
  {
  X[k,j] <- mean(as.integer(runif(i,1,7))) 
  }
hist(X[,j],main=paste( " Disribution of the mean of", i, "rolls"), freq = FALSE, xlim = c(0.5,6.5))  
}


