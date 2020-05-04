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

install_if_missing("outliers")
library(outliers)

data <- read_tsv(here("wip/data/Fish1.txt"))
head(data)

#...scatterplots relating all variables
panel.hist <- function(x, ...)
{
  usr <- par("usr"); on.exit(par(usr))
  par(usr = c(usr[1:2], 0, 1.5) )
  h <- hist(x, plot = FALSE)
  breaks <- h$breaks; nB <- length(breaks)
  y <- h$counts; y <- y/max(y)
  rect(breaks[-nB], 0, breaks[-1], y, col = "cyan", ...)
}
pairs(data, diag.panel = panel.hist)

##...check for univariate outliers with grubbs.test
grubbs.test(data$mg)

##...check for multivariate outliers

#....create a vector eith the variance of each variable
cov_matrix<-cov(data)
#...create a vector containing the means
center<-colMeans(data)
# calculate mahalanobis: each value represents the square of the mahalanobis distance (a k-dimensional z-score).
mahalanobis(data, center, cov_matrix)

#..we need to compare this values against the centroid of the data set, 
# so we compare them to the critical value from the chi-squared distribution with alpha 0.05
# and k=4
qchisq(0.95, df=4) # 9.487729 -> there is one outlier !!!



