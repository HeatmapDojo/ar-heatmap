CC?=gcc
CXX?=g++
AR?=ar

# Set the default optional flags if none are specified

# Release mode (If just dropping the lib into your project, check out -flto too.)
#
# Note1: OpenMP is (currently) not required by the lib, just for precise benchmarking.
# Note2: the -Wa,-ahl=... part only generates .s assembly so one can see generated code.
# Note3: If you want to add `-flto`, you should add the same -O to LDFLAGS as to FLAGS.
DEFAULT_FLAGS=-O3 -g -DNDEBUG -Wall -Wextra
DEFAULT_LDFLAGS=

# Debug mode
# DEFAULT_FLAGS=-fPIC -Wall -Wextra -D_GLIBCXX_DEBUG -D_GLIBCXX_DEBUG_PEDANTIC -I. -O0 -g -fopenmp -Wa,-ahl=$(@:.o=.s)
# DEFAULT_LDFLAGS=-fopenmp -g

# First set the flags to their defaults if not supplied externally.
CFLAGS?=$(DEFAULT_FLAGS)
CXXFLAGS?=$(DEFAULT_FLAGS)
LDFLAGS?=$(DEFAULT_LDFLAGS)

# Then add those flags we can't live without, unconditionally.
CFLAGS+=-fPIC -I. -pedantic
CXXFLAGS+=-fPIC -I. -std=c++0x
LDFLAGS+=-lm


.PHONY: all benchmarks samples clean

all: libheatmap.a libheatmap.so generators
generators: generators/heatmap_gen generators/heatmap_gen_fit_image
clean:
	rm -f libheatmap.a
	rm -f libheatmap.so
	rm -f generators/heatmap_gen
	rm -f generators/heatmap_gen_fit_image
	find . -name '*.[os]' -print0 | xargs -0 rm -f

heatmap.o: heatmap.c heatmap.h
	$(CC) -c $< $(CFLAGS) -o $@

colorschemes/%.o: colorschemes/%.c colorschemes/%.h
	$(CC) -c $< $(CFLAGS) -o $@

libheatmap.a: heatmap.o $(patsubst %.c,%.o,$(wildcard colorschemes/*.c))
	$(AR) rs $@ $^

libheatmap.so: heatmap.o $(patsubst %.c,%.o,$(wildcard colorschemes/*.c))
	$(CC) $(LDFLAGS) -shared -o $@ $^

generators/lodepng_cpp.o: generators/lodepng.cpp generators/lodepng.h
	$(CXX) -c $< $(CXXFLAGS) -o $@

generators/lodepng_c.o: generators/lodepng.cpp generators/lodepng.h
	$(CC) -x c -c $< $(CFLAGS) -o $@

generators/heatmap_gen.o: generators/heatmap_gen.cpp
	$(CXX) -c $< $(CXXFLAGS) -o $@

generators/heatmap_gen: generators/heatmap_gen.o generators/lodepng_cpp.o libheatmap.a
	$(CXX) $^ $(LDFLAGS) -o $@

generators/heatmap_gen_fit_image.o: generators/heatmap_gen.cpp
	$(CXX) -c $< $(CXXFLAGS) -DFIT_IMAGE -o $@

generators/heatmap_gen_fit_image: generators/heatmap_gen_fit_image.o generators/lodepng_cpp.o libheatmap.a
	$(CXX) $^ $(LDFLAGS) -o $@
