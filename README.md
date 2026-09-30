# Wilderness Logout

A small RuneLite plugin with:

- an always-visible, movable **LOG OUT** overlay button;
- a configurable one-press logout hotkey (F12 by default);
- an optional persistent tone while any other player is visibly rendered in the Wilderness;
- normal game logout restrictions preserved, including the combat logout delay.

The overlay can be moved with RuneLite's normal **Alt-drag** gesture. The button and hotkey only act while logged in.

## Development

Build and test with Java 11:

```text
./gradlew clean test
./gradlew run
```

The `run` task starts RuneLite in developer mode with this plugin loaded.
