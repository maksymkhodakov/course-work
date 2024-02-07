## zoo-module:

Implemented a secure MVC/REST API functional for managing Zoos across the globe, using microservice approach and message  
brokers suck as: Apache Kafka, RabbitMQ. Also, a modern cloud storage systems like Amazon S3 buckets

Basic stream functionality:
Create page for file animal stream upload (file is saved to separate AWS S3 bucket) and record 'load result'  
(which will have a field by which identify what message broker to choose for processing) stores in db, create page for   
'load result' view

## producer-module: DONE

Basic stream functionality:
Producer will have a schedule task which reads data from db, (load uploaded file from S3 bucket) and based on field of  
'load_result' sends it to appropriate queue/topic to be published by either Kafka ot RabbitMQ. Also provides a REST API  
for manual creation of animal streams 

## consumer-module: DONE

Basic stream functionality:
Accepts 'animal_stream' values from RabbiMQ/Kafka and transforms it to 'animal' entity and persists it in DB  
all processed animals can be seen on existing api endpoints. Accepts both manually requested either via REST API  
of producer module or by file upload. 
