# Read me.

Before you start to review code, i would like to give some high level breifing regarding this project.
I will share the idea why i am writing the code in this way. 

Hope to see you in the next round interview so we can go thru the code together for more details :)

# Programming language and Framework
- Java 17
- Spring boot 3.2.0

# Package Manager 
- Maven

# Database
- MySQL

The reason I choose MySQL because it has several advantage in this scenario
1. RDBMS, MySQL is very good fit if our data is very structured data and there is clear relationship between different entitites.
In this scenario, book and borrower is a many-to-one relationship, and we can expecting the book and borrower is structured data.
Unless we have a very fluid or hierarchical data strucutre, then i would choose NoSQL database.
2. Data Integrity, mysql support constraints and foreign key, allowing me to enforce data integrity rules
This is used as one of the validation to make sure the data i manage to save into DB is always consistent.
3. Scalability, as our system grows, we will have more data records to keep. MySQL can handle the number of records with it's scalability
4. Open-Source, it's free... Unless we go enterprise 
5. Community support, one of the factor when i choosing a tech stack / system. I always check the size of community. 
With large and active community, we can always find a resources/solutions/tutorial and etc. This will help us when we facing some trouble.
6. Performance tuning, indexing, optimize join operation, stored procedures etc etc good for performance

# Provide clear documentation for how to use your API
I am using open api 3, also known as swagger to provide the details for my API endpoint. After launch the application,
please go to http://localhost:8080/swagger-ui/index.html you will get everything you need to call the API. 

Besides, i have create a postman for you, so you can download and import the collection and play around with it.

# Assumption
I have make some assumption before i start the projects
1. Assuming this library system is use by a librarian. 
2. Visitor only allowed to get list of books. 
3. Library responsibility use to the system to add, borrow and return book.
4. Assuming there is admin portal for librarian to use this system
5. Assuming there is visitor portal for visitor to view the books. *This is also reason why i design the get books in pagination response.
No body like to hurt their eye :( Pagination help in performance and user friendly
6. Assuming add book into the system, librarian can just add the books based on number of books. (Can't be you want the librarian re-insert 100times if there is 100 same books, it's better if he just type in the book information and choose the number of books to add.)

# Testing
Junit 5 and Mockito is implemented
1. Unit Test
2. MVC Test

# How to execute this system
When i am writing this application, what i trying to achieve is you dont have to do anything, all you need is type in this command

1. ``docker-compose -f docker-compose-mvn.yml --env-file .env up --build`` 
- If you want to build directly in container, but it's SLOWWWW. No body like to wait


2. ``docker-compose -f docker-compose-target.yml --env-file .env up --build``
- Choose this if you dont want to wait. Before you run, make sure you run ``mvn clean package``

The docker compose included MySQL, Redis, and the WebApplication

# Extra
1. Flyway

I have implement flyway to simplify database migration and versioning the database. I feel pain in current company when i have to manually trigger all the script to align with the latest database  version

2. Redis

The reason why include redis in this application, one of the requirement ``2 books with the same ISBN numbers must have the same title and same author``

Personally i feel it's very good to implement caching, because we dont have to go into DB to retrieve the data just for validation. Performance of the system will be noticed when we have large amount of records in the book table.


# Last but not least
Hope we have have a talk in the future and discuss about this sytem so i can share more about my idea. 

# Thanks for reading ! It's long and boring i know :) 