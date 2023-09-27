# Tanzu Application Platform Deployment

Follow these steps to deploy Vere Copia to Tanzu Application Platform.  These instructions assume you will deploy all resources
to the same namespace.

## Deploy Wavefront Proxy

- Obtain a Wavefront API token.  You can create one either for the freemium server, a trial account, or a fully functionaly account. 
- Create a Kubernetes secret to store your API token.  You can use the following command replacing `<TOKEN>` with your API token:

```
kubectl create secret generic wavefront-token  --from-literal=token=<TOKEN>
```

- If needed, update the Wavefront URL in the `ConfigMap` in the `deployment-config/common/wavefrontProxy.yaml` file to point to a different wavefront server such as
`https://longboard.wavefront.com/api/`  The default value uses the freemium server.
- Deploy the Wavefront proxy running the following command:

```
kubectl apply -f deployment-config/common/wavefrontProxy.yaml
```

## Deploy Postgres (cloudnative-pg)

Postgres is deployed using a ClassClaim that references a new service class called `cloudnative-pg`.  This class automates the deployment of a Postgres cluster using
`cloudnative-pg`

- Install the `cloudnative-pg` operator by running the following command:

```
kubectl apply -f https://raw.githubusercontent.com/cloudnative-pg/cloudnative-pg/release-1.17/releases/cnpg-1.17.5.yaml
```

- Install the `cloudnative-pg` service class by running the following command:

```
kubectl apply -f deployment-config/tap/cloudNativePGSTK.yaml
```

-- Deploy a default configuration a Postgres `cluster` via a `ClassClaim` by running the following command:

```
kubectl apply -f deployment-config/tap/classClaims.yaml
```


## Deploy Vere Copia Microservices

The application services are built using TAP supply chains by creating cartographer `workloads`.  Workloads using the `server` workload type and
will require manually creating an `Ingress`

- Build and deploy the workloads by running the following command:

```
kubectl apply -f deployment-config/tap/workloads.yaml
```

- Edit the `deployment-config/tap/ingress.yaml` and update the `<YOUR DOMAIN HERE>` placeholder with your own DNS domain.  It assumed that you have alread configured DNS
for your TAP install.
- Deploy the `Ingress` by running the following command:

```
kubectl apply -f deployment-config/tap/ingress.yaml
```

## Access The Web Application

Vere Copia will be available at the following URL replacing `<YOUR DOMAIN>` with the domain name that you used in the `Ingress` object (in the previous section):

```
http://vere-copia.<YOUR DOMAIN>
```