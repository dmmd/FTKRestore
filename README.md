FTKRestore: The FTK Reheirarchizer

Dependencies: commons-io

Description: FTK, rather annoyingly, exports files into a single flat directory. To restore the file system hierarchy export a tab file named FileList.txt from FTK that contains only the 'path' column into the directory that contains the exported files. 

Your directory should look similar to this:

/MyExport
	FileList.txt
	MyFile1.doc
	MyFile2.doc
	MyFile2[1003].doc

Then run the FTKRestore jar with the export directory as the only argument, as so: java -jar FTKRestore.jar MyExport

Voila! the heirarchy is restored within the directory. 



