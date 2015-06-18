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

public class KafkaConsumerProducerDemo implements KafkaProperties
{
  public static void main(String[] args)
  {
    final boolean isAsync = args.length > 0 ? !args[0].trim().toLowerCase().equals("sync") : true;
    final String groupID = args.length > 1 ? args[1] : "testGroup";

    Producer producerThread = new Producer("multiplepartitions", isAsync);
    producerThread.start();

    // newKafkaConsumer consumerThread = new newKafkaConsumer("multiplepartitions", groupID);
    // consumerThread.start();

    // Consumer consumerThread = new Consumer("multiplepartitions");
    // consumerThread.start();

    // ExampleThreadedConsumer consumerThread = new ExampleThreadedConsumer("localhost:2181", "group1", "multiplepartitions");
    // consumerThread.run(6);

    myThreadedConsumer consumerThread = new myThreadedConsumer("group1", "multiplepartitions");
    consumerThread.run(6);
  }
}
