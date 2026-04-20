# Architecture

Top-level code packages and responsibilities:

- `com.epxzzy.epxzzysabers` — main mod classes (`epxzzySabers`, `epxzzySabersClient`) and central registration.
- `item` — item definitions and typed sabers (see `SaberItems` and `item/types/*`).
- `block` — custom blocks and `KyberStation` block.
- `entity` — custom entities and renderers (rotary saber, plasma bolt).
- `rendering` — item & player pose renderers and rendering helpers.
- `networking` — `SaberMessages` and packet handlers for client/server sync.
- `screen` — UI screens, menu types, and HUD renderers.
- `mixin` — runtime mixins for player/model behaviour.
- `util` — helper utilities (config, animation tick holder, colour conversion).

Registration pattern:
- DeferredRegister is used for items, blocks, entities, sounds and screens. See `SaberItems`, `SaberBlocks`, `SaberEntities`, `SaberSounds`.
- Client-only registration (renderers, item tints, screen factories) happens in `epxzzySabers.ClientModEvents` and `epxzzySabersClient`.
