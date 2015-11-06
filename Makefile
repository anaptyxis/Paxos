all:	paxos

paxos:
	javac -d bin src/com/cs380d/application/*.java src/com/cs380d/value/*.java src/com/cs380d/message/*.java src/com/cs380d/framework/*.java src/com/cs380d/role/*.java

clean:
	rm  -r -f bin/* 



