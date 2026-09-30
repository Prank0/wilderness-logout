# Wilderness Logout

A small RuneLite plugin with:

- an always-visible, movable **LOG OUT** overlay button;
- an optional persistent tone while any other player is visibly rendered in the Wilderness;
- normal game logout restrictions preserved, including the combat logout delay.

The overlay can be moved with RuneLite's normal **Alt-drag** gesture. The button only acts while logged in.

The warning intentionally treats every other rendered player the same. It does not calculate Wilderness combat brackets, identify attackable players, or summarize groups of players.

## Compatibility and privacy

Wilderness Logout is written in Java 11 and uses only RuneLite and Java standard-library APIs. It has no native libraries, operating-system commands, network access, file access, telemetry, or third-party services. Its warning tone uses the cross-platform Java Sound API and disables itself gracefully if no compatible audio output is available.

## Development

Build and test with Java 11 on Linux or macOS:

```text
./gradlew clean test
./gradlew run
```

On Windows, use `gradlew.bat clean test` and `gradlew.bat run`.

The `run` task starts RuneLite in developer mode with this plugin loaded.
