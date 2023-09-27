# Local Device Deployment

## Setup Wavefront
* Go to Browse --> Proxies in the wavefront UI  
* add a proxy and not the token value 
* update the  `.env` file in the `laptop` directory replacing `<UPDATE ME>` with the token value from wavefront the ui
* If necessary, update the `docker-compose.yaml` file to point to the freemium version of the wavefront API (if you have a freemium API token).

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

