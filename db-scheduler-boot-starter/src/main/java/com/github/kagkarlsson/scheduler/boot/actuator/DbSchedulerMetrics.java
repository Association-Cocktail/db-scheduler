package com.github.kagkarlsson.scheduler.boot.actuator;

import com.github.kagkarlsson.scheduler.Scheduler;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

public class DbSchedulerMetrics implements MeterBinder {

    private final Scheduler scheduler;

    public DbSchedulerMetrics(Scheduler scheduler, MeterRegistry meterRegistry) {
        this.scheduler = scheduler;
        bindTo(meterRegistry);
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        Gauge.builder("dbscheduler.status", scheduler, s -> s.getSchedulerState().isStarted() ? 1.0 : 0.0)
                .description("Current status of the db-scheduler (1=started, 0=stopped)")
                .register(registry);

        Gauge.builder("dbscheduler.shuttingdown", scheduler, s -> s.getSchedulerState().isShuttingDown() ? 1.0 : 0.0)
                .description("Whether the db-scheduler is shutting down (1=yes, 0=no)")
                .register(registry);

        Gauge.builder("dbscheduler.paused", scheduler, s -> s.getSchedulerState().isPaused() ? 1.0 : 0.0)
                .description("Whether the db-scheduler is paused (1=yes, 0=no)")
                .register(registry);
    }
}