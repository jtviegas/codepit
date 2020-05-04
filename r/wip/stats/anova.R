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

data <- read_tsv(here("wip/data/soil_data.txt"))
head(data)
#... organize data
data <- data %>% gather( key = "soil", value = "yield")
#...summarize by soil type
data %>% group_by(soil) %>% summarise(mean_yield=mean(yield), sd_yield=sd(yield),max_yield=max(yield), min_yield=min(yield), n=n())
# ...to do anova we must ensure soil is a factor
is.factor(data$soil)
data$soil <- as.factor(data$soil)
is.factor(data$soil)
levels(data$soil)

#...we can visualize the effect of the soil using a box & whisker plot
boxplot(data$yield~data$soil)
#...we can check if there is a potential outlier in clay
grubbs.test(data$yield[data$soil=="Clay"])
#... the lowest value(3) is not an outlier

#...assumptions to the Anova
#.. 1. random sampling
#.. 2. variances of different treatments are equal
#.. 3. the error terms are independent from observation to observation
#.. 4. the error terms are normally distributed with zero mean and the same variance

##...let's check the homogeneity of hte variance
#..let's get the variances
vars <- data %>% group_by(soil) %>% summarise(var_yield=var(yield))
#... let's use Fligner-Killeen test of homogeneity of variances
fligner.test(data$yield~data$soil) # p-value = 0.8332 : we don't reject null hypothesis, variances are the same
#... so we will check the rest of th assumptions after the anova
#...anova
model_treat <- aov(data$yield~data$soil)
summary(model_treat) 
#            Df Sum Sq Mean Sq F value Pr(>F)  
#data$soil    2   99.2   49.60   4.245  0.025 *
#...so the soils are different at the 95% significance level

#...now we can obtain a model from anova command, 
# The default command will output treatment contrasts that compare each treatment to a baseline
summary.lm(model_treat)
#Coefficients:
#  Estimate Std. Error t value Pr(>|t|)    
#(Intercept)     11.500      1.081  10.638  3.7e-11 ***
#  data$soilLoam    2.800      1.529   1.832   0.0781 .  
#data$soilSand   -1.600      1.529  -1.047   0.3046    
#---
#  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
#Residual standard error: 3.418 on 27 degrees of freedom
#Multiple R-squared:  0.2392,	Adjusted R-squared:  0.1829 
#F-statistic: 4.245 on 2 and 27 DF,  p-value: 0.02495

contrasts(data$soil)
#...we specify sum contrasts
contrasts(data$soil) <- contr.sum
#...we run the anova again
model_sum <- aov(data$yield~data$soil)
summary(model_sum) 
summary.lm(model_sum)
#Coefficients:
#  Estimate Std. Error t value Pr(>|t|)    
#(Intercept)  11.9000     0.6241  19.067   <2e-16 ***
#  data$soil1   -0.4000     0.8826  -0.453   0.6540    
#data$soil2    2.4000     0.8826   2.719   0.0113 *  
#  ---
#  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1
#
#Residual standard error: 3.418 on 27 degrees of freedom
#Multiple R-squared:  0.2392,	Adjusted R-squared:  0.1829 
#F-statistic: 4.245 on 2 and 27 DF,  p-value: 0.02495

#...Notice that for this output, the intercept coefficient corresponds to the overall mean.
# The Soil1 coefficient corresponds to the difference between the overall mean and the
# mean of the Clay treatment.
# The Soil2 coefficient corresponds to the difference between the overall mean and the
# mean of the Loam treatment.

#...What about the Sand treatment? To figure out how to calculate the mean of the Sand
# treatment from the output, we need to look at the contrasts:
contrasts(data$soil)

#...we can now view the treatment effects graphically using
plot.design(data$yield~data$soil)
#.. the treatment effects in tabular form
model.tables(model_treat)

#...to return the contrasts to the default setting
contrasts(data$soil) <- NULL

### Effect Size

#...to measure the effect size with a one way anova, we use the (r squared)
# Multiple R-squared:  0.2392 => 23.9% of the variation in yield was caused by soil type

## pairwise comparisons
#...to compare each pair of soil types we can use Tukey's honest significat difference (HSD)
TukeyHSD(model_treat)
#          diff        lwr        upr     p adj
#Loam-Clay  2.8 -0.9903777  6.5903777 0.1785489
#Sand-Clay -1.6 -5.3903777  2.1903777 0.5546301
#Sand-Loam -4.4 -8.1903777 -0.6096223 0.0204414 => mean yield of Sand and Loam are significantly different

## Model Validation
#...checking the assumptions
par(mfrow=c(2,2))
plot(model_treat)




par(mfrow=c(1,1))
