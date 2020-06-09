# Documentation


## Api usage

If you like to use the api please follow the steps below:

1. You have to create a ApplicationUser. Therefore send a request with a deviceId and a password of your choice to the /user/sign-up endpoint by POST method.

2. You can log user in by send the deviceId and password to the /user/login endpoint by POST method. In response you obtain a authorization token by header.

3. Now you can enable the api by providing the token in the headers of each request.

Troubleshooting:

* Make sure that you have set a secret for JWT in your enviroment variables. You just have to set it under the key JWT_SECRET.
