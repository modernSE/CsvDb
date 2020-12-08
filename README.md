# CsvDB Kata
We want to create a simple database based on CSV-Files.
But in order to be able to write proper unit tests, we need to get rid of the direct file access on the hard disk.

## Task I
We want to introduce a new abstraction layer for the persistence. Then we could swap the file-based implementation with an in-memory approach for our tests.
Also sometime in the near future it is panned to add support for cloud storage, our boss just learned about the "new clouds".

## Task II
Currently the Database is limited to store only Addresses. Extend the implementation in a way, that arbitrary new object types can easily be added in the future.
Verify your concept by implementing a new type "Task".


[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/modernSE/kata-csvdb-java) 
