---
layout: page
title: User Guide
---
## <font color="#f0932b">Table of Contents</font>

* Table of Contents
{:toc}


--------------------------------------------------------------------------------------------------------------------

## <font color="#f0932b">Introduction:</font>
ManageMe is a **lightweight but powerful desktop application built to help university students manage their school life, available on Windows, Linux and Mac**. You can add your modules, tasks, schedules and online learning resources easily into ManageMe and access them with simple commands. Whether you are a fast typer or a more visual user, ManageMe has you covered. The application is optimized for use via a **Command Line Interface (CLI)** but also provides a convenient Graphical User Interface (GUI) for interaction.

This user guide will give you a comprehensive understanding in using ManageMe to its full potential. Begin your journey with the [Quick Start](#quick-start) section. For a full overview of the user guide, check out the [Table of Contents](#table-of-contents).

--------------------------------------------------------------------------------------------------------------------

## <font color="#f0932b">Quick Start</font>
This section aims to provide a quick introduction to using the application.
It should take less than 5 minutes to read and will provide new users with the necessary knowledge to install and start using the application.

### <font color="#f0932b">Steps for installation:</font>
1. Ensure you have Java 11 or above installed in your Computer.
2. [Download](https://github.com/AY2122S1-CS2103T-W11-3/tp/releases) the latest jar release of ManageMe.
3. Open your browser's download folder and locate the downloaded jar file. ManageMe can run on multiple platforms, including Windows, Mac and Linux.
4. Move the jar file into your desired folder. ManageMe will use this folder to store its data by default.
5. Start the app by double-clicking on the jar file, or if you are using the command line, type `java -jar manageme.jar`.
6. Enjoy using the application!

### <font color="#f0932b">User Interface</font>

Below is a screenshot of our User Interface (UI). As you can see, there are management panels for Modules, Tasks,
Calendar, and Links which can contain both website links and file paths stored on your computer.
You can type command lines in the command input box, and the ManageMe's response will be shown in the app response box.

![Ui](images/UiHomepage.png)
<br>*Homepage of Application*

### <font color="#f0932b">Basics</font>
Type a command in the command box and press Enter to execute it.
Some example commands you can try:
* `addMod n/CS2103T`: Adds a module named "CS2103T".
* `deleteTask 1`: Deletes the 1st task shown in the current list.
* `editLink 3 n/CS2100 Exam Link`: Edits the name of the 3rd link shown in the current list to “CS2100 Exam Link”.
* `clear`: Deletes all modules, tasks and links in ManageMe. Use this command to reset ManageMe.
* `exit`: Exits the application.

Refer to [Features](features) below for details of each command.
<br><br>

Return to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## <font color="#f0932b">Features</font>
This section gives a full list of all the application's features with details such as the format, some tips and examples of using it.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user. E.g. `addTask n/TASK_NAME`
  , here `TASK_NAME` is the parameter.<br>

* Items in square brackets are optional. E.g. `addTask n/TASK_NAME [mod/CS2103]`<br>

</div>

### <font color="#6ab04c">Tasks:</font>

#### <font color="#6ab04c">Adding a task: `addTask`</font>

Adds a task to the task list.

Format: `addTask n/NAME d/DESCRIPTION [mod/ASSOCIATED_MODULE_NAME] [s/START_DATETIME] [e/END_DATETIME]`

* A **name** and **description** for the task is **compulsory**.
* It is optional to include an associated Module name, a start datetime and an end datetime.
* A task created with a start datetime MUST also have an end datetime.
* Format for a `DATETIME` is as follows: `year-month-dayThr:min` e.g. `2021-10-29T23:59`

Example: `addTask n/Do CS2103T Assignment d/Refer to lecture 10 for examples mod/CS2103T s/2021-10-05T11:00
e/2021-10-07T23:59`

A reminder will pop-up when a task is happening:<br>
To close the reminder, press Enter or click on the Acknowledge button. Simply closing the window will not work and will cause the reminder to pop-up again!
![Reminder](images/reminder.png)<br>

*Pop-up reminder for tasks*
<br><br>

#### <font color="#6ab04c">Edit a task: `editTask`</font>
Edit an existing task in the task list.

Format: `editTask INDEX [n/NAME] [d/DESCRIPTION] [mod/ASSOCIATED_MODULE_NAME] [s/START_DATETIME] [e/END_DATETIME]`
* The index refers to the index number shown in the displayed module list
* The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Format for a `DATETIME` is as follows: `year-month-dayThr:min` e.g. `2021-10-29T23:59`

Example: `editTask 3 d/Complete assignments e/2021-10-25T16:00`
<br><br>

#### <font color="#6ab04c">Deleting a task: `deleteTask`</font>
Deletes the specified task from the task list.

Format: `deleteTask INDEX`
* Deletes the task at the specified `INDEX`
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, ...

Example: `deleteTask 2`
<br><br>

<div markdown="block" class="alert alert-info">

**:warning: Caution**<br>

* Deleting a task is irreversible.<br>

</div>

#### <font color="#6ab04c">Find a task by keyword: `findTask`</font>
Finds all tasks whose names contain any of the specified keywords.

Format: `findTask KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g lecture will match Lecture.
* The order of the keywords does not matter. e.g. Lecture Slide will match Slide Lecture.
* Only the name field is searched.
* Only full words will be matched e.g. Assignment will not match Assignments.
* Tasks matching at least one keyword will be returned (i.e. OR search).

Example: `findTask assignment` <br>
Before findTask:<br>
![findTask](images/findTask_before.png)<br>


After findTask:<br>
![findTask](images/findTask_after.png)

<br><br>

#### <font color="#6ab04c">List all tasks : `listTask`</font>
Display the full list of tasks. This command is used to return to the full list
of tasks after searching for specific tasks.

Format: `listTask`
<br><br>

#### <font color="#6ab04c">Mark a task as done/undone: `markTask`</font>
Marks the specified task from the task list as done/undone. Marking a task that is done will change it to undone while marking a task that is not done will change it to done.

Format: `markTask INDEX`
- Marks the task at the specified `INDEX`
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, ...

Example: `markTask 2`

Feature in UI:<br>

![Ui](images/UiOngoingTask.png)<br>
*An ongoing task not yet done will be in the default colour*
<br><br>

![Ui](images/UiDoneTask.png)<br>
A task marked as done will be coloured green.
<br><br>

#### <font color="#6ab04c">Delete all done tasks : `deleteDoneTask`</font>
Deletes all tasks that have been marked as done from the task list.

Format: `deleteDoneTask`

<div markdown="block" class="alert alert-info">

**:warning: Caution**<br>

* Deleting a task that is marked as done is irreversible.<br>

</div>
<br>

Return to [Table of Contents](#table-of-contents).

### <font color="#7ed6df">Modules:</font>
#### <font color="#7ed6df">Adding a module: `addMod`</font>
Adds a module with its name into the module list.

Format: `addMod n/NAME` <br/>
Examples: `addMod n/CS2103`
<br><br>

#### <font color="#7ed6df">Read details of a module: `readMod`</font>
View module in detail. Creates a pop-up window to show the course name, link, and all tasks associated with the course.

Format: `readMod INDEX`<br/>
* The index refers to the index number shown in the displayed module list
* The index **must be a positive integer** 1, 2, 3, ...

Examples: `readMod 2`

Screenshot:<br>
![readMod](images/readMod.png)<br>

*Pop-up window for readMod*
<br><br>

#### <font color="#7ed6df">Edit a module: `editMod`</font>
Edits an existing module in the mod list.

Format: `editMod INDEX [n/NAME]`
* Edits the mod by the specified `INDEX`.<br/>
* The index refers to the index number shown in the displayed module list
* The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example: `editMod 2 n/CS2103T`
<br><br>

#### <font color="#7ed6df">Delete a module: `deleteMod`</font>
Deletes the specified mod from the mod list.

Format: `deleteMod INDEX`
* Deletes the mod by the specified `INDEX`.
* The index refers to the index number shown in the displayed module list.
* The index **must be a positive integer** 1, 2, 3, ...

Example: `deleteMod 2` deletes module No.2 from the list.
<br><br>

<div markdown="block" class="alert alert-info">

**:warning: Caution**<br>

* Deleting a module is irreversible.<br>

</div>


#### <font color="#7ed6df">Find a module by keyword: `findMod`</font>
Finds modules whose names contain any of the given keywords.

Format: `findMod KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g hans will match Hans.
* The order of the keywords does not matter. e.g. Hans Bo will match Bo Hans.
* Only the name is searched.
* Only full words will be matched e.g. Han will not match Hans.
* Modules matching at least one keyword will be returned (i.e. OR search).

Example: `findMod computer` returns `Computer Organization` and `Computer Architecture`.
<br><br>

#### <font color="#7ed6df">List all modules : `listMod`</font>
Display the full list of modules. This command is used to return to the full list
of modules after searching for specific modules.

Format: `listMod`
<br><br>

Return to [Table of Contents](#table-of-contents).

### <font color="#f9ca24">Links:</font>
#### <font color="#f9ca24">Add a link: `addLink`</font>
Adds a link into the link list. A link contains its name and a website link for online learning.

Format: `addLink n/NAME a/LINK_ADDRESS [mod/ASSOCIATED_MODULE_NAME]` <br/>
* A link should be in a valid uri format, beginning with https://, ftp:// of file:/
* Aftering tagging the module, the link will appear at in the readMod panel of the particular module

Examples: `addLink n/google a/https://www.google.com mod/CS1101S`
<br><br>

#### <font color="#f9ca24">Edit a link: `editLink`</font>
Edits an existing link in the link list.

Format: `editLink INDEX [n/NAME] [a/LINK_ADDRESS] [mod/ASSOCIATED_MODULE_NAME]`
* Edits the link by the specified `INDEX`.<br/>
* The index refers to the index number shown in the displayed link list
* The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example: `editLink 2 n/amazon`
<br><br>

#### <font color="#f9ca24">Delete a link: `deleteLink`</font>
Deletes the specified mod from the mod list.

Format: `deleteLink INDEX`
* Deletes the mod by the specified `INDEX`.
* The index refers to the index number shown in the displayed link list.
* The index **must be a positive integer** 1, 2, 3, ...

Example: `deleteLink 2` deletes link No.2 from the list.
<br><br>

<div markdown="block" class="alert alert-info">

**:warning: Caution**<br>

* Deleting a link is irreversible.<br>

</div>

#### <font color="#f9ca24">Open a link:</font>
Format: `openLink INDEX`
* Opens the link identifies by the index in the link list. If it is a link for a webpage, it will open the link in a browser. If it is a file path on your computer, it will open the file using the default app.
* The index refers to the index number shown in the displayed link list
* The index **must be a positive integer** 1, 2, 3, ...

Example: `openLink 1` open link No. 1 from the list of links.
<br><br>

#### <font color="#f9ca24">Find a link by keyword:</font>
Find a link whose name contains the keyword given.

Format: `findLink KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g hans will match Hans.
* The order of the keywords does not matter. e.g. Hans Bo will match Bo Hans.
* Only the name is searched.
* Only full words will be matched e.g. Han will not match Hans.
* Links with a name matching at least one keyword will be returned (i.e. OR search).

Example: `findLink computer` returns `Computer Organization https://....` and `Computer Architecture https://....`.
<br><br>

#### <font color="#f9ca24">List all links: `listLink`</font>
Display the full list of links. This command is used to return to the full list after findLink.

Format: `listLink`
<br><br>

Return to [Table of Contents](#table-of-contents).

### <font color="#eb4d4b">Calendar:</font>
#### <font color="#eb4d4b">Change calendar to next month: `nextMonth`</font>
Display the calendar for the next month. This command will
update the entire calendar panel to display the calendar and the related task information for the following month.

Format: `nextMonth`
<br><br>

#### <font color="#eb4d4b">Change calendar to previous month: `prevMonth`</font>
Similar to `nextMonth`. However, this command display the calendar for the previous month instead.

Format: `prevMonth`
<br><br>

#### <font color="#eb4d4b">Read details happening on a day: `readDay`</font>
View a day in detail. Tasks happening on the specified day will be displayed.

Format: `readDay DATE`
* `DATE` given must be a valid date.
* Format for a `DATE` is as follows: `year-month-day` e.g. `2021-10-19`

Example: `readDay 2021-10-19`
<br><br>

Return to [Table of Contents](#table-of-contents).

### <font color="#4834d4">Others:</font>

#### <font color="#4834d4">Viewing help: `help`</font>

Shows the command summary and the url to the full User Guide.
<br><br>

#### <font color="#4834d4">Archive current data: `archive`</font>

Resets the application data and saves the deleted application data into a timestamped file located in the data folder.
<br><br>

#### <font color="#4834d4">Exiting the program : `exit`</font>

Exits the program.


#### <font color="#4834d4">Saving the data</font>

Data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.
<br><br>

#### <font color="#4834d4">Reminder for overdue tasks:</font>

Tasks that are overdue(past the end time) will automatically be coloured red for users to clearly see which tasks are
overdue.

Feature in UI:<br>
![Ui](images/UiOverDueTask.png)<br>
*A Task coloured red since it is overdue*

Return to [Table of Contents](#table-of-contents).

--------------------------------------------------------------------------------------------------------------------

## <font color="#f0932b">Command summary</font>
This section serves as a quick reference for all the available commands that can be used in the application.

Action | Format, Examples
--------|------------------
**AddTask** | `addTask n/NAME d/DESCRIPTION [mod/ASSOCIATED_MODULE_NAME] [s/START_DATETIME] [e/END_DATETIME]`<br>e.g., `addTask n/Do Assignment d/Read Lecture 7 mod/CS2100 s/2021-10-05T12:00 e/2021-10-07T23:59`
**EditTask** | `editTask INDEX [n/NAME] [d/DESCRIPTION] [mod/ASSOCIATED_MODULE_NAME] [s/START_DATETIME] [e/END_DATETIME]`<br>e.g., `editTask 3 d/buy milk`
**DeleteTask** | `deleteTask INDEX`<br>e.g., `deleteTask 3`
**findTask** | `findTask KEYWORD [MORE_KEYWORDS]`<br>e.g., `findTask work`
**ListTask** | `listTask`
**MarkTask** | `markTask INDEX`<br>e.g., `markTask 1`
**DeleteDoneTask** | `deleteDoneTask`
**AddModule** | `addMod n/NAME`<br>e.g.,`addMod n/CS2103`
**ReadModule** | `readMod INDEX`<br>e.g., `readMod 2`
**EditModule** | `editMod INDEX [n/NAME] [l/LINK]`<br>e.g., `editMod 2 n/CS2103T l/https://...`
**DeleteModule** | `deleteMod INDEX`<br>e.g., `deleteMod 2`
**FindModule** | `findMod KEYWORD [MORE_KEYWORDS]`<br>e.g., `findMod computer`
**ListModule** | `listMod`
**AddLink** | `addLink n/NAME l/LINK_ADDRESS [mod/ASSOCIATED_MODULE_NAME]`<br>e.g., `addLink n/google a/https://www.google.com`
**DeleteLink** | `deleteLink INDEX`<br>e.g., `deleteLink 2`
**EditLink** | `editLink INDEX [n/NAME] [a/LINK_ADDRESS] [mod/ASSOCIATED_MODULE_NAME]`<br>e.g.,`editLink 2 n/amazon`
**OpenLink** | `openLink INDEX` <br>e.g.,`openLink 1`
**FindLink** | `findLink KEYWORD`<br>e.g.,`findLink google`
**ListLink** | `listLink`
**NextMonth** | `nextMonth`
**PreviousMonth** | `prevMonth`
**ReadDay** | `readDay DATE`<br>e.g., `readDay 2021-10-19`
**Help** | `help`
**Archive** | `archive`
**Exit** | `exit`

Return to [Table of Contents](#table-of-contents).
