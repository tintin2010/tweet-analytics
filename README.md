tweet-analytics
===============

sample project which uses titan and storm

STEPS :

* first created a sample application at dev.twitter.com to be able to download tweets

* GET search/tweets API was used to download sample tweets based on queries and have been saved in sample-tweets dir

* Gson is the library used to parse the tweets and pretty print them to understand the entities

* TODO : create a graphdb for the tweets 

* TODO : currently using storm-jms for messaging and apache cassandra, Pavan had suggested storm-kafka and datastax cassandra instead.
