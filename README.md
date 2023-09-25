# vere-copia

A sample set of microservices for a store to demo microservices observability. The apps are 
intentionally implemented in a bad way and there is a Hardship generator that does one of the 
following.

*  Leak a random amount of memory
*  Cause a stack overflow or block the executing thread
*  Leak a database connection 
*  compute a random fibonaci number between 1000 and 1,000,000 

## Zipkin and OpenTracing Support

The main branch uses micrometer and zipkin for instrumenting the spring application
for distributed tracing. The branch `opentracing` uses the opentracing java api instead of zipkin. 

## Setup Wavefront
* Go to Browse --> Proxies in the wavefront UI  
* add a proxy and not the token value 
* add a file called `.env` in the `laptop` diretory with a property `WAVEFRONT_TOKEN=token value from wavefront ui`
* If necessary, update the docker-compose.yaml file to point to the freemium version of the wavefront API (if you have a freemium API token).

## Steps to run the application: 

* run `./mvnw clean package` to build the application and generate `git.propertis` file in the 
 `central-inventory` application, if the `git.properties` is not generated the application 
 will not startup. 
* run `docker-compose up` from the `laptop` directory to setup the postgres database and wavefront proxy that the app services use
* run the central-inventory application
* run the store-inventory application 
* run the store-kiosk
    ```
    cd ./store-kiosk
    npm install
    npm run build
    npm start
    ```
*  go to http://localhost:4200 to access the kiosk

Inspect the store inventory `StoreStockApiController` to learn how to send it requests, it will
in turn call the central-inventory service. Both central-inventory and store-inventory send data
to wavefront.

## Run the store gateway (Optional)

An api gateway based on spring cloud gateway  is available in the `store-gateway` project. It will
proxy traffic to the angular app running with `ng serve`, the store-operations microservice and 
wavefront proxy. it runs on port 7777 visit `localhost:7777` to access the application via the
gateway. 





