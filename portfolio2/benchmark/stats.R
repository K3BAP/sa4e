# Set working directory.
setwd("/home/fabian/programming/sa4e/portfolio2/benchmark")

# Read the latencies from the text file
latencies <- as.numeric(readLines("./results.txt"))

# Summary statistics
summary(latencies)

boxplot(latencies, main = "Requests per 15 seconds")

# Compute mean and 95% confidence interval
results <- t.test(latencies, conf.level = 0.95)

mean_latency <- results$estimate
ci_lower <- results$conf.int[1]
ci_upper <- results$conf.int[2]

# Plot the distribution
hist(latencies, main = paste0("Requests per 15 seconds"), xlab = "Number of Requests", breaks = 30)

# Plot mean and confidence interval
abline(v = mean_latency, col = "blue", lwd = 2)
abline(v = ci_upper, col = "skyblue", lwd = 2)
abline(v = ci_lower, col = "skyblue", lwd = 2)
