library(outliers)


# import data
heptathlon<-read.table("heptathlon.txt", header = T)


## re-order variables so that a good position in the running corresponds to a high value
heptathlon$hurdles<-max(heptathlon$hurdles)-heptathlon$hurdles
heptathlon$run200m<-max(heptathlon$run200m)-heptathlon$run200m
heptathlon$run800m<-max(heptathlon$run800m)-heptathlon$run800m


###########  examine the data  #######

#boxplots
windows(10,10)
par(mfrow=c(3,3))
    for (i in 2:ncol(heptathlon))
boxplot(heptathlon[,i],ylab=colnames(heptathlon)[i])

#histograms
windows(10,10)
par(mfrow=c(3,3))
for (i in 2:ncol(heptathlon))
  hist(heptathlon[,i],xlab=colnames(heptathlon)[i])

#scatterplots
pairs(heptathlon[,2:8])

#check for outliers in hurdles, high jump, longjump run800m
grubbs.test(heptathlon$hurdles)
grubbs.test(heptathlon$highjump)
grubbs.test(heptathlon$longjump)
grubbs.test(heptathlon$run800m)

# observation 25 seems unusual

#check for multivariate outliers
cov_matrix <- cov(heptathlon[,2:8])
center<- colMeans(heptathlon[,2:8])
# Calculate the mahalanobis distance for the data set 
mahalanobis(heptathlon[,2:8], center, cov_matrix)

#critical value
qchisq(0.95,df = 7)

#remove observation 25
heptathlon<-heptathlon[-25,]


###re-examine data
#boxplots
windows(10,10)
par(mfrow=c(3,3))
for (i in 2:ncol(heptathlon))
  boxplot(heptathlon[,i],ylab=colnames(heptathlon)[i])

pairs(heptathlon[,2:8])

#store the information in Positions in rownames and remove Positions variable
rownames(heptathlon)<- heptathlon$Position
heptathlon$Position<-NULL



pca<-prcomp(heptathlon, center=TRUE, scale.=TRUE)
summary(pca)

#scores and loading PC1 vs PC2
windows(6,6)
par(mfrow=c(1,1))
plot(pca$x)
text(pca$x, labels=rownames(heptathlon), adj=c(0.3, -0.5), cex=0.7)

windows(6,6)
plot(pca$rotation) #xlim=c(-0.6 , 0))
text(pca$rotation, labels=colnames(heptathlon), adj=c(0.3, -0.5), cex=0.7)

#scores for PC1 only
windows(6,6)
plot(pca$x[,1])
text(pca$x[,1], labels=rownames(heptathlon), adj=c(0.3, -0.5), cex=0.7)

#scores and loading PC1 vs PC3
windows(6,6)
plot(pca$x[,1],pca$x[,3])
text(pca$x[,1],pca$x[,3], labels=rownames(heptathlon), adj=c(0.3, -0.5), cex=0.7)

windows(6,6)
plot(pca$rotation[,1],pca$rotation[,3], xlim=c(-0.6, 0))
text(pca$rotation[,1],pca$rotation[,3], labels=colnames(heptathlon), adj=c(0.3, -0.5), cex=0.7)

## Calculate R^2 for each original variable ##
## Calculate fitted/predicted values for a PCA model with 1, 2 and 3 PCs
X_pred1 <- pca$x[,1]%*%t(pca$rotation[,1])
X_pred2 <- pca$x[,1:2]%*%t(pca$rotation[,1:2])
X_pred3 <- pca$x[,1:3]%*%t(pca$rotation[,1:3])

# scale original dataset
X<- scale(heptathlon)

# calculate the residuals (observed - fitted)
E1<- X-X_pred1
E2<- X-X_pred2
E3<- X-X_pred3

# square the residuals
E1<-E1*E1
E2<-E2*E2
E3<-E3*E3

# calculate R^2
R2X1 <-1-colSums(E1)/colSums(X*X)
R2X2 <-1-colSums(E2)/colSums(X*X)
R2X3 <-1-colSums(E3)/colSums(X*X)

windows(6,6)
barplot(R1X1, las=2, ylab="R2X1", ylim = c(0,1), cex.names = 0.8)
windows(6,6)
barplot(R2X2, las=2, ylab="R2X2", ylim = c(0,1), cex.names = 0.8)
windows(6,6)
barplot(R3X3, las=2, ylab="R2X3", ylim = c(0,1), cex.names = 0.8)



