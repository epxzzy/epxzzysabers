# Combat Mechanics Documentation - epxzzySabers Mod

This document details the complete combat system in the epxzzySabers mod, based on source code analysis. This information is intended for wiki documentation and should be treated as authoritative for the mod's mechanics.

---

## Table of Contents

1. [Saber Types & Properties](#saber-types--properties)
2. [Attack System](#attack-system)
3. [Blocking & Parrying](#blocking--parrying)
4. [Projectile Deflection](#projectile-deflection)
5. [Stance System](#stance-system)
6. [Special Abilities](#special-abilities)
7. [Animation System](#animation-system)
8. [Server/Client Synchronization](#serverclient-synchronization)
9. [Configuration Values](#configuration-values)

---

## Saber Types & Properties

There are 7 saber types in the mod, each with distinct combat characteristics:

### Single Bladed Saber
- **Attack Damage:** 3 damage per hit
- **Attack Speed:** -2 (slower swing)
- **Parry Range:** 18 blocks
- **Classification:** Light weapon
- **Description:** Balanced single-blade lightsaber

### Dual Bladed Saber
- **Attack Damage:** 4.5 damage per hit
- **Attack Speed:** -1 (moderate swing)
- **Parry Range:** 16 blocks
- **Classification:** Heavy weapon
- **Description:** Two-bladed saber with dual-arm animations

### Rotary Saber
- **Attack Damage:** 3.5 damage per hit
- **Attack Speed:** -1 (moderate swing)
- **Parry Range:** 12 blocks
- **Classification:** Unusual weapon
- **Special Abilities:** Flight, throw mechanic
- **Description:** Spinning saber capable of flight when activated

### Crossguard Saber
- **Attack Damage:** 3 damage per hit
- **Attack Speed:** -2 (slower swing)
- **Parry Range:** 10 blocks
- **Classification:** Light weapon
- **Description:** Defensive saber with crossguard design

### Blaster Saber (Blaster Hybrid)
- **Attack Damage:** 3 damage per hit
- **Attack Speed:** -2 (slower swing)
- **Parry Range:** 14 blocks
- **Classification:** Light weapon
- **Special Abilities:** Plasma bolt projection
- **Description:** Hybrid weapon combining saber with ranged attack

### Saber Pike
- **Attack Damage:** 4.5 damage per hit
- **Attack Speed:** -1 (moderate swing)
- **Parry Range:** 16 blocks
- **Classification:** Heavy weapon
- **Description:** Extended-reach pike-style saber

### Saber Gauntlet
- **Attack Damage:** 3 damage per hit
- **Attack Speed:** -1 (moderate swing)
- **Parry Range:** 12 blocks
- **Classification:** Unusual weapon
- **Special Abilities:** Charge/Surge, disruption field
- **Description:** Gauntlet-mounted saber with charge mechanics

### Shared Properties
- **Max Stack:** 1 (non-stackable)
- **Fire Resistant:** Yes
- **Shield Disabling:** Yes (can break shields on hit)
- **Activation:** All sabers start inactive and must be activated via SHIFT+Click to deal full damage
- **Color Customization:** Supported (except Gauntlet) via Kyber Station

---

## Attack System

### Attack Activation Requirements
- Player must be holding an active saber (activated via SHIFT+Click)
- Saber must have the "ActiveBoiii" NBT tag set to true
- Attack applies full damage only when active (inactive deals minimal damage)

### Attack Variation System (Kewl Fights)

The mod generates 8 different attack variations **randomly** rather than based on hit location. The "kewl fights" system refers to this random variation selection system.

#### Attack Variation Selection
1. System selects a random attack value from 1-8
2. If the selected value is the same as the previous attack, it increments by 1 (capped at 8)
3. If `kewl_fights` is disabled in config, returns 0 (no variation)

**Built-in Raycast System (Commented Out):**
The codebase contains an advanced body-part detection system using raycasting, but it is currently **disabled and commented out**. This system was designed to:
- Raycast from the attacker's eye position through their look vector
- Detect which part of the target's body was hit (head, shoulders, torso, legs)
- Determine left/right side based on target's rotation
However, this code is not active, so attacks are purely random.

#### Attack Animation Variations
Each attack number (1-8) corresponds to a different arm and body animation:
- Different arm angles and rotations for each variation
- Attack animations last 6 ticks (0.3 seconds at 20 ticks/sec)
- Animations are interpolated smoothly between start and end positions

### Damage Calculation

**Minecraft Base System:**
All sabers use Minecraft's standard attribute modifier system:
- Base attack damage: 5 (Minecraft default)
- When **INACTIVE**: Applies +1 damage modifier → **6 total damage**
- When **ACTIVE**: Applies +ATTACK_DAMAGE modifier (varies by weapon)

**Active Damage Examples:**
- Single Bladed (ATTACK_DAMAGE = 3): 5 + 3 = **8 total damage**
- Dual Bladed (ATTACK_DAMAGE = 4.5): 5 + 4.5 = **9.5 total damage**
- Rotary Saber (ATTACK_DAMAGE = 3.5): 5 + 3.5 = **8.5 total damage**

The ATTACK_SPEED modifier is applied separately as an attack speed attribute modifier. Final damage is reduced normally by armor and other protective equipment through standard Minecraft mechanics.

### Sweep Attack (Gauntlet with Charge)

When the Saber Gauntlet is charged (holding right-click for 40 ticks):
- Searches for nearby entities within 1 block radius
- Hits each entity 3 times with 1 damage per hit
- Applies knockback of 0.4 intensity
- This occurs in addition to the primary attack on the initially hit target

---

## Blocking & Parrying

### Blocking (Defensive Right-Click)

When a player right-clicks while holding an active saber, they assume a blocking (defensive) stance.

**Block Activation**
- **Input:** Right-click while holding an active saber
- **Duration:** Maximum 6 ticks (0.3 seconds per block)
- **Animation:** Uses vanilla blocking animation with custom visual rotation

**Block Requirements**

For **Saber-to-Saber Blocking** (player attacks):
1. The defending player is holding and using (right-click) an active saber
2. The attacker is another player/entity with an active saber
3. The defender is facing the attacker:
   - Calculates vector from attacker to defender
   - Checks defender's view vector
   - Requires dot product < 0.0 (vectors point in opposite directions)

For **Projectile Blocking** (projectiles only):
- Can ONLY occur in the first 25 ticks of holding the block
- After 25 ticks of holding, the block cannot deflect projectiles anymore
- This creates a tight "soft parry window" of only 1.25 seconds (25 ticks)

**Block Effects**
- Plays CLASH sound effect
- Cancels incoming saber attacks completely
- Applies a 5-tick cooldown to the defender's saber
- Exception: Flying rotary saber users can maintain flight despite being blocked

---

### Parrying (Active Left-Click Swing)

When a player left-clicks with an active saber (normal swing), projectiles within range are automatically deflected.

**Parry Activation**
- **Input:** Left-click (normal attack swing) while holding an active saber
- **Trigger:** During the swing animation
- **Animation:** Normal attack animation always plays

**Parry Mechanics**
1. When a player swings a saber, the server scans for projectiles within the saber's parry range
2. Any projectiles found within range are immediately redirected
3. Projectiles are redirected toward the **player's current facing direction** (where they're looking)
4. The swing animation always plays regardless of whether projectiles are deflected

**Required Conditions for Projectile Deflection**

For a projectile to be deflected during a parry swing, ALL conditions must be met:

1. **Range Check:** Projectile is within the saber's parry range
   - Single Bladed: 18 blocks
   - Dual Bladed: 16 blocks
   - Rotary Saber: 12 blocks
   - Crossguard: 10 blocks
   - Blaster: 14 blocks
   - Pike: 16 blocks
   - Gauntlet: 12 blocks

2. **Speed Check:** Projectile is moving (has velocity)
   - Requirement: `speed > -2.0`

3. **Angle Check:** Player is generally facing the projectile
   - Creates normalized vector from projectile to player
   - Checks if player's view vector aligns with incoming projectile
   - Requirement: `dot(relative_vector, view_vector) < 0.4` (approximately within a 60° cone in front)

**Deflection Behavior**

**Arrows:**
- Velocity reversed and redirected toward the player's facing direction
- Speed multiplier: 1.5x original velocity
- Plays CLASH sound

**Thrown Rotary Sabers:**
- Special return mechanic with owner-aware velocity scaling
- Return velocity multiplier: 0-5x based on throw count
- Can be caught back into inventory on player contact

**Tridents:**
- Velocity significantly reduced: `multiply(-0.01, -0.1, -0.01)`
- Plays CLASH sound
- Interaction cancelled (prevents normal trident collision)

**Fireballs & Hurting Projectiles:**
- Velocity reversed via xPower, yPower, zPower modification
- Redirected at 0.1x power in player's facing direction
- Ownership transferred to deflecting player (player gets credit for redirected projectile)

---

## Stance System

### Overview
The stance system allows players to assume 7 different combat poses. **Stances are purely cosmetic** and do not affect combat statistics.

### The 7 Stances
1. **Shi-Cho** - Aggressive forward stance
2. **Makashi** - Balanced form
3. **Soresu** - Defensive posture
4. **Atarua** - Wide aggressive stance
5. **Shen/Djem So** - Angled defensive form
6. **Niman** - Hybrid balanced form
7. **Juyo/Vapaad** - High-energy stance

### Activating a Stance
- **Input:** Configurable keybind (default varies, often right-click secondary)
- **Effect:** Player assumes pose while holding the keybind
- **Movement:** Input movement is normalized to 0.0 while stancing
- **Prevention:** Cannot swing arms while stancing

### Stance Animations
Each stance modifies:
- Head position and rotation
- Body (torso) position and rotation
- Both arm positions and rotations
- Both leg positions and rotations

Each pose creates a distinct silhouette and martial form appearance.

### Stance Persistence
- Currently **not persisted** across sessions
- Resets to default stance when player logs in or dies
- Can be cycled through via keybind during gameplay

---

## Special Abilities

### Rotary Saber - Flight (SaberFlight)

**Activation Conditions:**
- Player must be holding a Rotary Saber (active state required)
- Player must be looking upward (head pitched < -35°) OR already airborne
- Player must be in Survival mode (not Creative)
- Rotary Flight cooldown must be zero (not on cooldown)
- Player cannot already be flying

**Mechanics:**
- Enables creative mode flight for the player
- Player can move freely in all directions
- Flight lasts for a configurable duration (default: 200 ticks / 10 seconds)
- After flight ends, a cooldown is applied before flight can be used again (default: 40 ticks / 2 seconds)

**Audio:**
- Plays SWING_RAPID sound every 2 ticks during flight

**Termination:**
- Automatically ends when player stops holding the Rotary Saber
- Automatically ends after reaching maximum duration
- Places player into falling state

### Rotary Saber - Throw (SaberThrow)

**Activation:**
- Input: Dedicated ability keybind (default: X)
- Requires Rotary Saber in hand (active state required)
- Cooldown: 80 ticks (4 seconds)

**Mechanics:**
- Spawns a "ThrownRotarySaber" projectile entity
- Initial velocity: 4.5 blocks per second
- Removed from player's inventory (unless in Creative mode)
- Projectile deals 2 damage per tick to any entity within 2.5 block radius during flight
- Direct impact deals 12 damage
- Returns to player automatically after either:
  - 10 ticks in ground/stopped state
  - 10 ticks of flight time
- Can be deflected by other players' sabers and redirected

**Return Mechanics:**
- Rotary saber automatically returns toward the original thrower
- Return velocity scales based on distance to owner (0-5x multiplier)
- Player can catch the returning saber back into inventory on contact

### Saber Gauntlet - Surge/Charge (SuperCharge)

**Activation:**
- Hold right-click while wearing Saber Gauntlet
- Charging begins immediately

**Charge Phase:**
- Duration to full charge: 40 ticks (2 seconds)
- During charge: Base damage remains unchanged, no visual effect during charge phase

**Charged State (After reaching full charge):**
- Adds "ChargedBoiii" NBT tag to gauntlet
- Bonus damage: +8 damage (stacks on top of base damage)
- Bonus reach: +3 blocks (extends melee reach)
- Extra attack radius: Inflates hitbox by 1 block
- Duration of charged effect: 160 ticks (8 seconds) after achieving full charge
- Sound effect: AXE_SCRAPE on full charge

**Sweep During Charge:**
- When fully charged, nearby entities (within 1 block) are hit automatically by the attack
- Each nearby entity takes hit 3 times with 1 damage per hit
- Applies knockback at 0.4 intensity

**Decay:**
- After 160 ticks of being charged, effect expires
- Damage returns to base value
- Reach returns to normal
- Must charge again to regain bonus

### Saber Gauntlet - Disruption Field

**Activation:**
- Input: Ability keybind (default: X)
- Requires Saber Gauntlet in hand (active state required)
- Cooldown: 60 ticks (3 seconds)

**Effect Radius:** 16 blocks

**Targets Affected:**
- All living entities within 16 block radius
- Excludes: The gauntlet user and other gauntlet users

**Effect on Rotary Saber Users:**
- Immediately stops all item usage (stops flight if in use)
- Adds 80-tick cooldown to their Rotary Saber
- Plays DEACTIVATION sound at 2.0 pitch

**Effect on Other Players:**
- Adds 20-tick cooldown to their main-hand item
- Plays DEACTIVATION sound at 1.0 pitch

**Sound Effect:**
- Plays BELL_RESONATE at 0.5 volume and 2.0 pitch at gauntlet user's location

### Blaster Saber - Plasma Bolt

**Activation:**
- Input: Ability keybind (default: X)
- Requires Blaster Saber in hand (active state required)
- Cooldown: 6 ticks (0.3 seconds) - allows rapid fire

**Mechanics:**
- Spawns a "PlasmaBolt" projectile entity
- Initial velocity: 2.0 blocks per second
- Damage on hit: 2 damage
- Projectile persists for 10 ticks before disappearing
- Leaves ELECTRIC_SPARK particle trail during flight

---

## Animation System

### Attack Animations (8 Variations)

Attack animations are triggered based on the randomly-selected attack variation (1-8). Each animation involves:

**Light Weapons** (Single Bladed, Crossguard, Blaster):
- Single-arm swing animations
- 3 different flourish patterns available
- Duration: 6 ticks

**Heavy Weapons** (Dual Bladed, Pike):
- Dual-arm swing animations
- Different animation patterns from light weapons
- Duration: 6 ticks

**Animation Variations (1-8):**
While there are 8 distinct attack animations, the actual assignment of which animation plays is random. The 8 animations are available for visual variety, but there is no deterministic mapping to specific body parts since the attack selection system was disabled.

### Defense Animations (Blocking)

When a player blocks, an animation plays based on the attack direction:
- Mirrors the 8 attack zones
- Defensive arm and body positioning
- Duration: 6 ticks
- Smooth interpolation via easing function

### Flourish/Idle Animations

Displayed while holding a saber without actively attacking or blocking.

**Light Weapon Flourishes:**
- **X-Cross:** Periodic X-pattern arm motion
- **Circular:** Rotating circular arm motion
- **Spin:** Full rotational spin with phase offset

**Heavy Weapon Flourishes:**
- **Skip-Catch:** Dual-arm synchronized skip-catch pattern
- **Behind-the-Back:** Behind-back rotation with phase-offset arms
- **Figure-Eight:** Symmetrical figure-eight pattern

**Selection Logic:**
- **X-Cross/Skip-Catch:** Default (normal stance)
- **Behind-the-Back:** When player's rotation on X-axis (pitch) ≥ 25° (head tilted downward)
- **Spin/Figure-Eight:** When player is sprinting
- **No Flourish:** When player is already attacking

### Stance Animations (7 Forms)

Each of the 7 stances has unique full-body posing:
- Head: Position and rotation specific to stance
- Torso: Angled or centered based on form
- Arms: Positioned according to martial form
- Legs: Stance width and angle per form

---

## Server/Client Synchronization

### Synced Combat State

Combat state is maintained across server and client using the following synchronized fields:

**Player Entity Data (Synced):**
- `STANCE_PREFERENCE`: Current stance (0-6, displayed as 1-7)
- `ROTARY_FLIGHT_COOLDOWN`: Remaining cooldown ticks (0-40 default)
- `GAUNTLET_CHARGE`: Current charge level (0-160 default)
- `ROTARY_USEDUR`: Remaining flight duration (0-200 default)

**Attack/Defense State (Per-Attack Packet):**
- `attacking`: Boolean - is player currently attacking
- `attackProgress`: Tick counter (0-6)
- `attackPose`: Attack variation (1-8)
- `defending`: Boolean - is player currently blocking
- `defendProgress`: Tick counter (0-6)
- `defendPose`: Block animation variation (0-3)
- `flourishId`: Idle animation ID (0-3)

### Packet System

**Client → Server Packets:**
- `ServerboundSaberAbilityPacket`: Ability keybind press (throw, disruption, plasma bolt, etc.)
- `ServerboundSaberStancePacket`: Stance keybind press/release
- `ServerboundSaberDeflectPacket`: Right-click deflection attempt

**Server → All Clients Packets:**
- `ClientboundPlayerAttackPacket`: Attack variation (1-8) + animation state
- `ClientboundPlayerDefendPacket`: Defense animation state
- `ClientboundPlayerStancePacket`: Stance change
- `ClientboundPlayerFlourishPacket`: Parry animation selection

### Attack Synchronization Flow

1. **Client sends attack input** (clicking target entity)
2. **Server receives attack** via PlayerMixin.AttackWithSaber()
3. **Server determines attack form:**
   - Raycasts from attacker's eye through look vector
   - Identifies body part hit (head, shoulders, torso, legs)
   - Determines left/right side
   - Sets attackPose value (1-8)
4. **Server broadcasts** ClientboundPlayerAttackPacket to all clients with attackPose value
5. **Clients receive packet** and synced via PlayerMixin.SyncATKtoPacket()
6. **Clients animate** based on received attackPose value
7. **Damage applied** via standard Minecraft damage system

### Ability Synchronization Flow

1. **Client presses ability keybind** (default: X)
2. **Client sends** ServerboundSaberAbilityPacket
3. **Server receives and validates:**
   - Checks cooldown status
   - Verifies item is active
   - Checks player state requirements
4. **Server executes ability:**
   - For Rotary Throw: Spawns ThrownRotarySaber entity
   - For Disruption: Applies effects to nearby entities
   - For Plasma Bolt: Spawns PlasmaBolt entity
5. **Server broadcasts** entity spawn packets
6. **Clients render** spawned entities and effects

### Interpolation

Combat animations interpolate between frames:
- **Easing Functions Used:**
  - easeOut: Smooth deceleration
  - easeInOutQuart: Soft start/end with swift middle
  - easeInExpo: Explosive end
  - easeOutBack: Ease out with recoil

---

## Configuration Values

### Config File Location
`/data/epxzzysabers/config.json`

### Live Reload
Configuration changes take effect immediately via `/reload` command or datapack reload without restarting the server.

### Configurable Parameters

```json
{
  "rotary_flight_duration": 200,
  "rotary_flight_cooldown": 40,
  "gauntlet_surge_chargeup": 40,
  "gauntlet_surge_duration": 160,
  "kewl_fights": true
}
```

**`rotary_flight_duration`** (integer, ticks)
- Maximum flight duration for Rotary Saber flight ability
- Default: 200 ticks (10 seconds)
- Range: Any positive integer

**`rotary_flight_cooldown`** (integer, ticks)
- Cooldown duration after Rotary Saber flight ends
- Default: 40 ticks (2 seconds)
- Range: Any positive integer
- Must be in cooldown before flight can be used again

**`gauntlet_surge_chargeup`** (integer, ticks)
- Time required to fully charge Saber Gauntlet
- Default: 40 ticks (2 seconds)
- Range: Any positive integer
- Player must hold right-click for this duration to achieve full charge

**`gauntlet_surge_duration`** (integer, ticks)
- Duration of charged damage/reach bonus on Saber Gauntlet
- Default: 160 ticks (8 seconds)
- Range: Any positive integer
- After this duration, charge effect expires and gauntlet must be charged again

**`kewl_fights`** (boolean)
- Toggle for deterministic attack variation system
- When `true`: Attack variations (1-8) are determined by body part hit
- When `false`: Attack variations are randomly selected (1-8)
- Default: `true`
- Set to `false` to disable body-part-aware combat system

---

## Additional Notes

### Left-Handed Support
All animations have left-handed variants automatically applied when player has left arm preference enabled in Minecraft settings. Arm rotations and positions are mirrored appropriately.

### Shield Compatibility
Sabers can disable shields on hit (vanilla shields and modded shields). Shields break immediately when struck by an active saber.

### Damage Reduction
Saber damage is reduced normally by armor and other protective effects. No armor-piercing capability exists.

### Item State
- All sabers start in an **inactive** state
- Activation requires SHIFT+Click (toggles on/off)
- Resets to inactive when dropped
- Plays activation/deactivation sounds on toggle

