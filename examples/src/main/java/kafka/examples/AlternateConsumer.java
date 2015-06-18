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

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class AlternateConsumer extends Thread {
	private final KafkaConsumer<Integer, String> consumer;
	private final String topic;
	private final Boolean isAsync = false;

	public AlternateConsumer(String topic, String consumerGroup) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("group.id", consumerGroup);
		properties.put("partition.assignment.strategy", "roundrobin");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<Integer, String>(properties);
		consumer.subscribe(topic);
		this.topic = topic;
	}


	public void run() {
		while (true) {
			ConsumerRecords<Integer, String> records = consumer.poll(0);
			for (ConsumerRecord<Integer, String> record : records) {
				System.out.println("We received message: " + record.value() + " from topic: " + record.topic());
			}
		}
		
		// ConsumerRecords<Integer, String> records = consumer.poll(0);
		// for (ConsumerRecord<Integer, String> record : records) {
		// 	System.out.println("We received message: " + record.value() + " from topic: " + record.topic());
		// }
		// consumer.close();
	}
}