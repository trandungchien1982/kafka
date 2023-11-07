
echo 'Start build Spring Boot for Subscriber ...'

cd ./spring-kafka-subscriber
chmod +x ./gradlew
./gradlew clean build

cd ..

echo 'List result build files ...'
ls -l ./spring-kafka-subscriber/build/libs


