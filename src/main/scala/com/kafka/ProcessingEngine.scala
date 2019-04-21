package com.kafka

object ProcessingEngine {

  def main(args: Array[String]): Unit = {

    val servers: String = "localhost:9093"
    val groupId: String = "stock"
    val sourceTopic: String = "input-topic"
    val targetTopic: String = "output-topic"

    val reader: Reader = new Reader(servers, groupId, sourceTopic)

    val writer: Writer = new Writer(servers, targetTopic)
    reader.run(writer)
  }
}
