
filename<-"23-09-2020_20.41.02.CongestionSizeOnRoads"
filename2<-"23-09-2020_20.40.59.RoadStatisticsFile"
SampleRoadID<-'45d0ae8c-aa85-437c-aee0-d747d6e93bf1'
SampleRoadID2<-'32dd6829-ee64-4c56-afd5-67c1582531c3'
SumRoadID<-'00000000-0000-0000-0000-000000000000'
T0<-2000

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
  
  fAverage <- file.choose()
  dfAverage <- read.table(fAverage,header = TRUE)
  dfAverage[is.na(dfAverage)] <- 0
  ACC<-'ACC 1.0'
  
  df1Average<-subset(dfAverage, RoadID == SampleRoadID)
  df2Average<-subset(dfAverage, RoadID == SumRoadID)
  
  
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
  
  ##Average Plots
  
  plot.all.roads.average.speed <- ggplot( dfAverage,aes(Time, AverageSpeed, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  plot.all.roads.average.speed.normalized <- ggplot( dfAverage,aes(Time, AverageSpeedNormalized, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.average.speed.normalized <- ggplot( df1Average,aes(Time, AverageSpeedNormalized, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.speed.average <- ggplot( df1Average,aes(Time, AverageSpeed, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.average.speed.normalized <- ggplot( df1Average,aes(Time, AverageSpeedNormalized, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.Time.average <- ggplot( df1Average,aes(Time, AverageSpeed, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.Time.average <- ggplot( df1Average,aes(Time, AverageSpeed, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sum.average.speed <- ggplot(df2Average,aes(Time, AverageSpeed, color = RoadID) )+
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sum.average.time <- ggplot(df2Average,aes(Time, AverageTime, color = RoadID) )+
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  ##p <- plot_grid(p1, p2, labels = paste(ACC))
  plot.file.name<-paste(file_path_sans_ext(basename(f)),'.png')
  save_plot(plot.file.name, p, ncol = 2)
}