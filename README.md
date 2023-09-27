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

## Application Deployment

This application contains instructions and configuration to deploy it to multiple platforms.  These include:

- [Local Device](doc/LocalDeviceDeployment.md) - Use this deployment when first learning how the application works.
- Tanzu Application Service (TAS)
- Amazon ECS
- Azure Spring Apps Enterprise (ASA-E)
- Google Cloud Run 
- Tanzu App Engine
- [Tanzu Application Platform (TAP)](doc/TanzuAppicationPlatformDeployment.md)
- Tanzu TAS adapter
- Open Shift
- *Vanilla* Kubernetes
  - Google Kubernetes Engine (GKE)
  - Azure Kubernetes Service (AKS)
  - Elastic Kubernetes Service (EKS)
  - DYI Kubernetes
