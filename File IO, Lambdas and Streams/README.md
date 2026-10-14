# File IO, Lambdas and Streams

Compile and run from the project root:

```text
javac -d out "File IO, Lambdas and Streams\FileIOLambdasStreamsExamples.java"
java -cp out FileIOLambdasStreamsExamples "File IO, Lambdas and Streams"
```

Enter five lines when prompted. The program saves them as `five-lines.txt`
in the assignment folder and reads them back. It then reports line, word,
and character counts for that file. The character count excludes line
breaks.

The program also demonstrates a `Predicate` with `removeIf`, and uses
streams to print the names of sample `Student` records whose marks are
above the class average, sorted alphabetically.

## Learner CSV and reports

`learners.csv` is a sample input file with the columns `name,marks`. Replace
or extend its rows to use other learners. Names must not contain commas;
marks must be integers from 0 to 100. Grade bands are A for 80-100, B for
60-79, and C for 0-59.

The program uses `Collectors.groupingBy` and writes `learners-A.txt`,
`learners-B.txt`, and `learners-C.txt` in the assignment folder. Each report
is sorted by learner name. Invalid rows or unreadable files cause a clear
error rather than producing an incomplete report.
