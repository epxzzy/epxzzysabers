# Items

This page documents the mod's items and the most relevant implementation details.

Overview:
- Registry: `SaberItems` (item keys and registration)
- Base class: `Protosaber` — contains activation state, attribute handling, parry/flourish hooks and client tooltips.

Primary saber items (registry key -> class) and their constructor stats (`parry range`, `attack damage`, `attack speed`):

- `single_bladed_saber` -> `SingleBladed` — parry range: 3, attack damage: 18, attack speed: -2
- `dual_bladed_saber` -> `DualBladed` — parry range: 4.5, attack damage: 16, attack speed: -1
- `rotary_saber` -> `RotarySaber` — parry range: 3.5, attack damage: 12, attack speed: -1
- `crossguard_saber` -> `CrossguardSaber` — parry range: 3, attack damage: 10, attack speed: -2
- `blaster_saber` -> `BlasterHybrid` — parry range: 3, attack damage: 14, attack speed: -2
- `saber_pike` -> `SaberPike` — parry range: 4.5, attack damage: 16, attack speed: -1
- `saber_gauntlet` -> `SaberGauntlet` — parry range: 3, attack damage: 12, attack speed: -1

Notes on behavior (see `Protosaber` for core mechanics):

- Activation state: sabers use an NBT tag `ActiveBoiii` toggled via `ToggleSaberCore` (Shift + main-hand use).
- Active vs inactive changes attribute modifiers (attack damage/speed) on the item stack.
- Blocking: when active, using the item (right click / use) changes the use animation to `BLOCK` and enables blocking behaviour handled in code.
- Parry: swinging an active/appropriate saber triggers a parry/deflection routine; on client swing a `ServerboundSaberDeflectPacket` is sent and client-side deflection animations are played.
- Flourish: missed swings cause a flourish event which is broadcast to nearby players via `ClientboundPlayerFlourishPacket` (visual animation hook).

Special abilities by item (inherent or peripheral):

- `RotarySaber` (see `RotarySaber`):
	- SaberFlight (inherent): hold use while looking up / in air to enter a timed flight state for quick escapes. Flight is gated by cooldowns stored on the player via mixin (`PlayerHelperLmao`).
	- SaberThrow (peripheral): ability key spawns/throws the saber as a returning projectile.
- `BlasterHybrid` (see `BlasterHybrid`):
	- StunBolt (peripheral): ability key fires a weak stun projectile.
- `SaberGauntlet` (see `SaberGauntlet`):
	- SuperCharge (inherent): hold use to charge; when fully charged grants extra melee damage and reach for a duration.
	- Disruption (peripheral): ability key disrupts nearby sabers (prevents blocking/flying briefly).

Color & tinting:
- Kyber tinting / recolour is supported via the Kyber Station UI (`KyberStationTintMenu`, `KyberStationTintScreen`). Item tint values are stored in the item's `display` NBT (see `Protosaber.getColor()` and `ColourConverter`).
- Client-side item tint registration occurs in `epxzzySabersClient.itemTints`.

Where to look in code:
- `Protosaber` — core item behaviours and NBT format.
- `item/types/*` — concrete item classes and renderers.
- `SaberItems` — registry entries.

Other items:
- `krystal` — kyber crystal (foil, rare)

Notes:
- Item tinting and color logic is applied in `epxzzySabersClient.itemTints`.
- Item behavior (attacks, parry ranges) is implemented in each typed saber class under `item/types/`.
