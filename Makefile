all: clean compile run

compile:
	@javac code/*.java -d ./bin

run:
	@java -cp ./bin Programa

clean:
	@rm -v bin/*.class
