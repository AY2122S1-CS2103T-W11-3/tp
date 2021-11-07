---
layout: page
title: Cheng Chuan's Project Portfolio Page
---

### Project: ManageMe

ManageMe is a desktop app for time management and resource organisation, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, ManageMe can get your tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **Major Enhancements**
  * Added Time Component and its related managers and interfaces (PR #112)
    * Description: An additional component (on top of Logic, Model, Storage, UI and Commons)
    * Reason: To facilitate the implementation of the Reminder function and other time related components.
    * Difficulty: This was rather difficult to implement despite the few lines of code it is represented by. When I was
      tasked with implementing the reminder function, I had to find a way to constantly run a separate thread in the
      background. Considerable amount of time was spent not just testing different kinds of code but also in
      brain-storming for potential ways to implement it. Ultimately, I settled with the Threads class to constantly keep
      track of the current time, which allowed for subsequent implementation of time related components.
      <br><br>
  * Added Reminder Function (PR #112)
    * Description: The reminder function is a key part of ManageMe in that it reminds the user in the form of a pop up
      when the start time is reached or when the task is already in progress.
    * Reason: As a time-and-task management application, it is of utmost importance for there to be a feature to remind
      users when an upcoming important task is happening. For our target users, which are students, it can serve as a way
      to keep track of time and maximise productivity.
    * Difficulty: Most of the difficulty came from the implementation of the time component as written above. There are
      two parts to this Reminder Function - UI and Logic. The UI's popup box was done by Rui Yan. The logic behind it was
      implemented by me. The main problem that took me quite some time was getting a list of modifiable task from the
      model, due to the limitations in architecture and types of list stored. After implementing the idea of threads,
      I had to create several related functions to compare the different times of a task as well as whether the tasks
      were already marked as done or not.
      <br><br>
  * Added Edit Task Command (PR #59)
    * Description: The Edit Task Command allows the user to edit the specified task.
    * Reason: For ManageMe, adding tasks are a core feature we have but in the event that the user typed in something
      wrong, they can use the edit task function to edit it.
    * Difficulty: Although the difficulty of implementing this was not high, it was very time-consuming due to the sheer
      amount of fields we have for a task to be edited.
      <br><br>
  * Added Delete Task Command (PR #59)
    * Description: The Delete Task Command allows the user to delete a specified task.
    * Reason: Deleting tasks are a core feature of ManageMe. Tasks that are no longer relevant can be cleared from the
      database with this function. With this feature, the GUI will remain clean and simple, ensuring that the user
      experience is smooth and hassle free.
    * Difficulty: This was not too difficult because most of the work was transferring the previous Delete command to
      this.
      <br><br>
  * Added Delete Done Task Command (PR #125)
    * Description: The Delete Done Task Command deletes ALL tasks that have been marked as done previously using the
      markTask Command.
    * Reason: As a user uses ManageMe, over time tasks that have been completed will be marked as Done. However, we
      as developers, decided not to automatically remove those "Done" tasks to prevent cases of accidental marking.
      This command allows specifically all "Done" tasks to be cleared from the database.
    * Difficulty: This was not particularly difficulty since we have already implemented the filter function. I could
      then implement a function that removes all tasks that are done.
      <br><br>
  * Added Find Task Command (PR #104)
    * Description: The Find Task Command allows user to type in several keywords and a filtered list matching those
      keywords will be returned.
    * Reason: As a user of ManageMe, when they are too many tasks for one to handle, the Find Task Command is of great
      importance in ensuring that user is still able to find the tasks that was previously added.
    * Difficulty: This was not particularly difficult to implement because of the availability of previous template
      code.
      <br><br>
  * Added List Task Command (PR #123)
    * Description: The List Task Command lists all tasks that are currently in the user's list of tasks.
    * Reason: After different commands, the user might only see some of the tasks, modules and links that might be
      important. As such, the List Task function effectively "resets" the current view to back to the original view, with
      all the tasks.
    * Difficulty: This was not too difficult as I could just fetch the original list from the model again and show it.
      <br><br>
  * Added Task attributes like TaskTime, TagModule (PR #59)
    * Description: Some of the attributes that are core to the implementation of one complete task are
      implemented by me.
    * Reason: When we first started from the brownfield project, major changes had to be made to the Task class to
      suit our plans. The original attributes of phone, address etc were not relevant. As a result, I implemented a few of
      attributes, particularly TaskTime, which stores the start and end date and time, and TagModule, which is stores
      the string value and Module Object of an object.
    * Difficulty: This was of moderate difficulty as a lot of time was spent brainstorming how a task would appear.
      <br><br>

* **Minor Enhancements**
  * Did the use cases for User Guide
  * Wrote the first few paragraphs and the glossary for the developer guide. (PR #40)
  * Wrote the ReadMe file. (PR #18)
  * Edited several PUML Diagrams for developer guide.
    <br><br>

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=zhangchengchuan&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=zhangchengchuan&tabRepo=AY2122S1-CS2103T-W11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)
  <br><br>

* **Other Contributions**
  <br><br>

  * **Contributions to User Guide**
    * Added alert boxes to sections that needed more attention from users.
    * Made QoL changes such as a hyperlink back to the Table of Contents at the end of every section
      <br><br>

  * **Contributions to Developer Guide**
    * Responsible for the writing and editing of the storage component. (PR#112)
    * Wrote the implementation of the time component as well as the reminder functions. (Do now and PR)
    * Helped to type out all the user stories as well as use cases. (PR#46)
    * Contributed to the brainstorming of Glossary terms and Non-Functioning Requirements. (PR#46)
      <br><br>

  * **Contributions to Team-based Tasks**
    * On a regular basis, I would help to organize the team's next meeting through the use of When2Meet.
    * Responsible for the writing and editing of the other website files like settings, testing. (PR #4)
    * Responsible for setting of the team's Continuous Integration and Continuous Deployment. (PR #4)
      <br><br>

  * **Review Contributions**
    * As I was in charge of the Task functionalities with Markus, most of the reviews for his PRs were done by me.
      I approved and merge all of his PRs after reviewing to make sure there was no erroneous code.
    * Once in a while, when I collaborated with Rui Yan to implement UI features, I would also help to review, approve
      and merge his PRs.
      <br><br>
