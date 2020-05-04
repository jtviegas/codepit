## Worksheet 12

library(faraway)
orings<-orings 
y<-cbind(orings$damage, 6-orings$damage)

##create Binary response variable to use in logistic regression model
Binary<-orings$damage
Binary[orings$damage == 0] <- 0
Binary[orings$damage > 0] <- 1

## plot data
windows(5,5)
plot(Binary~orings$temp,xlim=c(50,90))

##create logistic regression model
Ch_model_Binary<-glm(Binary~orings$temp, family=binomial)
summary(Ch_model_Binary)

## plot logistic regression model
x<-seq(25, 85, 1)
lines(x,ilogit(15.04-0.23*x))

## model checking
windows(10,10)
par(mfrow=c(2,2))
plot(Ch_model_Binary)

## count proportion of correct predictions
pred<-predict(Ch_model_Binary)
pred_prob<-exp(pred)/(1+exp(pred))
pred<-predict(Ch_model, type=”response”)

 pred_binary <- pred_prob
 pred_binary [pred_binary < 0.5] <- 0
 pred_binary [pred_binary > 0.5] <- 1

diff<- Binary-pred_binary
1-sum(diff)/length(pred_binary)

### Analysis for challanger data - response variable is proportion of O-rings damaged.

plot(orings$damage/6~orings$temp, xlim=c(30,90), ylim=c(0,1), xlab="Temperature", ylab="p")

#create the matrix containing the number of successes and the number of failures
y<-cbind(orings$damage, 6-orings$damage)

#fit model
Ch_model_prop<-glm(y~orings$temp, family=binomial)
summary(Ch_model_prop)

## plot the fitted model
x<-seq(25, 85, 1)
lines(x,ilogit(11.66-0.22*x),type="l")



#### Worksheet  #####  insect data

library(faraway)

plot(sexratio$males/sexratio$density~sexratio$density, xlim=c(-100,1000), xlab="Density", ylab="proportion male")
y<-cbind(sexratio$males,sexratio$females)
model<-glm(y~sexratio$density,binomial)
summary(model)
z<-seq(-700, 1000, 1)
lines(z,ilogit(0.0807368+0.0035101*z))

hist(sexratio$density)
hist(log(sexratio$density))
model1<-glm(y~log(sexratio$density),binomial)
summary(model1)
plot(sexratio$males/sexratio$density~log(sexratio$density), xlim=c(-0,8), xlab="log(Density)", ylab="proportion male")
lines(z,ilogit(-2.65927+0.6941*z))

pchisq(71.1593-5.6739,1, lower=F)

Null_model<-glm(y~1,binomial)
anova(model, Null_model, test="Chisq" )


################  hypertension

no.yes<-c("No", "Yes")
smoking<-gl(2,1,8,no.yes)
obesity<-gl(2,2,8, no.yes)
snoring<-gl(2,4,8,no.yes)
n.tot<-c(60,17,8,2,187,85,51,23)
n.hyp<-c(5,2,1,0,35,13,15,8)
hyp<-data.frame(smoking,obesity,snoring,n.tot,n.hyp)

y<-cbind(n.hyp,n.tot-n.hyp)
y
z<-n.hyp/n.tot

xtabs(~ smoking + obesity)
xtabs(~ smoking + snoring)
xtabs(~ obesity + snoring)
xtabs(~ smoking + snoring+obesity)

windows(5,5)
plot(z~hyp$smoking, xlab="Smoking", ylab="Proportion of subjects")
plot(z~hyp$obesity, xlab="Obesity", ylab="Proportion of subjects")
plot(z~hyp$snoring, xlab="Snoring", ylab="Proportion of subjects")
hist(z, xlab = "Proportion of subjects", ylab="Frequency", main="")

modelA<- glm(y~smoking+obesity+snoring,binomial, data = hyp)
summary(modelA)

modelB<- glm(y~obesity+snoring,binomial, data = hyp)
summary(modelB)

model<-glm(z~smoking+obesity+snoring,binomial, data = hyp, weights = n.tot)


