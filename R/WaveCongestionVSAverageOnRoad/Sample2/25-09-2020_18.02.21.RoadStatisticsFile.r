
filename<-"25-09-2020_18.02.23.CongestionSizeOnRoads"
filename2<-"25-09-2020_18.02.21.RoadStatisticsFile"
SampleRoadID<-'9d72ac90-ab7e-4d4f-bd67-5ef46fc116a9'
SampleRoadID2<-'42810e18-bfb2-4d4a-b7de-b2bbf4c83cc9'
SumRoadID<-'00000000-0000-0000-0000-000000000000'
T0<-1300

library(gganimate)
library(ggplot2)
library(plotly)
library(cowplot)
library(tools)

plotCongestions <- function(){
 
   f <- file.choose()
  df <- read.table(f,header = TRUE)
  ACC<-'ACC 1.0'
  
  df1<-subset(df, RoadID == SampleRoadID2)
  df2<-subset(df, RoadID == SumRoadID)
  
  fAverage <- file.choose()
  dfAverage <- read.table(fAverage,header = TRUE)
  is.na(dfAverage) <- do.call(cbind,lapply(dfAverage, is.infinite))
  dfAverage[is.na(dfAverage)] <- 0
  ACC<-'ACC 1.0'
  
  df1Average<-subset(dfAverage, RoadID == SampleRoadID2)
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
  
  plot.sample.road.time.average <- ggplot( df1Average,aes(Time, AverageTime, color = RoadID)) +
    theme(legend.position="bottom")+
    geom_line()+
    geom_vline(xintercept = T0, 
               color = "blue", size=0.5)
  ##geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)
  
  plot.sample.road.time.average.normalized <- ggplot( df1Average,aes(Time, AverageTimeNormalized, color = RoadID)) +
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
  
  plot.sum.grid <- plot_grid(plot.sum, plot.sum.average.speed, labels = paste(ACC))
  plot.file.name<-paste(file_path_sans_ext(basename(f)),'Total Congestion','.png')
  save_plot(plot.file.name, plot.sum.grid, ncol = 2)
  
  plot.sample0.grid <- plot_grid(plot.sample.road, plot.sample.road.time.average, labels = paste(ACC))
  plot.file.name<-paste(file_path_sans_ext(basename(f)),'Sample Road Congestion VS Average Time','.png')
  save_plot(plot.file.name, plot.sample0.grid, ncol = 2)
  
  plot.sample1.grid <- plot_grid(plot.sample.road, plot.sample.road.speed.average, labels = paste(ACC))
  plot.file.name<-paste(file_path_sans_ext(basename(f)),'Sample Road Congestion VS Average Speed','.png')
  save_plot(plot.file.name, plot.sample1.grid, ncol = 2)
}