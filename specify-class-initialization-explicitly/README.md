# Build Time vs Run Time

## Run Time for class static initializer
This is a default behavior, but the native image size will be bigger, and startup performance is not good as build time since it initializes at compile/build time.

## Build Time for class static initializer
You cannot use build time for all cases such as open socket, etc since it depends on runtime environment. Even for System.getProperty("abc"), build time needs to pass -Dabc=some-value at native-image build command so that some-value is compiled to binary code at build time. 

Later on if at runtime, you pass -Dabc=other-value, the abc will be some-value since the binary code already hardcodes the value, and runtime, it won't trigger class initializer again.

