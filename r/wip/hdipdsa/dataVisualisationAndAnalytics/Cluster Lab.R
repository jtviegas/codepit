# prepare hierarchical cluster
hc = hclust(dist(mtcars))
# very simple dendrogram
plot(hc)

# labels at the same level
plot(hc, hang = -1)

# tweeking some parameters
op = par(bg = "#DDE3CA")
plot(hc, col = "#487AA1", col.main = "#45ADA8", col.lab = "#7C8071", 
    col.axis = "#F38630", lwd = 3, lty = 3, sub = "", hang = -1, axes = FALSE)
# add axis
axis(side = 2, at = seq(0, 400, 100), col = "#F38630", labels = FALSE, 
    lwd = 2)
# add text in margin
mtext(seq(0, 400, 100), side = 2, at = seq(0, 400, 100), line = 1, 
    col = "red", las = 2)

# using dendrogram objects
hcd = as.dendrogram(hc)
# alternative way to get a dendrogram
plot(hcd)


# using dendrogram objects
plot(hcd, type = "triangle")


# plot dendrogram with some cuts
op = par(mfrow = c(2, 1))
plot(cut(hcd, h = 75)$upper, main = "Upper tree of cut at h=75")
plot(cut(hcd, h = 75)$lower[[2]], main = "Second branch of lower tree with cut at h=75")




# vector of colors labelColors = c('red', 'blue', 'darkgreen', 'darkgrey',
# 'purple')
labelColors = c("#CDB380", "#036564", "#EB6841", "#EDC951")
# cut dendrogram in 4 clusters
clusMember = cutree(hc, 4)
# function to get color labels
colLab <- function(n) {
    if (is.leaf(n)) {
        a <- attributes(n)
        labCol <- labelColors[clusMember[which(names(clusMember) == a$label)]]
        attr(n, "nodePar") <- c(a$nodePar, lab.col = labCol)
    }
    n
}
# using dendrapply
clusDendro = dendrapply(hcd, colLab)
# make plot
plot(clusDendro, main = "Cool Dendrogram", type = "triangle")




# load package ape; remember to install it: install.packages('ape')
install.packages("ape")
library(ape)
# plot basic tree
plot(as.phylo(hc), cex = 0.9, label.offset = 1)


plot(as.phylo(hc), type = "cladogram", cex = 0.9, label.offset = 1)


plot(as.phylo(hc), type = "unrooted")


plot(as.phylo(hc), type = "fan")


# load code of A2R function
source("http://addictedtor.free.fr/packages/A2R/lastVersion/R/code.R")
# colored dendrogram
op = par(bg = "#EFEFEF")
A2Rplot(hc, k = 3, boxes = FALSE, col.up = "gray50", col.down = c("#FF6B6B", 
    "#4ECDC4", "#556270"))


# another colored dendrogram
op = par(bg = "gray15")
cols = hsv(c(0.2, 0.57, 0.95), 1, 1, 0.8)
A2Rplot(hc, k = 3, boxes = FALSE, col.up = "gray50", col.down = cols)


