---
navigation:
  title: "Additional Configuration"
  icon: "minecraft:crafting_table"
  position: 3
  parent: nexus:gamemode_configuration.md
---

# Additional Configuration

You can customize the game mode even further with [/scoreboard commands](https://minecraft.wiki/w/Scoreboard), 
I'll show you some examples on the next pages. 

If there are blue tags in the commands, you can click on them and see the possible choices you can use.

-----

## Kill/Death tracker

**__Death tracker:__** 

- */scoreboard objectives add Deaths deathCount* 

- */scoreboard objectives setdisplay [[displaySlot]](./display_slots.md)* *Deaths*  

**__Kill tracker:__** 

- */scoreboard objectives add Kills playerKillCount* 

- */scoreboard objectives setdisplay [[displaySlot]](./display_slots.md)* *Kills*

-----

## Track Nexus breaking

You can track when a *Nexus* of a certain color is destroyed or enters a different destruction phase: 

__For destruction:__ 

- */scoreboard objectives add GreenNexusDestruction minecraft.mined:nexus.nexus_green* 

- Now all you have to do is test for a value that you need and do certain things that you want

__For switching to another phase:__ 

- */scoreboard objectives add RedNexusPhases minecraft.broken:nexus.nexus_red* 

- Now all you have to do is test for a value that you need and do certain things that you want

