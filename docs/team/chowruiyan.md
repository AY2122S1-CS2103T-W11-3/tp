---
layout: page
title: Chow Rui Yan's Project Portfolio Page
---

### Project: ManageMe

ManageMe is a **lightweight but powerful desktop application built to help university students manage their school life, available on Windows, Linux and Mac**. You can add your modules, tasks, schedules and online learning resources easily into ManageMe and access them with simple commands.

Given below are my contributions to the project.

* **New Feature**: Added Calendar and panel for tasks happening on a specific date.
  * What it does: Allows users to see a summary of their schedule for the month. A panel exist for users to see the tasks happening on a specific date in greater details.
  * Justification: This allows users to have a better idea of their schedule for the month ahead which makes planning and managing their tasks easier. By listing out the tasks that need to be done, users can better plan out their schedule to ensure that they are not overloaded on some days while underloaded on other days.
  * Highlights: When implementing this feature, we explored the option of using [CalendarFX](https://dlsc.com/products/calendarfx), an open source calendar framework or javaFX 8. However, after skimming through the code base, we decided that it would be an overkill to use it for our calendar feature. Hence, the calendar is implemented from scratch with little to no references to external sources or third-party libraries.

* **New Feature**: Added readMod command.
  * What it does: Display the list of tasks and links associated with a module in a separate window.
  * Justification: This provides an avenue for users to categorise their tasks and links. This command groups all the tasks and links associated with a module together and filters out the rest. User only has to tag each task and link with their corresponding module and does not need to worry about the order for adding each task and link. This makes organisation simpler and easier for users.
  * Highlights: Since `Module` is referenced by `Task` and `Link`, this creates a dependency between the two pairs. The initial implementation in V1.2 resulted in too much coupling between these classes which makes unit testing difficult. We came up with a better implementation in V1.3 to reduce coupling which enhances the overall code quality.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=chowRuiYan&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull request [\#138](https://github.com/AY2122S1-CS2103T-W11-3/tp/pull/138))
  * Changed layout the GUI (Pull requests [\#55](https://github.com/AY2122S1-CS2103T-W11-3/tp/pull/55), [\#61](https://github.com/AY2122S1-CS2103T-W11-3/tp/pull/61))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `nextMonth`, `prevMonth` and `readDay`.
    * Did minor adaptation changes to `Quick Start` section.
  * Developer Guide:
    * Added implementation details of the `Calendar` feature.
