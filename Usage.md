
## `USAGE.md`

````markdown
# Usage Guide for CCRM

This guide shows how to operate the Campus Course & Records Manager from the console.

---

## Starting the Program
Compile and run:

```bash
javac -d out $(find src -name "*.java")
java -cp out edu.ccrm.cli.MainMenu
````

You will see the main menu:

```
1-stu 2-course 3-enrl 4-io 5-rep 0-exit
```

---

## Student Management

* **Add a student**

  * Enter `1` (students menu)
  * Type `s1`
  * Provide name and registration number
* **List students** → `s2`
* **Update name** → `s3`
* **Deactivate** → `s4`

---

## Course Management

* **Add a course**

  * Enter `2` (courses menu)
  * Type `c1`
  * Provide course code and title
* **List courses** → `c2`
* **Assign instructor** → `c3`

---

## Enrollment & Grades

* **Enroll student**

  * Enter `3` (enrollment menu)
  * Type `e1`
  * Provide student ID and course code
* **Unenroll** → `e2`
* **Record marks** → `e3`

Grades are automatically assigned from marks using the `Grade` enum.

---

## Import / Export / Backup

* **Import students** from CSV → menu `4`, option `i1`
* **Import courses** from CSV → menu `4`, option `i2`
* **Export all** (students, courses, enrollments) → menu `4`, option `i3`
* **Backup data** → menu `4`, option `i4`

Backups are saved in a timestamped folder.
Size of backup is shown using recursion utility.

---

## Reports

* Enter `5` in main menu.
* Shows GPA distribution sorted, top students first.

---

## Exit

Enter `0` at main menu.

---

## Example Session

```
1
s1
name
Ana
reg
22BCE10300
b
2
c1
CS101
IntroJava
b
3
e1
stu1
CS101
e3
en16956784234
95
b
5
```
