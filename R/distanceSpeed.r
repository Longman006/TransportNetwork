library(ggplot2)
df<-`10.03.2020_19.39.30.600dv`

sp<-ggplot(df,aes(x=t,y=d,color=v))+geom_point()

sp+scale_color_brewer(palette = "RdYlGn")


