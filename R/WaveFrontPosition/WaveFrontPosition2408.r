library(gganimate)
library(ggplot2)
library(plotly)
library(cowplot)
library(tools)

plotWaveFronts <- function(WaveFront,WaveEnd,ACC){
f <- file.choose()
df <- read.table(f,header = TRUE)

##WaveFront 906b0de3-8076-4b8e-84f2-e0016348d39a
##WaveEnd e3af6668-ccb0-4cba-8eda-1841fbb10c5f
##ACC 1.0

#WaveFront<-'906b0de3-8076-4b8e-84f2-e0016348d39a'
#WaveEnd<-'e3af6668-ccb0-4cba-8eda-1841fbb10c5f'

# test if there is at least one argument: if not, return an error


# if (!interactive()) {
#   args <- commandArgs(trailingOnly = TRUE)
#   if (length(args)<2) {
#     stop("Two arguments must be supplied", call.=FALSE)
#   } else if (length(args)==2) {
#     WaveFront<-args[1]
#     WaveEnd<-args[2]
#   }
# }

#T1<-1923
T2<-1800
df<-subset(df, Time < T2)
df1<-subset(df, WaveFrontID == WaveFront | WaveFrontID == WaveEnd, select = c("Time","Distance","Speed", "WaveFrontID"))

diff<-data.frame(
  Length=df1[df1$WaveFrontID == WaveFront,]$Distance  - df1[df1$WaveFrontID == WaveEnd,]$Distance,
  Time=df1[df1$WaveFrontID == WaveFront,]$Time)

# max<-max(df1$Distance)
# df2<-subset(df1, Time > T1)
# df2<-subset(df2, Time < T2)
# df2forFitting<-subset(df2, Time > 2200)
# 
# mm <- lmList(Distance~Time|WaveFrontID,df2forFitting)
# summary(mm)
# 
# xy.lm<- lm(Distance ~ Time,subset(df, WaveFrontID == WaveFront))
# summary(xy.lm)
# xy.lm$coefficients
# b <- xy.lm$coefficients[1]
# a <- xy.lm$coefficients[2]
# xy.lm$Time<-df2$Time
# 
# getDistance <- function(x){
#   return(a*x+b)}
# 
# df2$Distance<-lapply(xy.lm$Time,getDistance)

# Equation of the line : 
# eq = paste0("y = ", round(xy.lm$coefficients[2],1), "*x + ", round(xy.lm$coefficients[1],1))
# 
# p <- ggplot( df1,aes(Time, Distance, color = Speed)) +
#   geom_point() + geom_abline(intercept = xy.lm$coefficients[1], slope = xy.lm$coefficients[2],color="red", 
#                              linetype="dashed", size=1.5) + ggtitle(eq)
#   
#   geom_line(df2,aes(df2$Time,df2$Distance, color="red"))

  
p1 <- ggplot( df1,aes(Time, Distance, color = WaveFrontID)) +
  theme(legend.position="bottom")+
  geom_line()+
  geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)

p2 <- ggplot(diff,aes(Time, Length) )+
  theme(legend.position="bottom")+
  geom_line()+
  geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)

p <- plot_grid(p1, p2, labels = paste(ACC))
plot.file.name<-paste(file_path_sans_ext(basename(f)),'.png')
save_plot(plot.file.name, p, ncol = 2)
}

