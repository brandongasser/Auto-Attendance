# Auto Attendance
Will only work on Windows systems

## Set up
1) Install [java](https://www.java.com/en/) for Windows
1) Install [git](https://git-scm.com/downloads) for Windows
1) Install [Visual Studio Code](https://code.visualstudio.com/download) for Windows
1) Install [Chrome Driver](https://chromedriver.chromium.org/downloads) for latest Chrome version
1) Press code dropdown menu on repository in GitHub and press clipboard button to copy repository url
1) Open Visual Studio Code
1) Press ctrl+shift+P to open taskbar
1) Find type Git: Pull
1) Paste repository url
1) In the terminal window at the botton, type gradlew shadowJar

## Scheduling
1) Open task scheduler
1) Press Task Scheduler Library on the left side
1) Press Create Task... on the right side
1) Put whatever you want in Name
1) Check run whether user is logged in or not and run with highest priveleges
1) In triggers menu, press new, weekly, start on today's date at 9:00:00 AM
1) Check Monday through Friday and press ok
1) In actions tab, press new
1) In first box, copy path to java.exe
1) In second box, type -Dwebdriver.chrome.driver"path to chrome driver" -jar "path to Auto Attendance Web-all.jar" [school email] [school password] [first name] [last name] [grade #]
1) Press ok
1) In conditions tab and under power, uncheck the first two boxes and check the third box
1) Press ok and enter admin password