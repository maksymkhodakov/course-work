## zoo-module:  

Create page for file animal stream upload (file is saved to separate AWS S3 bucket) and record 'load result'  
(which will have an field by which identify what message broker to choose for processing) stores in db, create page for   
'load result' view

## producer-module:

Producer will have a schedule task which reads data from db, (load uploaded file from S3 bucket) and based on field of  
'load_result' sends it to appropriate queue/topic to be published by either Kafka ot RabbitMQ  

## consumer-module:

Accepts 'animal_stream' values from RabbiMQ/Kafka and transforms it to 'animal' entity and persists it in DB  
all processed animals can be seen on existing api endpoints
