
echo 'Start build Spring Boot for Consumer ...'

cd ./spring-kafka-consumer
chmod +x ./gradlew
./gradlew build

cd ..

echo 'List result build files ...'
ls -l ./spring-kafka-consumer/build/libs


