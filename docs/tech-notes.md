## zoo-module:

### Short description:
Implemented a secure MVC/REST API functional for managing Zoos across the globe, using microservice approach and message  
brokers suck as: Apache Kafka, RabbitMQ. Also, a modern cloud storage systems like Amazon S3 buckets

## Tech notes:
* App support classic security (email + password) for MVC and both classic and OAuth2 for REST API.
* Both Kafka and RabbitMQ functionality support DLT/DLX handling approach
* REST API supports authorization and provide limited access for some endpoints
* Services and some endpoints are covered by unit tests

### Basic functionality
App provides admin country - zoom - animal relation handling by providing CRUD operations, as well as statistics about  
current data in DB. Also provides possibility to manage entities relations. REST API functionality is also provided and   
secured by OAuth2 security protocol.

### Basic stream functionality (Microservice part):
Create page for file animal stream upload (file is saved to separate AWS S3 bucket) and record 'load result'  
(which will have a field by which identify what message broker to choose for processing) stores in db, create page for   
'load result' view

## producer-module:

### Basic stream functionality:
Producer will have a schedule task which reads data from db, (load uploaded file from S3 bucket) and based on field of  
'load_result' sends it to appropriate queue/topic to be published by either Kafka ot RabbitMQ. Also provides a REST API  
for manual creation of animal streams 

## consumer-module:

### Basic stream functionality:
Accepts 'animal_stream' values from RabbiMQ/Kafka and transforms it to 'animal' entity and persists it in DB  
all processed animals can be seen on existing api endpoints. Accepts both manually requested either via REST API  
of producer module or by file upload.

#### Docs Google Drive
[Google Drive](https://drive.google.com/drive/folders/1Tygdd6vEOOClsuFoUrV8GDmYmt-ArCEc?usp=drive_link)
