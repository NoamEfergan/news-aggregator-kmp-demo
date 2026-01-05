#!/bin/bash

# Android Demo Video Recording Script
# Usage: ./record-android.sh [flow-name]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
MAESTRO_DIR="$PROJECT_ROOT/.maestro"
ARTIFACTS_DIR="$MAESTRO_DIR/artifacts/android"
FLOW_NAME="${1:-full-demo}"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="$ARTIFACTS_DIR/${FLOW_NAME}_${TIMESTAMP}.mp4"

# Ensure artifacts directory exists
mkdir -p "$ARTIFACTS_DIR"

# Check for connected device/emulator
if ! adb devices | grep -q "device$"; then
    echo "Error: No Android device/emulator connected"
    echo "Start an emulator or connect a device first"
    exit 1
fi

echo "=== Android Demo Recording ==="
echo "Flow: $FLOW_NAME"
echo "Output: $OUTPUT_FILE"
echo ""

# Set environment variables
export MAESTRO_APP_ID="com.newsaggregator"

# Optionally build and install the app first
if [ "$2" == "--build" ]; then
    echo "Building and installing app..."
    cd "$PROJECT_ROOT"
    ./gradlew :composeApp:installDebug
    echo ""
fi

echo "Recording demo..."
maestro record \
    --format mp4 \
    --output "$OUTPUT_FILE" \
    "$MAESTRO_DIR/flows/demo/${FLOW_NAME}.yaml"

echo ""
echo "=== Recording Complete ==="
echo "Video saved to: $OUTPUT_FILE"

# Open the video on macOS
if [[ "$OSTYPE" == "darwin"* ]]; then
    open "$OUTPUT_FILE"
fi
