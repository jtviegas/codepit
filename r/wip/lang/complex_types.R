### complex types

## vectors - fundamental data type, every element must be the same type

y <- c(10,3,23,67,43,55)
x <- c("10as","ytem","55")

# functions on vectors
max(y)
min(y)
mean(y)
length(y)
var(y)
sqrt(var(y))


# indexing vectors - one based
print(y[2])
print(y[2:4])


## Matrices

m1 <- matrix(c(1,2,3,4,5,6), nrow=2, ncol=3, byrow = TRUE)
m2 <- matrix(c(1,2,3,4,5,6), nrow=2, ncol=3, byrow = FALSE)

print(m1[1,3]) # 3
print(m2[1,2]) # 3

# for vectors,and matrices, arithmetic and logical operations are applied element wise.
x<-c(1,2,3)
y<-c(4,5,6)
print(x+y) # [1] 5 7 9

print(x*y) # [1]  4 10 18

# using rules of matrix multiplication
print(x%*%y)

## Higher-Dimensional Arrays

m1 <- matrix(c(1, 2, 3, 4, 5, 6), nrow = 2, ncol = 3, byrow = TRUE)
m2 <- matrix(c("a", "b", "c", "d", "e", "f" ),nrow = 2,ncol=3,byrow=TRUE)
a <- array(c(m1,m2), dim=c(2,3,2))


## Lists - like a vector but with elements of different classes

x <- list(1, "s", TRUE, 1+4i)
print(x)

## factors - categorical variables
## R stores the vector along with distinct values of the elements in the vector as labels,
## the labels are always character

gender <- c("m", "f", "m", "f")
gender <- factor(gender)
print(gender)









