GRAAL_HOME=/home/zpc/.sdkman/candidates/java/25.0.1-graal
"$GRAAL_HOME/bin/native-image" --initialize-at-run-time=TalkParser,Talk -o out/runtime-parser TalkParser
"$GRAAL_HOME/bin/native-image" --initialize-at-run-time=Example -o out/runtime-example Example -Dmessage=native

