package com.github.kagkarlsson.scheduler.boot.autoconfigure;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.boot.actuator.DbSchedulerMetrics;
import com.github.kagkarlsson.scheduler.stats.MicrometerStatsRegistry;
import com.github.kagkarlsson.scheduler.stats.StatsRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = MetricsAutoConfiguration.class)
@ConditionalOnClass({Scheduler.class, MeterRegistry.class})
@ConditionalOnBean({Scheduler.class, MeterRegistry.class})
public class DbSchedulerMetricsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DbSchedulerMetricsAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public DbSchedulerMetrics dbSchedulerMetrics(Scheduler scheduler, MeterRegistry meterRegistry) {
        return new DbSchedulerMetrics(scheduler, meterRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public MicrometerStatsRegistry micrometerStatsRegistry(Scheduler scheduler, MeterRegistry meterRegistry) {
        log.debug("Creating MicrometerStatsRegistry for db-scheduler");
        // For now, create with empty task list - the tasks will be added dynamically
        return new MicrometerStatsRegistry(meterRegistry, java.util.Collections.emptyList());
    }
}