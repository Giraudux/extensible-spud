#

all:
	$(MAKE) -C src/main/java && \
	mkdir -p build && \
	mv src/main/java/*.jar build

clean:
	-rm -r build ; \
	$(MAKE) -C src/main/java clean
