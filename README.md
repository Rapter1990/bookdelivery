# Case Study - Book Delivery

<p align="center">
    <img src="screenshots/book_delivey_main_image.png" alt="Main Information" width="700" height="500">
</p>

### 📖 Information

<ul style="list-style-type:disc">
  <li><b>Book Delivery</b> is a kind of Spring Boot with covering important and useful features</li> 
  <li>Here is the explanation of the example
       <ul>There are 2 roles named <b>Admin</b> and <b>Customer</b></ul>
       <ul><b>Admin</b> handles with creating book, updating stock of book and updating book information, getting all books and book by id, showing statistic by customer's order and all orders while <b>Customer</b> tackle with creating order, showing own orders, getting all books and book by id except for their own authentication process covering the process of register, login, refresh token and logout</ul>
  </li>
</ul>

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Request Body</th>
      <th>Header</th>
      <th>Valid Path Variable</th>
      <th>No Path Variable</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/auth/register</td>
      <td>Register of both Admin and Customer</td>
      <td>SignupRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/auth/login</td>
      <td>Login of both Admin and Customer</td>
      <td>LoginRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/auth/refreshtoken</td>
      <td>Refresh Token of both Admin and Customer</td>
      <td>TokenRefreshRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/auth/logout</td>
      <td>Logout of both Admin and Customer</td>
      <td></td>
      <td>token</td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/books</td>
      <td>Create Book from Admin</td>
      <td>BookCreateRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>PUT</td>
      <td>/api/v1/books/stock-amount/{bookId}</td>
      <td>Update Stock of Book from Admin</td>
      <td>BookUpdateStockRequest</td>
      <td></td>
      <td>bookId</td>
      <td></td>
  <tr>
  <tr>
      <td>PUT</td>
      <td>/api/v1/books/{bookId}</td>
      <td>Update Book from Admin</td>
      <td>BookUpdateRequest</td>
      <td></td>
      <td>bookId</td>
      <td></td>
  <tr>
  <tr>
      <td>PUT</td>
      <td>/api/v1/books/{bookId}</td>
      <td>Update Book from Admin</td>
      <td>BookUpdateRequest</td>
      <td></td>
      <td>bookId</td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/books/{bookId}</td>
      <td>Get Book by Id from Admin and Customer</td>
      <td></td>
      <td></td>
      <td>bookId</td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/books/{bookId}</td>
      <td>Get Books from Admin and Customer</td>
      <td>PaginationRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/customers</td>
      <td>Create Customer from Admin</td>
      <td>CustomerCreateRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/orders</td>
      <td>Create Order from Customer</td>
      <td>CreateOrderRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/orders/{orderId}</td>
      <td>Get Order by Id from Admin and Customer</td>
      <td></td>
      <td></td>
      <td>orderId</td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/orders/{orderId}</td>
      <td>Get Orders by Customer Id from Admin and Customer</td>
      <td>PaginationRequest</td>
      <td></td>
      <td>customerId</td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/orders/between-dates</td>
      <td>Get Orders by between dates from Admin and Customer</td>
      <td>PaginationRequest</td>
      <td></td>
      <td>customerId</td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/orders/between-dates</td>
      <td>Get Orders by between dates from Admin and Customer</td>
      <td>PaginationRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/statistics/{customerId}</td>
      <td>Get Order Statistics By CustomerId from Admin and Customer</td>
      <td>PaginationRequest</td>
      <td></td>
      <td>customerId</td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/statistics</td>
      <td>Get Order Statistics from Admin</td>
      <td>PaginationRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
</table>



### Technologies

---
- Java 17
- Spring Boot 3.0
- Restful API
- Lombok
- Maven
- Junit5
- Mockito
- Integration Tests
- Docker
- Docker Compose
- CI/CD (Github Actions)
- Prometheus and Grafana
- Postman
- Actuator
- Swagger 3


### How to test Pessimistic Lock using ApacheHttpServer?
We implemented a pessimistic lock mechanism to avoid scenarios where multiple users attempt to order books simultaneously,
ensuring that their actions are synchronized properly to prevent data conflicts.
So with a pessimstic lock, if multiple users attempt to order one last stock of a book, only one of them will be able to order.

### Install Apache 2 on Linux & Test
Installation

```
sudo apt-get install apache2
```

To test, you need to make requests to the create order endpoint.
So you're going to need a valid payload json which is shown below.
(Do not forget to change the bookId with a valid one)

```json
{
  "orderDetailSet": [
    {
      "bookId": "3a0e7efc-1e32-404c-9a70-d9fb63262c6e",
      "amount": 1
    }
  ]
}
```

Now we can test the create order endpoint, and see if the locking mechanism works

- <b>n</b> represents the number of requests to be made to the URL specified
- <b>c</b> represents the number of requests to send at a time. Here, it's set to 2, so ApacheBench will send 2 requests concurrently.

Here is the example shown below for testing
```
ab -n 100 -c 2 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0NVU1RPTUVSIl0sInVzZXJGdWxsTmFtZSI6ImN1c3RvbWVyX2Z1bGxuYW1lIiwiaWQiOjIsImVtYWlsIjoiY3VzdG9tZXJAYm9va2RlbGl2ZXJ5LmNvbSIsInVzZXJuYW1lIjoiY3VzdG9tZXJfMSIsInN1YiI6ImN1c3RvbWVyXzEiLCJpYXQiOjE2OTQ4ODkyNTMsImV4cCI6MTY5NDg4OTg1M30.UokFWxgZnSnYZsbAlwbfhQj1F54QrEOU5_5KzHyRmtY" -T "application/json" -p post_data.json http://localhost:1221/api/v1/orders
```

### Swagger

```
http://localhost:1221/swagger-ui/index.html
```

### Prerequisites

#### Define Variable in .env file

```
DATABASE_USERNAME={DATABASE_USERNAME}
DATABASE_PASSWORD={DATABASE_PASSWORD}
```

---
- Maven or Docker
---


### Docker Run
The application can be built and run by the `Docker` engine. The `Dockerfile` has multistage build, so you do not need to build and run separately.

Please follow directions shown below in order to build and run the application with Docker Compose file;

```sh
$ cd bookdelivery
$ docker-compose up -d
```

If you change anything in the project and run it on Docker, you can also use this command shown below

```sh
$ cd bookdelivery
$ docker-compose up --build
```

---
### Maven Run
To build and run the application with `Maven`, please follow the directions shown below;

```sh
$ cd bookdelivery
$ mvn clean install
$ mvn spring-boot:run
```

### Screenshots

<details>
<summary>Click here to show the screenshots of project</summary>
    <p> Figure 1 </p>
    <img src ="screenshots/prometheues.PNG">
    <p> Figure 2 </p>
    <img src ="screenshots/prometheues_1.PNG">
    <p> Figure 3 </p>
    <img src ="screenshots/grafana_1.PNG">
    <p> Figure 4 </p>
    <img src ="screenshots/grafana_2.PNG">
    <p> Figure 5 </p>
    <img src ="screenshots/grafana_3.PNG">
    <p> Figure 6 </p>
    <img src ="screenshots/grafana_4.PNG">
    <p> Figure 7 </p>
    <img src ="screenshots/grafana_5.PNG">
    <p> Figure 8 </p>
    <img src ="screenshots/grafana_6.PNG">
    <p> Figure 9 </p>
    <img src ="screenshots/grafana_7.PNG">
    <p> Figure 10 </p>
    <img src ="screenshots/grafana_8.PNG">
    <p> Figure 11 </p>
    <img src ="screenshots/grafana_9.PNG">
</details>


### Contributors

- [Sercan Noyan Germiyanoğlu](https://github.com/Rapter1990)
- [Ahmet Aksünger](https://github.com/AhmetAksunger)
- [Muhammet Oğuzhan Aydoğdu](https://github.com/moaydogdu)