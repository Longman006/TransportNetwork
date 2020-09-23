library(gganimate)
library(ggplot2)
library(plotly)
library(cowplot)
library(tools)

plotWaveFronts <- function(){
f <- file.choose()
df <- read.table(f,header = TRUE)
ACC<-'ACC 1.0'


  
p1 <- ggplot( df,aes(Time, Size, color = WaveFrontID)) +
  theme(legend.position="bottom")+
  geom_line()+
  geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)

p2 <- ggplot(df,aes(Time, Speed,color = WaveFrontID) )+
  theme(legend.position="bottom")+
  geom_line()+
  geom_smooth(method = "lm", formula = y ~ poly(x, 4), se = FALSE)

p <- plot_grid(p1, p2, labels = paste(ACC))
plot.file.name<-paste(file_path_sans_ext(basename(f)),'.png')
save_plot(plot.file.name, p, ncol = 2)
}

