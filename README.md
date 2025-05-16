# Zombie-Game-Design-Pattern
Hello, a game was made by Yerassyl, Saniya, Assiya. Have a nice time

## How to Play
1. Compile: `javac src/*.java`
2. Run: `java -cp src Main`
3. Controls (for each player):
   - w/a/s/d: move up/left/down/right
   - k: attack
   - e: use ability
   - q: quit
Players take turns, Jack goes first, then Emily after Brad.

## Features
- Three players: Jack, Emily and Brad
- Zombies with different behaviors (regular, runner, huge, poison)
- Waves get harder, boss every 5th wave
- Health kits spawn randomly
- Abilities: Jack (more damage), Emily (faster shots), Brad (push zombies)
- Score saved to highscores.txt

## Design Patterns
- Singleton: GameManager, ScoreManager
- Factory: EntityFactory for players/zombies
- Strategy: ZombieBehavior, WaveStrategy
- Observer: GameEventManager for events
- Command: Move, Attack, Ability commands
- Decorator: DamageBoostWeapon, FireRateBoostWeapon
