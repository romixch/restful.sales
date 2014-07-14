#A simple example how a RESTful sales API could look like#

The aim of this project is to design a simple RESTful API with all the Richardson Maturity levels from 0 to 3 and
caching implemented JAX-RS. There is no DB, no big business logic. It just shows you how to design and implement a
RESTful API.

##How to install##
I recommend to use eclipse to mess around with the project. Clone it from GitHub, start maven build and run the main class 
`ch.romix.restful.sales.Main`. Now can explore the API using a web browser. I would even recommend a REST client like the chrome
app [Advanced Rest Client](https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo?hl=de&utm_source=chrome-ntp-launcher).
The embedded Jetty server runs on port 8080. Start with one of the following URLs:

* [http://localhost:8080/api/orders/](http://localhost:8080/api/orders/)
* [http://localhost:8080/api/customers/](http://localhost:8080/api/customers/)

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

###Use http caching features###
At certain places there are different caching strategies implemented. Here is an index for you to quickly look up:

####expires: GET /api/orders####
This is the simplest caching possibility. The resource behind /api/orders can be cached for five seconds. Each request within those
five seconds will be cached directly in the client. There is no subsequent server call until the cache has expired.

####Using ETags: Not yet implemented####

####Using Last modification: Not yet implemented####

####Using max-age: Not yet implemented####

#Work in progress!#
This is not finished nor is it bug free. There is always something to do. Feel free to make pull requests.