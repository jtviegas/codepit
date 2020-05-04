install.packages(c("faraway", "gdata"))
library(faraway)
library(gdata)

######## worksheet 13 Logistic Regression

dev.off()
d0<-orings 
head(d0)

### orings binary

binary <- orings$damage
class(binary)
head(binary)

binary[orings$damage==0] <- 0
binary[orings$damage>0] <- 1
plot(binary~d0$temp)
model_binary <- glm(binary~d0$temp, family = binomial)
summary(model_binary)
#Coefficients:
#(Intercept)  15.0429     7.3786   2.039   0.0415 *
#  d0$temp      -0.2322     0.1082  -2.145   0.0320 *
# Null deviance: 28.267  on 22  degrees of freedom
# Residual deviance: 20.315  on 21  degrees of freedom
# model => y = 15.0429 - 0.2322 x
# so as the temperature goes up the log of odds go down

x <- seq(40,90,1)
lines(x,ilogit(15.0429-0.2322*x))
par(mfrow=c(2,2))
plot(model_binary)

pred <- predict(model_binary)
head(pred)
pred_prob <- exp(pred)/(1+exp(pred))

# as ni=1 we can't use the residual deviance to assess the model fit
# we just have the predictions so we will use it and if p>0.5 we say we have y=1
# and we will compare
pred_binary <- pred_prob
pred_binary[pred_prob < 0.5] <- 0
pred_binary[pred_prob > 0.5] <- 1

diff <- binary - pred_binary
correct_cases <- 1 - sum(diff)/length(pred_binary)

### orings proportion

y <- cbind(d0$damage,6-d0$damage)
model_prop <- glm(y~d0$temp, family = binomial)
summary(model_prop)
#Coefficients:
#  Estimate Std. Error z value Pr(>|z|)    
#(Intercept) 11.66299    3.29626   3.538 0.000403 ***
#  d0$temp     -0.21623    0.05318  -4.066 4.78e-05 ***
# Null deviance: 38.898  on 22  degrees of freedom
# Residual deviance: 16.912  on 21  degrees of freedom

# goodness of fit
# residual deviance =< chi^2 with 22 df at alpha 5% => 33.924
# so the model dows not significantly differ from the saturated model


### --- gender ratio

d0 <- as.data.frame(read.table("genderratio.txt", header=T))
plot(d0$males/d0$density~d0$density)
plot(d0$males/d0$density~log(d0$density))

y <- cbind(d0$males, d0$females)
model_prop <- glm(y~log(d0$density), family = binomial)
summary(model_prop)
# Coefficients:
#  Estimate Std. Error z value Pr(>|z|)    
#(Intercept)     -2.65927    0.48758  -5.454 4.92e-08 ***
#  log(d0$density)  0.69410    0.09056   7.665 1.80e-14 ***
# Null deviance: 71.1593  on 7  degrees of freedom
# Residual deviance:  5.6739  on 6  degrees of freedom
x<-seq(0,500,1)
plot(d0$males/d0$density~log(d0$density))
lines(x,ilogit(-2.65927+0.6941*x))

# compare fit model against null model
pchisq(71.1593-5.6739,1,lower.tail = F)
# 5.854609e-16 so the fitted model explains more significant variation int he data then the null mode

# testing against the saturated model
pchisq(5.6739,6,lower.tail = F)
# 0.4606925 => the residual model does not explain any significant additional variation then the saturated model


######## -------------------- exercise sheet 6

d0 <- as.data.frame(read.xls("plasma.xls", header=T))
head(d0)

hist(log(d0$fibrinogen))
hist(d0$globulin)
d0$logfib <- log(d0$fibrinogen)

# ploting it
plot(ESR~logfib, data = d0)

# fit  alogistic regression
model <- glm(d0$ESR~d0$logfib, family = binomial)
summary(model)
# Coefficients:
#   Estimate Std. Error z value Pr(>|z|)  
# (Intercept)          -6.884      2.851  -2.415   0.0157 *
#   log(d0$fibrinogen)    5.115      2.554   2.002   0.0452 *
# Null deviance: 30.885  on 31  degrees of freedom
# Residual deviance: 25.807  on 30  degrees of freedom
model
x <- seq(0,3,0.1)
lines(x,ilogit(-6.884+5.115*x))


### 2 vars
# fit  alogistic regression
model <- glm(d0$ESR~d0$logfib+d0$globulin, family = binomial)
summary(model)
#Coefficients:
#  Estimate Std. Error z value Pr(>|z|)  
#(Intercept)  -12.572      5.659  -2.222   0.0263 *
#  d0$logfib      5.268      2.706   1.946   0.0516 .
#  d0$globulin    0.151      0.117   1.291   0.1968  
# Null deviance: 30.885  on 31  degrees of freedom
# Residual deviance: 23.989  on 29  degrees of freedom

