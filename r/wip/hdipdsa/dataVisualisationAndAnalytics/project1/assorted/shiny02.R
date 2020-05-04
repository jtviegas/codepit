if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
}

source("../functions.R")

# globals
tickers <- getTickers()
strategies <- getStrategies()

# functions


# shiny

ui <- fluidPage(
  
  titlePanel("NASDAQ experiment")
  , hr()
  , fluidRow(
    column(4, selectInput('ticker', 'Ticker', c(Choose='', tickers$Name), selectize=FALSE))
    , column(4, dateRangeInput('dates', label = 'Date range:', start = Sys.Date() - 7, end = Sys.Date()))
    , column(4, selectInput('strategy', 'Investment Strategy', c(Choose='', strategies), selectize=FALSE))
  )
  , hr()
  , plotOutput(outputId = "plot", height ='600')
  
)

server <- function(input, output, session) {
  
  dataInput <- reactive({
    getData(input$ticker,input$dates)
  })
  
  output$plot <- renderPlot({
    plotIt(input$ticker,input$dates,input$strategy)
  })
  
  observeEvent(input$ticker, {
    updateSelectInput(session, "strategy",selected = '')
    reset_plot(output)
    if(input$ticker != ''){
      print(paste("ticker has changed:", input$ticker))
      plot_data(output, input$ticker , input$dates)
    }
  })
  
  observeEvent(input$dates, {
    updateSelectInput(session, "strategy",selected = '')
    reset_plot(output)
    print(paste("dates changed:", input$dates))
    print(paste("ticker is:", input$ticker))
    if(input$ticker != ''){
      plot_data(output, input$ticker , input$dates)
    }
  })
  
  observeEvent(input$strategy, {
    print(paste("strategy has changed:", input$strategy))
    if(input$strategy != '' && input$ticker != ''){
      if(input$strategy == "Moving Avg 20-50"){
        strategy_ma2050(output, input$ticker , input$dates)
      }
      else{
        if(input$strategy == "Moving Avg 50-100"){
          strategy_ma50100(output, input$ticker , input$dates)
        }
      }
      
    }
  })

}


shinyApp(ui = ui, server = server)



