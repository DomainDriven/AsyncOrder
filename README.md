# AsyncOrder(비동기 주문)
## Overview
![Overview](https://user-images.githubusercontent.com/16472109/28051397-08a48ff8-663f-11e7-87e7-c8f5b14c0631.png)
![Overview2](https://user-images.githubusercontent.com/16472109/28051418-2084df10-663f-11e7-8259-b436c64a4a0f.png)

## Getting Start

* Inventory Service 실행 
```java
# inventory-service module
mvn clean spring-boot:run
```

* Payment Service 실행
```java
# payment-service module
mvn clean spring-boot:run
```

* Notification Service 실행
```java
# notification-service module
mvn clean spring-boot:run
```

* Order Service 실행
```java
# order-service module
mvn clean spring-boot:run
```

## 사용 기술
* SDK
  * JDK 8u101
* 이벤트 기반 프로그래밍
  * [RxJava](https://github.com/ReactiveX/RxJava) 1.1.6
* MOM
  * [Apache Kafka](https://kafka.apache.org/) 0.10.1.0
* Persistence API
  * [JPA2](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)/[Hibernate](http://hibernate.org/) 4.3.10.Final
* Database
  * [H2 Database](http://www.h2database.com/html/main.html)
