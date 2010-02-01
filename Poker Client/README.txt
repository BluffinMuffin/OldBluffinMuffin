-----
SETUP
-----
In order to start correctly the project two folders need to be copied in the bin folder:
 -images
 -SVMs

*Sometime, Eclipse flush the bin folder, so each time, 
 you will have to manually copy them in the bin folder.
 
 
---------
SVM FILES
---------
SVM files have been generated with svm_light (http://svmlight.joachims.org/), because
 it is faster and stronger, but it is in C++. So, what we have manually converted
 resulting files into a LIBSVM format.
 
We made a little script to help us, but it uses GnuWin32 programs. The file can
 be found in the folder "scripts" to see what are the differences between the two formats.