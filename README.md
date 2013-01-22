FTKRestore: The FTK Reheirarchizer
==================================
Dependencies: 
-------------
commons-io

Description: 
------------
FTK, rather annoyingly, exports files into a single flat directory. To restore the file system hierarchy export a tab file named FileList.txt from FTK that contains only the 'path' column into the directory that contains the exported files. 

Your directory should look similar to this:

* /MyExport
    * EAP providers for DM.xls  
    * FileList.txt
    * Information Session Feedback.doc
    * Information Session Feedback[1045].doc  


Example FileList.txt  
source [AD1]/Angie Wang/Ongoing Recovery Program/EAP providers for DM.xls  
source [AD1]/Angie Wang/Correspondence and Notes/Information Session Feedback.doc  
source [AD1]/Angie Wang/Ongoing Recovery Program/Information Session Feedback[1045].doc  

Then run the FTKRestore jar with the export directory as the only argument, as so: java -jar FTKRestore.jar MyExport

Voilà! the hierarchy is restored within the directory. 

* /MyExport
    * /Angie Wang
        * /Correspondence and Notes
            * Information Session Feedback.doc
        * /Ongoing Recovery Program 
            * EAP providers for DM.xls 
            * Information Session Feedback.doc 
    * FileList.txt



Notes
-----
THIS WILL ONLY WORK ON *NIX TYPE SYSTEMS -- I AM LAZY AND THE FILE SEPARATORS ARE HARD CODED AS "/"
FTKRestore removes the first level of hierarchy from a given path for each file to remove the image name from the restored Heirarchy. It will account for duplicate filenames by finding the first matching name in the file list and moving it to the correct target directory and renaming it by removing the FTK designated file ID that it assigns. 







