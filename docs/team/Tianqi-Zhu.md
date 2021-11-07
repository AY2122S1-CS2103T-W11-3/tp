---
layout: page
title: Zhu Tianqi's Project Portfolio Page
---

### Project: ManageMe

ManageMe is a one-stop solution for zoom-age students facing problems like missing deadlines, forgetting homework, oversleeping and searching for zoom links. Through efficient resource management, accurate time tracking and frequent reminders, our simple time management app will allow every student to finally unlock their fullest potential.

Given below are my contributions to the project.

* **New Feature**: Added addMod command
  * What it does: Allows users to add a Module to ManageMe.
  * Justification: This allows users
  * Difficulty:  This function was not difficult to implement, however it was modified in different stages of the project
    due to changing needs. Initially in v1.2, a module contains both a name and a Link, thus addMod must be able to add both.
    However, later in v1.3, Link was extracted out to become a standalone class with its own commands,
    thus all dependencies with Link was removed from addMod.

* **New Feature**: Added deleteMod command.
  * What it does: Allows users to delete a Module to ManageMe.
  * Justification: This is not

* **New Feature**: Introducing link functionality with both local filepath and website url
  * What it does: Allows users to search for links to ManageMe.
  * Justification: This allows users to quickly find a module by keywords instead of browsing through the whole module list.
  * Difficulty: DEPENDS ON WHETHER search by character is used.

* **New Feature**: Added findLink command.
  * What it does: Allows users to search for links to ManageMe.
  * Justification: This allows users to quickly find a module by keywords instead of browsing through the whole module list.
  * Difficulty: DEPENDS ON WHETHER search by character is used.

* **New Feature**: Added listLink command.
  * What it does: Allows users to delete a Module to ManageMe.
  * Justification: This allows users to quickly find a module by keywords instead of browsing through the whole module list.
  * Difficulty: DEPENDS ON WHETHER search by character is used.

* **New Feature**: Added openLink command.
* What it does: Allows users to open a file or a folder using the system default application from ManageMe, or open a
url using a local web browser.
* Justification: This allows users to open the folders and websites that they frequently access with only one command,
which is very convenient to use and saves users from the trouble of digging through their inbox with thousands of
* Difficulty: This is challenging. Java desktop doesn't perform well on Linux.

* **Code contributed**: [RepoSense link]()

* **Project management**:
    * Managed most of the issue trackers on GitHub
    * Managed releases `v1.2.1` and `v1.3` (2 releases) on GitHub

* **Documentation**:
    * User Guide:
        * Added documentation for the features `addMod`, `deleteMod`, `findLink`, `listLink`and  [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `delete` feature.

* _{you can add/remove categories in the list above}_
