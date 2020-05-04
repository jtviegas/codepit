if (!require("forecast")) {
  install.packages("forecast")
  library(forecast)
}
if (!require("tseries")) {
  install.packages("tseries")
  library(tseries)
}
if (!require("ggplot2")) {
  install_github('hadley/ggplot2')
  library(ggplot2)
}
if (!require("TTR")) {
  install.packages("TTR")
  library(TTR)
}
if (!require("devtools")) {
  install.packages("devtools")
  library(devtools)
}
if (!require("Quandl")) {
  install_github("quandl/quandl-r")
  library(Quandl)
}
if (!require("plotly")) {
  install.packages("plotly")
  library(plotly)
}
if (!require("quantmod")) {
  install.packages("quantmod")
  library(quantmod)
}
if (!require("TSstudio")) {
  install.packages("TSstudio")
  library(TSstudio)
}

Quandl.api_key('xQ9Sq7ybYvkuhLnyC1JF')

get_tickers <- function(){
  r <- stockSymbols(exchange=c("NASDAQ"))
  return (r)
}

getSymbolPrices <- function(sym, dates){
  print(paste("get_data", sym, dates))
  d <- Quandl(c(
    paste0("WIKI/", sym, ".8"),  #  Adj. Open
    paste0("WIKI/", sym, ".9"),  # Adj. High
    paste0("WIKI/", sym, ".10"), # Adj. Low
    paste0("WIKI/", sym, ".11"), # Adj. Close
    paste0("WIKI/", sym, ".12")), # Adj. Volume
    start_date = dates[[1]], end_date = dates[[2]], 
    type = "xts", force_irregular = TRUE
  )
  colnames(d) <- c("open","high", "low", "close", "volume" )
  return(d)
}

getWindowForecasts <- function(data, training_window, forecast_window){
  r <- rep(NA, length(data))
  i <- 1
  while(i < length(data) ) {
    first_training_window <- i
    last_training_window <- first_training_window+training_window-1
    first_forecast_window <- last_training_window + 1
    
    if(first_forecast_window <= length(data)){
      window_available <- length(data) - first_forecast_window + 1
      if(window_available < forecast_window)
        this_forecast_window <- window_available
      else
        this_forecast_window <- forecast_window
      
      last_forecast_window <- first_forecast_window + this_forecast_window -1
      model <- auto.arima(data[first_training_window:last_training_window])
      f <- forecast(model, h=this_forecast_window)
      r[first_forecast_window:last_forecast_window] <- f$mean[1:this_forecast_window]
    }
    i <- i + this_forecast_window
  }
  return (r)
}




getPositions <- function(data, valueColumn, forecastColumn, first_forecast_index, forecast_window_length){
  
  len <- length(data[,valueColumn])
  r <- rep(NA, len)
  real_value_index <- first_forecast_index - 1
  signal <- 0
  while(real_value_index < len ){
    
    if( (real_value_index + forecast_window_length) > len ){
      forecast_index <- len 
    }
    else {
      forecast_index <- (real_value_index + forecast_window_length)
    }
      
    
    real_value <- data[,valueColumn][[real_value_index]]
    forecast <- data[,forecastColumn][[forecast_index]]
    
    
    if ( forecast < real_value && signal == 1 )
      signal <- -1
    else if (forecast > real_value )
      signal <- 1

    r[real_value_index] <- signal
    
    real_value_index <- real_value_index + first_forecast_index - 1
  }
  
  signal <- 0
  for(i in 1:length(r)){
    if( FALSE == is.na(r[i]) )
      signal <- r[i]
    else
      r[i] <- signal
  }
  
  return (r)
  
}

getBalance <- function(data, valueColumn, positionColumn){
  
  len <- length(data[,valueColumn])
  r <- rep(NA, len)
  
  position <- 0
  balance <- 0
  for(i in 1:length(data[,positionColumn])){
    pos <- data[,positionColumn][[i]]
    if( pos != position ){
      position <- pos
      
      if( position == 1 )
        balance <- balance - data[,valueColumn][[i]]
      else if ( position == -1 )
        balance <- balance + data[,valueColumn][[i]]
    }
    r[i] <- balance
  }
  
  return (r)
}

tickers <- get_tickers()



