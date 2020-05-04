# --- Example 1 Maps

install.packages("rworldmap")
library(rworldmap)
thecountries=0
malDF <- data.frame(country = c("GBR","USA","IRL"),
                    data = c(1,2,4))
# malDF is a data.frame with the ISO3 country names plus a variable to
# merge to the map data

malMap <- joinCountryData2Map(malDF, joinCode = "ISO3",
                              nameJoinColumn = "country")
# This will join your malDF data.frame to the country map data

mapCountryData(malMap, nameColumnToPlot="data", catMethod = "categorical",
               missingCountryCol = gray(.8),addLegend="F")
# And this will plot it, with the trick that the color palette's first
# color is red

# --- Example 2 Turn regression into a visualisation
#Read in the Regression Data file.
#Use the first 100 rows of data. Partition into two pieces rows 1:70 and 71-100.
#Use the first seventy to generate a linear model. Use the second thirty as the holdout data.
#Now use leave out twenty cross validation. 
#The last thirty rows in the Regression Data file is from the summer while the first 100 rows are from the winter. Investigate if the model can accommodate this.
#setwd("C:/users/david hawe/desktop")
data=read.table("RegressionData.txt")
r100x=data[1:100,1]
r100y=data[1:100,2]
plot(r100x,r100y)
lmod=lm(r100y[1:70]~0+r100x[1:70])
summary(lmod)
plot(r100x[1:70],r100y[1:70],xlab="Seventy Xs",ylab="Seventy Ys",main="Scatterplot",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*')
abline(lm(r100y[1:70]~0+r100x[1:70]),col="red",lwd=2)
points(r100x[71:100],r100y[71:100],col=c(3),pch='.',cex=6)
plot(summary(lmod)$residuals,ylab="Residuals",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*',main="Residuals")
points(r100x[71:100],(summary(lmod)$coeff[1]*r100x[71:100])-r100y[71:100],col=c(3),pch='.',cex=6)
par(mfrow=c(2,5))
count=0
count1=20
for(i in 1:5){
  lmod=lm(r100y[-c(count:count1)]~0+r100x[-c(count:count1)])
  
  plot(r100x[-c(count:count1)],r100y[-c(count:count1)],xlab="Xs",ylab="Ys",main="Scatterplot",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*')
  abline(lm(r100y[-c(count:count1)]~0+r100x[-c(count:count1)]),col="red",lwd=2)
  points(r100x[c(count:count1)],r100y[c(count:count1)],col=c(3),pch='.',cex=6)
  plot(summary(lmod)$residuals,ylab="Residuals",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*',main="Residuals")
  points(r100x[c(count:count1)],(summary(lmod)$coeff[1]*r100x[c(count:count1)])-r100y[c(count:count1)],col=c(3),pch='.',cex=6)
  count=count+20
  count1=count+20
}

par(mfrow=c(1,2))
r130x=data[101:130,1]
r130y=data[101:130,2]
lmod=lm(r100y~0+r100x)
plot(r100x,r100y,xlab="Xs",ylab="Ys",main="Scatterplot",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*')
abline(lm(r100y~0+r100x),col="red",lwd=2)
points(r130x,r130y,col=c(4),pch='.',cex=6)
plot(summary(lmod)$residuals,ylab="Residuals",cex=2,cex.lab=2,cex.main=2,cex.axis=2,pch='*',main="Residuals",ylim=c(-7,7))
points(r130x,(summary(lmod)$coeff[1]*r130x-r130y),col=c(3),pch='.',cex=6)

#Exercise use the above data to generate a model excluding the bottome row. Build a display predicting this. Allow the display to provide information around accuracy 


#Example 3 Neural Networks
install.packages("neuralnet")
library(neuralnet)
Var1 <- runif(50, 0, 100) 
sqrt.data <- data.frame(Var1, Sqrt=sqrt(Var1)) 
print(net.sqrt <- neuralnet(Sqrt~Var1,  sqrt.data, hidden=c(10,10), threshold=0.01))
compute(net.sqrt, (1:10)^2)$net.result
plot(net.sqrt)




#------------------------------
# 
plot( data[100:130,1] * summary( lm(r100y~0+r100x) )$coeff[1] , data[100:130,1] )
# residuals
plot( data[100:130,1] * summary( lm(r100y~0+r100x) )$coeff[1] - data[100:130,2], data[100:130,1] )
res<- data[100:130,1] * summary( lm(r100y~0+r100x) )$coeff[1] - data[100:130,2]

qqplot(res, rnorm(31, mean = mean(res), sd = sd(res)))

