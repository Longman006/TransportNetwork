library(ggplot2)

f <- file.choose()
df <- read.table(f,header = TRUE)
df$CarID<-as.factor(df$CarID)
df$OnRampID<-as.factor(df$OnRampID)
allDataPlot<-ggplot(df,aes(x=Time,y=Distance,color=Speed))+geom_point()

firstCar<-'7341ff4e-8d69-4352-9db9-591837805730'
lastCar<-'9ecfa52c-8ce1-4145-a7e1-fdedbd56d010'

#sp+scale_color_brewer(palette = "RdYlGn")

colorplot<-allDataPlot+scale_color_gradientn(colours = rainbow(5))
colorplot


filterDataPlot<-ggplot(subset(df, CarID == c(firstCar,lastCar)),aes(x=Time, y=Distance,color=CarID))+geom_point()
filterDataPlot

SimpleDataPlot<-ggplot(df,aes(x=Time, y=Distance))+geom_point(alpha = 1/5,size=0.2)
SimpleDataPlot
