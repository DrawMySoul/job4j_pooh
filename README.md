# Pooh JMS Project

## Contents

- [Description](#description)
    - [Queue](#queue)
    - [Topic](#topic)
- [Build & Run](#build-and-run)
- [Technologies](#technologies)
- [Contacts](#contacts)

## Description

In this project, an analog of an asynchronous queue is implemented.
The application launches a socket and waits for clients. The cURL
(https://curl.se/download.html) is used as a client. HTTP is used as a protocol.
The server is a message exchange system based on thread-safe classes from
the java.util.concurrent package. The program operates in two modes: Queue and Topic.

### Queue

- Sender
    - The sender sends a request to add data specifying the queue (weather)
      and parameter value (temperature=18). The message is added to the end of the
      queue. If the queue does not exist in the service, a new one should be created and
      the message added to it

- Receiver
    - The receiver sends a request to retrieve data specifying the queue.
      The message is retrieved from the beginning of the queue and deleted.
    - If multiple receivers subscribe to the queue, they receive messages
      from the queue in a round-robin fashion.
    - Each message in the queue can be consumed by only one receiver.

***Examples of requests:***

POST request adds items to the weather queue.

- queue indicates the "queue" mode.
- weather points to the name of the queue.

``curl -X POST -d "temperature=18" http://localhost:9000/queue/weather``

The GET request should retrieve elements from the weather queue

``curl -X GET http://localhost:9000/queue/weather``

**Response:** temperature=18

---

### Topic

- Sender
    - The sender sends a request to add data specifying the topic
      (weather) and parameter value (temperature=18). The message is added to
      the end of each individual receiver's queue.
      If the topic does not exist in the service, the data is ignored.

- Receiver
    - The receiver sends a request to retrieve data specifying the topic.
      If the topic does not exist, a new one is created. If the topic exists, the message is retrieved
      from the beginning of the individual receiver's queue and removed.
    - When the receiver first retrieves data from the topic, an individual empty queue is created for
      them. All subsequent messages from senders with data for this topic are also added to this queue.
    - Thus, in the "topic" mode, each consumer will have a unique queue with data, unlike the "queue"
      mode where all consumers receive data from the same queue.

***Examples of requests:***

*Sender*

``POST /topic/weather -d "temperature=18"``

- topic indicates the "topic" mode
- weather points to the name of the topic

*Receiver*

``GET /topic/weather/1``

1 - receiver's ID

**Response:** temperature=18

## Build and Run

### Terminal

1. Build jar with Maven ``mvn instal``
2. Run jar file ``java -jar target/pooh-jms.jar``

### IDE

Directory``src/main/java/`` and file ``ru.job4j.pooh.PoohServer``

## Technologies

- Java 17
- Maven 4.0
- Concurrency
- Sockets
- Java IO/NIO
- JUnit 5.8.2
- AssertJ 3.23.1
- cURL 7.78.0

## Contacts

<a href="https://t.me/draw_my_soul">
<img src="https://cdn-icons-png.flaticon.com/512/5968/5968804.png" width="30px">
</a>&nbsp;&nbsp;
<a href="mailto:michael.lobov13@gmail.com">
<img src="https://ssl.gstatic.com/ui/v1/icons/mail/rfr/logo_gmail_lockup_default_1x_r5.png">&nbsp;
</a>michael.lobov13@gmail.com
