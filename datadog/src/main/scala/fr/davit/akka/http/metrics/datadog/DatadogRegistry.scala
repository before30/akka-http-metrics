/*
 * Copyright 2019 Michel Davit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.davit.akka.http.metrics.datadog

import com.timgroup.statsd.StatsDClient
import fr.davit.akka.http.metrics.core._
import fr.davit.akka.http.metrics.core.scaladsl.server.HttpMetricsSettings

object DatadogRegistry {

  def apply(client: StatsDClient, settings: HttpMetricsSettings = HttpMetricsSettings.default): DatadogRegistry = {
    new DatadogRegistry(settings)(client)
  }
}

/**
  * see [https://docs.datadoghq.com/developers/faq/what-best-practices-are-recommended-for-naming-metrics-and-tags/]
  * @param client
  */
class DatadogRegistry(settings: HttpMetricsSettings)(implicit client: StatsDClient)
    extends HttpMetricsRegistry(settings) {

  override lazy val active: Gauge = new StatsDGauge("akka.http.requests_active")

  override lazy val requests: Counter = new StatsDCounter("akka.http.requests_count")

  override lazy val receivedBytes: Histogram = new StatsDHistogram("akka.http.requests_bytes")

  override lazy val responses: Counter = new StatsDCounter("akka.http.responses_count")

  override lazy val errors: Counter = new StatsDCounter("akka.http.responses_errors_count")

  override lazy val duration: Timer = new StatsDTimer("akka.http.responses_duration")

  override lazy val sentBytes: Histogram = new StatsDHistogram("akka.http.responses_bytes")

  override lazy val connected: Gauge = new StatsDGauge("akka.http.connections_active")

  override lazy val connections: Counter = new StatsDCounter("akka.http.connections_count")

}
