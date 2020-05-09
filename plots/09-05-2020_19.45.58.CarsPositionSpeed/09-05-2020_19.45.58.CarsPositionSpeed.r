library(ggplot2)

f <- file.choose()
df <- read.table(f)
df$id<-as.factor(df$id)
allDataPlot<-ggplot(df,aes(x=t,y=d,color=v))+geom_point()

firstCar<-'7341ff4e-8d69-4352-9db9-591837805730'
lastCar<-'9ecfa52c-8ce1-4145-a7e1-fdedbd56d010'

#sp+scale_color_brewer(palette = "RdYlGn")

colorplot<-allDataPlot+scale_color_gradientn(colours = rainbow(5))
colorplot


filterDataPlot<-ggplot(subset(df, id == c(firstCar,lastCar)),aes(x=t, y=d,color=id))+geom_point()
filterDataPlot

SimpleDataPlot<-ggplot(df,aes(x=t, y=d))+geom_point(alpha = 1/5,size=0.2)
SimpleDataPlot
