
# Qualitätsbaum Clandestine Dungeons

## Merkmal: Performance
### Verfeinert: Reaktionszeit
- **P1 – Server verarbeitet Kampfaktionen <200 ms**  
  (Nutzen: flüssiger Multiplayer-Kampf; Risiko: hohe Serverlast) **High**, **High**
 - **Quelle:** Spieler  
- **Auslöser:** Kampfaktion (Angriff, Fähigkeit, Interaktion)  
- **Artefakt:** Game-Server (Combat System)  
- **Umgebung:** Mehrere Spieler im Kampf  
- **Antwort:** Aktion wird berechnet und an alle Clients synchronisiert  
- **Antwortmaß:** <200 ms

### Verfeinert: Generierungszeit
- **P2 – Dungeon-Generierung <4 Sekunden**  
  (Nutzen: kurze Ladezeiten; Risiko: ineffiziente Algorithmen) **Medium**, **High**
 - **Quelle:** Spieler  
- **Auslöser:** Betritt einen neuen Dungeon  
- **Artefakt:** Dungeon-Generator  
- **Umgebung:** Client- oder Serverseitige Generierung  
- **Antwort:** Dungeon wird erzeugt, Gegner, Räume, Loot erstellt
- **Antwortmaß:** <4 Sekunden

---

## Merkmal: Skalierbarkeit
### Verfeinert: Instanzenverwaltung
- **S1 – Server unterstützt 5 parallele Dungeon-Instanzen**  
  (Nutzen: mehrere gleichzeitige Spieler; Risiko: Ressourcenengpässe) **High**, **High**
- **Quelle:** Spielsystem  
- **Auslöser:** Viele Spieler öffnen Dungeons  
- **Artefakt:** Instanzmanager  
- **Umgebung:** hohe Parallelität
- **Antwort:** Server erzeugt oder verwaltet 5 Instanzen ohne nennenswerte Leistungseinbrüche
- **Antwortmaß:** Keine CPU- oder RAM Engstellen
---

## Merkmal: Zuverlässigkeit
### Verfeinert: Serverstabilität
- **Z2 – Server läuft 12h ohne Neustart stabil**  
  (Nutzen: dauerhafter Multiplayer-Betrieb; Risiko: Memory Leaks) **Medium****High**
 - **Quelle:** Serverbetrieb  
- **Auslöser:** Dauerhafter Multiplayer-Betrieb  
- **Artefakt:** Serverprozess  
- **Umgebung:** 12 Stunden Dauerlauf unter Auslastung  
- **Antwort:** Server bleibt fehlerfrei und stabil  
- **Antwortmaß:** Keine Memory Leaks oder Crashes innerhalb 12h

---

## Merkmal:  Wartbarkeit
### Verfeinert: Erweiterbarkeit
- **W1 – Neue Gegner oder Waffen in <4h integrierbar**  
  (Nutzen: schnelle Content-Updates; Risiko: schwierige Implementierung? **High** **High**
- **Quelle:** Entwickler  
- **Auslöser:** Neue Content-Anforderung  
- **Artefakt:** Code 
- **Umgebung:** Entwicklungsumgebung  
- **Antwort:** Neuer Gegner/Waffe/Content kann ohne Änderung bestehender Systeme erstellt werden  
- **Antwortmaß:** maximal 4 Stunden Aufwand
---

## Merkmal: Sicherheit
### Verfeinert: Manipulationsschutz
- **SE1 – Serverseitige Validierung aller Aktionen**  
  (Nutzen: Anti Cheat; Risiko: erhöhte Serverlast) **High** **High**
 - **Quelle:** Client  
- **Auslöser:** Aktion wie Angriff, Bewegung, Item-Interaktion  
- **Artefakt:** Server 
- **Umgebung:** Online-Multiplayer  
- **Antwort:** Server prüft Gültigkeit und führt nur erlaubte Aktionen aus  
- **Antwortmaß:** 99% Validierungsquote aller Aktionen
