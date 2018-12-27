PATRICK DELONG cs435 2200 mp

PROGRAMS WERE WRITTEN IN JAVA 10 IN UBUNTU

OPTION 1 - HUFFMAN
TO RUN PROGRAM DO THE FOLLOWING EXCLUDING QUOTATIONS...
1)  COMPILE JAVA PROGRAM IN TERMINAL TYPE "javac henc_2200.java"
2)  COMPILE JAVA PROGRAM IN TERMINAL TYPE "javac hdec_2200.java"
3)  RUN THE PROGRAM WITH THE FOLLOWING COMMANDS
    "java henc_2200 filename" where filename is the file which you wish to compress
    "java hdec_2200 filename.huff" where filename is the file which has the .huff extension

DEBUG REPORT FOR HUFFMAN

    The huffman tree builds out correctly however something may be slightly wrong with the encoding and decoding. I was able to
    compress very small text files and decode them however when they got large enough or I gave it a binary file it would
    seem to compress it nicely, but on the decompression it would blow up over its original size.
