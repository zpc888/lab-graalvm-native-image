# GraalVM 2026 Dev Notes

## How does GraalVM Native Image work?

### Processing

| Input | Process | Output |
|-------|---------|--------|
|All classes from application, libararies, and VM | Loop (AOT Compilation, Image Headp Writing | Native executable |
|Application, libraries, JDK, Substrate VM | Points-to Analysis, Run Initializations, Heap Snapshotting | Code in Text Section, Image Heap In Section |

### Output Type
| Fully Dynamic | Most Static | Fully Static |
|-------|---------|--------|
| OS must include all dynamically linked libs | OS only need provide libc | No Libs provided by OS |
|Application Code | Application Code + stdlibc++, zlib, etc | Application Code + Stdlibc++, zlib, etc + musl libc |
| stdlibc++, zlib, etc | glibc | |
| glibc | | |

### Optimization Tips
1. Optimization Level: -O0 -O1 -O2 -O3 -Ob -Os
2. Using G1GC instead of default SerialGC

### JIT vs AOT

| JIT | AOT |
|-----|-----|
|Load JAR files from disk | Load executable from disk |
|Uncompress class files | Execute at peak performance |
|Verify class definitions | |
|Execute in terpreter (~20x slower) | |
|Gather profiling feedback| |
|Compile to machine code| |
|Execute at peak performance| |

## Issue holding back so long

```text
When building a spring boot 3.3.13 application with JAP hibernate to MS SQL server, if not including some internal jars, it can be built and run native executable. After including some internal jars, native build is ok, but when run executable, it gives the below error: Error create bean with name "xyzEntityManagerBuilder": org.hibernate.bytecode.spi.BytecodeProvider: Provider org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl could not be instantiated at ... Caused by: com.oracle.svm.core.jdk.UnsupportedFeatureError: No classes have been predefined during the image build to load from bytecodes at runtime
```
