#

JAVAC = javac
sources = fr/univ/nantes/extensiblespud/bean/Bean.java \
          fr/univ/nantes/extensiblespud/bean/ConfigurationBean.java \
          fr/univ/nantes/extensiblespud/bean/DescriptionBean.java \
          fr/univ/nantes/extensiblespud/bean/HandlerBean.java \
          fr/univ/nantes/extensiblespud/bean/StatusBean.java \
          fr/univ/nantes/extensiblespud/parser/DescriptionParser.java \
          fr/univ/nantes/extensiblespud/parser/DescriptionPropertiesParser.java \
          fr/univ/nantes/extensiblespud/parser/Parser.java \
          fr/univ/nantes/extensiblespud/parser/PropertiesParser.java \
          fr/univ/nantes/extensiblespud/handler/Handler.java \
          fr/univ/nantes/extensiblespud/handler/HandlerManager.java \
          fr/univ/nantes/extensiblespud/handler/LazyLoaderHandler.java \
          fr/univ/nantes/extensiblespud/Platform.java
classes = $(sources:.java=.class)
project = ExtensibleSpud

all: $(classes)
	jar cvf $(project).jar $(classes)

clean:
	-rm -f $(classes) $(project).jar

%.class: %.java
	$(JAVAC) $<
