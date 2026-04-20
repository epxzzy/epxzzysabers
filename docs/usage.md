# Usage & Controls

Controls
- `Shift + Right-Click (MAIN HAND)` — Toggle saber active state (calls `Protosaber.ToggleSaberCore`).
- `Right-Click` (use while active) — Hold to block (use animation changes to `BLOCK`).
- `Left-Click / Swing` — Normal attack; missed swings trigger flourishes and perform parry/deflect checks.
- `Left Alt` (default) — Saber ability key (see per-item ability in `items.md`). Default mapping: `KeyBinding.SABER_ABILITY_KEY`.
- `V` (default) — Saber stance key (`KeyBinding.SABER_STANCE_KEY`).

Mechanics
- Activation: Active sabers store `ActiveBoiii` in NBT. Active state applies attribute modifiers for attack damage/speed.
- Parry: swinging a saber in range can deflect incoming projectiles; deflection logic triggers packets and client animations.
- Blocking vs Parrying: Blocking is a held-use defensive state. Parrying is swing-based and can redirect projectiles.
- Abilities: some sabers (rotary, gauntlet, blaster) have abilities bound to the ability key; abilities are implemented in item classes under `item/types/`.

Kyber Station (recolouring)
- Place a `kyber_station` block and interact to open the recolour UI (`KyberStationTintScreen`). You can set HSL or RGB and apply to a dyeable lightsaber item.

Notes
- Many mechanics are gated by player-side cooldowns and mixin-provided persistent data (see `PlayerHelperLmao` in `util/`).
