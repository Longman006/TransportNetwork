library(gganimate)
library(ggplot2)
library(plotly)
f <- file.choose()
df <- read.table(f,header = TRUE)
df$RoadID<-as.factor(df$RoadID)
##df$Time<-as.factor(df$Time)
myRoadID<-'40b9e30c-a826-4026-ad8c-bcd8cb087689'

df<-subset(df, RoadID == c(myRoadID))
           
p <- ggplot(df, aes(x = DistanceToEnd)) + 
  geom_density(aes(group = Time))

fig <- ggplotly(p)

g <- ggplot(df, aes(x = DistanceToEnd,fill = as.factor(Time))) + 
  geom_density()

anim <- p + 
  transition_states(Time)

anim

ggtitle('Now showing {closest_state}',
        subtitle = 'Frame {frame} of {nframes}')

plot(p)


df <- read.table(f,header = TRUE)
df$RoadID<-as.factor(df$RoadID)
##df$Time<-as.factor(df$Time)
myRoadID<-'40b9e30c-a826-4026-ad8c-bcd8cb087689'

df<-subset(df, RoadID == c(myRoadID) )
df<-subset(df, Time == c(1000,1001,1002,1003))
dataTomek<-df[c("Time","DistanceToEnd")]


fig<-ggplot(dataTomek,aes(x = DistanceToEnd))+
  geom_density()

anim<-fig+transition_states(Time,transition_length = 3, state_length = 1)
save.gif <- image_animate(anim, fps = 2); 
image_write(save.gif, "output.gif")
data <- data.frame(
  x = c(rnorm(50, mean = 5, sd = 3), rnorm(40, mean = 2, sd = 1)),
  y = c(rnorm(50, mean = -2, sd = 7), rnorm(40, mean = 6, sd = 4)),
  state = rep(c('a', 'b'), c(50, 40))
)

ggplot(data, aes(x = x, y = y)) +
  geom_contour(stat = 'density_2d') + 
  facet_wrap(~state)