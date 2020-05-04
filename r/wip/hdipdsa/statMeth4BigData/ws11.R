# worksheet 11 - PCA

x1 <- c(122,21,105,101,155,131,115,53,75,45)
x2 <- c(117,32,140,105,149,146,82,60,82,37)

x1_scaled <- (x1 - mean(x1))/sd(x1)
x2_scaled <- (x2 - mean(x2))/sd(x2)

x <- cbind(x1_scaled, x2_scaled)
x

dev.new(6,6)

plot(x2_scaled~x1_scaled)

# run a pca
pca_example <- prcomp(x, scale.=T, center=T)
summary(pca_example)
pca_example
# the components
pca_example$rotation

# plot the principal components
slope1 <- (-0.7071068/-0.7071068)
slope2 <- (0.7071068/-0.7071068)
abline(0,slope1)
abline(0,slope2)

pca_example$x

plot(pca_example$x)


# pca by hand

cor_matrix <- cor(x)
p <- eigen(cor_matrix)
p <- p$vectors

# T=XP
t<-x%*%p

plot(t)
abline(v=0,h=0)

# x = tp^t+e calculate the x values by the pca model
# and then we calculate the residuals
# e = x - tp^t

# calculate the residuals for the model with 2 pc => e2

x_mod2 <- pca_example$x%*%t(pca_example$rotation)







