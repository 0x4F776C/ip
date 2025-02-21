# TrashBot - User Guide
[![Build and Test Java](https://github.com/0x4F776C/ip/actions/workflows/gradle.yml/badge.svg)](https://github.com/0x4F776C/ip/actions/workflows/gradle.yml)

![Ui](https://github.com/0x4F776C/ip/docs/Ui.png)

## Introduction

TrashBot is designed to keep track of your daily tasks, deadlines, and events. With its intuitive interface, TrashBot makes task management effortless and efficient.

## Quick Start

1. Ensure you have Java 17 installed on your computer
2. Download the latest `TrashBot.jar` from the release page
3. Open a command terminal and navigate to the folder containing the JAR file
4. Run the application with `java -jar TrashBot.jar`
5. Start managing your tasks with the commands listed in the Features section

## Features

### 1. Adding a todo task: `todo`

Creates a simple task without any specific deadline.

Format: `todo TASK_DESCRIPTION`

Examples:
* `todo Buy groceries`
* `todo Call mom`

Expected outcome:
```
Got it. I've added this task:
  [T][] Buy groceries
Now you have 1 task in the list.
```

### 2. Adding a deadline task: `deadline`

Creates a task with a specific completion deadline.

Format: `deadline TASK_DESCRIPTION /by DATE_TIME`

Acceptable date formats:
* `MMM d yyyy h:mma` (e.g., "Jan 5 2025 2:30pm")
* `MMM dd yyyy h:mma` (e.g., "Jan 05 2025 2:30pm")
* `yyyy-MM-dd HHmm` (e.g., "2025-01-05 1430")

Examples:
* `deadline Submit report /by Jan 15 2025 5:00pm`
* `deadline File taxes /by 2025-04-15 2359`

Expected outcome:
```
Got it. I've added this task:
  [D][] Submit report (by: Jan 15 2025 5:00pm)
Now you have 2 tasks in the list.
```

### 3. Adding an event: `event`

Creates a scheduled event with start and end times.

Format: `event EVENT_DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`

Examples:
* `event Team meeting /from Jan 20 2025 10:00am /to Jan 20 2025 11:30am`
* `event Summer vacation /from 2025-06-01 0800 /to 2025-06-15 2200`

Expected outcome:
```
Got it. I've added this task:
  [E][] Team meeting (from: Jan 20 2025 10:00am to: Jan 20 2025 11:30am)
Now you have 3 tasks in the list.
```

### 4. Deleting a task: `delete`

Removes a task or multiple tasks from your list.

Format: `delete INDEX` or `delete INDEX_{1} INDEX_{2} INDEX_{3} ... INDEX_{n}`

Examples:
* `delete 2` (deletes the second task)
* `delete 3 4 5` (delete tasks 3, 4, and 5)

Expected outcome:
```
Got it. I've added this task:
  [D][] Submit report (by: Jan 15 2025 5:00pm)
Now you have 2 tasks in the list.
```

### 5. Marking a task as done: `mark`

Changes the status of a task to completed.

Format: `mark INDEX`

Example:
* `mark 1`

Expected outcome:
```
Nice! I've marked this task as done:
  [T][✗] Buy groceries
```

### 6. Unmarking a task: `unmark`

Changes the status of a completed task back to pending.

Format: `unmark INDEX`

Example:
* `unmark 1`

Expected outcome:
```
Okay, I've marked this task as not done:
  [T][] Buy groceries
```

### 7. Listing all tasks: `list`

Displays all tasks currently stored in TrashBot.

Format: `list`

Expected outcome:
```
----------------------------------------------------------
Here are the tasks in your list:
  1.[T][✗] Buy groceries
  2.[E][] Team meeting (from: Jan 20 2025 10:00am to: Jan 20 2025 11:30am)
----------------------------------------------------------
```

### 8. Finding tasks: `find`

Searches for tasks containing specific keywords.

Format: `find KEYWORD`

Examples:
* `find mee`
* `find ing`
* `find meet`

Expected outcome:
```
Here are the matching tasks in your list:
  1.[E][✗] Team meeting (from: Jan 20 2025 10:00am to: Jan 20 2025 11:30am)
```

### 9. Exiting the program: `bye`

Saves all tasks and exits TrashBot.

Format: `bye`

Expected outcome:
```
[PROGRAM WILL TERMINATE]
```

## Command Summary

| Command | Format                                                                |
| ------- |-----------------------------------------------------------------------|
| **Todo** | `todo TASK_DESCRIPTION`                                               |
| **Deadline** | `deadline TASK_DESCRIPTION /by DATE_TIME`                             |
| **Event** | `event EVENT_DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`     |
| **Delete** | `delete INDEX` or `delete INDEX_{1} INDEX_{2} INDEX_{3} ... INDEX_{n}` |
| **Mark** | `mark INDEX`                                                          |
| **Unmark** | `unmark INDEX`                                                        |
| **List** | `list`                                                                |
| **Find** | `find KEYWORD`                                                        |
| **Exit** | `bye`                                                                 |

## FAQ

**Q: Will TrashBot work on my operating system?**  
A: TrashBot is compatible with Windows, macOS, and Linux systems that have Java 17.

**Q: Are my tasks saved automatically?**  
A: Yes, tasks are automatically saved after each command. You can safely exit using the `bye` command, knowing your data is secure.

**Q: Can I backup my task data?**  
A: Yes, your tasks are stored in a file named `trashbot.sav` in `/data/` which is in the same directory as the application. You can copy this file to create backups.

## Glossary

* **Task**: Any item that you create in TrashBot (Todo, Deadline, or Event)
* **Todo**: A simple task without any time constraints
* **Deadline**: A task that must be completed by a specific date and time
* **Event**: An activity that occurs during a specific time period
* **Index**: The number shown next to a task in the list view, used to identify tasks for commands like `delete` and `mark`