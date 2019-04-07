# Totally-Ordered-Multicasting-using-Lamport's-Algorithm

Implementation, Learnings and Issues:

Implemented Totally Ordered Multicasting using the Lamport’s algorithm. For a pair of processes Pi and Pj, Total Ordering of messages and for any given pair of messages m and n., i.e. send to both of these processes implies that if a message m is received before n at process Pi then message m is also received before n at process Pj. The message order delivery is same for all processes. Multicasting of a message means sending the message to a group of receivers. Multicasting is a one to many communication technique. Hence to achieve total ordering for multicast messages we need to assure that for any set of processes receiving the multicast message, the order of the delivered message is same.

Here the sender sends the messages to multiple receivers and once the message is received it is pushed into a message buffer. Then these messages are ordered according to the timestamp and multicasted in the same order. If and only if the message is at the head of the queue in the message buffer., then the message is delivered. The goal was to make sure that all processes print the message in the same order.

We were able to understand the concept of how totally ordered multicast works along with Lamport’s algorithm in real time.

The issues faced were:
1.	Lamport timestamps do not capture casuality.
2.	One cannot compare the timestamps of two events to determine their precedence relationship with Lamport’s clock.
3.	 Simple integer clock cannot order both events within a process and events in different processes.

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
* Open the projects in the eclipse IDE.
*	Run each of the Java files one after the other.
*	After all three are running, press enter in each of the three consoles to start the multicasting.

Build All JAVA files and Run them as “Java Applications”
Node1

Node2

Node3

In 3 different consoles to see the output while they are multicasting the messages with Lamport clock implementation

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

