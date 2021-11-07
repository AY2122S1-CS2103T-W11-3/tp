---
layout: page
title: Zhao Jinxin's Project Portfolio Page
---

### Project: ManageMe Level 3

ManageMe is a **lightweight but powerful desktop application built to help university students manage their school life,
available on Windows, Linux and Mac**. You can add your modules, tasks, schedules and online learning resources easily
into ManageMe and access them with simple commands.

Given below are my contributions to the project.

* **New Feature**: Added editMod command
  * What it does: Allows users to edit an existing module.
  * Justification: This allows users to update the information of the modules they take. For example,
    when a user take new modules in a new school term, or found a typo in a previously entered module,
    the user can change the module information accordingly.
  * Difficulty: This function was not difficult to implement, however it was modified in different stages of the project
    due to changing needs. Initially in v1.2, a module contains both a name and a Link, thus editMod must be able to edit both.
    However, later in v1.3, Link was extracted out to become a standalone class with its own commands,
    thus all dependencies with Link was removed from editMod.

* **New Feature**: Added findMod command.
  * What it does: Find modules whose names contain given keywords.
  * Justification: This allows users to quickly find a module by keywords instead of browsing through the whole module list.
  * Difficulty: DEPENDS ON WHETHER search by character is used.


* **Testing**:
  * Wrote test utilities for Module features.
  * Wrote test cases for editMod and findMod Parsers and Commands.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=JinxinZhao315&tabRepo=AY2122S1-CS2103T-W11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Documentation**:
  * User Guide:
    * Added documentation for features `editMod` and `findMod`.
    * Added most of the feature screenshots.
    * Edited `Introduction` and `Quick Start` sections to tailor to our project.
    * Enhanced readability through color coding and formatting.
    * Added content table and "back to content table" navigation.
  * Developer Guide:
    * Wrote architecture descriptions of the Logic component.
    * Added implementation details of the Module features.
    * Wrote use cases for Generic Add, Delete, Edit, Find and List commands.
    * Edited User stories to tailor to our project.

