
echo 'Start build Spring Boot for Producer ...'

cd ./spring-kafka-producer
chmod +x ./gradlew
./gradlew build

cd ..

echo 'List result build files ...'
ls -l ./spring-kafka-producer/build/libs


