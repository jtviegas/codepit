#1. What exactly does a p-value tell you?
# probability that a variable might take a value higher than your statistic value, 
# when you're deciding about rejecting or not, the null hypothesis. So the limit for your statistic, 
# in a one-sided 95% confidence level will have a p-value of 0.05 = 5%

#2. What is a type II error?
# error of not rejecting the null hypothesis when it is actually false, false negative

#3. As the sample size increases does the probability of making a type II error increase or
#   decrease? decreases

#4. Define the bias of an estimator. Explain what it means for an estimator to be biased.
# the bias of an estimator is the difference between the estimator expected value value and 
# the real value of the parameter being estimated.
#An estimator is said to be an unbiased estimate of a given population parameter
#when the mean of the sampling distribution of that estimator can be shown to be
#equal to the parameter being estimated.

#5. The Central Limit Theorem states that as the sample size increases, the sampling
#distribution of the sample mean, , can be approximated by a normal distribution with
#mean μ and standard deviation σ/√n where:
#  μ is the population mean,
#σ is the population standard deviation,
#n is the sample size.

x <- as.integer(runif(10000,1,7))
hist(as.integer(runif(10000,1,7)))


