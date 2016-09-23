# Object Oriented Programming Project
## A Multi-threaded Cypher Breaker
## By Christopher Weir
### G00309429 


* To start, the runner class gives the user an option to either enter user input or allow the user to select a file using a JFileChooser. 

* Depending on the users choice the runner class reads the input and encrypts it with a key of 5 using the RailFence class.

* The program then outputs the encrypted text along with the matrix of the text in a 2D array in CSV format.

It then parses the quadgrams.txt file into a concurrent hashmap of <string, double> and that map is then used to create a TextScorer object.

The Runner class then creates an instance of CypherBreaker which calls its init() method.

The init() method creates a new Decrypter thread for each key starting from 2 up until the cyphertext.length/2 and starts its own thread with a blocking queue in it.

Each of these threads then decypts the cyphertext using the RailFence decrypt method and then add the plaintext, key and score to a Resultable object.

The result is then put onto the queue and on the CypherBreaker class the queue thread checks if the current result being read off the queue has a higher score than
the local result variable in CypherBreaker. 

If the result is greater the local result it is changed to the higher scoring one.

The synchronized function increment() is called, everytime a result is read from the queue, which will check for the end of the queue and add a poison object.

When the queue has reached the end and the poison object added, it will print the result that it thinks is the most english sounding, the key used to 
get this result, and its score and hopefully it will match users inputed text and the key used to encrypt it.

!!NOTE!!
When choosing a file the JFileChooser opens up in the background of the program you may need to
minimise the program to see it.
