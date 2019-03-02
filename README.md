#Voting system for deciding where to have lunch

## REST API using Hibernate/Spring/SpringMVC

### get a menu of all the restaurants for today
GET http://localhost:8080/topjava15graduation/rest/menu \
Authorization: Basic user@yandex.ru password

curl -X GET \
  http://localhost:8080/topjava15graduation/rest/menu \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

### vote for restaurant menu with id
PUT http://localhost:8080/topjava15graduation/rest/menu/100008/vote \
Authorization: Basic user@yandex.ru password

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/menu/100008/vote \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

## For admin

### get restaurant by id
GET http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
Authorization: Basic admin@gmail.com admin

curl -X GET \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

### get all restaurants
GET http://localhost:8080/topjava15graduation/rest/restaurants \
Authorization: Basic admin@gmail.com admin

curl -X GET \
  http://localhost:8080/topjava15graduation/rest/restaurants \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

### create restaurant
POST http://localhost:8080/topjava15graduation/rest/restaurants \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{"name":"New"}

curl -X POST \
  http://localhost:8080/topjava15graduation/rest/restaurants \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{"name":"New"}'

### delete restaurant
DELETE http://localhost:8080/topjava15graduation/rest/restaurants/100005 \
Authorization: Basic admin@gmail.com admin

curl -X DELETE \
  http://localhost:8080/topjava15graduation/rest/restaurants/100005 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

### update restaurant
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{"name":"Mama Roma Updated"}

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{"name":"Mama Roma Updated"}'

### get a menu of restaurant with id for today
GET http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu \
Authorization: Basic admin@gmail.com admin

curl -X GET \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

### update menu of restaurant with id for today
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{
    "dishes": [
        {
            "name": "Новая похлебка",
            "price": 77700
        },
        {
            "name": "Новая каша",
            "price": 1000
        }
    ]
}

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{
	"dishes": [
		{
	    	"name": "Новая похлебка",
	    	"price": 77700
		},
		{
	    	"name": "Новая каша",
	    	"price": 1000
		}
	]
}'

### update menu of restaurant with id for another day
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01 \
Authorization: Basic admin@gmail.com admin
Content-Type: application/json

{
    "dishes": [
        {
            "name": "Пельмени",
            "price": 55500
        }
    ]
}

curl -X PUT \
  'http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{
    "dishes": [
        {
            "name": "Пельмени",
            "price": 55500
        }
    ]
}'

### get a menu of restaurant with id for date
GET http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2018-12-11 \
Authorization: Basic admin@gmail.com admin

curl -X GET \
  'http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2018-12-11' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'