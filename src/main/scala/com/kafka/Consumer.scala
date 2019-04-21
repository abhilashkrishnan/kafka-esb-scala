package com.kafka

import java.util.Properties

trait Consumer {

  def createConfig(servers: String): Properties
  def run(producer: Producer): Unit
}