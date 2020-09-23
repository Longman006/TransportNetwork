filename<-"04-09-2020_12.17.25.CongestionSizeOnRoads"
SampleRoadID<-'f713621e-051e-4b24-94fd-eb4aa0c3ea14'
SumRoadID<-'00000000-0000-0000-0000-000000000000'
T0<-2200

library(gganimate)
library(ggplot2)
library(plotly)
library(cowplot)
library(tools)

plotCongestions <- function(){
 
   f <- file.choose()
  df <- read.table(f,header = TRUE)
  ACC<-'ACC 1.0'
  
  df1<-subset(df, RoadID == SampleRoadID)
  df2<-subset(df, RoadID == SumRoadID)
  
  
  
  plot.all.roads <- ggplot( df,aes(Time, Size, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
    ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  plot.all.roads.normalized <- ggplot( df,aes(Time, SizeNormalized, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.normalized <- ggplot( df1,aes(Time, SizeNormalized, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road <- ggplot( df1,aes(Time, Size, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sum <- ggplot(df2,aes(Time, Size, color = RoadID) )+
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
    ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  ##p <- plot_grid(p1, p2, labels = paste(ACC))
  plot.file.name<-paste(file_path_sans_ext(basename(f)),'.png')
  save_plot(plot.file.name, p, ncol = 2)
}