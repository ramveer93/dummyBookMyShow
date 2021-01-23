# How to setup

  ```git
  git clone https://github.com/ramveer93/dummyBookMyShow.git
  ```
  ```mvn
  mvn clean install
  ```
- install mysql version 8 in your local machine
- Go to src/main/resources config.sql and run the commands mentioned in this file on mysql cmd prompt
- Restore db dump to mysql from src/main/resources/Dump20201019.sql
- Run the application as spring boot application

# Swagger documents
```git
 http://localhost:8080/swagger-ui.html
 ```
 
 # Run the APIs in steps
 - First create a user using /v1/addUser
 ```json
 {
  "authentication": "admin",
  "email": "panjaguttamall@mmall.com",
  "firstName": "Jayram",
  "lastName": "Nayak",
  "mobileNumber": "8712098768",
  "userType": "ADMIN"
}
```
 - Then Add a theater using POST /v1/registerTheater
 ```json
{
  "address": "Ground Floor, Vibrant Mall , Panjagutta",
  "city": "Hyderabad",
  "country": "India",
  "languages": "Hindi, English,Telugu",
  "name": "Vibrant Mall Hyderabad",
  "userName": "NayakJayram8712098768"
}
```
- Add a movie to this theater by calling POST /v1/registerMovie
```json
{
  "activeDateEnd": "2021-10-18 19:43:00",
  "activeDateStart": "2020-10-18 19:43:00",
  "castId": null,
  "director": "Raaj Shaandilyaa",
  "duration": "132 min",
  "language": "HINDI",
  "name": "Dream Girl",
  "plot": "Rom-com Movie, directed by Raaj Shaandilyaa, stars Ayushmann Khurrana who plays a 'dream girl'. In every love story, there is always one trying to win the heart of the other, who could be the dream girl",
  "posterUrl": "https://m.media-amazon.com/images/M/MV5BNmUyMzU3YjgtZTliNS00NWM2LWI5ODgtYWE3ZjAzODgyNjNhXkEyXkFqcGdeQXVyNjY1MTg4Mzc@._V1_SX300.jpg",
  "rating": "8.0",
  "releaseYear": "2020",
  "theaterId": "VibrantMallHyderabad",
  "trailerUrl": "not available",
  "type": "Drama, Comedy"
}
```
- Add cast to this movie by calling POST /v1/addCast
```json
[
{
  "castDetails": "AyushMan Khurana as Karan Singh",
  "characterName": "AyushMan Khurana",
  "characterOccupation": "actor",
  "movieId": "DreamGirl"
},

{
  "castDetails": "Vijay Raj as Rajpal",
  "characterName": "Vijay Raj",
  "characterOccupation": "actor",
  "movieId": "DreamGirl"
}]
```
- Add screens in a theater which are showing this movie using POST /v1/registerScreen
```json
{
  "endsAt": "2020-10-18 09:30:00",
  "screenDetails": {
    "movieId": "DreamGirl",
    "startsAt": "2020-10-18 06:00:00",
    "theaterId": "VibrantMallHyderabad"
  }
}
```
- Initialize the seat matrix for this screen by calling POST /v1/addDefaultSeatMatrix
```json
{
  "booked": true,
  "price": 0,
  "primaryKey": {
    "movieId": "DreamGirl",
    "screenStartsAt": "2020-10-18 06:00:00",
    "seatNumber": "A1",
    "theaterId": "VibrantMallHyderabad"
  },
  "seatType": "NORMAL"
}
```
- Get the list of supported cities in system by calling GET /v1/getSupportedCities
- Get the list of now showing movies by calling end point GET /v1/getMoviesByCity
- Get the list of cinemas/screens showing this movie in a city by calling GET /v1/getScreensShowingMovie
- Get the seat availabilities GET /v1/getAvailabilityOnAScreen
- Book the seats POST /v1/bookSeats with below json
```json
[
   {
    "booked": true,
    "price": 100,
    "primaryKey": {
      "movieId": "DreamGirl",
      "screenStartsAt": "2020-10-18 06:00:00",
      "seatNumber": "A0",
      "theaterId": "VibrantMallHyderabad"
    },
    "seatType": "NORMAL"
  },
   {
    "booked": true,
    "price": 100,
    "primaryKey": {
      "movieId": "DreamGirl",
      "screenStartsAt": "2020-10-18 06:00:00",
      "seatNumber": "A1",
      "theaterId": "VibrantMallHyderabad"
    },
    "seatType": "NORMAL"
  },
   {
    "booked": true,
    "price": 100,
    "primaryKey": {
      "movieId": "DreamGirl",
      "screenStartsAt": "2020-10-18 06:00:00",
      "seatNumber": "A2",
      "theaterId": "VibrantMallHyderabad"
    },
    "seatType": "NORMAL"
  }
]
```
here we are trying to book three seats A0, A1 and A2.
- Once the seats booked above api should give details about seat,user, theater and movie 
- If we try to book the seats which are not available , user will get appropriate error msg 

# Authorization
- To authorize the APIs , we have to un comment line 58 of SecurityConfig.java , this will start authenticating all the end points except /v1/token and /v1/addUser
https://github.com/ramveer93/dummyBookMyShow/blob/c9f8b8815280dd375faa5ac1a071fa71eda04c82/src/main/java/com/dummy/bookmyshow/security/SecurityConfig.java#L58
```java
.anyRequest().authenticated().and().exceptionHandling()
```
# License 
MIT License 


