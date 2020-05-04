################################################

########   Exercise Sheet 4 Two way ANOVA  #########

################################################

library(tidyverse)
library(here)
library(sjstats)
library(car)
library(readxl)

## Q1

## read in the data
running <-read_tsv(here("running.txt"))

#convert Gender and Exercise to factors
running$Exercise<-as.factor(running$Exercise)
running$Gender<-as.factor(running$Gender)


## summarise and visualise data
##################################

# Frequency tables to see how many observations there are in each treatment
table(running$Gender, running$Exercise)


## individual boxplots
boxplot(running$HR~running$Gender, ylab = "HR")
boxplot(running$HR~running$Exercise, ylab = "HR")

## create a boxplot showing the distribution of HR for each treatment
windows(10,10)
par(mar=c(7,5,1,1))
boxplot(HR~Exercise*Gender, data=running,  col=(c("gold","darkgreen")),
       main="Heart Rate", xlab="Gender and Exercise")

## Calculate the mean and variance of the HR for each Gender level and each Exercise level
running %>% group_by(Gender) %>% 
  summarise( mean_HR = mean(HR), var_HR = var(HR))
running %>% group_by(Exercise) %>% 
  summarise( mean_HR = mean(HR), var_HR = var(HR))



## check for an interaction between the two factors
interaction.plot(running$Exercise, running$Gender, running$HR)

## Check homogeneity of variance
fligner.test(HR~interaction(Exercise,Gender), data = running)

## Two way ANOVA with interaction effect
model1<-aov(HR~Gender*Exercise, data = running)

summary(model1)


## calculate treatment means
model.tables(model1, type="means", se=F)

# post hoc test
TukeyHSD(model1)


## Calculate effect size
eta_sq(model1, partial=T)
omega_sq(model1)

## Model Diagnostics
windows(10,10)
par(mfrow = c(2,2))
plot(model1)

####################
## Q2
####################


# import data
growth<-read_tsv(here("growth.txt"))
growth$supplement<-as.factor(growth$supplement)
growth$diet<-as.factor(growth$diet)

## summarise and visualise data
##################################

## individual boxplots
boxplot(growth$gain~growth$diet, ylab = "gain")
boxplot(growth$gain~growth$supplement, ylab = "gain")

windows(10,10)
par(mar=c(8,5,1,1))
## create a boxplot showing the distribution of gain for each treatment
boxplot(gain~diet*supplement, data=growth,  col=(c("gold","darkgreen", "red")),
         las=2, main="gain")

windows(15,6)
par(mfrow = c(1,3))
growth %>%
  filter(diet == "wheat") %>%
  with(hist(gain, main = "wheat"))

## Calculate the mean and variance of the gain for each diet level and each supplement level
growth %>% group_by(diet) %>% 
  summarise( mean_gain = mean(gain), sd_gain = sd(gain), min_gain = min(gain),max_gain = max(gain))
growth %>% group_by(supplement) %>% 
  summarise( mean_gain = mean(gain), sd_gain = sd(gain),min_gain = min(gain),max_gain = max(gain))
hist(growth$gain)
table(growth$diet)
table(growth$supplement)

## check for an interaction between the two factors
windows(5,5)
interaction.plot(growth$supplement, growth$diet, growth$gain)

## Check homogeneity of variance
fligner.test(gain~interaction(supplement,diet), data = growth)

## Fit the model
m1<-aov(growth$gain~growth$supplement*growth$diet)
m2<-aov(growth$gain~growth$supplement+growth$diet)

summary(m1)
summary(m2)

omega_sq(m1)

TukeyHSD(m1)

## Model Diagnostics
windows(10,10)
par(mfrow = c(2,2))
plot(m1)

## The QQ plot looked troublesome but a histogram of the residuals appears to be reasonable 
hist(resid(m1))

