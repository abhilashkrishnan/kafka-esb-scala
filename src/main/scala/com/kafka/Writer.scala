package com.kafka

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class Writer(servers: String, topic: String) extends Producer {

  val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](createConfig(servers))

  override def createConfig(servers: String): Properties = {
    val config = new Properties()
    config.setProperty("bootstrap.servers", servers)
    config.setProperty("acks", "all")
    config.setProperty("retries", "0")
    config.setProperty("batch.size", "1000")
    config.setProperty("linger.ms", "1")
    config.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    config.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    config
  }

  override def process(message: String): Unit = {
    write(message)
  }

  override def write(message: String): Unit = {
    println("Write Message: " + message + " to topic " + topic)
    val record = new ProducerRecord[String, String](topic, message)
    producer.send(record)
  }
}
