# Kafka as Enterprise Service Bus - Scala 

We are modelling a system where customer consults stock price. We are using Apache Kafka as Enterprise Service Bus (ESB) which acts as the middleware.

Enterprise Service Bus (ESB) consists of taking one or more events from an event stream and applying actions over those events. The most common actions performed by ESB are:

* Data Transformation
* Event Handling
* Protocol Conversion
* Data Mapping

In our example we are performing Event Modelling through Apache Kafka as ESB.

The first step in the event modellling is to express the event in English in the following form:

#### Subject-verb-direct object

For this example we are modelling the event **customer consults stock price**.

* The subject in the sentence is **customer**.
* The verb in the sentence is **consults**.
* The direct object in the sentence is **stock price**.

We represent our message in JSON format.

The sample message in JSON format is,

```
{ "event": "CUSTOMER_CONSULTS_STOCKPRICE",
  "customer": {
    "id": "13548310",
    "name": "Abhilash, Krishnan",
    "ipAddress": "185.86.151.11"
  },
  "stock": {
    "name": "GOOG",
    "price": "USD"
  },
    "timestamp": "2018-09-28T08:08:14Z"
}
```

In our example we have Readers or Consumers, Writers or Producers and a Processing Engine.

The process flow can be depicted as:

* Kafka Console Producer -> input-topic
* Reader or Consumer <- input-topic
* Writer or Producer -> output-topic
* Kafka Console Consumer <- output-topic

To run this application do the following steps:

1. Start Zookeeper with the following command:
```
   zookeeper-server.sh zookeeper.properties
```
Minimal zookeeper.properties file entries are,

```
  #the directory where the snapshot is stored.
  dataDir=/kafka/zookeeper
  #the port at which the clients will connect
  clientPort=2181
  #disable the per-ip limit on the number of connections since this is a non-production config
  maxClientCnxns=0
```

2. Start Kafka broker instance running on localhost at port 9093 with the following command:
```
   kafka-server-start.sh server.properties
```
Minimal server.properties file entries are,

```
  broker.id=1
  port=9093
  zookeeper.connect=localhost:2181
  log.dirs=/tmp/kafka/server-1-logs
  offsets.topic.replication.factor=1
```

3. Start Kafka Producer console
```
   kafka-console-producer.sh –broker-list localhost:9093 –topic input-topic
```
4. Start Kafka Consumer console
```
   kafka-console-consumer.bat –bootstrap-server localhost:9093 –topic output-topic
```
5. Run the Scala application.

6. Send the following JSON message from Kafka Producer console opened in Step 3.

```
{ "event": "CUSTOMER_CONSULTS_STOCKPRICE",
  "customer": {
    "id": "13548310",
    "name": "Abhilash, Krishnan",
    "ipAddress": "185.86.151.11"
  },
  "stock": {
    "name": "GOOG",
    "price": "USD"
  },
    "timestamp": "2018-09-28T08:08:14Z"
}
```

7. You will be able to see the JSON message in the Kafka Consumer console opened in Step 4.
