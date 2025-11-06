package com.github.kagkarlsson.scheduler.boot.autoconfigure;

import com.github.kagkarlsson.scheduler.boot.actuator.DbSchedulerHealthIndicator;
import com.github.kagkarlsson.scheduler.Scheduler;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({Scheduler.class, HealthIndicator.class})
@ConditionalOnBean(Scheduler.class)
public class DbSchedulerActuatorAutoConfiguration {

    @Bean
    @ConditionalOnEnabledHealthIndicator("dbscheduler")
    @ConditionalOnMissingBean(name = "dbSchedulerHealthIndicator")
    public DbSchedulerHealthIndicator dbSchedulerHealthIndicator(Scheduler scheduler) {
        return new DbSchedulerHealthIndicator(scheduler);
    }
}