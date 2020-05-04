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

get_tickers <- function(){
  r <- stockSymbols(exchange=c("NASDAQ"))
  return (r)
}

STRATEGIES <- c("Moving Avg 20-50", "Moving Avg 50-100") 
get_strategies <- function(){
  return(STRATEGIES)
}

get_data <- function(sym, dates) {
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
  return(d)
}

do_sig <- function(dxts,cols){
  print("@do_sig")
  regime_val <- sigComparison("", data = dxts, columns = cols, relationship = "gt") -
    sigComparison("", data = dxts, columns = cols, relationship = "lt")
  dxts$sig <- diff(regime_val) / 2
  # ensure first transaction is a buy
  for(i in(1:length(dxts$sig))){
    v <- dxts$sig[i]
    if( is.na(v) == FALSE) {
      if( v == 1 ) break
      if( v == -1 ) dxts$sig[i] <- 0
    }
  }
  print("do_sig@")
  return(dxts$sig)
}

ma_intersect_signal <- function(d, n1, n2){
  print("@ma_intersect_signal")
  
  o <- as.xts(as.data.frame(d))
  o$man1 <- SMA( d, n = n1 )
  o$man2 <- SMA( d, n = n2 )
  
  o$sig <- do_sig(o, c("man1", "man2"))
  
  print("ma_intersect_signal@")
  return (o$sig)
}

sig_to_trx<-function(x){
  r <- 0
  if( not(is.na(x["sig"])) ){
    if(1 == x["sig"])
      r <- -1 * x[1]
    if(-1 == x["sig"])
      r <- x[1]
  }
  return(r)
}

ma_intersect_balance <- function(d, n1, n2){
  print("@ma_intersect_balance")
  
  o <- as.xts(as.data.frame(d))
  o$man1 <- SMA( d, n = n1 )
  o$man2 <- SMA( d, n = n2 )
  
  o$sig <- do_sig(o, c("man1", "man2"))
  o$trx <- apply(o, MARGIN=1, FUN = sig_to_trx)
  o$balance <- cumsum(o$trx)
  
  print("ma_intersect_balance@")
  return (o$balance)
}

tickers <- get_tickers()
strategies <- get_strategies()

addma_intersect_signal <- newTA(FUN = ma_intersect_signal, preFUN = Cl, col = "blue", legend = "MA intersect signal", data.at = 1)
addma_intersect_balance <- newTA(FUN = ma_intersect_balance, preFUN = Cl, col = "green", legend = "MA intersect balance", data.at = 1)
addsma1 <- newTA(FUN = SMA, preFUN = Cl, legend = "SMA", data.at = 1, on = 1, col = "orange")
addsma2 <- newTA(FUN = SMA, preFUN = Cl, legend = "SMA", data.at = 1, on = 1, col = "brown")


