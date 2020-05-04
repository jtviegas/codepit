source("../include.R")
if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
}
if (!require("shinydashboard")) {
  install.packages("shinydashboard")
  library(shinydashboard)
}

ui <- dashboardPage(
  dashboardHeader(title = "nasdaq dumb forecasting investment strategy", titleWidth = 450),
  dashboardSidebar(
    selectInput('ticker', 'ticker', c(Choose='', tickers$Name), selectize=FALSE)
    , dateRangeInput('dates', label = 'date range:', start = Sys.Date() - 182, end = Sys.Date())
    , sliderInput("training_window", "training window (days):", 1, 356, 28)
    , sliderInput("forecast_window", "forecast window (days):", 1, 28, 7)
    , br()
    , box(
      title = "simulation strategy", width = 12, background = "light-blue",
      "At the end of the training window, the real value is compared with the last forecast value.
      If the forecast is higher, the signal is to buy, otherwise the signal is to sell. 
      Then the training window moves on to provide prediction for the next forecasting window, based on real values."
    )
    
  ),
  dashboardBody(
    
    fluidRow(
      box(title="stock symbol value+forecast", width=12, plotlyOutput("plot_values"))
    )
    , fluidRow(

      box(title="simulated position", width=12, plotlyOutput("plot_position")),
      box(title="simulated balance (1 share/operation)", width=12, plotlyOutput("plot_balance"))
    )
    
  )
)

server <- function(input, output) {
  
  load_data <- reactive({
    print("@server/load_data")
    if('' == input$ticker){
      print("no ticker yet")
      return (NULL)
    }
    
    tickerName <- input$ticker
    symbol<-tickers[tickers$Name==tickerName,1][1] 
    print(paste("symbol:", symbol))
    d <- getSymbolPrices(symbol,input$dates)

    d$forecast <- getWindowForecasts(d$close, input$training_window, input$forecast_window)
    d$position <- getPositions(d, "close", "forecast", input$training_window + 1, input$forecast_window)
    d$balance <- getBalance(d, "close", "position")
    
    print(paste("server/load_data@rows:", nrow(d)))
    return(d)
  })
  
  output$plot_values <- renderPlotly({
    
    d <- load_data()
    if(is.null(d))
      return(NULL)
    
    p <- ggplot(d) +
      geom_line(mapping = aes(x=index(d), y = close, colour = "close")) +
     geom_line(mapping = aes(x=index(d), y = forecast,   colour = "forecast")) +
      ylab('close ($)') +
      xlab('date')
    ggplotly(p)

  })
  
  output$plot_position <- renderPlotly({
    
    d <- load_data()
    if(is.null(d))
      return(NULL)
    
    p <- ggplot(d) +
      geom_line(mapping = aes(x=index(d), y = position, colour = "position")) +
      ylab('position (1=buy/-1=sell)') +
      xlab('date')
    ggplotly(p)
    
  })
  
  output$plot_balance <- renderPlotly({
    
    d <- load_data()
    if(is.null(d))
      return(NULL)
    
    p <- ggplot(d) +
      geom_line(mapping = aes(x=index(d), y = balance, colour = "balance")) +
      ylab('balance ($)') +
      xlab('date')
    ggplotly(p)
    
  })
  
}

shinyApp(ui, server)

