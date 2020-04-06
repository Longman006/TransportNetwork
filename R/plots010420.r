library(ggplot2)

df<-`31.03.2020_18.53.05.1350dv`
df$id<-as.factor(df$id)
allDataPlot<-ggplot(df,aes(x=t,y=d,color=v))+geom_point()

firstCar<-'e0be9c7c-b1da-466b-889b-71c5bc228056'
lastCar<-'f7d306d9-bd99-421a-9d4d-01e006c2be51'

#sp+scale_color_brewer(palette = "RdYlGn")

colorplot<-allDataPlot+scale_color_gradientn(colours = rainbow(5))

filterDataPlot<-ggplot(subset(df, id == c(firstCar,lastCar)),aes(x=t, y=d,color=id))+geom_point()
filterDataPlot

