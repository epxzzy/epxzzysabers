epxzzy's sabers

//TODO: make a fuckin wiki lmao
//TODO: add info to wiki lmao
//TODO: make wiki prettyy lmao

ideas n stuff:

# Saber Types [DONE]
 - [DONE] Single blade (low attack speed, high attack damage, low parry range)
 - [DONE] Dual blade (high attack speed, mildly low attack damage, high parry range)
 - [DONE] Rotary Saber (dualblade but fly)
 - [DONE] Saber-blaster hybrid (singleblade but pew pew)
 - [DONE] Crossguard (high attack speed, randomised damage, low parry range)
 - [DONE] Saber Pike (high attack speed, mid attack damage, very high parry range)
 - [DONE] Saber gauntlet (high attack speed, low attack damage, low parry range)
 - Saber Whip (fishing rod like functionality, cant block but can be blocked)
 - Saber Trident (probably do something fun)

//above stats are bs, update them too
//also add more exotic saber types

# Mechanics 
 - [DONE] Parry 
 - [DONE] Deflection (different from parry)
 - [DONE] Saber Flight
 - [DONE] Saber Throw
 - [DONE] Saber Shooting
 - [DONE] Saber Supercharge
 - [DONE] Saber Disruption
 - Pike combat
   
# Stances 
 1. shi-cho [DONE]
 2. makashi [DONE] 
 3. soresu [DONE] 
 4. atarua [DONE] 
 5. shen/djem so [DONE]
 6. niman [DONE] 
 7. juyo/vapaad [DONE]

each form will have a different block, attack, parry and flourish animations/visuals
then a player mixin would trace every [attack, miss(flourish), block, parry(deflection)].

//TODO expand on above

### Attacking [DONE]
every saber a randomised attack out of one to eight upon swing.
ofc these will only be animated if the item is applicable FOR NOW.
(ie; saber gauntlet, saber pike)

might cause an unintentional parry.
(more on parrying below)


### Blocking 
every saber attack can be blocked via holding down right click,
as you would do with a shield. blocking will only work for sabers
though and will not defend against any other form of melee, BUT
it can block ANY projectile IF the saber is held for less than
50 ticks (2.5 Seconds). any more than that and saber will fail to
block.
(more on ranged defence below: parrying)

NOTE: you don't have to worry about blocking the correct attack as 
the saber automatically syncs up with other players to block properly.
just imagine you're fighting with a really deadly shield, only thing
you need to worry about is facing the source of damage.

//TODO: make blocking only work in mainhand.

### Parrying
~~upon either:~~
~~- A. double left clicking~~
~~- B. just left clicking~~
on every (arm swing, attack, punching air, mining blocks. as long as it is being "swung" around)
the saber will perform what is called a parry. a parry deflects
ANY projectile in a certain range
(determined by type of saber equipped: parry range)
towards/in the direction player was facing. certain projectiles
like shulker bullets and (now fixed) tridents are processed
differently so those might fail to change directions by a
player.
(juggling: blocking an incoming projectile
to parry it back to the source)

NOTE: parry is nothing special, its just when you swing a saber
it changes trajectories of projects in your parry range.

//TODO: make flourishes exclusive to parrying


### Posing (Assuming Stance)
crouching during blocking with a saber triggers a stance.
there are eight total stances, and to assume them you need to get
them on your item. this can be done via the kyber station.

a stance does literally nothing but look cool.

NOTE: as of right now there are a few stances missing. posing
overall is buggy or doesn't render properly.

### ~~Missed Attacks (Flourishes)~~ [MERGED WITH PARRYING]
~~a miss(not hitting any entity, just swinging)
(where saber is not parrying) animate flourishes.
flourishes will not toggle between animations unless player keeps
missing every goddamn time. a parry will be read from itemstack
and handled either flouish or custom animations.~~



## FOR NEXT UPDATE:
- attack & block animations for all saber forms
- heavy weapon attack and block animations
- lefty animations for le above
- jarr kai
- pike combat
- more possible saber types (whips etc)
- armour for sabers
- items planned (probably mentioned somewhere below)



### Items
- Proton bombs (cylindrical superpowered tnt)
- Kyber crystals (Natural && Synthetic)
- Thermal detonators (a grenade pretty much)
- Laser Blaster (w/ variations)
- Beskar alloy
- Beskar armour (armour that is resistant to saber attacks)
- [Ark pulse generator](https://starwars.fandom.com/wiki/Arc_Pulse_Generator)
- grevious mobile
- gungan shield and big booma????
- disrupter block as redstone component

