
# User Management API with AWS Integration
This is a Spring Boot application that exposes RESTful APIs 
for user registration, login, and profile management. 
It integrates with AWS Cognito for user authentication and 
uses AWS Amplify to store and retrieve user profile images. 
Additionally, it includes an endpoint to retrieve a list of 
recommended users using AWS Personalize.

## Table of Contents
* Installation
* Authentication
* Profile Images
* Recommended Users

### Installation
To run this application, you will need to have Java and Maven 
installed on your system. You will also need to have an AWS account 
and credentials for Cognito, S3, and Personalize.

* Clone this repository to your local machine `git clone https://github.com/ife0luwa/FinEstate-Capital.git`

* Open the project in your preferred IDE.
* Configure your AWS credentials by creating a ~/.aws/credentials file or setting environment variables.
* Modify the application.properties file with your AWS Cognito, Amplify, and Personalize configuration settings.
* Build and run the application using Maven.<br>



### Authentication
* This application integrates with AWS Cognito for user authentication. 
To register a new user, and upload picture to AWS Amplify(S3 bucket) to store send a POST request to `/signup` with the following payload: <br>
`@RequestParam("name") String name, 
@RequestParam("email")String email,
@RequestParam("phoneNumber") String phoneNumber,  
@RequestParam("password") String password, 
@RequestParam("picture") MultipartFile picture` <br>

* To log in, send a POST request to /login with the following payload:<br>
`{
"username": "email",
"password": "password"
}`. If the login is successful, the response will include an access token that you can use to access protected resources.


* To retrieve a user's profile image, send a GET request to <br> 
`image/{imageName}` with the user ID as a path parameter.

* Recommended Users:
This application includes an endpoint to retrieve a list of recommended users 
using AWS Personalize. To retrieve the list of recommended users, send a GET request 
to `/users/recommended` with the following parameters:
