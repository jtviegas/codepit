if (!require("devtools")) {
  install.packages("devtools")
  library(devtools)
}
if (!require("Quandl")) {
  install_github("quandl/quandl-r")
  library(Quandl)
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

Quandl.api_key('xQ9Sq7ybYvkuhLnyC1JF')

STRATEGIES <- c("Moving Avg 20-50", "Moving Avg 50-100") 
getStrategies <- function(){
  return(STRATEGIES)
}

getTickers <- function(){
  r <- stockSymbols(exchange=c("NASDAQ"))
  return (r)
}

getData <- function(sym, dates) {
    print(paste("getData", sym, dates))
    d <- Quandl(c(
    paste0("WIKI/", sym, ".8"),  #  Adj. Open
    paste0("WIKI/", sym, ".9"),  # Adj. High
    paste0("WIKI/", sym, ".10"), # Adj. Low
    paste0("WIKI/", sym, ".11"), # Adj. Close
    paste0("WIKI/", sym, ".12")), # Adj. Volume
    start_date = dates[[1]], end_date = dates[[2]], 
    type = "xts", force_irregular = TRUE
  )
  return(d)
}

plotIt<-function(data, title, strategy){
    candleChart(data, up.col = "black", dn.col = "red", theme = "white", name = title)
    if( strategy != '' ){
      print(paste("strategy:", strategy))
      doStrategy(strategy, data)
    }
}

STRATEGIES <- c("Moving Avg 20-50", "Moving Avg 50-100") 
getStrategies <- function(){
  return(STRATEGIES)
}





calculate_profit<-function(data){
  
  d<-data
  position<-NULL
  balance<-0
  buy<-FALSE
  sell<-FALSE
  d$balance <- 0
  
  lastDay <- nrow(d)
  print(paste("lastDay:", lastDay))
  
  for(i in c(1:lastDay)){
    print(paste("day:", i))
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
        d[i,c("balance")] <- new_balance
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
      d[i,c("balance")] <- new_balance

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
  print(paste("lastDay:", lastDay))
  return(d)
}

f_ma50100 <- function(data){
  print("at f_ma50100")
  ma100 <- SMA( Cl(data), n = 100 )
  addTA(ma100, on = 1, col = "blue")
  ma50 <- SMA( Cl(data), n = 50 )
  addTA(ma50, on = 1, col = "red")
  
  data$fifty <- ma50
  data$hundred <- ma100
  
  data$regime_val <- sigComparison("", data = data, columns = c("fifty", "hundred"), relationship = "gt") -
    sigComparison("", data = data, columns = c("fifty", "hundred"), relationship = "lt")
  data$sig <- diff(data$regime_val) / 2
  addTA(data$sig, col = "blue", ylim = c(-2, 2), legend = "signal", on = 3)
  data <- calculate_profit(data)
  addTA(data$balance, col = "orange", legend = "balance", on = 4)
}

do_strategy <- function(strategy, data){
  print(paste("strategy: ", strategy))
  r <- NULL
  if( "Moving Avg 20-50" == strategy )
    r <- strategy_ma50100(data)
  else
    r <- strategy_ma50100(data)
  
  return(r)
}

# sym<-"GOOG"
# dates<-list(as.Date("2010-01-01"), as.Date("2018-01-01"))
# data<-getData(sym,dates)
# title <- "title"
# candleChart(data, up.col = "black", dn.col = "red", theme = "white", name = title)

# returns a xts object with columns, the ones named on the "one" entry are the columns to print on the chart
# the others sould be printed separately
strategy_ma50100 <- function(data){
  
  print("at strategy_ma50100")
  ma100 <- SMA( Cl(data), n = 100 )
  ma50 <- SMA( Cl(data), n = 50 )

  d <- data.frame(ma50, ma100)
  colnames(d) <- c("ma50","ma100")
  d<-as.xts(d)
  
  regime_val <- sigComparison("", data = d, columns = c("ma50", "ma100"), relationship = "gt") -
    sigComparison("", data = d, columns = c("ma50", "ma100"), relationship = "lt")
  d$sig <- diff(regime_val) / 2
  d <- do_balance(data, d)
  
  r <- list()
  r[["strategy"]] <- d
  r[["1"]] <- c("ma50","ma100")
  
  return (r)
}


do_balance<-function(data, d){
  
  position<-NULL
  balance<-0
  buy<-FALSE
  sell<-FALSE
  d$balance <- 0
  
  lastDay <- nrow(data)
  print(paste("lastDay:", lastDay))
  
  for(i in c(1:lastDay)){
    
    print(paste("day:", i))
    carried_balance<-0.0
    if(i>1)
      carried_balance<-as.numeric(d[i-1,]$balance)
    
    open<-as.numeric(data[i,1])
    trx <- 0.0
    
    if(i == lastDay){
      # lets wrap up
      if(position > 0){
        close<-as.numeric(data[i,4])
        trx <- close
      }
    }
    else {
      # operate actions based on previous day analysis
      if(buy)
        trx <- -1 * open
      
      if(sell)
        trx <- open

      # clean up instructions
      buy<-FALSE
      sell<-FALSE
    
      # do analysis
      if(is.na(d[i,]$sig) == FALSE){
        if(d[i,]$sig > 0 && ( is.null(position) || (position < 0) ) ){
          # if we have a signal shift upwards we -> buy
          print(paste("position:", as.character(position),"signal:", as.character(d[i,]$sig), "-> we should buy"))
          buy<-TRUE
          position <- d[i,]$sig
        }
        if( d[i,]$sig < 0 && is.null(position)==FALSE && position > 0 ){
          # if we have a signal shift downwards we -> sell
          print(paste("position:", as.character(position),"signal:", as.character(d[i,]$sig), "-> we should sell"))
          sell<-TRUE
          position <- d[i,]$sig
        }
      }
    }
    # update balance based on trx defined
    if( 0.0 != trx ){
      new_balance <- carried_balance + trx
      print(paste("new trx:", trx, "old balance:", carried_balance, "new balance:", new_balance))
      d[i,]$balance <- new_balance
    }
    else
      d[i,]$balance <- carried_balance
    
    
  }
  print(paste("lastDay:", lastDay))
  return(d)
}
