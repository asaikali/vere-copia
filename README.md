# vere-copia

A sample set of microservices for a store to demo microservices observability. The apps are 
intentionally implemented in a bad way and there is a Hardship generator that does one of the 
following.

*  Leak a random amount of memory
*  Cause a stack overflow or block the executing thread
*  Leak a database connection 
*  compute a random fibonaci number between 1000 and 1,000,000 

Steps to run the application: 
* run `docker-compose up` to setup the postgres database that the app uses
* run the central-inventory application
* run the store-inventory application 
* run the store-kiosk
    ```
    cd ./store-kiosk
    npm install
    npm run build
    npm start
    ```
  - http://localhost:4200 to access the kiosk

Inspect the store inventory `StoreStockApiController` to learn how to send it requests, it will
in turn call the central-inventory service. Both central-inventory and store-inventory send data
to wavefront.





