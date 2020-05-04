if (!require("shiny")) {
  install.packages("shiny")
  library(shiny)
}
if (!require("shinyTree")) {
  install.packages("shinyTree")
  library(shinyTree)
}
if (!require("shinyjs")) {
  install.packages("shinyjs")
  library(shinyjs)
}




ui <- shinyUI(
  pageWithSidebar(
    # Application title
    
    headerPanel("shinyTree with 'selected' input"),
    
    sidebarPanel(
      helpText(HTML("An example of using shinyTree's <code>get_selected</code> function to extract the cells which are currently selected.
                    <hr>Created using <a href = \"http://github.com/trestletech/shinyTree\">shinyTree</a>.")),
      
      actionButton('submit','SUBMIT')
      
    ),
    mainPanel(
      useShinyjs(),
      "Currently Selected:",
      verbatimTextOutput("selTxt"),
      hr(),
      shinyTree("tree")
    )
  ))

server <- shinyServer(function(input, output, session) {
  log <- c(paste0(Sys.time(), ": Interact with the tree to see the logs   here..."))
  shinyjs::hide("submit")
  output$tree <- renderTree({
    list(
      root1 = structure("123"),
      root2 = list(
        SubListA = list(leaf1 = "", leaf2 = "", leaf3=""),
        SubListB = list(leafA = "", leafB = "")
      )
    )
  })
  
  output$selTxt <- renderText({
    tree <- input$tree
    if (is.null(tree)){
      "None"
    } else{
      unlist(get_selected(tree))
      
    }})
  
  observe({
    if(!is.null(unlist(get_selected(input$tree)))){
      if(length(unlist(strsplit(unlist(get_selected(input$tree)),"leaf")))>1){
        shinyjs::show("submit")
      }else{
        shinyjs::hide("submit")
      }
    }
    
  })
  
})

shinyApp(ui, server)