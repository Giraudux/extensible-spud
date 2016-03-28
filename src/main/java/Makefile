JAVAC = javac
sources = fr/univ/nantes/extensiblespud/bean/Configuration.java \
          fr/univ/nantes/extensiblespud/bean/Description.java \
          fr/univ/nantes/extensiblespud/proxy/LazyLoaderHandler.java \
          fr/univ/nantes/extensiblespud/Platform.java
classes = $(sources:.java=.class)

all: ExtensibleSpud

ExtensibleSpud: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<

jar: $(classes)
	jar cvf ExtensibleSpud.jar $(classes)
