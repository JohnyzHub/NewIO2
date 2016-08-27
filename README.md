# MyJavaIOPractice
Practicing New IO using JDK8

--------------------------------------------------------
FileChannle Example : 
--------------------------------------------------------
      Read file using RandomAccessFile/Paths
      get channel of File  ... FileChannel
      Initialize a bytebuffer with a initial capacity
      read data from channel to bytebuffer    fileChannel1.read(bytebuffer)
      When the bytebuffer reaches capacity, FileOutPutStream on a new file (i.e.outputstream into the file), get the channel of that file.
      Flip bytebuffer, write the content to the file (fileChannel2.write(buffer))
      and clear the buffer to get read for next read.
