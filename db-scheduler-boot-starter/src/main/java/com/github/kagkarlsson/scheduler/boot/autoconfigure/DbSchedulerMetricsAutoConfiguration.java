package com.github.kagkarlsson.scheduler.boot.autoconfigure;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.boot.actuator.DbSchedulerMetrics;
import io.micrometer.core.instrument.MeterRegistry;
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

    @Bean
    @ConditionalOnMissingBean
    public DbSchedulerMetrics dbSchedulerMetrics(Scheduler scheduler, MeterRegistry meterRegistry) {
        return new DbSchedulerMetrics(scheduler, meterRegistry);
    }
}