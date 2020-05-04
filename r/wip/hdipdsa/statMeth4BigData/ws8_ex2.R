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

data <- NULL
data <- read_tsv(here("wip/data/tensile.txt"))
head(data)

## 1. What is the factor in this experiment? 
# concentration
is.factor(data$Concentration)
data$Concentration <- as.factor(data$Concentration)
is.factor(data$Concentration)

## 2. What is the response variable in this experiment?
# Strength

## 3. How many levels does the factor have?
levels(data$Concentration)

## 4. State the null and alternative hypotheses associated with this experiment.
#... null: the mean strengths on each concentration are equal 
#... alternative: the mean strengths on each concentration are not equal

## 5. Carry out a one way analysis of variance on this data and state your conclusion.
# Use the 5% significance level and be sure to check that the assumptions of ANOVA are satisfied.

#...summarize by Concentration
data %>% group_by(Concentration) %>% summarise(mean_Strength=mean(Strength), sd_Strength=sd(Strength))

#...we can visualize the effect of the Concentration using a box & whisker plot
boxplot(data$Strength~data$Concentration)
#...there are no outliers

#...assumptions to the Anova
#.. 1. random sampling
#.. 2. variances of different treatments are equal
#.. 3. the error terms are independent from observation to observation
#.. 4. the error terms are normally distributed with zero mean and the same variance

##...let's check the homogeneity of the variance
#..let's get the variances
vars <- data %>% group_by(Concentration) %>% summarise(var_Strength=var(Strength))
#... let's use Fligner-Killeen test of homogeneity of variances
fligner.test(data$Strength~data$Concentration) # p-value = 0.5442 : we don't reject null hypothesis, variances are the same
#... so we will check the rest of th assumptions after the anova
#...anova
model_treat <- aov(data$Strength~data$Concentration)
summary(model_treat) 
#                   Df Sum Sq Mean Sq F value   Pr(>F)    
#data$Concentration  3  382.8  127.60   19.61 3.59e-06 ***
#...so the Strength are different based on the concentration at the 95% significance level

#...now we can obtain a model from anova command, 
# The default command will output treatment contrasts that compare each treatment to a baseline
summary.lm(model_treat)
#Coefficients:
#                       Estimate Std. Error t value Pr(>|t|)    
#(Intercept)            10.000      1.041   9.602 6.24e-09 ***
#  data$Concentration10    5.667      1.473   3.847 0.001005 ** 
#  data$Concentration15    7.000      1.473   4.753 0.000122 ***
#  data$Concentration20   11.167      1.473   7.581 2.65e-07 ***
#  ---
#  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#Residual standard error: 2.551 on 20 degrees of freedom
#Multiple R-squared:  0.7462,	Adjusted R-squared:  0.7082 
#F-statistic: 19.61 on 3 and 20 DF,  p-value: 3.593e-06

#... note that the p-values for thee mean strength for the concentrations 10,15 and 20  
# are signifficantly different than the one for the 5
#..althought r-squared says that te model explains 74.62% of the variation

#...we can now view the treatment effects graphically using
plot.design(data$Strength~data$Concentration)
#.. the treatment effects in tabular form
model.tables(model_treat)
### Effect Size

#...to measure the effect size with a one way anova, we use the (r squared)
# Multiple R-squared: says that te model explains 74.62% of the variation

## pairwise comparisons
#...to compare each pair of soil types we can use Tukey's honest significat difference (HSD)
TukeyHSD(model_treat)
#$`data$Concentration`
#diff         lwr       upr     p adj
#10-5   5.666667  1.54410408  9.789229 0.0051108 => significantly different
#15-5   7.000000  2.87743741 11.122563 0.0006501 => significantly different
#20-5  11.166667  7.04410408 15.289229 0.0000015 => significantly different
#15-10  1.333333 -2.78922925  5.455896 0.8022275 => mean Strength of 15 and 10 are NOT significantly different
#20-10  5.500000  1.37743741  9.622563 0.0065966 => significantly different
#20-15  4.166667  0.04410408  8.289229 0.0470251 => significantly different

## Model Validation
#...checking the assumptions
par(mfrow=c(2,2))
plot(model_treat)

par(mfrow=c(1,1))
