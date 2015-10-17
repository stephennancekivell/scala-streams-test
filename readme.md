Background
----------
This is a simple app to generate and parse weather balloon data.

Usage
-----
Run this program with `sbt run` then `sbt run gen` or `sbt run parse`

Approach
--------
Parsing, is done using lots of Try's generating a Iterator[Try[Row]]. Invalid rows are ignored by the flatMap, however in a real world situation these should be monitored.

Data is parsed into strong types to ensure accuracy improve maintainability.

Stats are calculated by parsing over the data once using a Iterator.fold from Row => Results.

Pure functions and immutable data have been used in this project for maintainability.

Outstanding
-----------
Due to time constraints the distance calculations and output Units conversions have been left out.

When calculating the distance traveled you need to group the data by the observatory and sort by time before calculating the distance.


Performance
-----------
Has been tested to generate and parse large files without issue. To verify memory doesnt run out, it has been tested with small JVM memory limits.