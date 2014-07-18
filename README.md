#A simple example how a RESTful sales API could look like#

The aim of this project is to design a simple RESTful API with all the Richardson Maturity levels from 0 to 3 and
caching implemented JAX-RS. There is no DB, no big business logic. It just shows you how to design and implement a
RESTful API.

##How to install##
I recommend to use eclipse to mess around with the project. Clone it from GitHub, start maven build and run the main class 
`ch.romix.restful.sales.Main`. Now can explore the API using a web browser. I would even recommend a REST client like the chrome
app [Advanced Rest Client](https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo?hl=de&utm_source=chrome-ntp-launcher).
The embedded Jetty server runs on port 8080. Start with following URL:

* [http://localhost:8080/api/](http://localhost:8080/api/)

##Domain model##
Currently there are three entities:

* CustomerEntity
* OrderEntity
* PositionEntity

##DTOs##
I introduced DTOs because there is a difference between domain model entities and resources. Entities can contain lists of other 
entities which we don't want to render to the client. Another good reason for DTOs is to have better control over the API. You
can make changes to your entities and don't affect the REST API. I use Dozer to map between entities and DTOs.

##Some principles##

###Each resource has exactly one URL###
There must be a one to one relationship between resource and URL. Otherwise caching becomes impossible or extremely complicated.

###Use HTTP status codes###
Responses use the right HTTP response codes.

###Use HTTP verbs###
GET, PUT, POST, DELETE are used how it is described in the Richardsons Maturity Model Level 2.

###Make API explorable and provide links###
A client only needs to know the entry point of the applications. All other resource URIs and transitions are provided by the server.

###Use HTTP caching features###
At certain places there are different caching strategies implemented. Here is an index for you to quickly look up:

####expires: GET /api/customer/{id}####
This is the simplest caching possibility. The resource behind /api/customer/{id} can be cached for ten seconds. Each request 
within those ten seconds will be cached directly in the client. There is no subsequent server call until the cache has expired.

####Using ETags: GET /api/orders/{id}####
An ETag is usually a hash of the resource. The first response of such a resource contains the ETag in the header. The next 
request to the same response sends the ETag back to the server. The sent ETag is compared with the hash value of the resource
at the server. If both are the same, the server just sends 304 NotModified to the client. The resource must only be included
in the body if there has something changed meaning the server has a more recent representation of the resource than the client.
See OrderREST for implementation details.

####Using Last modification: Not yet implemented####

####Using max-age: Not yet implemented####

#Work in progress!#
This is not finished nor is it bug free. There is always something to do. Feel free to make pull requests.