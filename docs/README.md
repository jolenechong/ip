# Simon User Guide

<img src="Ui.png" width="300" alt="Simon Demo Ui">

Manage your tasks efficiently and effortlessly with Simon, a user-friendly task management application. Simon helps you add, organize, and track your tasks, deadlines, and events. It supports single and batch operations, undo functionality, and filtering by date.

Features at a glance
* **Add tasks:** Todos, Deadlines, and Events
* **List tasks:** See all tasks in your list
* **Mark/unmark tasks:** Track single or multiple completed and pending tasks
* **Delete tasks:** Remove single or multiple tasks
* **Multi-commands/Command chaining:** Execute multiple commands at once using `&&`
* **Undo actions:** Revert your last action
* **Search tasks:** Find tasks containing keywords
* **View tasks by date:** List tasks occurring on a specific date
* **Command history navigation:** Use ↑ and ↓ keys to navigate previous commands

## Commands

### Adding Tasks
Simon allows you to add three types of tasks:
1. **Todo**, a simple task without a deadline.
2. **Deadline**, a task that must be completed by a specific date/time.
3. **Event**, a task that happens over a specific time period.

To add a task, use the following commands:
- `todo <description>`: Adds a Todo task.
- `deadline <description> /by <date/time>`: Adds a Deadline task.
- `event <description> /from <start date/time> /to <end date/time>`: Adds an Event task.

**Example:**

```
todo Read book
```

**Outcome:**

```
Got it. I've added this task:
  [T][ ] Read book
Now you have 1 task in the list.
```

---

### Listing Tasks

```
list
```

Displays all tasks in the list with their type, status, and details.

---

### Marking Tasks

**Mark as done:**

```
mark <index>
```

**Mark as not done:**

```
unmark <index>
```

**Multi-mark/unmark:**

```
mark 1,3
unmark 2-4
```

---

### Deleting Tasks

**Delete a single task:**

```
delete <index>
```

**Delete multiple tasks:**

```
delete 1,3
delete 2-4
```

---

### Searching Tasks

**Find tasks containing a keyword:**

```
find <keyword>
```

**Example:**

```
find book
```

**Outcome:**

```
Here are the matching tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: 1 Jan 2026 12:00am)
```

---

### Viewing Tasks on a Specific Date

```
on <date>
```

**Example:**

```
on 1/1/2026
```

---

### Undo Last Action

```
undo
```

Restores the last executed command (adding, deleting, marking, multi-delete, or multi-mark).

---

### Exiting Simon

```
bye
```

Closes the application.

---

### Pro Tips

* Use `&&` to chain multiple commands:

```
todo Read book && deadline Submit report /by 18/2/2026 2359
```

* Use **up/down arrow keys** to navigate through your previous commands for faster input.

---

Simon makes task management simple and fun. Stay organized and never forget a task again! ✅
