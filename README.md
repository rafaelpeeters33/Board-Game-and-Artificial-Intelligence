# Board Game & Artificial Intelligence

Biosphere7 is a turn-based strategic board game engine developed in Java, played on a 14x14 grid between two competing players.
Each turn, players deploy plants on empty cells to optimize board coverage and accumulate vitality points across 40 timed rounds.
The system features strict rule validation, legal move computation, and a dynamic scoring engine covered by custom JUnit test suites.
It embeds autonomous AI agents communicating over TCP, using lookahead search and greedy heuristics under a 2-second per-turn limit.

---

## Project Structure
```
.
├── nbproject/          # NetBeans internal project configuration
├── src/                # Java source code
├── test/               # JUnit unit tests
├── build.xml           # Ant build and automation script
├── manifest.mf         # JAR packaging metadata
└── README.md           # Project documentation
```
