source("include.R")
if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
}

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
      return("addsma1(n=20);addsma2(n=50);addma_intersect_signal(n1=20, n2=50);addma_intersect_balance(n1=20, n2=50)")
    
    if( "Moving Avg 50-100" == input$strategy )
      return("addsma1(n=50);addsma2(n=100);addma_intersect_signal(n1=50, n2=100);addma_intersect_balance(n1=50, n2=100)")
    
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

shinyApp(ui = ui, server = server)
