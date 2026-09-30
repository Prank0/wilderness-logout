# Wilderness Logout

A small RuneLite plugin with:

- an always-visible, movable **LOG OUT** overlay button;
- a configurable one-press logout hotkey (F12 by default);
- an optional persistent tone while any other player is visibly rendered in the Wilderness;
- normal game logout restrictions preserved, including the combat logout delay.

For RuneLite rules compliance, the player tone deliberately does not calculate combat brackets or distinguish attackable players; any other visibly rendered player triggers it.

The overlay can be moved with RuneLite's normal **Alt-drag** gesture. The button and hotkey only act while logged in.

## About the Fn key

Most keyboards handle the physical `Fn` key in firmware and do not send it to Linux or Java as a standalone key. Because RuneLite cannot bind an event it never receives, this plugin defaults to **F12**. In the plugin configuration you can bind any key or combination RuneLite can see. If your keyboard software can remap `Fn` to F12 (or another real key), the plugin will work with that remapping.

## Development

Build and test with Java 11:

```text
./gradlew clean test
./gradlew run
```

The `run` task starts RuneLite in developer mode with this plugin loaded.
