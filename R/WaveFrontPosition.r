library(gganimate)
library(ggplot2)
library(plotly)
f <- file.choose()
df <- read.table(f,header = TRUE)


##WaveFront 62aa0281-26ab-46bc-9266-756d3e55f69b

df1<-subset(df, WaveFrontID == '62aa0281-26ab-46bc-9266-756d3e55f69b', select = c("Time","Distance","Speed") )

max<-max(df1$Distance)
df2<-subset(df1, Time > 2800)
df2<-subset(df2, Time < 2965)
xy.lm <- lm(df2$Distance ~ df2$Time)
summary(xy.lm)
xy.lm$coefficients
b <- xy.lm$coefficients[1]
a <- xy.lm$coefficients[2]
xy.lm$Time<-df2$Time

getDistance <- function(x){
  return(a*x+b)}

df2$Distance<-lapply(xy.lm$Time,getDistance)

# Equation of the line : 
eq = paste0("y = ", round(xy.lm$coefficients[2],1), "*x + ", round(xy.lm$coefficients[1],1))

p <- ggplot( df1,aes(Time, Distance, color = Speed)) +
  geom_point() + geom_abline(intercept = xy.lm$coefficients[1], slope = xy.lm$coefficients[2],color="red", 
                             linetype="dashed", size=1.5) + ggtitle(eq)
  
  geom_line(df2,aes(df2$Time,df2$Distance, color="red"))
p

fig <- ggplotly(p)

fig
