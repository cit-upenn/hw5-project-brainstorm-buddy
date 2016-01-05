# hw5-project-brainstorm-buddy
hw5-project-brainstorm-buddy created by Classroom for GitHub

Description: Brainstorm Buddy is an easy to use, intuitive tool aimed at helping you find resources in the beginning stages of a research paper, project, or study session. Simply type into the main text box words or full sentences about your topic, select what resources you want to pull from, click “create resources” and sit back while we collect information for you!

We have a GUI that is attached to various APIs, firstly, the Alchemy API from IBM and Watson, which we use as a NLP tool to extract keywords and concepts from the user's brainstorm. We then use the Alchemy, Merriam Webster, New York Times, and JSOUP APIs to get the data (links, definitions, encyclopedia abstracts). We use DOM to help parse input that is outputted in xml format and use JSON to parse JSON. We use JSoup to scrape html from websites. This is used for getting previews from DBPedia and for getting links to articles on JSTOR. 

Most methods are commented with Javadoc Style comments. Not all instance variables are commented with javadoc comments, as there are a lot of them, but the in line comments and variable names should make it fairly clear what is going on. 

Tests: Most of the code is either I/O or GUI, but we were able to test the pattern matching used for parsing XML output from the API's. We were also able to test the methods using the DOMParser as well as the JSON parser for correct outputs.

Exceptions: I believe that most of the exceptions are handled well, however, we were not sure how to deal with bad inputs to API. However, the user does not interact with this and exceptions should not crash the output for the user.
