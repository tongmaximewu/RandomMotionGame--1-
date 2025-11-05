#!/bin/bash

# COMP 303 Assignment 2 Build Script
# This script compiles, tests, generates JavaDoc, and packages the submission

echo "========================================="
echo "COMP 303 Assignment 2 - Build Script"
echo "========================================="
echo ""

# Clean previous builds
echo "[1/6] Cleaning previous builds..."
rm -rf *.class docs newgame.zip docs.zip
echo "Done."
echo ""

# Compile source files
echo "[2/6] Compiling source files..."
javac *.java
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    exit 1
fi
echo "Done."
echo ""

# Run the program briefly (optional - comment out if not needed)
echo "[3/6] Testing program execution..."
timeout 3 java RandomMotionGameWithObstacles &
sleep 2
pkill -f RandomMotionGameWithObstacles
echo "Done."
echo ""

# Generate JavaDoc
echo "[4/6] Generating JavaDoc..."
mkdir -p docs
javadoc -d docs -author -version -quiet \
    RandomMotionGameWithObstacles.java \
    GamePanel.java \
    GameWorld.java \
    Avatar.java \
    Obstacle.java \
    MotionStrategy.java \
    RandomMotionStrategy.java \
    RandomLinearMotionStrategy.java

if [ $? -ne 0 ]; then
    echo "WARNING: JavaDoc generation had warnings (this is usually okay)"
fi
echo "Done."
echo ""

# Create newgame.zip
echo "[5/6] Creating newgame.zip..."
zip -q newgame.zip \
    RandomMotionGameWithObstacles.java \
    GamePanel.java \
    GameWorld.java \
    Avatar.java \
    Obstacle.java \
    MotionStrategy.java \
    RandomMotionStrategy.java \
    RandomLinearMotionStrategy.java \
    RandomMotionStrategyTest.java \
    RandomLinearMotionStrategyTest.java \
    AvatarTest.java \
    ObstacleTest.java \
    GameWorldTest.java \
    README.txt

if [ $? -ne 0 ]; then
    echo "ERROR: Failed to create newgame.zip!"
    exit 1
fi
echo "Done. Created: newgame.zip"
echo ""

# Create docs.zip
echo "[6/6] Creating docs.zip..."
cd docs
zip -r -q ../docs.zip *
cd ..

if [ $? -ne 0 ]; then
    echo "ERROR: Failed to create docs.zip!"
    exit 1
fi
echo "Done. Created: docs.zip"
echo ""

# Summary
echo "========================================="
echo "BUILD COMPLETE!"
echo "========================================="
echo "Files ready for submission:"
echo "  - newgame.zip (source code + tests)"
echo "  - docs.zip (JavaDoc documentation)"
echo ""
echo "To run the program:"
echo "  java RandomMotionGameWithObstacles"
echo ""
echo "To run tests (requires JUnit 4):"
echo "  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RandomMotionStrategyTest"
echo "  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RandomLinearMotionStrategyTest"
echo "  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore AvatarTest"
echo "  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ObstacleTest"
echo "  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore GameWorldTest"
echo ""


