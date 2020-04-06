library(ggplot2)

df<-`01.04.2020_20.05.24.2900dv`
df$id<-as.factor(df$id)
allDataPlot<-ggplot(df,aes(x=t,y=d,color=v))+geom_point()

firstCar<-'03663d38-b014-4e82-a362-762c47f5a02c'
lastCar<-'d2f34992-115d-4449-8be9-6d2d2085666b'

#sp+scale_color_brewer(palette = "RdYlGn")

colorplot<-allDataPlot+scale_color_gradientn(colours = rainbow(5))

filterDataPlot<-ggplot(subset(df, id == c(firstCar,lastCar)),aes(x=t, y=d,color=id))+geom_point()
filterDataPlot

SimpleDataPlot<-ggplot(df,aes(x=t, y=d))+geom_line()
SimpleDataPlot
