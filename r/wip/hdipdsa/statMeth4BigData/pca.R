install.packages(c("pls", "car", "outliers"))
library(pls)
library(car)
library(outliers)
#### === worksheet 11
x1 <- c(122,21,105,101,155,131,115,53,75,45)
x2 <- c(117,32,140,105,149,146,82,60,82,37)
x1_scaled <- (x1 - mean(x1))/sd(x1)
x2_scaled <- (x2 - mean(x2))/sd(x2)

x <- cbind(x1_scaled, x2_scaled)
plot(x2_scaled~x1_scaled)

o_pca <- prcomp(x,center = T, scale. = T)

summary(o_pca)
o_pca
o_pca$rotation

# plot the pc's
slope1 <- (-0.7071068/-0.7071068)
slope2 <- (0.7071068/-0.7071068)
abline(0,slope1)
abline(0,slope2)

o_pca$x
plot(o_pca$x)

## ... by hand
cor_matrix <- cor(x)
p <- eigen(cor_matrix)
p <- p$vectors

# ... two component model
# t = xp
t <- x%*%p

#... the residuals
# T = XP <=> X = TPt + E <=> E = X - TPt

x_mod2 <-  t %*% t(p)
e2 <- x - x_mod2

# goodness of fit
 1 - ( sum(e2[,1]*e2[,1])/sum(x*x) )
 1 - ( sum(e2[,2]*e2[,2])/sum(x*x) )

r2x <- 1 - ( sum(e2*e2)/sum(x*x) )


# one component model
# t = xp
t1 <- x%*%p[,1]
x_mod1 <-  t1 %*% t(p[,1])
e1 <- x - x_mod1
# goodness of fit
r1x <- 1 - ( sum(e1*e1)/sum(x*x) )

#### === worksheet 12

foods <- as.data.frame(read.table("Foods.txt", header=T))
head(foods)
row.names(foods) <- foods$Country
foods$Country <- NULL

dev.new(20,16)
par(mfrow=c(4,5))

for (i in c(1,3,11,15,16,17,19)){
  foods[,i] <- (-log( max(foods[,i]) + 1 -foods[,i] ) )
}

for(i in seq(1, length(foods), 1)){
  hist(foods[,i],xlab=names(foods[i]), main=names(foods[i]))
}

pca <- prcomp(foods, scale. = T, center = T)
summary(pca)
pca

dev.off()
plot(pca$x, xlim=c(-5,5), ylim=c(-5,5))
text(pca$x,labels=rownames(foods), adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)

loadings <- pca$rotation
sorted_loadings <- loadings[order(loadings[,1]), 1]
plot(sorted_loadings, main="loadings plot for pc1", ylim=c(-0.5,0.5), xlab = "variable loadings")
text(sorted_loadings, labels = names(sorted_loadings), adj=c(0.3,-0.5), cex = 0.7)

plot(pca$rotation[,1], pca$rotation[,2], xlab = "pc1", ylab = "pc2", xlim=c(-0.5,0.5), ylim=c(-0.5,0.5))
#text(pca$rotation[,1], pca$rotation[,2], labels=row.names(pca$rotation), adj=c(0.3,-0.5), cex=0.7)
text(pca$rotation[,1], pca$rotation[,2], labels=colnames(foods), adj=c(0.3,-0.5), cex=0.7)

loadingplot(pca,scatter=T, xlim=c(-0.5,0.5), ylim=c(-0.5,0.5), labels = names(foods))

# how many components do we need?
summary(pca)
screeplot(pca)
screeplot(pca, type = "lines")

scoreplot(pca, comps = c(1,3), labels="names")
abline(h=0,v=0)
loadingplot(pca,scatter=T, xlim=c(-0.5,0.5), ylim=c(-0.5,0.5), labels = names(foods), comps=c(1,3))
abline(h=0,v=0)
dataEllipse(pca$x[,1], pca$x[,2], levels=0.95, xlim=c(-7,7), ylim=c(-6,6), xlab="pc1", ylab="pc2")
abline(h=0,v=0)

# t = xp
x <- scale(foods)
# one component
x_pred1 <- pca$x[,1] %*% t(pca$rotation[,1])
E <- x - x_pred1
E_sq <- E*E
R2x1 <- 1 - colSums(E_sq)/colSums(x*x)
barplot(R2x1, las=2, ylab="R2x1", ylim = c(0,1), cex.names = 0.8)
# 2 components
x_pred2 <- pca$x[,1:2] %*% t(pca$rotation[,1:2])
E2 <- x - x_pred2
E2_sq <- E2*E2
R2x2 <- 1 - colSums(E2_sq)/colSums(x*x)
barplot(R2x2, las=2, ylab="R2x2", ylim = c(0,1), cex.names = 0.8)
# 2 components
x_pred5 <- pca$x[,1:5] %*% t(pca$rotation[,1:5])
E5 <- x - x_pred5
E5_sq <- E5*E5
R2x5 <- 1 - colSums(E5_sq)/colSums(x*x)
barplot(R2x5, las=2, ylab="R2x5", ylim = c(0,1), cex.names = 0.8)

#### === exercise sheet 5
##### 1
d0 <- as.data.frame(read.table("heptathlon.txt", header=T))
for(i in c(2,5,8)){
  d0[,i] <- max(d0[,i])-d0[,i]
}

# ... study data
dev.new()
par(mfrow=c(4,2))
for(i in 2:length(d0))
  hist(d0[,i], xlab=names(d0[i]), main=names(d0[i]))

dev.off()
dev.new()
par(mfrow=c(2,4))
for(i in 2:length(d0))
  boxplot(d0[,i], xlab=names(d0[i]), main=names(d0[i]))

#scatterplots
pairs(d0[,2:8])

dev.off()



#check for outliers in hurdles, high jump, longjump run800m
grubbs.test(d0$hurdles)
grubbs.test(d0$highjump)
grubbs.test(d0$longjump)
grubbs.test(d0$run800m)
# observation 25 looks like an outlier

#check for multivariate outliers
cov_matrix <- cov(d0[,2:8])
center<- colMeans(d0[,2:8])
# Calculate the mahalanobis distance for the data set 
mahalanobis(d0[,2:8], center, cov_matrix)

#critical value
qchisq(0.95,df = 7)
#observation 25 is an outlier, should be removed
#remove observation 25
d0<-d0[-25,]

#reexamine data
dev.new()
par(mfrow=c(4,2))
for(i in 2:length(d0))
  hist(d0[,i], xlab=names(d0[i]), main=names(d0[i]))

dev.off()
dev.new()
par(mfrow=c(2,4))
for(i in 2:length(d0))
  boxplot(d0[,i], xlab=names(d0[i]), main=names(d0[i]))

#scatterplots
pairs(d0[,2:8])

dev.off()

row.names(d0) <- d0$Position
d0$Position <- NULL
# ... pca
pca <- prcomp(d0, scale. = T, center = T)
summary(pca)
pca$x

#...If we use the Kaiser Criterion to select the number of principal components used for
# the analysis, how many components will be retained?
#                         PC1    PC2     PC3     PC4     PC5     PC6    PC7
# Standard deviation     2.1119 1.0928 0.72181 0.67614 0.49524 0.27010 0.2214
# R: the sd is the squared root of the eigenvalue associated with the component, 
# Kaiser chooses the eigenvalues higher that 1 so we choose PC1 and PC2

#... Produce a scores plot and a loading plot.
dev.off()
dev.new()
par(mfrow=c(2,1))
plot(pca$x)
text(pca$x,labels=rownames(d0), adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)
loadingplot(pca,scatter=T, labels = names(d0))
#...Comment on the score plot.
# R: PC1 and PC2 are kind of positively correlated, and that the results are negatively correlated with PC1 and positively with PC2.

#... Use the loadings plot to identify which four events had the highest correlation with
# PC1 and which event had the lowest correlation with PC1.
# R: 4 highest correlation with PC1: javelin, run800m,highjump, shot
#    lowest correlation with PC1: longjump

# ... Which events are correlated with PC2?
# R: run800m, run200m

# ...Looking at PC2, if a competitor performed well in the high jump, which event are
# they less likely to have performed well in?
# R: run800m

# .. Which events did the competitor that finished in 1 st place perform well in?
# R: hurdles, longjump and run200m

# .. Produce a scores plot and a loading plot for PC1 only. How well does PC1
# correspond with the criteria used to determine a participants position?
plot(pca$x[,1])
text(pca$x[,1],labels=rownames(d0), adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)
loadingplot(pca,scatter=T, labels = names(d0), comps = (1))
# R: PC1 is negatively correlated with position, and the most negatively correlated variables are hurdles, longjump and run200m

#...Produce a scores plot and a loading plot for PC1 vs PC3.
plot(pca$x[,c(1,3)])
text(pca$x[,c(1,3)],labels=rownames(d0), adj=c(0.3,-0.5), cex=0.7)
abline(h=0,v=0)
loadingplot(pca,scatter=T, labels = names(d0), comps = c(1,3))

#... Which event is highly correlated with PC3?
# R: longjump, javelin

dev.off()
dev.new()
par(mfrow=c(3,1))
#..Calculate the model goodness of fit for each of the original variables for a model
# with one principal component (R 1 ²X 1 … R 1 ²X 7 ) and plot this.
# t = xp
x <- scale(d0)
# one component
x_pred1 <- pca$x[,1] %*% t(pca$rotation[,1])
E1 <- x - x_pred1
E1_sq <- E1*E1
R2x1 <- 1 - colSums(E1_sq)/colSums(x*x)
barplot(R2x1, las=2, ylab="R2x1", ylim = c(0,1), cex.names = 0.8)

#... Calculate the model goodness of fit for each of the original variables for a model
# with two principal components (R 2 ²X 1 … R 2 ²X 7 ) and plot this.
x_pred2 <- pca$x[,1:2] %*% t(pca$rotation[,1:2])
E2 <- x - x_pred2
E2_sq <- E2*E2
R2x2 <- 1 - colSums(E2_sq)/colSums(x*x)
barplot(R2x2, las=2, ylab="R2x2", ylim = c(0,1), cex.names = 0.8)

#... Calculate the model goodness of fit for each of the original variables for a model
# with three principal components (R 3 ²X 1 … R 3 ²X 7 ) and plot this.
x_pred3 <- pca$x[,1:3] %*% t(pca$rotation[,1:3])
E3 <- x - x_pred3
E3_sq <- E3*E3
R2x3 <- 1 - colSums(E3_sq)/colSums(x*x)
barplot(R2x3, las=2, ylab="R2x3", ylim = c(0,1), cex.names = 0.8)

#Using the plots produced for (m), (n) and (o), comment on how well each variable is explained by a PCA model with
#1 principal component ()
#2 principal components )
#3 principal components

##### 2


