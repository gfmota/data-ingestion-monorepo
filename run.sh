SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"

docker-compose -f $COMPOSE_FILE up -d --build

mkdir log
> log/metadata-service.log
> log/ingest-service.log
> log/austrian-geosphere-data-collector.log
> log/orchestrator.log

./gradlew :applications:metadata-service:bootRun >> log/metadata-service.log &
PID1=$!

./gradlew :applications:ingest-service:bootRun >> log/ingest-service.log &
PID2=$!

./gradlew :applications:austrian-geosphere-data-collector:bootRun >> log/austrian-geosphere-data-collector.log &
PID3=$!

./gradlew :applications:orchestrator:worker:bootRun >> log/orchestrator.log &
PID4=$!

# Function to stop both applications on exit
function cleanup {
  echo "Stopping applications..."
  kill $PID1
  kill $PID2
  kill $PID3
  kill $PID4
  docker-compose -f $COMPOSE_FILE down
}

# Trap the EXIT signal to ensure cleanup is done
trap cleanup EXIT

# Wait for both applications to finish
wait $PID1
wait $PID2
wait $PID3
wait $PID4