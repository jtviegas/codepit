install.packages("ggplot2")
library(ggplot2)

install.packages("plotly")
library(plotly)
library(dplyr)


# Example 1 Diamond data Carat against price by category z (black circles) is 
clarityp <- ggplot(data = diamonds, aes(x = carat, y = price)) +  geom_point(aes(text = paste("Clarity:", clarity)), size = 4) +  geom_smooth(aes(colour = cut, fill = cut)) + facet_wrap(~ cut)
(gg <- ggplotly(clarityp))
#Example 2
plot_ly(z = volcano, type = "surface")

# Example 3 Interact with R data using GGobi
# --------------------------------------------------
if (!require('devtools')) install.packages('devtools')  # needs Curl for RCurl
library(devtools)

install_github('ggobi/qtbase@qt4'); install_github('ggobi/qtpaint')

install.packages(c('scales', 'tourr', 'objectSignals', 'objectProperties', 'plumbr', 'SearchTrees'))
install_github(c('hadley/productplots', 'hadley/densityvis'))
## and finally cranvas:
install_github('ggobi/cranvas', args = "--no-multiarch")

library(devtools)
library(ggobi)
# --------------------------------------------------

source("http://www.ggobi.org/downloads/install.r")
ggobi(mtcars)
#Example 4 Use identify to find details of a point on a scatter plot
attach(mtcars)
plot(cyl, disp) # scatterplot
identify(cyl, disp, labels=row.names(mtcars)) # identify points 
coords <- locator(type="l") # add lines
coords # display list
#Example 5 the interactive process#get ggplot 
library(ggplot2)
g <- ggplot(txhousing, aes(x = date, y = sales, group = city)) +
    geom_line(alpha = 0.4)
g
#add in plotly
library(plotly)
g <- ggplot(txhousing, aes(x = date, y = sales, group = city)) +  geom_line(alpha = 0.4) 
ggplotly(g, tooltip = c("city"))

#Example 6 More complicated version plotly directly
g <- txhousing %>% 
    # group by city    group_by(city) %>%  # initiate a plotly object with date on x and median on y    plot_ly(x = ~date, y = ~median) %>%  # add a line plot for all texan cities    add_lines(name = "Texan Cities", hoverinfo = "none",             type = "scatter", mode = "lines",             line = list(color = 'rgba(192,192,192,0.4)')) %>%  # plot separate lines for Dallas and Houston    add_lines(name = "",             data = filter(txhousing,                           city %in% c("Dallas", "Houston")),            hoverinfo = "city",            line = list(color = c("red", "blue")),            color = ~city)
g

#Example 7 LInking Data
library(crosstalk)
# define a shared data object
d <- SharedData$new(mtcars)
# make a scatterplot of disp vs mpg
scatterplot <- plot_ly(d, x = ~mpg, y = ~disp) %>%  add_markers(color = I("navy"))
# define two subplots: boxplot and scatterplot
subplot(
    # boxplot of disp    
	plot_ly(d, y = ~disp) %>%         
	add_boxplot(name = "overall",                                   
	color = I("navy")),
    # scatterplot of disp vs mpg    
	scatterplot, 
      shareY = TRUE, titleX = T) %>%     
	layout(dragmode = "select")
#Example 8 Linking Data
# make subplots
p <- subplot(
    # histogram (counts) of gear    
plot_ly(d, x = ~factor(gear)) %>%         
add_histogram(color = I("grey50")),    # scatterplot of disp vs mpg    
scatterplot,     titleX = T
  ) 
layout(p, barmode = "overlay")

#Example 9 Networks
install.packages("GGally")
library(GGally)
install.packages("network")
library(network)
install.packages("sna")
library(sna)

# random graphnet = rgraph(10, mode = "graph", tprob = 0.5)
net = network(net, directed = FALSE)
# vertex names
network.vertex.names(net) = letters[1:10]
ggnet2(net)
ggnet2(net, node.size = 6, node.color = "black", edge.size = 1, edge.color = "grey")
ggnet2(net, size = 6, color = "black", edge.size = 1, edge.color = "grey")
ggnet2(net, size = 6, color = rep(c("tomato", "steelblue"), 5))
ggnet2(net, mode = "circle")
ggnet2(net, mode = "kamadakawai")

ggnet2(net, size = "degree")
ggnet2(net, label = TRUE)


#Illustration  of Networks
source("https://goo.gl/q1JFih")
x = cut_number(as.integer(net %v% "year"), 4)
col = c("#E1AF00", "#EBCC2A", "#78B7C5", "#3B9AB2")
names(col) = levels(x)
ggnet2(net, color = x, color.legend = "period", palette = col,
              edge.alpha = 1/4, edge.size = "weight",              size = "outdegree", max_size = 4, size.cut = 3,              legend.size = 12, legend.position = "bottom") +    coord_equal()
#lots of different network packages another can be found here: https://christophergandrud.github.io/networkD3/
