GRAAL_HOME=/home/zpc/.sdkman/candidates/java/25.0.1-graal
"$GRAAL_HOME/bin/native-image" --initialize-at-build-time=TalkParser,Talk -o out/buildtime-parser TalkParser
"$GRAAL_HOME/bin/native-image" --initialize-at-build-time=Example -o out/buildtime-example Example -Dmessage=native

