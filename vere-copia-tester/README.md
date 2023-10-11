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
