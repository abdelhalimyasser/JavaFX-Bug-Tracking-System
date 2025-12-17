# 🐞 JavaFX Bug Tracking System

<img src="https://github.com/user-attachments/assets/ca6ed4c5-a06c-4f4f-9107-78a9b172c436" alt="Bug Tracking System Logo" width="180" align="right" />

**Faculty of Computing and Artificial Intelligence**  
**Helwan University**

**Course:** Programming Languages 2 (CS-213)  
**Instructor:** Dr. Mohammed El-Said  
**Academic Year:** 2025/2026

> A powerful desktop **Bug Tracking System** built with **JavaFX**, featuring role-based access control, automated email notifications, and persistent storage using text files (CSV).

---

### Project Overview
A full-featured bug tracking application that supports **four user roles** (Tester, Developer, Project Manager, Admin) with tailored permissions and automated notifications.

---

### ✨ Key Features by Role

**Tester**  
- Create & assign bugs with attachments  
- View own reported bugs  
- Auto email developer on assignment  

**Developer**  
- View assigned bugs  
- Update status + add notes  
- Auto email tester when fixed  

**Project Manager**  
- Performance dashboards & analytics  
- Track team metrics and resolution times  

**Admin**  
- Full user management (CRUD)  
- View all bugs across the system  

---

### 🛠️ Technologies Used

| Technology        | Purpose                              |
|-------------------|--------------------------------------|
| JavaFX            | Rich desktop interface               |
| Scene Builder     | GUI design                           |
| CSV Text Files    | Lightweight database                 |
| JavaMail API      | Simulated email notifications        |
| MVC Architecture  | Clean code organization              |

---

### 📄 Documentation
- [Project Requirements (PDF)](Requirements/Bug%20Tracking%20System.pdf)

- [UML Class Diagrams](Class%20Diagrams)

---

### Internal Team Roles & Responsibilities (7 Members)

**Goal:** Everyone learns GUI + real logic. Only **2 people** touch the GUI (for unified design), but they also own heavy backend modules.

| Role                                        | Owner                                 | Responsibilities |
|---------------------------------------------|---------------------------------------|----------|
| **1. GUI Master + Full Tester Module**      | • Abdelhalim Yasser<br> • Ali Samy    | • All GUI screens (Login, Register, Tester UI, Navigation)<br>• Unified styling & structure<br>• Full Tester logic (create bug, assign, attach screenshot, view bugs)<br>• Defines `users.csv` structure<br>• Calls email on assignment |
| **2. GUI Master + Full Developer Module**   | • Abdelhalim Yasser<br> • Ali Samy    | • Remaining GUI (Bug forms, tables, Developer/PM/Admin dashboards)<br>• Screenshot dialog & final polish<br>• Full Developer logic (view assigned bugs, update status, auto-date)<br>• Defines `bugs.csv` structure<br>• Calls email on completion |
| **3. Full Project Manager Module**          | • Norhan Mohamed<br> • Youssef Saeed  | • Complete PM dashboard logic<br>• Performance reports (bugs per person, avg fix time, charts)<br>• Open vs Closed statistics |
| **4. Full Admin Module** | • Norhan Mohamed<br> • Youssef Saeed                     | • Admin CRUD for users<br>• View all bugs<br>• Prevent duplicate usernames/emails<br>• Safe user file operations |
| **5. Authentication + Session Manager**     | • Abdelrahman Ahmed                   | • Login/Register validation<br>• Session management & role-based access<br>• Logout & simple password hashing |
| **6. CSV Master Controller** | • Abderahman Alaa<br> • Nada Moustafa                | • Central reusable CSV utility class (read/write/update/delete/append)<br>• Auto-increment BugIDs<br>• Creates `screenshots/` folder |
| **7. Email System + Notification Manager**  | • Abderahman Alaa<br> • Nada Moustafa | • Realistic simulated emails (formatted output)<br>• Templates for assignment & completion<br>• Date/time utilities<br>• Helps with CSV edge cases |

> REVIEWD AND SUPERVISED BY [Abdelhalim Yasser](https://github.com/abdelhalimyasser) - The Team Leader of the Project

### CSV File Structures
- `users.csv` → `firstName,lastName,phone,email,role,password`
- `bugs.csv` → `bugId,name,projectName,type,priority,level,description,dateReported,status,testerUsername,developerUsername,screenshotPath`

**This division is perfectly fair** — everyone writes significant logic, deals with file I/O, and contributes to a complete working system.

---

### 👥 Team Members

| Name                  | GitHub                                                           |
|-----------------------|------------------------------------------------------------------|
| Abdelhalim Yasser     | [@abdelhalimyasser](https://github.com/abdelhalimyasser)         |
| Abdelrahman Ahmed     | [@eng-boda](https://github.com/eng-boda)                         |
| Abdelrahman Alaa      | [@abdelrahmanalaa-3094](https://github.com/abdelrahmanalaa-3094) |
| Ali Samy              | [@AliSamy12](https://github.com/AliSamy12)                       |
| Nada Moustafa         | [@qNVDV](https://github.com/qNVDV)                               |
| Nourhan Mohamed       | [@Nour-FCAI](https://github.com/Nour-FCAI)                       |
| Youssef Saeed         | [@Youssef-Saeed14](https://github.com/Youssef-Saeed14)           |

> Contributions are welcome! Feel free to fork and submit pull requests

---

<p align="center">
  <strong>FCAI – Helwan University</strong><br>
  Programming Languages 2 • Final Project • 2025/2026
</p>
