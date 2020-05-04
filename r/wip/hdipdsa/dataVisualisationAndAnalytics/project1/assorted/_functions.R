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






getTickers <- function(){
  r <- stockSymbols(exchange=c("NASDAQ"))
  return (r)
}

getTickerData <- function(ticker, start, end){
  data <- getSymbols(ticker, src = "yahoo",from = start, to = end)
  return(data)
}

plotTickerDataChart <- function(data){
  candleChart(data, up.col = "black", dn.col = "red", theme = "white")
  #return(chartSeries(data, up.col = "black", dn.col = "red", theme = "white", plot = FALSE, type = c("auto", "candlesticks", "matchsticks", "bars","line")))
}

strategy_MA2050 <-function(data, name, start, initialEquity){
  
  rm(list = ls(.blotter), envir = .blotter)  # Clear blotter environment
  currency("USD")  # Currency being used
  Sys.setenv(TZ = "GMT")  # Allows quantstrat to use timestamps
  d <- adjustOHLC(data)
  stock(name, currency = "USD", multiplier = 1)

  strategy_st <- portfolio_st <- account_st <- "SMAC-20-50"  # Names of objects
  rm.strat(portfolio_st)  # Need to remove portfolio from blotter env
  rm.strat(strategy_st)   # Ensure no strategy by this name exists either
  initPortf(portfolio_st, symbols = name, 
            initDate = start, currency = "USD")
  
  initAcct(account_st, portfolios = portfolio_st,  # Uses only one portfolio
           initDate = start, currency = "USD",
           initEq = initialEquity)  
  
  initOrders(portfolio_st, store = TRUE)  # Initialize the order container; will
  # contain all orders made by strategy
  strategy(strategy_st, store = TRUE)  # store = TRUE tells function to store in
  # the .strategy environment
  
  # Now define trading rules
  # Indicators are used to construct signals
  add.indicator(strategy = strategy_st, name = "SMA",     # SMA is a function
                arguments = list(x = quote(Cl(mktdata)),  # args of SMA
                                 n = 20),
                label = "fastMA")
  
  add.indicator(strategy = strategy_st, name = "SMA",
                arguments = list(x = quote(Cl(mktdata)),
                                 n = 50),
                label = "slowMA")
  
  add.signal(strategy = strategy_st, name = "sigComparison",  # Remember me?
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "gt"),
             label = "bull")
  
  add.signal(strategy = strategy_st, name = "sigComparison",
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "lt"),
             label = "bear")
  
  
  # Finally, rules that generate trades
  add.rule(strategy = strategy_st, name = "ruleSignal",  # Almost always this one
           arguments = list(sigcol = "bull",  # Signal (see above) that triggers
                            sigval = TRUE,
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open",
                            osFUN = osMaxDollar,
                            # The next parameter, which is a parameter passed to
                            # osMaxDollar, will ensure that trades are about 10%
                            # of portfolio equity
                            maxSize = quote(floor(getEndEq(account_st,
                                                           Date = timestamp) * .1)),
                            tradeSize = quote(floor(getEndEq(account_st,
                                                             Date = timestamp) * .1))),
           type = "enter", path.dep = TRUE, label = "buy")
  
  
  add.rule(strategy = strategy_st, name = "ruleSignal",
           arguments = list(sigcol = "bear",
                            sigval = TRUE,
                            orderqty = "all",
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open"),
           type = "exit", path.dep = TRUE, label = "sell")
  
  applyStrategy(strategy_st, portfolios = portfolio_st)
  
  updatePortf(portfolio_st)
  dateRange <- time(getPortfolio(portfolio_st)$summary)[-1]
  updateAcct(portfolio_st, dateRange)
  updateEndEq(account_st)
  
  tStats <- tradeStats(Portfolios = portfolio_st, use="trades",
                       inclZeroDays = FALSE)
  tStats[, 4:ncol(tStats)] <- round(tStats[, 4:ncol(tStats)], 2)
  print(data.frame(t(tStats[, -c(1,2)])))
  
  final_acct <- getAccount(account_st)
  plot(final_acct$summary$End.Eq["2010/2016"], main = "Portfolio Equity")
}



f <- function(){
  

  start <- as.Date("2016-01-01")
  end <- as.Date("2016-10-01")
  
  # Let's get Apple stock data; Apple's ticker symbol is AAPL. We use the
  # quantmod function getSymbols, and pass a string as a first argument to
  # identify the desired ticker symbol, pass 'yahoo' to src for Yahoo!
  # Finance, and from and to specify date ranges
  
  # The default behavior for getSymbols is to load data directly into the
  # global environment, with the object being named after the loaded ticker
  # symbol. This feature may become deprecated in the future, but we exploit
  # it now.
  
  getSymbols("AAPL", src = "yahoo", from = start, to = end)
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white")
  
  # Let's get data for Microsoft (MSFT) and Google (GOOG) (actually, Google is
  # held by a holding company called Alphabet, Inc., which is the company
  # traded on the exchange and uses the ticker symbol GOOG).
  getSymbols(c("MSFT", "GOOG"), src = "yahoo", from = start, to = end)
  
  # Create an xts object (xts is loaded with quantmod) that contains closing
  # prices for AAPL, MSFT, and GOOG
  stocks <- as.xts(data.frame(AAPL = AAPL[, "AAPL.Close"], MSFT = MSFT[, "MSFT.Close"], 
                              GOOG = GOOG[, "GOOG.Close"]))
  head(stocks)
  
  # Create a plot showing all series as lines; must use as.zoo to use the zoo
  # method for plot, which allows for multiple series to be plotted on same
  # plot
  plot(as.zoo(stocks), screens = 1, lty = 1:3, xlab = "Date", ylab = "Price")
  legend("right", c("AAPL", "MSFT", "GOOG"), lty = 1:3, cex = 0.5)
  
  plot(as.zoo(stocks[, c("AAPL.Close", "MSFT.Close")]), screens = 1, lty = 1:2, 
       xlab = "Date", ylab = "Price")
  par(new = TRUE)
  plot(as.zoo(stocks[, "GOOG.Close"]), screens = 1, lty = 3, xaxt = "n", yaxt = "n", 
       xlab = "", ylab = "")
  axis(4)
  mtext("Price", side = 4, line = 3)
  legend("topleft", c("AAPL (left)", "MSFT (left)", "GOOG"), lty = 1:3, cex = 0.5)
  
  
  stock_return = apply(stocks, 1, function(x) {x / stocks[1,]}) %>% 
    t %>% as.xts
  
  head(stock_return)
  
  plot(as.zoo(stock_return), screens = 1, lty = 1:3, xlab = "Date", ylab = "Return")
  legend("topleft", c("AAPL", "MSFT", "GOOG"), lty = 1:3, cex = 0.5)
  
  
  stock_change = stocks %>% log %>% diff
  head(stock_change)
  
  plot(as.zoo(stock_change), screens = 1, lty = 1:3, xlab = "Date", ylab = "Log Difference")
  legend("topleft", c("AAPL", "MSFT", "GOOG"), lty = 1:3, cex = 0.5)
  
  
  # moving averages
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white")
  addSMA(n = 20)
  
  
  start = as.Date("2010-01-01")
  getSymbols(c("AAPL", "MSFT", "GOOG"), src = "yahoo", from = start, to = end)
  
  # The subset argument allows specifying the date range to view in the chart.
  # This uses xts style subsetting. Here, I'm using the idiom
  # 'YYYY-MM-DD/YYYY-MM-DD', where the date on the left-hand side of the / is
  # the start date, and the date on the right-hand side is the end date. If
  # either is left blank, either the earliest date or latest date in the
  # series is used (as appropriate). This method can be used for any xts
  # object, say, AAPL
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white", subset = "2016-01-04/")
  addSMA(n = 20)
  
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white", subset = "2016-01-04/")
  addSMA(n = c(20, 50, 200))
  
  
  # ---part 2
  
  start <- as.Date("2010-01-01")
  end <- as.Date("2016-10-01")
  
  # Let's get Apple stock data; Apple's ticker symbol is AAPL. We use the quantmod function getSymbols, and pass a string as a first argument to identify the desired ticker symbol, pass "yahoo" to src for Yahoo! Finance, and from and to specify date ranges
  
  # The default behavior for getSymbols is to load data directly into the global environment, with the object being named after the loaded ticker symbol. This feature may become deprecated in the future, but we exploit it now.
  
  getSymbols("AAPL", src="yahoo", from = start, to = end)
  
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white", subset = "2016-01-04/")
  
  AAPL_sma_20 <- SMA(
    Cl(AAPL),  # The closing price of AAPL, obtained by quantmod's Cl() function
    n = 20     # The number of days in the moving average window
  )
  
  AAPL_sma_50 <- SMA(
    Cl(AAPL),
    n = 50
  )
  
  AAPL_sma_200 <- SMA(
    Cl(AAPL),
    n = 200
  )
  
  zoomChart("2016")  # Zoom into the year 2016 in the chart
  addTA(AAPL_sma_20, on = 1, col = "red")  # on = 1 plots the SMA with price
  addTA(AAPL_sma_50, on = 1, col = "blue")
  addTA(AAPL_sma_200, on = 1, col = "green")
  
  
  AAPL_trade <- AAPL
  AAPL_trade$`20d` <- AAPL_sma_20
  AAPL_trade$`50d` <- AAPL_sma_50
  
  regime_val <- sigComparison("", data = AAPL_trade,
                              columns = c("20d", "50d"), relationship = "gt") -
    sigComparison("", data = AAPL_trade,
                  columns = c("20d", "50d"), relationship = "lt")
  
  plot(regime_val["2016"], main = "Regime", ylim = c(-2, 2))
  plot(regime_val, main = "Regime", ylim = c(-2, 2))
  
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white", subset = "2016-01-04/")
  addTA(regime_val, col = "blue", yrange = c(-2, 2))
  addLines(h = 0, col = "black", on = 3)
  addSMA(n = c(20, 50), on = 1, col = c("red", "blue"))
  zoomChart("2016")
  
  table(as.vector(regime_val))
  sig <- diff(regime_val) / 2
  plot(sig, main = "Signal", ylim = c(-2, 2))
  table(sig)
  
  # The Cl function from quantmod pulls the closing price from the object
  # holding a stock's data
  # Buy prices
  Cl(AAPL)[which(sig == 1)]
  
  # Sell prices
  Cl(AAPL)[sig == -1]
  
  # Since these are of the same dimension, computing profit is easy
  as.vector(Cl(AAPL)[sig == 1])[-1] - Cl(AAPL)[sig == -1][-table(sig)[["1"]]]
  
  candleChart(AAPL, up.col = "black", dn.col = "red", theme = "white")
  addTA(regime_val, col = "blue", yrange = c(-2, 2))
  addLines(h = 0, col = "black", on = 3)
  addSMA(n = c(20, 50), on = 1, col = c("red", "blue"))
  zoomChart("2014-05/2014-07")
  
  candleChart(adjustOHLC(AAPL), up.col = "black", dn.col = "red", theme = "white")
  addLines(h = 0, col = "black", on = 3)
  addSMA(n = c(20, 50), on = 1, col = c("red", "blue"))
  
  
  
  
  rm(list = ls(.blotter), envir = .blotter)  # Clear blotter environment
  currency("USD")  # Currency being used
  
  Sys.setenv(TZ = "GMT")  # Allows quantstrat to use timestamps
  AAPL_adj <- adjustOHLC(AAPL)
  stock("AAPL_adj", currency = "USD", multiplier = 1)
  
  initDate <- "1990-01-01"  # A date prior to first close price; needed (why?)
  
  strategy_st <- portfolio_st <- account_st <- "SMAC-20-50"  # Names of objects
  rm.strat(portfolio_st)  # Need to remove portfolio from blotter env
  rm.strat(strategy_st)   # Ensure no strategy by this name exists either
  initPortf(portfolio_st, symbols = "AAPL_adj",  # This is a simple portfolio
            # trading AAPL only
            initDate = initDate, currency = "USD")
  
  initAcct(account_st, portfolios = portfolio_st,  # Uses only one portfolio
           initDate = initDate, currency = "USD",
           initEq = 1000000)  # Start with a million dollars
  
  initOrders(portfolio_st, store = TRUE)  # Initialize the order container; will
  # contain all orders made by strategy
  strategy(strategy_st, store = TRUE)  # store = TRUE tells function to store in
  # the .strategy environment
  
  # Now define trading rules
  # Indicators are used to construct signals
  add.indicator(strategy = strategy_st, name = "SMA",     # SMA is a function
                arguments = list(x = quote(Cl(mktdata)),  # args of SMA
                                 n = 20),
                label = "fastMA")
  
  add.indicator(strategy = strategy_st, name = "SMA",
                arguments = list(x = quote(Cl(mktdata)),
                                 n = 50),
                label = "slowMA")
  
  add.signal(strategy = strategy_st, name = "sigComparison",  # Remember me?
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "gt"),
             label = "bull")
  
  add.signal(strategy = strategy_st, name = "sigComparison",
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "lt"),
             label = "bear")
  
  
  # Finally, rules that generate trades
  add.rule(strategy = strategy_st, name = "ruleSignal",  # Almost always this one
           arguments = list(sigcol = "bull",  # Signal (see above) that triggers
                            sigval = TRUE,
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open",
                            osFUN = osMaxDollar,
                            # The next parameter, which is a parameter passed to
                            # osMaxDollar, will ensure that trades are about 10%
                            # of portfolio equity
                            maxSize = quote(floor(getEndEq(account_st,
                                                           Date = timestamp) * .1)),
                            tradeSize = quote(floor(getEndEq(account_st,
                                                             Date = timestamp) * .1))),
           type = "enter", path.dep = TRUE, label = "buy")
  
  
  add.rule(strategy = strategy_st, name = "ruleSignal",
           arguments = list(sigcol = "bear",
                            sigval = TRUE,
                            orderqty = "all",
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open"),
           type = "exit", path.dep = TRUE, label = "sell")
  
  applyStrategy(strategy_st, portfolios = portfolio_st)
  
  updatePortf(portfolio_st)
  dateRange <- time(getPortfolio(portfolio_st)$summary)[-1]
  updateAcct(portfolio_st, dateRange)
  updateEndEq(account_st)
  
  tStats <- tradeStats(Portfolios = portfolio_st, use="trades",
                       inclZeroDays = FALSE)
  tStats[, 4:ncol(tStats)] <- round(tStats[, 4:ncol(tStats)], 2)
  print(data.frame(t(tStats[, -c(1,2)])))
  
  final_acct <- getAccount(account_st)
  plot(final_acct$summary$End.Eq["2010/2016"], main = "Portfolio Equity")
  
  
  # --- multiple stocks
  
  # Get new symbols
  symbols = c("AAPL", "MSFT", "GOOG", "FB", "TWTR", "NFLX", "AMZN", "YHOO",
              "SNY", "NTDOY", "IBM", "HPQ")
  getSymbols(Symbols = symbols, from = start, to = end)
  # Quickly define adjusted versions of each of these
  `%s%` <- function(x, y) {paste(x, y)}
  `%s0%` <- function(x, y) {paste0(x, y)}
  for (s in symbols) {
    eval(parse(text = s %s0% "_adj <- adjustOHLC(" %s0% s %s0% ")"))
  }
  symbols_adj <- paste(symbols, "adj", sep = "_")
  
  stock(symbols_adj, currency = "USD", multiplier = 1)
  
  strategy_st_2 <- portfolio_st_2 <- account_st_2 <- "SMAC-20-50_v2"
  rm.strat(portfolio_st_2)
  rm.strat(strategy_st_2)
  initPortf(portfolio_st_2, symbols = symbols_adj,
            initDate = initDate, currency = "USD")
  initAcct(account_st_2, portfolios = portfolio_st_2,
           initDate = initDate, currency = "USD",
           initEq = 1000000)
  initOrders(portfolio_st_2, store = TRUE)
  
  strategy(strategy_st_2, store = TRUE)
  
  add.indicator(strategy = strategy_st_2, name = "SMA",
                arguments = list(x = quote(Cl(mktdata)),
                                 n = 20),
                label = "fastMA")
  add.indicator(strategy = strategy_st_2, name = "SMA",
                arguments = list(x = quote(Cl(mktdata)),
                                 n = 50),
                label = "slowMA")
  
  # Next comes trading signals
  add.signal(strategy = strategy_st_2, name = "sigComparison",  # Remember me?
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "gt"),
             label = "bull")
  add.signal(strategy = strategy_st_2, name = "sigComparison",
             arguments = list(columns = c("fastMA", "slowMA"),
                              relationship = "lt"),
             label = "bear")
  
  # Finally, rules that generate trades
  add.rule(strategy = strategy_st_2, name = "ruleSignal",
           arguments = list(sigcol = "bull",
                            sigval = TRUE,
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open",
                            osFUN = osMaxDollar,
                            maxSize = quote(floor(getEndEq(account_st_2,
                                                           Date = timestamp) * .1)),
                            tradeSize = quote(floor(getEndEq(account_st_2,
                                                             Date = timestamp) * .1))),
           type = "enter", path.dep = TRUE, label = "buy")
  add.rule(strategy = strategy_st_2, name = "ruleSignal",
           arguments = list(sigcol = "bear",
                            sigval = TRUE,
                            orderqty = "all",
                            ordertype = "market",
                            orderside = "long",
                            replace = FALSE,
                            prefer = "Open"),
           type = "exit", path.dep = TRUE, label = "sell")
  
  applyStrategy(strategy_st_2, portfolios = portfolio_st_2)
  
  # Now for analytics
  updatePortf(portfolio_st_2)
  
  dateRange <- time(getPortfolio(portfolio_st_2)$summary)[-1]
  updateAcct(account_st_2, dateRange)
  
  updateEndEq(account_st_2)
  
  tStats2 <- tradeStats(Portfolios = portfolio_st_2, use="trades",
                        inclZeroDays = FALSE)
  tStats2[, 4:ncol(tStats2)] <- round(tStats2[, 4:ncol(tStats2)], 2)
  print(data.frame(t(tStats2[, -c(1,2)])))
  
  final_acct2 <- getAccount(account_st_2)
  plot(final_acct2$summary$End.Eq["2010/2016"], main = "Portfolio Equity")

}
