docker system prune -f
docker-compose build db --no-cache
if ($LastExitCode -ne 0) {
    exit $LastExitCode
}
docker-compose up db -d
docker compose build spring --no-cache --progress=plain
if ($LastExitCode -ne 0) {
    exit $LastExitCode
}
docker-compose build spring-test --no-cache --progress=plain
if ($LastExitCode -ne 0) {
    exit $LastExitCode
}
docker-compose up spring-test --exit-code-from spring-test
if ($LastExitCode -ne 0) {
    exit $LastExitCode
}
docker-compose up spring