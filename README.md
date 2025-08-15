create saburs
create 6.0.3-71

//TODO: make rotary saber put on as an chest item if rightclicked turned off
//TODO: make most of the unaccessible content (flourishes, stances, gaycolour) accessible

ideas n stuff:

# Saber Types:
 - [DONE] Single blade (low attack speed, high attack damage, low parry range)
 - [DONE] Dual blade (high attack speed, mildly low attack damage, high parry range)
 - [DONE] Inquisitorious (dualblade but fly)
 - [DONE] Saber-blaster hybrid (singleblade but pew pew)
 - [DONE] Unstable crossguard (high attack speed, randomised damage, low parry range)
 - [DONE] Saber Pike (high attack speed, mid attack damage, very high parry range)
 - [DONE] Saber gauntlet (high attack speed, low attack damage, low parry range)

//above stats are bs, update them too

# Items: 
 - Proton bombs (cylindrical superpowered tnt)
 - Kyber crystals (Natural && Synthetic)
 - Thermal detonators (a grenade pretty much)
 - Laser Blaster (w/ variations)
 - Beskar alloy
 - Beskar armour (armour that is resistant to saber attacks)
 - [Ark pulse generator](https://starwars.fandom.com/wiki/Arc_Pulse_Generator)
 - grevious mobile

# Mechanics:
 - [DONE] Deflection
 - [DONE] Parry 
 - [DONE] Saber Flight
 - [DONE] Saber Throw
 - [DONE] Saber Shooting?

   
# Stances:
 1. shi-cho [DONE]
 2. makashi [DONE] 
 3. soresu [DONE] 
 4. atarua [DONE] 
 5. shen/djem so
 6. niman [DONE] 
 7. juyo/vapaad 

each form will have a different block, attack, parry and flourish animations/visuals
//TODO expand on above

[DONE]
give every saber a randomised attack area out of nine.
then a player mixin would trace every [attack, miss, block, parry(missed attack)].
[DONE]
a block will get attacker's saber to see what attack to animate playermodel to.
[DONE]
an attack will be traced and animated by the mixin as to the itemstack.
[DONE]
a miss(not hitting any entity, just swinging)(where saber is not parrying(more on this later)) animate flourishes that you worked on.
flourishes will change unless player keeps missing every goddamn time.

[DONE]
a parry will be read from itemstack and handled either flouish or custom animations



 
