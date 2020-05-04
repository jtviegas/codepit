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

if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
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

addma_intersect_signal <- newTA(FUN = ma_intersect_signal, preFUN = Cl, col = "blue", legend = "MA intersect signal", data.at = 1)

addma_intersect_balance <- newTA(FUN = ma_intersect_balance, preFUN = Cl, col = "green", legend = "MA intersect balance", data.at = 1)


# ----------------------------------- shiny

tickers <- get_tickers()
strategies <- get_strategies()

ui <- fluidPage(
  
  titlePanel("NASDAQ investment experiment")
  , hr()
  , fluidRow(
    column(4, selectInput('ticker', 'Ticker', c(Choose='', tickers$Name), selectize=FALSE))
    , column(4, dateRangeInput('dates', label = 'Date range:', start = Sys.Date() - 182, end = Sys.Date()))
    , column(4, selectInput('strategy', 'Investment Strategy', c(Choose='', strategies), selectize=FALSE))
  )
  , hr()
  , plotOutput(outputId = "plot", height ='600')
)


server <- function(input, output, session) {
  
  load_data <- reactive({
    print("@server/load_data")
    if('' == input$ticker)
      return (NULL)
    
    tickerName <- input$ticker
    symbol<-tickers[tickers$Name==tickerName,1][1] 
    print(paste("symbol:", symbol))
    d <- get_data(symbol,input$dates)
    print(paste("server/load_data@rows:", nrow(d)))
    return(d)
  })

  load_strategy <- reactive({
    if( "Moving Avg 20-50" == input$strategy )
      return("addSMA(n=20, col='blue');addSMA(n=50, col='orange');addma_intersect_signal(n1=20, n2=50);addma_intersect_balance(n1=20, n2=50)")
    
    if( "Moving Avg 50-100" == input$strategy )
      return("addSMA(n=50, col='blue');addSMA(n=100, col='orange');addma_intersect_signal(n1=50, n2=100);addma_intersect_balance(n1=50, n2=100)")
    
    return (NULL)
  })

  get_ticker_symbol <- reactive({
    tickerName <- input$ticker
    symbol<-tickers[tickers$Name==tickerName,1][1] 
    return(symbol)
  })
  
  observeEvent(input$ticker, {
    updateSelectInput(session, "strategy", selected = '')
  })
  
  output$plot <- renderPlot({
    data <- load_data()
    if(is.null(data))
      return(NULL)
    
    symbol<-get_ticker_symbol()
    strategyTA <- load_strategy()
    
    if(is.null(strategyTA))
      chartSeries(data, theme = "white", name = symbol)
    else
      chartSeries(data, theme = "white", name = symbol , TA=strategyTA)
  })
  
}



doIt<- function(x){
  sym<-"AAPL"
  dates<-list(as.Date("2017-07-05"), as.Date("2018-04-25"))
  data<-get_data(sym,dates)
  #colnames(data)<-c("Open", "High", "Low", "Close", "Volume")
  title <- "title"
  chartSeries(data, up.col = "black", dn.col = "red", theme = "white", name = title , TA="addSMA(n=20, col='blue');addSMA(n=50, col='red');addSMA(n=100, col='brown');addMA_50_100_sig();addMA_20_50_sig()")

  chartSeries(data, up.col = "black", dn.col = "red", theme = "white", name = title , TA="addSMA(n=20, col='blue');addSMA(n=50, col='red');addMA_20_50_balance()")
 

   chartSeries(data, up.col = "black", dn.col = "red", theme = "white", name = title , TA="addSMA(n=20, col='blue');addSMA(n=50, col='red');addma_intersect_balance(n1=40, n2=60);addma_intersect_signal(n1=40, n2=60)")
   d3 <- ma_intersect_signal(Cl(data), n1=20,n2=50)

  l <- strategy_ma50100(data)
  addTA(l$ma50, on = 1, col = "blue", plot=F)
  addTA(l$ma100, on = 1, col = "red", plot=F)
  addTA(l$sig, col = "green", plot=F)
  
  reChart(major.ticks='months',subset='first 64 weeks')
  
  chartSeries(data, up.col = "black", dn.col = "red", theme = "white", name = title , TA="addMA50_100()")
  class(Cl(data))
}


#shinyApp(ui = ui, server = server)
