package com.kafka

import java.util.Properties

trait Producer {

  def createConfig(servers: String): Properties
  def process(message: String)
  def write(message: String)
}
