# SkypeStoryToTxt
tool to convert skype database to separate txt files

#### get started
1. Import sqlite JDBC library
2. Find main.db file in your skype directory and place it in src directory
3. Run in IDE
4. PROFIT in folder "results"

#### returns 
files
named like: 
```
Friends Name.txt
```
in each dialog (with LOCAL formated time) like:
```
-- Friends Name -- 18:42:36; 10 feb 2015; Thu
Hi, dude

-- My name -- 18:43:06; 10 feb 2015; Thu
I hate you
```
## WARNING
1. Save main.db, because i dont give a track about any data loss
2. NO CALLS, SENT FILES etc, JUST text messages.
3. Conferences (>2 participants) are represented weirdly - file with right name may be incorect, but you can find right version nearby.
4. Dont forget №1: Save main.db
