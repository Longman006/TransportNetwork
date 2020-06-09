library(gganimate)
library(ggplot2)
library(plotly)
f <- file.choose()
df <- read.table(f,header = TRUE)
df$Time<-as.factor(df$Time)

##Road 220d0efa-1a03-4e35-9403-4d0dc4ba1346

df1<-subset(df, RoadID == '220d0efa-1a03-4e35-9403-4d0dc4ba1346', select = c("Time","Density","Distance","Speed") )

p <- ggplot(df1, aes(Distance, Density, color = Speed)) +
  geom_point(aes(frame = Time)) +


fig <- ggplotly(p)

fig