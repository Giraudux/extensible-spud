#

all:
	$(MAKE) -C src/main/java && \
	mkdir -p build && \
	mv src/main/java/*.jar build

clean:
	$(MAKE) -C src/main/java clean
