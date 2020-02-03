# Apex-LootMap (this is a sample of the core codebase)
An Eclipse project for a java-based tool for mapping item spawns in the game Apex Legends

The purpose was to discover more information about the loot spawn system in the game and general PRNG distribution.

It allows the user to initialize game variables such as the flight path and landing zone, and then tracks certain hotkey presses using jNativeHook to allow the user to log items that they find using F1-F8. As the player moves from one zone to another, they can use the numpad directions to update their location from within the game, and the tool moves the local representation of the player using a north-oriented mirror of the game map.

# Results
This tool was used to further investigate some details about item spawns, and while this might create an unfair advantage that was not the intent; The results of this tool were used to create a public WebApp named JumpTool (now abandoned) which was briefly used by the community.

Overall, the tool confirmed some expected item drop rates without having to data-mine the game. Nothing particularly interesting was found other than more information about the general map balance.
