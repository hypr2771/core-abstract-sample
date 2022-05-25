<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Explanations](#explanations)
  - [Notes](#notes)
  - [What are we talking about](#what-are-we-talking-about)
  - [How we address the issue](#how-we-address-the-issue)
  - [Make DTOs the basis of your application](#make-dtos-the-basis-of-your-application)
  - [Make your controller agnostic of the core](#make-your-controller-agnostic-of-the-core)
    - [Core abstraction](#core-abstraction)
    - [Implementing your core](#implementing-your-core)
  - [Versioning APIs](#versioning-apis)
- [Validating the application](#validating-the-application)
  - [Version 1](#version-1)
  - [Version 2](#version-2)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Explanations

## Notes

DTO term used in this post refers only to the `Data Transfer Object`, and not value objects, or any other objects carrying anything else than data initialized to avoid expensive _lazy_ computation.
A Projection is a DTO.

## What are we talking about

We often encounter hard times refactoring and upgrading our libraries.
Most of the time it goes smoothly and upgrading is straightforward: adding few flags, removing others, refactoring methods are always pretty easy and impactless tasks (with a well tested code base).
But when it comes to changing paradigm, refactoring domains, changing entity relationships,... it is where things becomes messy:

- you will most likely duplicate code to satisfy your legacy clients
- you will have to create new transformers (domain to DTO) or change your DTOs
- you may have to change the whole stack, or create new endpoints using your new code

## How we address the issue

This issue is really important, for we often change technology, sometimes for huge improvement, sometimes for clarity purpose, sometimes to respond to new business needs,...

Now, it is important to note that right now, this is not an issue we _fixed_, but a simple draft on how to do it.

## Make DTOs the basis of your application

As a REST API developer, what are you if no one consumes your API?
You are bound to provide your customer with data _first_.
Does anyone but you even cares whether you are using Hibernate or jooq or plain JDBC, as long as they have their DTOs?
Do they even care that your table `SHOE` as a join table to `SIZE` called `EXISTS_IN`?

We can rapidly notice that your DTO is the core of your REST API, way before your domain or business services.

The first suggestion of this post is then to create your DTO _before_ your domain, and let it drive your design.

<details>
<summary><b>The shoe example</b></summary>

<details>
<summary>Maven configuration</summary>

```pom.xml
  <parent>
    <artifactId>demo</artifactId>
    <groupId>com.example</groupId>
    <version>1.0</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>dto</artifactId>
```

</details>

<details>
<summary>File system</summary>

![image](https://user-images.githubusercontent.com/6195718/72352886-6187fe80-36e3-11ea-997f-f246f25b1c8c.png)

</details>

</details>

## Make your controller agnostic of the core

An important part of the idea is also that your controllers will be de facto _static_ once it will be consumed by at least one client.
Indeed, you cannot control the workload of your clients, hence you will have to adapt to them, and guarantee them your service for as long as you judge fit (in practice, since money drives us, if the client is worth to keep, we all know the controller will remain untouched as long as this client does not migrate).

![image](https://user-images.githubusercontent.com/6195718/72347878-647df180-36d9-11ea-97c7-4a618b08d464.png)

This means that you will need your controller to be agnostic (as for the DTOs) of the business implementation. Once again, do your customer care if you are using a new table which requires them to update to a new API, if they do not need it anyway?

So the whole point here is again to make your controller implementation agnostic of the core implementation.

<details>
<summary><b>The shoe example</b></summary>

<details>
<summary>Maven configuration</summary>

```pom.xml
  <parent>
    <artifactId>demo</artifactId>
    <groupId>com.example</groupId>
    <version>1.0</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>controller</artifactId>

  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>dto</artifactId>
      <version>${parent.version}</version>
    </dependency>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>core</artifactId> <!-- Explanations are coming -->
      <version>${parent.version}</version>
    </dependency>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>core-legacy</artifactId> <!-- Explanations are coming -->
      <version>${parent.version}</version>
    </dependency>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>core-new</artifactId> <!-- Explanations are coming -->
      <version>${parent.version}</version>
    </dependency>
  </dependencies>
```

</details>

<details>
<summary>File system</summary>

![image](https://user-images.githubusercontent.com/6195718/72353056-ac097b00-36e3-11ea-91cc-a0448baeb747.png)

</details>

<details>
<summary><code>ShoeController</code></summary>

```java
@Controller
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class ShoeController {

  private final ShoeFacade shoeFacade;

  @GetMapping(path = "/search")
  public ResponseEntity<Shoes> all(ShoeFilter filter, @RequestHeader BigInteger version){

    return ResponseEntity.ok(shoeFacade.get(version).search(filter));

  }

}
```

</details>

</details>

### Core abstraction

Well, we all know your controller needs to call some business code, whether to access direct domain data, or to compute some complex operations.
So the whole point of making your controller "business-agnostic" is to create a core abstraction, depending only on DTOs.
The contracts of this implementation should be something like:

> Given the input DTO, return an output DTO.

As simple as that. This way, your core implementation indeed depends only on DTOs.

![image](https://user-images.githubusercontent.com/6195718/72345453-20d4b900-36d4-11ea-86a6-40823269f0dc.png)

<details>
<summary><b>The shoe example</b></summary>

<details>
<summary>Maven configuration</summary>

```pom.xml
  <parent>
    <artifactId>demo</artifactId>
    <groupId>com.example</groupId>
    <version>1.0</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>core</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>dto</artifactId>
      <version>1.0</version>
    </dependency>
  </dependencies>
```

</details>

<details>
<summary>File system</summary>

![image](https://user-images.githubusercontent.com/6195718/72353336-34881b80-36e4-11ea-8228-93e4a5cd4dbc.png)

</details>

<details>
<summary><code>ShoeFacade</code></summary>

```java
@Component
public class ShoeFacade {

  private Map<BigInteger, ShoeCore> implementations = new HashMap<>();

  public ShoeCore get(BigInteger version){
    return implementations.get(version);
  }

  public void register(BigInteger version, ShoeCore implementation){
    this.implementations.put(version, implementation);
  }

}
```

</details>

<details>
<summary><code>ShoeCore</code></summary>

```java
public interface ShoeCore {

  Shoes search(ShoeFilter filter);

}
```

</details>

<details>
<summary><code>AbstractShoeCore</code></summary>

```java
public abstract class AbstractShoeCore implements ShoeCore {

  @Autowired
  private ShoeFacade shoeFacade;

  @PostConstruct
  void init(){

    val version = Optional.ofNullable(this.getClass().getAnnotation(Implementation.class))
                          .map(Implementation::version)
                          .orElseThrow(() -> new FatalBeanException("AbstractShoeCore implementation should be annotated with @Implementation"));

    shoeFacade.register(version, this);

  }

}
```

</details>

<details>
<summary><code>Implementation</code></summary>

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Implementation {

  int version();

}
```

</details>

</details>

### Implementing your core

Okay, so we have an abstract core, how do we register cores implementations so that your built application can use them?

Once again, it is quite simple: create a factory in the core abstraction, and let your core implementation register against this factory.

Once you do that, your business implementation will be picked by the factory, instead of the controllers directly. Controllers will only need to integrate the factory.

<details>
<summary><b>The shoe example</b></summary>

<details>
<summary>Maven configuration</summary>

```pom.xml
  <parent>
    <artifactId>demo</artifactId>
    <groupId>com.example</groupId>
    <version>1.0</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>core-new</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>core</artifactId>
      <version>1.0</version>
    </dependency>
  </dependencies>
```

</details>

<details>
<summary>File system</summary>

![image](https://user-images.githubusercontent.com/6195718/72353502-76b15d00-36e4-11ea-893e-d0c18bd3fcc2.png)

</details>

</details>

## Versioning APIs

Following a [REST API versioning guide](https://www.baeldung.com/rest-versioning), we think versioning using content negotiation is pretty relevant for our purpose:

- we will be able to return different DTO for the same endpoint
- we may simply fetch the `version` from the content type, and fetch our core implementation accordingly
- our controllers will not depend on core implementation, but on the factory providing us core implementation

# Validating the application

APIs can be accessed via Swagger on the URL:
http://localhost:8080/swagger-ui/index.html

To run the application, you can run the following command in the root folder of the project:

```shell script
mvn clean package && \
  java -jar controller/target/controller-1.0.jar
```

## Version 1

**Shoe API**

To test version 1, you can call:

```shell script
curl -X GET "http://localhost:8080/shoes/search" -H "version: 1"
```

which should answer (see `com.example.demo.core.ShoeCoreLegacy.search`):

```json
{"shoes":[{"name":"Legacy shoe","size":1,"color":"BLUE"}]}
```

**Stock API**

Since no prior version of Stock API exists. The API added is version 1.

```shell script
curl -X GET "http://localhost:8080/stock" -H "version: 1"
```

which should answer (see `com.example.demo.core.StockCore.getStockState`):

```json
{"state":"EMPTY","shoes":[{"size":40,"color":"BLUE","quantity":0},{"size":45,"color":"BLACK","quantity":0}]}
```

```shell script
curl -X 'PATCH' 'http://localhost:8080/stock' -H 'version: 1' -H 'Content-Type: application/json' -d '{"name": "Kalenji","size": 42,"color": "BLACK","quantity": 10}'
```

which should answer (see `com.example.demo.core.StockCore.updateStock`):

```json
{"color":"BLACK","size":42,"quantity":10}
```


## Version 2

To test version 2, you can call:

```shell script
curl -X GET "http://localhost:8080/shoes/search" -H "version: 2"
```

which should answer (see `com.example.demo.core.ShoeCoreNew.search`):

```json
{"shoes":[{"name":"New shoe","size":2,"color":"BLACK"}]}
```

# DataBase
An H2 database is embedded with the application. It is launched at the start of the latter and it is fed by default by the following data:

```json
[
  {"name": "Quechua", "color": "BLACK", "size": 44, "quantity": 5},
  {"name": "Quechua", "color": "BLACK", "size": 43, "quantity": 4},
  {"name": "Kalenji", "color": "BLUE", "size": 37, "quantity": 2},
  {"name": "Kalenji", "color": "BLACK", "size": 42, "quantity": 3},
  {"name": "Newfeel", "color": "BLUE", "size": 40, "quantity": 1},
  {"name": "Newfeel", "color": "BLACK", "size": 44, "quantity": 0}
] 

```

DataBase can be accessible using the following URL:
http://localhost:8080/h2-console

![database-properties](DatabaseProperties.png?raw=true)

Username: sa

Password: password

# Conclusion

We can see that both result are structurally identical, while the code is obviously different.

This is indeed useful, since we can use almost any paradigm, segregate our code versions and eventually just drop one when implementation becomes unused and/or deprecated.
