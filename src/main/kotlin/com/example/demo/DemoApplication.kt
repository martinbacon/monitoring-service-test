package com.example.demo

import io.micrometer.core.instrument.Metrics
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MetricsContributor::class)
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


class MetricsContributor : AbstractHealthIndicator() {

    private val log = LoggerFactory.getLogger(MetricsContributor::class.java)
    private val counter = Metrics.counter("health.check")

    override fun doHealthCheck(builder: Health.Builder) {
        log.info("MetricsContributor doing a health check #${counter.count()}")
        counter.increment()
        builder.up()
    }
}