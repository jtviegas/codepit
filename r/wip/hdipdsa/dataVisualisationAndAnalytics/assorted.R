source("include.R")
install.packages(c("forecast", "tseries", "ggplot2"))
install_github("quandl/quandl-r")
library(Quandl)
library(forecast)
library(tseries)
library(ggplot2)


getNextPointForecasts2 <- function(data, training_window){
  
  r <- vector()
  
  last_training_point <- length(data)-1
  last_training_window_start <- last_training_point-training_window+1
  r[c(1:training_window)] <- NULL
  
  for(i in seq(1,last_training_window_start)){
    model <- ets(data[c(seq(i:i+training_window-1))])
    f <- forecast(model,h=1)
    r[i+training_window] <- f$mean[1]
  }
  
  return (r)
  
}


getNextPointForecasts_arima2 <- function(data, training_window){
  
  len <- length(data)
  r <- rep(NA, len)
  training_window_start <- 1
  
  while(training_window_start + training_window - 1 < len){
    training_window_end <- training_window_start + training_window - 1
    forecast_instant <- training_window_end + 1
    f <- forecast(auto.arima(data[training_window_start:training_window_end]),h=1)
    r[forecast_instant] <- f$mean[1]
    
  }
  
  return (r)
}


ggplot(data = d, aes(x=index(d), y=open)) + geom_line() + scale_x_date('month')  + ylab("Daily Bike Checkouts") +
  xlab("")

fit <- ets(d)
plot(forecast(fit, ))


#replace missing values and outliers, interpolate substitutions
d$close_clean <-tsclean(d$close)

d$close_clean_ma_50  <- xts(ma(d$close_clean, order=50), order.by = index(d))
d$close_clean_ma_100  <- xts(ma(d$close_clean, order=100), order.by = index(d))

ggplot() +
  geom_line(data = d, aes(x=index(d), y = close, colour = "close")) +
  geom_line(data = d, aes(x=index(d), y = close_clean_ma_50,   colour = "50 day Moving Average"))  +
  geom_line(data = d, aes(x=index(d), y = close_clean_ma_100, colour = "100 day Moving Average"))  +
  ylab('$')

count_ma = ts(na.omit(d$close_clean), frequency=30)
decomp = stl(count_ma, s.window="periodic")
deseasonal_cnt <- seasadj(decomp)
plot(decomp)



x<-c(1,5,2,8,6,3,2,4,7)
ma(x,order= 4)
rollmean(x,4,,align="center")

ma4 <- rollapplyr(x,list(-(4:1)),mean,fill=NA)
?ets

#d <- getSymbolPrices("AAPL", c("2007-01-01","2016-01-01"))
#d$forecast <- getWindowForecasts(d$close,28,7)
#d$position <- getPositions(d, "close", "forecast", 29, 7)
#d$balance <- getBalance(d, "close", "position")

#ggplot() +
#  geom_line(data = d, aes(x=index(d), y = close, colour = "close")) +
#  geom_line(data = d, aes(x=index(d), y = forecast,   colour = "forecast"))  +
#  geom_line(data = d, aes(x=index(d), y = position,   colour = "position"))  +
#  geom_line(data = d, aes(x=index(d), y = balance,   colour = "balance"))  +
#  ylab('$')


#data <- d
#valueColumn <- "close"
#forecastColumn <- "forecast" 
#positionColumn <- "position" 
#first_forecast_index <- 29 
#forecast_window_length <- 7
