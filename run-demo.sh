#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p target/classes
find src/main/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d target/classes
java -cp target/classes vn.edu.patterns.extra.demo.ExtraPatternLauncher
