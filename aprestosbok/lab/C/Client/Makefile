CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

SRC = ${wildcard src/*.cpp}
OBJS = ${SRC:src/%.cpp=build/%.o}
LIBS =
TARGET = client

all: dirs $(TARGET) clean

dirs:	
	mkdir -p bin
	mkdir -p build

clean:
	rm -f $(OBJS) $(TARGET)
	
$(TARGET):	$(OBJS)
	$(CXX) -o bin/$(TARGET) $(OBJS) $(LIBS)
	
 
${OBJS}: build/%.o: src/%.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $<