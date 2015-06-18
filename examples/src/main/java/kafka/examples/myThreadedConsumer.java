/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kafka.examples;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class myThreadedConsumer {
	private final KafkaConsumer<Integer, String> consumer;
	private final String topic;
	private ExecutorService executor;

	public myThreadedConsumer (String groupID, String topic) {
		consumer = new KafkaConsumer<Integer, String>(createConfigs(groupID));
		consumer.subscribe(topic);
		this.topic = topic;
	}

	public void shutdown() {
		if (consumer != null) { consumer.close(); }
		if (executor != null) { executor.shutdown(); }
		try {
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				System.out.println("Timed out waiting for consumer threads to shutdown, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted during shutdown, exit unclean.");
		}
	}

	public static Properties createConfigs (String groupID) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		// properties.put("bootstrap.servers", "localhost:2181");
		properties.put("group.id", groupID);
		properties.put("partition.assignment.strategy", "roundrobin");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return properties;
	}

	public void run (int num_threads) {
		ConsumerRecords<Integer, String> records = consumer.poll(0);
		executor = Executors.newFixedThreadPool(num_threads);

		int threadNum = 0;

		for (ConsumerRecord<Integer, String> record : records) {
			executor.submit(new myThreadedRunner(record, num_threads));
			threadNum++;
		}
	}
}