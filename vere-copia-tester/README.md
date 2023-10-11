# vere-copia-tester

The vere-copia-tester application is stand alone Spring Shell based application that can exercise API of the Vere Copia application.  It is
also used as part of a larger harness to execute of multitude of test scenarios against various deployment configurations (cloud platforms, 
deployment topology, database configuration, etc).  At its simplest, it is composed of a collection of primitives that can execute a single transaction
against a specific Vere Copia API resource.  At the opposite end of the spectrum, it can run pre-configured simulations such as a shopper and 
inventory management roles at various load levels.  It gathers various client metrics that can be used for comparison in the test
harness scenarios.  Lastly, it also publishes request traces and its metrics to *Tanzu Aria Operations for Applications* (i.e. Wavefront)
for enhanced observability.

## Application Setup

The application can be run in an IDE or directly from the command line.  For Wavefront integration, you will first need to configure and launch 
the Wavefront proxy container as described in the [Local Device](../doc/LocalDeviceDeployment.md) deployment.

The application executes transactions against a service end point defined in the `vere-copia/src/main/resources/application.yaml` file.  By default,
it is configured to execute transactions against a local deployment.  To run the application against a different deployment, simply update the
`identifier` field in the `application.yaml` file.

```
vere-copia:
  service:
    identifier: http://localhost:7777
```

To run the application, simply run the following commands from the root of the vere-copia directory.

    ```
    cd ./vere-copia-tester
    ./mvnw spring-boot:run
    ```
    
## Application Commands

The application is built on top of Spring Shell and incorporates all of the Spring Shell default commands.  To list all of the available commands, execute the 
following command in the vere-copia-test:

```
help
```

To get help on an specific command, you simply run `help <command>`.

### Transaction Primitives

The tester includes the following transaction primitives:

- **updateProductInventoryLevel:** Updates the inventory level for a given project sku at the "Local" store.
- **search:** Perform a store search by a product name.
- **searchStoreAvailability:** Searches all stores for the availability for a given product sku.
- **purchaseProductInventory:** Purchase an given amount of a product sku at the "Local" store resulting in a decrease of inventory.
- **receiveProductInventory:** Receives new inventory resulting in additive an inventory level for a given project sku at the "Local" store.

### Simulations

The test includes the following simulation commands:

- **startStoreOpsSimulator:** Run a background simulations of shoppers and back office inventory operations
- **stopStoreOpsSimulator:** Stops a running background simulations of shoppers and back office inventory operations

Simulations can be run at different load levels of `light`, `medium`, and `heavy`.  In addition, you can set a `verbose` flag to true to output 
transaction logging in real time (by default, the simulation runs silently in the background).

### Statistics Commands

The testing application keeps track of what it thinks the store inventory should be using counter metrics (these are published to Wavefront)
per product sku.  It also keeps track a count of successful transactions per `request target`.  A `request target` is simply the logical node name
where the `store-operation` service is running.  For example, in kubernetes, the `request target` is the pod name.  

At any time, you can view or export the current statistic counts of the application using the following commands:

- **exportStats:** Exports captured stats to a local JSON file
- **getActualInventory:** Displays actual inventory count stored in the database.  This can be compared against the Counted Inventory to determine database consistency.
- **getCountedInventory:** Displays the expected inventory count in the local store based on purchases and restocks made in this test app
- **printStats:** Prints captured stats in raw JSON format

