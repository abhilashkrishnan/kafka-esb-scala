package com.kafka

import java.time.Duration
import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}

class Reader(servers: String, groupId: String, topic: String) extends Consumer {

  val consumer: KafkaConsumer[String,String] = new KafkaConsumer[String, String](createConfig(servers))

  override def createConfig(servers: String): Properties = {

    val config = new Properties();
    config.setProperty("bootstrap.servers", servers)
    config.setProperty("group.id", groupId)
    config.setProperty("enable.auto.commit", "true")
    config.setProperty("auto.commit.interval.ms", "1000")
    config.setProperty("auto.offset.reset", "earliest")
    config.setProperty("session.timeout.ms", "30000")
    config.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    config.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    config
  }

  override def run(producer: Producer): Unit = {
    this.consumer.subscribe(Collections.singletonList(this.topic))

    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(100))

      records.forEach( record => {
        println("Read record value " + record.value() + " from topic " + topic)
        producer.process(record.value())
      })
    }
  }
}
