JAVAC = javac
sources = fr/univ/nantes/extensiblespud/bean/Configuration.java \
          fr/univ/nantes/extensiblespud/bean/Description.java \
          fr/univ/nantes/extensiblespud/proxy/LazyLoaderHandler.java \
          fr/univ/nantes/extensiblespud/Platform.java
classes = $(sources:.java=.class)
project = ExtensibleSpud

all: $(classes)
	jar cvf $(project).jar $(classes)

clean :
	-rm $(classes) $(project).jar

%.class : %.java
	$(JAVAC) $<
