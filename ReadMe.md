#  A Digit Gym System
## Software Profile
The project is based on java 13, using javafx to create the UI. Junit is used to test the program. We used txt to restore
the data. The corresponding javafx is in the package. We used IntelliJ IDEA to finish the software.
### * txt Introduction
* "coachLesson.txt" restore the scheduled coach's information. It contains the course ID, coach name, coach's ID, 
coach's name, the course number of live course, price and status. 
* The live course time is:
Lesson 1:"07:00 ~ 09:00"; Lesson 2:"09:00 ~ 11:00"; Lesson 3:13:00 ~ 15:00"; Lesson 4:"15:00 ~ 17:00";  Lesson 5:"19:00 ~ 21:00";
* "course.txt" restore the live course information.(courseId,coachId,coachName,lessonNo,price,status)
* "cus.txt" restore the member's personal information.(user Id,user password,user email,user birth,user phoneNo,sex,user name,invitation number)
* "employee.txt" restore the coach's personal information. (coach ID, password, email, birthday, phone No., sex, name)
* "manager.txt" restore the staff(manager) personal information. (manager ID, password,email,birthday,phone No., sex)
* "member.txt" restore member's recharge record. (member ID, charge Id, balance, total recharge, charge time, member rank)
* "mylesson.txt" restore member's recorded course list. (member ID, course ID, course name, course description, course type, duration, course URL, member rank)
* "order.txt" restore the members' order live schedule information. (order ID, member ID, coach ID, coach name, lesson No, scheduled date, price, status)
***
### * Package Introduction
#### * entity: store the entity class 
* coachLesson: store the scheduled coach's information, including the coaches' ID and course entity
* coachRank: Manager can check the rank of coaches, including rank, coachID and Number of being ordered live courses
* Course: The live courses information
* employee: The coach personal information
* manager: The staff personal information
* memberRank: Rank manager by their total recharge
* MyRecordLesson: Member's recorded lesson list
* Order: Live course order information
* recharge: Member recharge information
* recordCourseRank: Rank the record courses by order number
* recordLesson: Record course information
* user: Member's personal information
####* Control/coach: store the controller for coach
####* Control/management: store the controller for log in ,forget password, register and video play.
####* Control/member:store the controller for member
####* Control/staff: store the controller for staff
####* Control/tool: file read, delete, change and insert operations
####* Boundary/icon: the image for GUI
***
### *Main function
####* All users 
login, reset password.
####* Member
Register; modify personal information; add recorded course, search recorded course, delete recorded course, watch recorded course;
invite other members; recharge; schedule live course and pay, check and watch live course, delete scheduled live course and get refund.
####* Coach 
Check personal information, check scheduled live courses and deliver the live courses.
####* Manager 
Check member and coach statics, check recorded courses and order records.
***
### * Journal
#### * April 9

1.Log in interface is finished, user can choose their identity. And if they forget their password, 
they can change their password after qualification, using their email.  

2.Add user. Users can register after filling in their their name, password, email, phone number, date of birth and sex to register. 

3.Data read in and write to txt file.

4.Users can modify their personal information.

5.Member center contains member’s personal information, including their count and course information.

6.Manager can check the information of members.

7.Members can search other members and trainers by their id and name, and add them to their address book. They can also delete them from address.                                  

####*  April  13

1.Manager side: record course management, finished searching, adding, modifying and deleting recorded courses.

2.Member side: record course management, members can add recorded courses into their course list and delete them. 
Members can watch videos if they double click the courses.

####* April 23

1. Coach side: check the scheduled live courses. Coach can modify their personal information.

2. Member side: search the available live courses, schedule the live courses and pay for the live courses. 
Member can check their ordered live courses in their course list.

####* May 9

Manager side: Manager can check and modify member personal information; check, modify, delete and add coaches' personal information; 
search, modify, add and delete the recorded courses information

####* May 17
Manager side: add some charts for statics
                        



                                                                       

