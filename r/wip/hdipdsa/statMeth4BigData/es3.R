install.packages("devtools")
library(devtools)
install_github("jtviegas/myrpack")
library(myrpack)
ls("package:myrpack")
install_if_missing("tidyverse")
library(tidyverse)
install_if_missing("readxl")
library(readxl)
install_if_missing("here")
library(here)

par(mfrow=c(1,1))
### 1. In the context of experimental design, what is:
#   a factor?
# ... is a controlled explanatory variable, which levels are managed by the experimenter
#   a level?
# ... represents the values of the controlled explanatory variable
#   a treatment?
# ... the different values of the factor, if an experiment has more than one 
#     factor than each combination of factors is a treatment

### 2. A mechanical engineer is studying the thrust force developed by a drill press. He suspects
#that the drilling speed and the feed rate of the material affect the thrust force. He selects
#four feed rates and uses a high and low drill speed chosen to represent the extreme
#operating conditions.
# How many factors does this experiment have?
# ... 2
#   How many levels does each of the factors have?
# ... 4 feed rates + 2 drill speeds
#   How many treatments does this experiment have? 
# ... 8

### 3. Give an example of an experiment that uses a randomized block design.


#5. If we test five null hypotheses (which are all true) using 0.01 as the critical significance
#level what is the probability that we will make at least one type I error?
#  p(X>=1)=1-P(X=0)=(1-0.01)^5=0.04901

#6. A statistician carries out an ANOVA on a data set. Name two assumptions that should be
#satisfied before carrying out the test.
#- data sampling should be random, and observations independent
#- variances between tratments should be equal
#- the error terms are independent from observation to observation
#- the error terms are normally distributed with zero mean and same variance

"7. The Kenton Food Company wished to test four different package designs for a new
breakfast cereal. The different designs were sold in twenty shops, with approximately
equal sales figures. Each shop was randomly assigned one of the package designs so that
each design was sold in five different shops. The number of cases sold were represented
by the variable ‘Cases’ and the type of packaging was represented by the variable
‘Package’ which had four different levels, identified as 1, 2, 3 and 4. After the

preliminary data analysis, a one way ANOVA was carried out on the data set, followed
by Tukey’s HSD post hoc test. The R output is shown below:
  
What does the result of the Fligner-Killeen test (see Figure 1) tell you?
...the fligner test tells that the variance is homogeneous among the treatments

What are the null and alternative hypotheses associated with this analysis?
... h0: the mean of cases for each packaging type is equal;
    ha: the mean number of cases is different for at least one package type;

Why does the variable ‘Package’ have 3 degrees of freedom?
...there is 4 package types so a-1=4-1=3

Report the results of the ANOVA including the pairwise comparisons, a
table indicating which (if any) treatments are significantly different at the
5% level and the effect size η².

...anova table shows a p-value of 1.33*10^-5 so the means of the cases variables 
    are different among treatments/packages at significance level 99.9%.
  the model r-square tells that the variable account for 78.58% of the cases number 
  also 4-1,4-2,3-2 have distinct effect on the cases, so that the mean case with the treatment 4 is different from 1 and 2
  and 3 is also different than 2.

Write down an ANOVA model for this data set, indicating clearly what each
term in the model represents.
... 14.6 - 1.2*X2 + 4.6*X3 + 12.6*X4 = Cases
"

"
8.A soft drink distributer knows that end-aisle displays are an effective way to
increase sales of the product. However there are several ways to design these
displays. The marketing group has designed three new end aisle displays and
wants to test their effectiveness. They have identified 15 stores of similar size
and type to participate in each study. Each store will test one of the displays
for a period of one month. The displays are assigned at random around the
stores with each display tested in 5 stores. The response variable is the
percentage increase in sales activity over typical sales in that store when the
display is not is use. The data from the experiment is available on Blackboard
in the soft_drink.txt file. Can you analyse the data to determine
whether the end-aisle displays have a significant impact on the soft drink
sales? Write up a brief report including the following outputs and any
conclusions that you draw.

 Exploratory data analysis
 ANOVA table
 Pairwise comparisons including a table summarizing which (if any)
treatments are significantly different.
 The effect size η²
 An ANOVA model indicating clearly what each term in the model represents.
 Model diagnostics
"
data <- read_tsv(here("wip/data/soft_drink.txt"))
head(data)

#...summarize by soil display type
data %>% group_by(Display) %>% summarise(mean_Increase=mean(Increase), sd_Increase=sd(Increase), max_increase=max(Increase), min_increase=min(Increase), n=n())
"
  Display mean_Increase sd_Increase max_increase min_increase     n
    <int>         <dbl>       <dbl>        <dbl>        <dbl> <int>
1       1          5.73       0.388         6.22         5.29     5
2       2          6.24       0.434         6.71         5.66     5
3       3          8.32       0.670         9.20         7.55     5
"
#... let's use Fligner-Killeen test of homogeneity of variances
fligner.test(data$Increase~data$Display) # p-value = 0.3601 : we don't reject null hypothesis, variances are the same

#...we can visualize the effect of the Display using a box & whisker plot
boxplot(data$Increase~data$Display)
# there are no outliers, clearly the increase was higher in Display 3
is.factor(data$Display)
data$Display<-as.factor(data$Display)
levels(data$Display)

model_treat <- aov(data$Increase~data$Display)
summary(model_treat) 
"
             Df Sum Sq Mean Sq F value   Pr(>F)    
data$Display  2  18.78   9.392   35.77 8.78e-06 ***
Residuals    12   3.15   0.263                     
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

...the mean increase of sales is significantly affected by the display,
as can be seen by the tiny p-value
"
summary.lm(model_treat)
"
Coefficients:
              Estimate Std. Error t value Pr(>|t|)    
(Intercept)     5.7320     0.2291  25.015 1.01e-11 ***
data$Display2   0.5060     0.3241   1.561    0.144    
data$Display3   2.5860     0.3241   7.980 3.86e-06 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.5124 on 12 degrees of freedom
Multiple R-squared:  0.8564,	Adjusted R-squared:  0.8324 
F-statistic: 35.77 on 2 and 12 DF,  p-value: 8.782e-06
"
plot.design(data$Increase~data$Display)
#.. the treatment effects in tabular form
model.tables(model_treat)

## pairwise comparisons
#...to compare each pair of soil types we can use Tukey's honest significat difference (HSD)
TukeyHSD(model_treat)
# display 3 is significantly different than 2 and 1

"anova model"
# increase = 5.7320 + 0.506*X2 + 2.5860*X3

## Model Validation
#...checking the assumptions
par(mfrow=c(2,2))
plot(model_treat)




par(mfrow=c(1,1))



print(2)

  
  
