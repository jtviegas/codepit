# null model -> model with only interceptor

# case A

if (!require("faraway")) {
  install.packages("faraway")
  library(faraway)
}

data(orings)
orings <- orings
head(orings)

# to fit the logistic model
Ch_model_binary <- glm(Binary~orings$temp, family=binomial)

Ch_model_binary
# Coefficients:
# (Intercept)  orings$temp  
#    15.0429      -0.2322  
# Degrees of Freedom: 22 Total (i.e. Null);  21 Residual
# Null Deviance:	    28.27 
# Residual Deviance: 20.32 	AIC: 24.32

plot(Binary~orings$temp, xlim=c(40,90),ylim=c(0,1), xlab="Temperature", ylab = "p")

x<- seq(40,90, 1)
tail(x)
head(x)
lines(x, ilogit(15.04-0.23*x), type="l")
 
plot(Ch_model_binary)

pred<-predict(Ch_model_binary)
pred_prob <- exp(pred)/(1+exp(pred))
pred <- predict(Ch_model_binary, type="response")
fitted(Ch_model_binary)

pred_binary <- pred_prob
pred_binary[pred_binary<0.5] <-0
pred_binary[pred_binary>0.5] <-1
diff <- Binary - pred_binary
1-sum(diff)/length(pred_binary)


# Case B

y <- cbind(orings$damage, 6-orings$damage)
Ch_model_prom <- glm(y~orings$temp, family=binomial)



# -------------------------------------------------------------------

g <- as.data.frame(read.table("genderratio.txt", header=T))

y <- cbind(g$females, g$males)
Ch_model_x <- glm(y~log(g$density), family=binomial)
plot(Ch_model_x)

Ch_model_x

#pchisq(71.1593-5.6)


