javac TalkParser.java
javac Example.java
./build-native-with-init-at-buildtime.sh
./build-native-with-init-at-runtime.sh

echo ""
echo "------------------ Time Parser Startup Time -----------------------"
echo "1. native image with initialize-at-run-time"
time ./out/runtime-parser

echo ""
echo "2. native image with initialize-at-build-time"
time ./out/buildtime-parser

echo ""
echo "3. JVM JIT"
time java -cp . TalkParser

echo ""
echo "------------------ Time Example Startup Time -----------------------"
echo "1. native image with initialize-at-run-time"
time ./out/runtime-example -Dmessage=hi

echo ""
echo "2. native image with initialize-at-build-time"
time ./out/buildtime-example -Dmessage=hi

echo ""
echo "3. JVM JIT"
time java -cp . -Dmessage=hi Example

echo ""
echo "------------------ Build Image Size -----------------------"
du -sh ./out/* *.class
echo ""

