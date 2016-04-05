#

all:
	$(MAKE) -C src/main/java && \
	mkdir -p build && \
	mv -f src/main/java/*.jar build

clean:
	-rm -rf build ; \
	$(MAKE) -C src/main/java clean
