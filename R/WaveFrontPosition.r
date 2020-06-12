library(gganimate)
library(ggplot2)
library(plotly)
f <- file.choose()
df <- read.table(f,header = TRUE)
df$Time<-as.factor(df$Time)

##Road 220d0efa-1a03-4e35-9403-4d0dc4ba1346

df1<-subset(df, RoadID == '220d0efa-1a03-4e35-9403-4d0dc4ba1346', select = c("Time","Density","Distance","Speed") )

## Road 3b1cdb07-abf8-41a2-96f7-cdf477ca48fa

df2<-subset(df, RoadID == '3b1cdb07-abf8-41a2-96f7-cdf477ca48fa', select = c("Time","Density","Distance","Speed") )
df2$Test<-df2$Density/(1+df2$Speed)^2
p <- ggplot(subset(df2,Time == 1490), aes(Distance, Test, color = Speed)) +
  geom_point() 
p

fig <- ggplotly(p)

fig
