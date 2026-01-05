#!/bin/bash

# iOS Demo Video Recording Script
# Usage: ./record-ios.sh [flow-name] [device-name]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
MAESTRO_DIR="$PROJECT_ROOT/.maestro"
ARTIFACTS_DIR="$MAESTRO_DIR/artifacts/ios"
FLOW_NAME="${1:-full-demo}"
DEVICE_NAME="${2:-iPhone 16 Pro}"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="$ARTIFACTS_DIR/${FLOW_NAME}_${TIMESTAMP}.mp4"

# Ensure artifacts directory exists
mkdir -p "$ARTIFACTS_DIR"

echo "=== iOS Demo Recording ==="
echo "Flow: $FLOW_NAME"
echo "Device: $DEVICE_NAME"
echo "Output: $OUTPUT_FILE"
echo ""

# Check if simulator is running
if ! xcrun simctl list devices booted | grep -q "$DEVICE_NAME"; then
    echo "Booting simulator: $DEVICE_NAME"
    DEVICE_UDID=$(xcrun simctl list devices available | grep "$DEVICE_NAME" | head -1 | grep -oE '[A-F0-9-]{36}')

    if [ -z "$DEVICE_UDID" ]; then
        echo "Error: Device '$DEVICE_NAME' not found"
        echo "Available devices:"
        xcrun simctl list devices available | grep -E "iPhone|iPad"
        exit 1
    fi

    xcrun simctl boot "$DEVICE_UDID"
    echo "Waiting for simulator to boot..."
    sleep 10
fi

# Open Simulator app
open -a Simulator

# Set environment variables
export MAESTRO_APP_ID="com.newsaggregator"

# Optionally build and install the app first
if [ "$3" == "--build" ]; then
    echo "Building iOS app..."
    cd "$PROJECT_ROOT"
    ./gradlew :composeApp:syncFramework
    xcodebuild -project iosApp/iosApp.xcodeproj \
        -scheme iosApp \
        -destination "platform=iOS Simulator,name=$DEVICE_NAME" \
        -derivedDataPath build/ios \
        build

    # Install the app
    APP_PATH=$(find build/ios -name "*.app" -type d | head -1)
    if [ -n "$APP_PATH" ]; then
        xcrun simctl install booted "$APP_PATH"
    fi
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

# Open the video
open "$OUTPUT_FILE"
