WORK MANAGER RESEARCH
=====================

This project provides a brief overview of WorkManager integration in an Android app and answers some common questions regarding its
behavior.

## What is WorkManager?

WorkManager is an API in Android (part of Android Jetpack) that provides a consistent and guaranteed way to schedule deferrable,
asynchronous tasks that are expected to run even if the app exits or the device restarts. 

Or at least that is how it was designed to work, but we detected, and it was also documented in [Stack Overflow](https://stackoverflow.com/a/52605503/978723) that it is not correctly behaving
when the application is forced to be closed.

. For a deeper description, refer to the official documentation:

  - [WorkManager Overview](https://developer.android.com/topic/libraries/architecture/workmanager)
  - [WorkManager Guide](https://developer.android.com/develop/background-work/background-tasks/persistent)

## Research 

This research aims to verify how periodic and scheduled tasks behave in different scenarios, considering other situations like force stops and reboots.

### Will WorkManager’s tasks be executed if the application is forced to be closed?

No, if the application is force-stopped by the user or the OS, all ongoing WorkManager tasks will be stopped.
The tasks will not resume until the app is manually reopened by the user.

### Will WorkManager’s tasks be executed if the device is rebooted? And if it was previously forced to be closed?

Yes, WorkManager is designed to reschedule tasks after a device reboot. If the app was force-stopped before the reboot,
the tasks will not be executed until the app is reopened by the user.

### How will it behave when we open the app again and the task was not executed?

When the app is reopened after being force-stopped, WorkManager will reschedule the tasks based on their defined intervals.
If a periodic task was not executed due to the app being closed, it will be scheduled to run at its next interval.
If it should to be executed during that period, it will be executed when the application starts and schedule the next execution
on the previously defined interval.

### How will it behave if the task is scheduled and we do not reschedule on the application or activity lifecycle?

Once a task is scheduled with WorkManager, it will continue to run based on its schedule even if you do not explicitly
reschedule it during the application or activity lifecycle events. WorkManager handles the scheduling and rescheduling internally,
ensuring tasks are executed as per the defined constraints.

