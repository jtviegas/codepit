"
PCA Project Report
	 	 	
Polymer Manufacture

João Viegas
21.05.2018
Student R00157699 	
	 	 	
Statistical Methods for Big Data
STAT 8007
Higher Diploma in Data Science and Analytics
"
install.packages(c("pls", "car", "outliers", "VIM"))
library(pls)
library(car)
library(outliers)
library(VIM)
library(tidyverse)

setwd("~/Documents/github/R/wip/hdipdsa/statMeth4BigData")

##################################################################################
##########                     1                                  ################
##################################################################################

d0 <- as.data.frame(read.table("Polymer1.txt", header=T))
summary(d0)
#Ranking          Mat_A             Mat_B            Mat_C            Mat_D            Mat_E            Mat_F       
#Min.   : 1.00   Min.   :  2.225   Min.   : 3.999   Min.   : 3.018   Min.   : 3.001   Min.   : 3.999   Min.   : 6.041  
#1st Qu.:15.75   1st Qu.: 13.421   1st Qu.: 8.505   1st Qu.: 7.850   1st Qu.: 6.527   1st Qu.:11.021   1st Qu.:18.873  
#Median :30.50   Median : 65.266   Median :10.974   Median :11.026   Median :10.857   Median :20.666   Median :28.717  
#Mean   :30.50   Mean   :128.821   Mean   :11.232   Mean   :10.885   Mean   :10.847   Mean   :20.689   Mean   :26.911  
#3rd Qu.:45.25   3rd Qu.:160.593   3rd Qu.:14.150   3rd Qu.:13.495   3rd Qu.:14.991   3rd Qu.:30.771   3rd Qu.:33.447  
#Max.   :60.00   Max.   :627.289   Max.   :19.000   Max.   :24.999   Max.   :18.002   Max.   :39.003   Max.   :48.977  
#                                                                                                      NA's   :15     
complete.cases(d0)  #displays which rows are complete
nrow(d0)
d0woNA <- d0[complete.cases(d0),] 
nrow(d0woNA)
aggr(d0)
marginmatrix(d0)

# we have 15 NA's in F, but with no clear pattern, the missing values seem to be random and not dependent on any variable
# we'll adopt two appraches:
# - d01 with the rows with NA removed
# - d02 with the rows with MAt_f completed by the irmi function
# we'll make a choce in the end


##########------------------d01 with the rows with NA removed-----------------################
d01 <- d0[complete.cases(d0),] 
summary(d01)
"
> summary(d01)
    Ranking          Mat_A             Mat_B            Mat_C            Mat_D            Mat_E            Mat_F       
 Min.   : 1.00   Min.   :  2.434   Min.   : 3.999   Min.   : 3.018   Min.   : 3.138   Min.   : 3.999   Min.   : 6.041  
 1st Qu.:18.00   1st Qu.: 33.574   1st Qu.: 7.657   1st Qu.: 9.752   1st Qu.: 9.932   1st Qu.: 9.452   1st Qu.:18.873  
 Median :30.00   Median : 96.299   Median :10.281   Median :11.719   Median :12.125   Median :17.709   Median :28.717  
 Mean   :30.89   Mean   :146.297   Mean   : 9.945   Mean   :12.211   Mean   :11.974   Mean   :18.061   Mean   :26.911  
 3rd Qu.:44.00   3rd Qu.:173.165   3rd Qu.:12.248   3rd Qu.:14.344   3rd Qu.:15.664   3rd Qu.:22.824   3rd Qu.:33.447  
 Max.   :60.00   Max.   :627.289   Max.   :19.000   Max.   :24.999   Max.   :18.002   Max.   :38.677   Max.   :48.977 
"
d <- d01
pairs(d[,2:7])
# correlations between D+E and C+B
par(mfrow=c(2,3))
for(i in seq(2, length(d), 1))
  hist(d[,i],xlab=names(d[i]), main=names(d[i]))
# Mat_A deviates substantially from normality, as seen in the histogram, 
# we transform it now
d$Mat_A <- log(d$Mat_A)
for(i in seq(2, length(d), 1))
  hist(d[,i],xlab=names(d[i]), main=names(d[i]))
# we will assume the other variables as drawn from a normal distribution 
for(i in seq(2, length(d), 1))
  hist(d[,i],xlab=names(d[i]), main=names(d[i]))
for(i in seq(2, length(d), 1))
  boxplot(d[,i],xlab=names(d[i]), main=names(d[i]))
# we might have significant outliers in Mat_A and Mat_C
grubbs.test(d$Mat_A)
grubbs.test(d$Mat_C)
"
	Grubbs test for one outlier
data:  d$Mat_A
G = 2.24690, U = 0.88265, p-value = 0.4782
alternative hypothesis: lowest value 0.889577078355732 is an outlier
Grubbs test for one outlier
data:  d$Mat_C
G = 3.59970, U = 0.69882, p-value = 0.002134
alternative hypothesis: highest value 24.9986 is an outlier
"
d[d$Mat_C==24.9986,]
# line 1 seems to be an outlier
# let's check it with the multivarialte outlier analysis
cov_matrix <- cov(d[,2:7])
center<- colMeans(d[,2:7])
# Calculate the mahalanobis distance for the data set 
mahalanobis(d[,2:7], center, cov_matrix)
#critical value
qchisq(0.95,df = 6)
# we confirm line 1 is an outlier, 4, 28 and 60 also are flagged by mahalanobis 
# but with so few data we will only remove line 1 and take all the other observations as input for the analysis
nrow(d)
d <- d[-c(1),]
nrow(d)
# 2nd iteration
for(i in seq(2, length(d), 1))
  boxplot(d[,i],xlab=names(d[i]), main=names(d[i]))
# at this stage we'll assume there are no more outliers in the dataset
dev.off()
dev.new()

#store the information in Positions in rownames and remove Positions variable
rownames(d) <- d$Ranking
d$Ranking <- NULL

pca<-prcomp(d, center=TRUE, scale.=TRUE)
summary(pca)
"
Importance of components:
                          PC1    PC2     PC3       PC4      PC5      PC6
Standard deviation     1.8707 1.5401 0.35866 0.0006438 2.59e-05 5.93e-06
Proportion of Variance 0.5832 0.3953 0.02144 0.0000000 0.00e+00 0.00e+00
Cumulative Proportion  0.5832 0.9786 1.00000 1.0000000 1.00e+00 1.00e+00
"
# we will use the Kaiser Criterion to select the number of principal components, where
#  the sd is the squared root of the eigenvalue associated with the component, 
# the Kaiser Criterion chooses the eigenvalues higher that 1: ... so we choose:
# PC1 and PC2 which on the whole account for 97.86% of the variance, and their
# effect on the variance is somewhat leveled with PC1 accounting for 58.32% and PC2 for 39.53%
# the screeplot is quite telling
screeplot(pca,type = "lines")

# doing model fitness analysis between the models with one or two components ...
# t = xp
x <- scale(d)
# one component
x_pred1 <- pca$x[,1] %*% t(pca$rotation[,1])
E <- x - x_pred1
E_sq <- E*E
R2x1 <- 1 - colSums(E_sq)/colSums(x*x)
barplot(R2x1, las=2, ylab="R2x1", ylim = c(0,1), cex.names = 0.8)
"
...we see that the model with PC1 can explain most of the variance of the variables Mat_B,C,D and E, 
but can only explain a small part of F and none of Mat_A
"
# 2 components
x_pred2 <- pca$x[,1:2] %*% t(pca$rotation[,1:2])
E2 <- x - x_pred2
E2_sq <- E2*E2
R2x2 <- 1 - colSums(E2_sq)/colSums(x*x)
barplot(R2x2, las=2, ylab="R2x2", ylim = c(0,1), cex.names = 0.8)
"
the 2 components model, pc1 and pc2, does explain almost entirely the variance of all the variables
"
# to check for outliers we can plot a confidence ellipse around the scores
dataEllipse(pca$x[,1], pca$x[,2], levels=0.95, xlim=c(-7,7), ylim=c(-6,6), xlab="pc1", ylab="pc2")
abline(h=0,v=0)
"
we see that we found an outlier in the lower right quadrant
"

##########-------------d02 with the rows with MAt_f completed by the irmi function----------################
d02 <- irmi(d0)
summary(d02)
"
    Ranking          Mat_A             Mat_B            Mat_C            Mat_D            Mat_E            Mat_F       
 Min.   : 1.00   Min.   :  2.225   Min.   : 3.999   Min.   : 3.018   Min.   : 3.001   Min.   : 3.999   Min.   :-4.827  
 1st Qu.:15.75   1st Qu.: 13.421   1st Qu.: 8.505   1st Qu.: 7.850   1st Qu.: 6.527   1st Qu.:11.021   1st Qu.:18.774  
 Median :30.50   Median : 65.266   Median :10.974   Median :11.026   Median :10.857   Median :20.666   Median :28.772  
 Mean   :30.50   Mean   :128.821   Mean   :11.232   Mean   :10.885   Mean   :10.847   Mean   :20.689   Mean   :27.171  
 3rd Qu.:45.25   3rd Qu.:160.593   3rd Qu.:14.150   3rd Qu.:13.495   3rd Qu.:14.991   3rd Qu.:30.771   3rd Qu.:34.306  
 Max.   :60.00   Max.   :627.289   Max.   :19.000   Max.   :24.999   Max.   :18.002   Max.   :39.003   Max.   :48.977
"
d <- d02
pairs(d[,2:7])
# correlations between D+E and C+B
par(mfrow=c(2,3))
for(i in seq(2, length(d), 1))
  hist(d[,i],xlab=names(d[i]), main=names(d[i]))
#we don't have much data, but Mat_A doesn't look as being drawn from a normal distribution we will try
# to transform it, it is the most blatant case 
d$Mat_A <- log(d$Mat_A)
for(i in seq(2, length(d), 1))
  hist(d[,i],xlab=names(d[i]), main=names(d[i]))

for(i in seq(2, length(d), 1))
  boxplot(d[,i],xlab=names(d[i]), main=names(d[i]))
# we might have a significant outlier in Mat_C
grubbs.test(d$Mat_C)
dev.off()
"
	Grubbs test for one outlier
data:  d$Mat_C
G = 3.54550, U = 0.78333, p-value = 0.005356
alternative hypothesis: highest value 24.9986 is an outlier
"
d[d$Mat_C==24.9986,]
# line 1 seem to be outlier
# let's check it with the multivarialte outlier analysis
cov_matrix <- cov(d[,2:7])
center<- colMeans(d[,2:7])
# Calculate the mahalanobis distance for the data set 
mahalanobis(d[,2:7], center, cov_matrix)
#critical value
qchisq(0.95,df = 6)
# we confirm line 1 is an outlier, 4, 28, 52 and 60 also are flagged by 
# mahalanobis, but with so few data we won't remove them at this stage
nrow(d)
d <- d[-c(1),]
nrow(d)
# 2nd iteration
for(i in seq(2, length(d), 1))
  boxplot(d[,i],xlab=names(d[i]), main=names(d[i]))
# we'll assume there are no more outliers in the dataset

#store the information in Positions in rownames and remove Positions variable
rownames(d) <- d$Ranking
d$Ranking <- NULL

pca<-prcomp(d, center=TRUE, scale.=TRUE)
summary(pca)
"
Importance of components:
                         PC1    PC2     PC3       PC4       PC5       PC6
Standard deviation     1.864 1.5483 0.35864 0.0005154 4.458e-05 1.501e-05
Proportion of Variance 0.579 0.3995 0.02144 0.0000000 0.000e+00 0.000e+00
Cumulative Proportion  0.579 0.9786 1.00000 1.0000000 1.000e+00 1.000e+00
"
# we will use the Kaiser Criterion to select the number of principal components, where
#  the sd is the squared root of the eigenvalue associated with the component, 
# the Kaiser Criterion chooses the eigenvalues higher that 1: ... so we choose:
# PC1 and PC2, also note that they are responsible repectively for 57.9% and 39.95% of the variance
dev.off()
screeplot(pca,type = "lines")

# doing model fitness analysis between the models with one or two components ...
# t = xp
x <- scale(d)
# one component
x_pred1 <- pca$x[,1] %*% t(pca$rotation[,1])
E <- x - x_pred1
E_sq <- E*E
R2x1 <- 1 - colSums(E_sq)/colSums(x*x)
barplot(R2x1, las=2, ylab="R2x1", ylim = c(0,1), cex.names = 0.8)
"
...we see that the model with PC1 can explain most of the variance of the variables Mat_B,C,D and E, 
but can only explain a small part of F and even smaller part of Mat_A
"
# 2 components
x_pred2 <- pca$x[,1:2] %*% t(pca$rotation[,1:2])
E2 <- x - x_pred2
E2_sq <- E2*E2
R2x2 <- 1 - colSums(E2_sq)/colSums(x*x)
barplot(R2x2, las=2, ylab="R2x2", ylim = c(0,1), cex.names = 0.8)
"
the 2 components model, pc1 and pc2, does explain almost entirely the variance of all the variables
"
# to check for outliers we can plot a confidence ellipse around the scores
dataEllipse(pca$x[,1], pca$x[,2], levels=0.95, xlim=c(-7,7), ylim=c(-6,6), xlab="pc1", ylab="pc2")
abline(h=0,v=0)
"
no outliers
"

##########------------- 
"
here we choose the dataset with the missing values replaced by th eirmi function, and carry on with our analysis
"

plot(pca$x)
text(pca$x,labels=rownames(d), adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)
"
 (a) whether PCA can reveal patterns in the ranking of the polymers
 ... clearly there is a correlation between PC1,PC2 and the ranking.
we can see in the scores plot is specially clear the fact that as lower the 
values are for PC2 and higher for PC1, the higher the ranking, so the higher ranked values can be seen in the 
lower right quadrant.
In the same way we can say that the lower rankings are correlated with the
higher values of PC2 and lower values of PC1, so the majority of lower rankings happen to be located
in the left upper quadrant
"
"(b) how the different materials relate to the ranking of the polymers"
loadingplot(pca,scatter=T, labels = names(d))
abline(h=0,v=0)
biplot(pca)

"from the loading plot we can see that Mat_E and Mat_A are related with 
the higher ranked polymers whereas Mat_F and Mat_D are mostly related with the
lower ranked polymers"

# to check for outliers we can plot a confidence ellipse around the scores
dataEllipse(pca$x[,1], pca$x[,2], levels=0.95, xlim=c(-7,7), ylim=c(-6,6), xlab="pc1", ylab="pc2")
abline(h=0,v=0)

# doing an analysis between the models with one or two components ...
# t = xp
x <- scale(d)
# one component
x_pred1 <- pca$x[,1] %*% t(pca$rotation[,1])
E <- x - x_pred1
E_sq <- E*E
R2x1 <- 1 - colSums(E_sq)/colSums(x*x)
barplot(R2x1, las=2, ylab="R2x1", ylim = c(0,1), cex.names = 0.8)
"
...we see that the mdel with PC1 can explain most of the variance of the variables Mat_B,C,D and E, 
but can only explain a small part of F and almost none of Mat_A
"
# 2 components
x_pred2 <- pca$x[,1:2] %*% t(pca$rotation[,1:2])
E2 <- x - x_pred2
E2_sq <- E2*E2
R2x2 <- 1 - colSums(E2_sq)/colSums(x*x)
barplot(R2x2, las=2, ylab="R2x2", ylim = c(0,1), cex.names = 0.8)
"
the 2 components model, pc1 and pc2, does explain almost entirely the variance of all the variables
"

dev.off()

##################################################################################
##########                     2                                 ################
##################################################################################

d2 <- as.data.frame(read.table("Polymer2.txt", header=T))
summary(d2)
"
Strength          Warp           Mat_A             Mat_B            Mat_C            Mat_D            Mat_E            Mat_F       
Min.   : 1.00   Min.   : 1.00   Min.   :  2.225   Min.   : 3.999   Min.   : 3.018   Min.   : 3.001   Min.   : 3.999   Min.   : 6.041  
1st Qu.:15.75   1st Qu.:15.75   1st Qu.: 13.421   1st Qu.: 8.505   1st Qu.: 7.850   1st Qu.: 6.527   1st Qu.:11.021   1st Qu.:18.873  
Median :30.50   Median :30.50   Median : 65.266   Median :10.974   Median :11.026   Median :10.857   Median :20.666   Median :28.717  
Mean   :30.50   Mean   :30.50   Mean   :128.821   Mean   :11.232   Mean   :10.885   Mean   :10.847   Mean   :20.689   Mean   :26.911  
3rd Qu.:45.25   3rd Qu.:45.25   3rd Qu.:160.593   3rd Qu.:14.150   3rd Qu.:13.495   3rd Qu.:14.991   3rd Qu.:30.771   3rd Qu.:33.447  
Max.   :60.00   Max.   :60.00   Max.   :627.289   Max.   :19.000   Max.   :24.999   Max.   :18.002   Max.   :39.003   Max.   :48.977  
                                                                                                                      NA's   :15  
"
"
  it is the sme data as in Part 1 except the two new rankings replacing the general one, so we'll use the same pca analysis 
previouly performed and just change the labels
"
nrow(d2)
d2 <- d2[-c(1),]
nrow(d2)
strength_rankings <- d2$Strength
warp_rankings <- d2$Warp

##########-------------d01 Strength analysis----------################
rownames(d) <- strength_rankings
pca<-prcomp(d, center=TRUE, scale.=TRUE)

plot(pca$x)
text(pca$x,labels=strength_rankings, adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)
"
From the scores plot we can see the that the observations with higher strength ranking are positively correlated with PC1, 
and in a slight degree, negatively correlated with pc2. The higher ranked observations are thus distributed in the right 
quadrants with the extreme values in lower right quadrant. Consequently, the lower ranked observations are almost in its 
entirety negatively correlated with PC1, with the correlation with PC2 not being that much clear in this case, so most 
of the lower ranked observations are placed on the most negative values of PC1 and in between values [-1:1] of PC2.
"
loadingplot(pca,scatter=T, labels = names(d), comps = c(1,2))
biplot(pca)
abline(h=0,v=0)

"
The biplot, reflecting the loadings and the scores, shows that Mat_E and in a lesser extent, Mat_B, are the most important 
materials when it comes to strength performance as are the most correlated with higher ranked observations. In the same way we 
can say that Mat_C and Mat_D are also related to lower Strength outcomes.
"

##########-------------d02 Warp resistance analysis----------################
rownames(d) <- warp_rankings
pca<-prcomp(d, center=TRUE, scale.=TRUE)

plot(pca$x)
text(pca$x,labels=warp_rankings, adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)

"
from the scores plot we can see that the observations with higher warp resistance ranking are 
negatively correlated with pc2, and we can't clearly realize the relationship between pc1 and warp resistance. 
Hence, the higher ranked observations are located in the lower values of pc2, and the lower values
in the higher values of pc2, both around the pc2 axis, e.g., the pc1 zero value.
"
loadingplot(pca,scatter=T, labels = names(d), comps = c(1,2))
biplot(pca)
abline(h=0,v=0)

"
This biplot in Figure 16, reflecting the loadings and the scores, shows that higher ranked polymers in warping resistance are 
strongly related with Mat_A and to a lesser extent to Mat_C and Mat_E. Mat_F and Mat_B seem to be related to the 
lower ranked observations.
"

##################################################################################
##########                      3                                 ################
##################################################################################

d30 <- as.data.frame(read.table("Polymer3.txt", header=T))
summary(d30)
"
     Label       Mat_A            Mat_B            Mat_C            Mat_D            Mat_E            Mat_F      
 A      :1   Min.   : 4.001   Min.   : 4.001   Min.   : 3.000   Min.   : 3.011   Min.   : 4.006   Min.   :12.45  
 B      :1   1st Qu.: 7.566   1st Qu.: 6.389   1st Qu.: 7.699   1st Qu.: 4.976   1st Qu.: 9.717   1st Qu.:13.82  
 C      :1   Median :16.090   Median :11.151   Median :10.849   Median :12.684   Median :16.405   Median :15.19  
 D      :1   Mean   :15.705   Mean   :10.877   Mean   :11.121   Mean   :10.850   Mean   :20.688   Mean   :15.19  
 E      :1   3rd Qu.:24.051   3rd Qu.:14.301   3rd Qu.:15.611   3rd Qu.:15.550   3rd Qu.:34.389   3rd Qu.:16.57  
 F      :1   Max.   :26.165   Max.   :18.980   Max.   :18.000   Max.   :18.000   Max.   :39.001   Max.   :17.94  
 (Other):4                                                                                        NA's   :8   
"
"we'll apply the irmi function to the column Mat_F, as otherwise we would have end up with two rows, and because we want to use the same variables to
compare with the pca analysis already performed
"
d3 <- d30[,2:7]
d3 <- irmi(d3)
rownames(d3) <- d30$Label

pred <- predict(pca, newdata=d3)
colnames <- c(colnames(pred),"color")
pred <- cbind(pred,c(1:length(pred[,1])))
colnames(pred) <- colnames

### strenght analysis
rownames(d) <- strength_rankings
pca<-prcomp(d, center=TRUE, scale.=TRUE)
plot(pca$x, col='blue', xlim=c(-5,4), ylim=c(-10,4))
text(pca$x,labels=strength_rankings, adj=c(0.3,-0.5), cex=0.7, col='blue')
abline(h=0,v=0)
points(pred[,c(1,2)], col='red')
text(pred[,c(1,2)],labels=rownames(pred), adj=c(0.3,-0.5), cex=0.7, col='red')
### warp analysis
rownames(d) <- warp_rankings
pca<-prcomp(d, center=TRUE, scale.=TRUE)
plot(pca$x, col='blue', xlim=c(-5,4), ylim=c(-10,4))
text(pca$x,labels=warp_rankings, adj=c(0.3,-0.5), cex=0.7, col='blue')
abline(h=0,v=0)
points(pred[,c(1,2)], col='red')
text(pred[,c(1,2)],labels=rownames(pred), adj=c(0.3,-0.5), cex=0.7, col='red')


#
#x3 <- scale(d3)
#x_pred3 <- pred[,1:2] %*% t(pca$rotation[,1:2])
#E3 <- x3 - x_pred3
#E3_sq <- E3*E3
#R32x2 <- 1 - colSums(E3_sq)/colSums(x3*x3)
#barplot(R32x2, las=2, ylab="R2x2", ylim = c(-5,5), cex.names = 0.8)
