if (!require("devtools")) {
  install.packages("devtools")
  library(devtools)
}
if (!require("quantmod")) {
  install.packages("quantmod")
  library(quantmod)
}
if (!require("magrittr")) {
  install.packages("magrittr")
  library(magrittr)
}
if (!require("TTR")) {
  install.packages("TTR")
  library(TTR)
}
if (!require("blotter")) {
  install_github("braverock/blotter") # dependency
  library(blotter)
}
if (!require("quantstrat")) {
  #install.packages("quantstrat", repos="http://R-Forge.R-project.org")
  install_github("braverock/quantstrat")
  library(quantstrat)
}

if (!require("stringi")) {
  install.packages("stringi")
  library(stringi)
}

if (!require("roxygen2")) {
  install.packages("roxygen2")
  library(roxygen2)
}

if (!require("IKTrading")) {
  install_github("IKTrading", username = "IlyaKipnis")
  library(IKTrading)
}

if (!require("fpp")) {
  install.packages("fpp")
  library(fpp)
}

getStrategies <- function(){
  return(c("Moving Avg 20-50", "Moving Avg 50-100"))
}

getTickers <- function(){
  r <- stockSymbols(exchange=c("NASDAQ"))
  return (r)
}

getData <- function(ticker, dates){
  return(getSymbols(ticker, from = dates[1], to = dates[2], auto.assign = FALSE))
}

plotIt<-function(data, title, strategy){
  result = tryCatch({
    candleChart(data, up.col = "black", dn.col = "red", theme = "white", name = title)
    if( strategy != '' ){
      applyStrategy(strategy)
    }
  }, error = function(e) {
    print(e)
  })
  
}

d <- getSymbols('ABAX', from = '2016-01-14',to = '2018-04-18', auto.assign = FALSE)

m <- auto.arima(d$ABAX.Close, stepwise=FALSE, approximation=FALSE)
m.f <- forecast(m,h=12)

plotTickerDataChart <- function(data, name){
  
}
plot_data<-function(output, ticker, dates){
  symbol<-tickers[tickers$Name==ticker,1][1] 
  
  result = tryCatch({
    data<-getTickerData(symbol, dates[1],dates[2])
    output$main_plot <- renderPlot({ plotTickerDataChart(data, paste(ticker,"/", symbol)) })
  }, warning = function(w) {
    print(w)
  }, error = function(e) {
    print(e)
  })
  
}
reset_plot<-function(output){
  output$main_plot <- renderPlot(NULL)
}

calculate_profit<-function(data){
  
  d<-data
  position<-NULL
  balance<-0
  buy<-FALSE
  sell<-FALSE
  d$balance <- 0
  
  lastDay <- nrow(d)
  
  for(i in c(1:lastDay)){
    
    balance<-0.0
    if(i>1){
      balance<-as.numeric(d[i-1,]$balance)
    }
    
    open<-as.numeric(d[i,1])
    
    trx <- 0.0
    
    if(i == lastDay){
      if(position > 0){
        close<-as.numeric(d[i,4])
        trx <- close
        new_balance <-  balance + trx
        print(paste("last trx:", trx, "old balance:", balance, "new balance:", new_balance))
        d[i,11] <- new_balance
      }
    }
    else {
      if(buy){
        trx <- -1*open
        buy<-FALSE
      }
      if(sell){
        trx <- open
        sell<-FALSE
      }
      
      new_balance <-  balance + trx
      print(paste("new trx:", trx, "old balance:", balance, "new balance:", new_balance))
      d[i,11] <- new_balance
      
      
      
      if(not(is.na(d[i,]$sig))){
        if(d[i,]$sig > 0 && ( is.null(position) || (position < 0) ) ){
          print(paste("position:", as.character(position),"signal:", as.character(d[i,]$sig), "-> we should buy"))
          buy<-TRUE
          position <- d[i,]$sig
        }
        if( d[i,]$sig < 0 && not(is.null(position)) && position > 0 ){
          print(paste("position:", as.character(position),"signal:", as.character(d[i,]$sig), "-> we should sell"))
          sell<-TRUE
          position <- d[i,]$sig
        }
      }
    }
    
    
    
    
  }
  return(d)
}

strategy_ma2050 <- function(output, ticker, dates){
  
  result = tryCatch({
    symbol<-tickers[tickers$Name==ticker,1][1] 
    data<-getTickerData(symbol, dates[1],dates[2])
    ma50 <- SMA( Cl(data), n = 50 )
    output$main_plot <- renderPlot(addTA(ma50, on = 1, col = "blue"))
    ma20 <- SMA( Cl(data), n = 20 )
    output$main_plot <- renderPlot(addTA(ma20, on = 1, col = "red"))
    
    data$twenty <- ma20
    data$fifty <- ma50
    
    data$regime_val <- sigComparison("", data = data, columns = c("twenty", "fifty"), relationship = "gt") -
      sigComparison("", data = data, columns = c("twenty", "fifty"), relationship = "lt")
    data$sig <- diff(data$regime_val) / 2
    output$main_plot <- renderPlot(addTA(data$sig, col = "green", ylim = c(-2, 2), legend = "signal",on = 3))
    data <- calculate_profit(data)
    output$main_plot <- renderPlot(addTA(data$balance, col = "brown", legend = "balance",on = 4))
    
  }, warning = function(w) {
    print(w)
  }, error = function(e) {
    print(e)
  })
}

strategy_ma50100 <- function(output, ticker, dates){
  result = tryCatch({
    symbol<-tickers[tickers$Name==ticker,1][1] 
    data<-getTickerData(symbol, dates[1],dates[2])
    
    ma100 <- SMA( Cl(data), n = 100 )
    output$main_plot <- renderPlot(addTA(ma100, on = 1, col = "blue"))
    ma50 <- SMA( Cl(data), n = 50 )
    output$main_plot <- renderPlot(addTA(ma50, on = 1, col = "red"))
    
    data$fifty <- ma50
    data$hundred <- ma100
    
    data$regime_val <- sigComparison("", data = data, columns = c("fifty", "hundred"), relationship = "gt") -
      sigComparison("", data = data, columns = c("fifty", "hundred"), relationship = "lt")
    data$sig <- diff(data$regime_val) / 2
    output$main_plot <- renderPlot(addTA(data$sig, col = "blue", ylim = c(-2, 2), legend = "signal"))
    data <- calculate_profit(data)
    output$main_plot <- renderPlot(addTA(data$balance, col = "orange", legend = "balance"))
  }, warning = function(w) {
    print(w)
  }, error = function(e) {
    print(e)
  })
}


#d<-getTickerData('ABAX', '2016-01-14','2018-04-18')
#strategy_ma2050(d, 'ABAX')

