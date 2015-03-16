# Introduction #

The page contains information about the project plan


# Details #

## Initial High Level Design ##

### Create flexible interfaces which describes: ###
  * Field Mapper
  * Source Table
  * Destination Table
  * Importer Interface
  * XML configuration
  * Dynamically loading of db drivers

The project is using a scrum methodology with 2 hours work on a week!

### v0.1.0 targets ###
  * table-importer can import tables data from one (source) db to another (dest) db using jdbc abstraction
  * table-importer is command line tool which reads import definitions from a xml configuration file
  * no user interface (command line interface which allows to run multiple importing tasks parallel and independent by each other so if some exception is raised in some of the importing tasks this exception will not crash other importing tasks)