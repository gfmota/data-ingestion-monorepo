SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"

docker-compose -f $COMPOSE_FILE up -d --build

mkdir log
> log/metadata-service.log
> log/ingest-worker.log
> log/ingest-api.log
> log/austrian-geosphere-data-collector.log
> log/orchestrator.log

./gradlew :applications:metadata-service:bootRun >> log/metadata-service.log &
PID1=$!
./gradlew :applications:ingest-service:worker:bootRun >> log/ingest-worker.log &
PID2=$!
./gradlew :applications:ingest-service:api:bootRun >> log/ingest-api.log &
PID3=$!
./gradlew :applications:austrian-geosphere-data-collector:bootRun >> log/austrian-geosphere-data-collector.log &
PID4=$!
./gradlew :applications:orchestrator:worker:bootRun >> log/orchestrator.log &
PID5=$!

echo "Applications running"
echo "Use Ctrl + C to stop docker containers and applications"

# Function to stop both applications on exit
function cleanup {
  echo "Stopping applications..."
  kill $PID1
  kill $PID2
  kill $PID3
  kill $PID4
  kill $PID5
  docker-compose -f $COMPOSE_FILE down
}

# Trap the EXIT signal to ensure cleanup is done
trap cleanup EXIT

# Wait for both applications to finish
wait $PID1
wait $PID2
wait $PID3
wait $PID4
wait $PID5
