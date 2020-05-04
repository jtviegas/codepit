if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
}

source("../functions01.R")

tickers <- getTickers()
strategies <- getStrategies()

ui <- fluidPage(
  
  titlePanel("NASDAQ experiment")
  , hr()
  , fluidRow(
    column(4, selectInput('ticker', 'Ticker', c(Choose='', tickers$Name), selectize=FALSE))
    , column(4, dateRangeInput('dates', label = 'Date range:', start = Sys.Date() - 30, end = Sys.Date()))
    , column(4, selectInput('strategy', 'Investment Strategy', c(Choose='', strategies), selectize=FALSE))
  )
  , hr()
  , plotOutput(outputId = "plot", height ='600')
)

server <- function(input, output, session) {
  
  dataInput <- reactive({
    if('' == input$ticker)
      return (NULL)
    
    symbol<-tickers[tickers$Name==input$ticker,1][1] 
    print(paste("symbol:", symbol))
    d <- getData(symbol,input$dates)
    print(paste("rows of data:", nrow(d)))
    return(d)
  })
  
  strategyInput <- reactive({
    if('' == input$strategy)
      return (NULL)
    d <- do_strategy(input$strategy, dataInput())
    return(d)
  })
  
  observeEvent(input$ticker, {
    updateSelectInput(session, "strategy", selected = '')
  })
  
  output$plot <- renderPlot({
    data <- dataInput()
    st <- strategyInput()
    
    
    
    if(length(data) == 0){
      return(NULL)
    }
    else {
      symbol<-tickers[tickers$Name==input$ticker,1][1]
      title <- paste(input$ticker,"/", symbol)
      #plotIt(dataInput(), title, st)
      print(chartSeries(data, up.col = "black", dn.col = "red", theme = "white", name = title ))
      if( not(is.null(st)) ){
        d <- st[["strategy"]]
        sameplotSeries <- st[["1"]]
        i = 1
        for( col in colnames(d) ){
          i <- i+1
          if( col %in% sameplotSeries){
            print(addTA(d[,c(col)], on = 1, col = i))
          }
          else {
            print(addTA(d[,c(col)], col = i))
          }
        }
      }
      
    }

  })

}

shinyApp(ui = ui, server = server)

#candleChart(data,theme='white', type='candles') 
#reChart(major.ticks='months',subset='first 64 weeks')
