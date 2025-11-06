package com.github.kagkarlsson.scheduler.boot.actuator;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.SchedulerState;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class DbSchedulerHealthIndicator implements HealthIndicator {

    private final Scheduler scheduler;

    public DbSchedulerHealthIndicator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Health health() {
        SchedulerState state = scheduler.getSchedulerState();
        if (state.isStarted() && !state.isShuttingDown() && !state.isPaused()) {
            return Health.up()
                    .withDetail("started", state.isStarted())
                    .withDetail("shuttingDown", state.isShuttingDown())
                    .withDetail("paused", state.isPaused())
                    .build();
        } else {
            return Health.down()
                    .withDetail("started", state.isStarted())
                    .withDetail("shuttingDown", state.isShuttingDown())
                    .withDetail("paused", state.isPaused())
                    .build();
        }
    }
}