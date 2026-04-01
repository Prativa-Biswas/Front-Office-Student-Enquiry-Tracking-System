Enquiry Tracking System (Frontend + Spring Boot)
Project Overview

The Enquiry Tracking System is a web-based application designed for front desk users to manage and track student enquiries efficiently.
It provides functionalities like user authentication, enquiry management, dashboard analytics, and filtering with AJAX, making the process smooth and user-friendly.

Technologies Used
Backend: Spring Boot, Spring MVC, Spring Data JPA
Frontend: HTML, CSS, JavaScript, Bootstrap, Thymeleaf
Database: Oracle (SQL, PL/SQL)
Server: Tomcat
Tools: Eclipse / STS

Features
User Management
User Registration
New users are created in a locked state.
System sends an email with OTP and unlock link.
Unlock Account
User clicks link → opens unlock form.
After unlocking, account becomes active.
If already unlocked → shows appropriate message.
Login
Only unlocked users can log in.
Session management is maintained after login.
Forgot Password
Sends the existing password to registered email.

Dashboard
After login, user is redirected to dashboard showing:

Total enquiries
Enrolled enquiries
Lost enquiries
Other status-based counts

 Enquiry Management
Add Enquiry
Create new student enquiry
Data stored in database using JPA

View Enquiries
Display all enquiries in a table format
Includes filter functionality

Filter Enquiries (AJAX)
Filter by:
Course
Status
Mode
Uses AJAX → No page reload (smooth UX)

Edit Enquiry
Update existing enquiry details

Logout
Ends session and redirects to Home page

Application Flow
User registers → Account created in locked state
Email sent with OTP + unlock link
User unlocks account
Login → Session starts
Dashboard displayed
Perform:
Add enquiry
View & filter enquiries
Edit enquiry
Logout → Session ends

Project Structure 
controller/    → Handles HTTP requests
service/       → Business logic
repository/    → Database operations (JPA)
entity/        → Database entities
dto/form/      → Form binding objects
templates/     → Thymeleaf UI pages

Key Highlights
Full Spring Boot MVC architecture
Session management implemented
AJAX-based filtering (no page reload)
Email integration for account unlock & password recovery
Clean separation of layers (Controller → Service → Repository)

Author

Prativa Biswas