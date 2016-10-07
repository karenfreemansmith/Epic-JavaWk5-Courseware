# Java Courseware
Version 0.0.0: October 7, 2016

![screenshot of project running](screenshot.jpg)

by [Brian Dutz](https://github.com/AIMWORLD213445),
[Elysia Nason](https://github.com/ElysiaAvery), [Ewa Manek](https://github.com/ewajm), [Karen Freeman-Smith](https://github.com/karenfreemansmith), and [Sara Jensen](https://github.com/thejensen)

## Description
Group Project for Week 5, Java at Epicodus - A web-based platform to create and administer courses of all types.

### Specifications
#### User Stories:

##### MVP (Musts)
* As a teacher, I want to be able to create a course X
  * As a teacher, I want to upload course materials as links or as simple text X
  * As a teacher I want to see all the students in the course X
  * As a teacher, I want to be able to see my student's grades X
  * As a teacher, I want to be able to be able to access and grade students' assignments X
  * As a teacher, I want to be able to see students' cumulative grades X
* As a teacher, I want to be able to see all my students by course O
* As a teacher, I want to be able to see all my courses X
* As a teacher, I want to navigate to the courses I am teaching. X
* As a teacher, I want some indication if I have assignments that need grading (need dashboard something)-

* As a student, I want to access the lessons and assignments for a course X
* As a student, I want to submit my assignments X
* As a student, I want to be able to see my grades X
* As a student, I want to see all available courses X
* As a student, I want to be able enroll in a course X
* As a student, I want to navigate to the courses in which I am enrolled. X


##### MVP Enhancements

* User Login (Teacher / Student)
  * Block duplicate names for both teachers and students
* Add assignments pending review to the Teacher Dashboard.

#### Database Diagram:
![database diagram](database.png)
(create diagram at - http://ondras.zarovi.cz/sql/demo/  )

## Setup/Installation
* Clone directory
* Setup database in PSQL:
  * CREATE DABASE courseware;
  (From command line in program directory)
  * psql courseware < coursware.sql
* Type 'gradle run' inside the directory
* Navigate to 'http://localhost:4567'

## Support & Contact
For questions, concerns, or suggestions please email karenfreemansmith@gmail.com

## Known Issues
* N/A

## Technologies Used
Java, JUnit, Spark, PostgreSQL, Gradle

## Legal
*Licensed under the GNU General Public License v3.0*

Copyright (c) 2016 Copyright _Brian Dutz, Elysia Nason, Ewa Manek, Karen Freeman-Smith & Sara Jensen_ All Rights Reserved.
