all:	paxos

paxos:
	javac -d bin src/com/cs380d/application/*.java src/com/cs380d/value/*.java src/com/cs380d/message/*.java src/com/cs380d/framework/*.java src/com/cs380d/role/*.java

test_simple:
	cat tests/1_simpleTest.test | $(cat COMMAND) 

clean:
	rm  bin/*.class 



