
# ADR1  Server-Authoritative Multiplayer Architektur

## Kontext und Problemstellung
Das Spiel ist ein Koop RogueLike. Um Cheats zu verhindern und synchronen Multiplayer zu gewährleisten, muss festgelegt werden, wer den Spielzustand verwaltet.  
**Frage:** Kontrolliert der Server oder der Client den aktuellen Spielzustand?

## Betrachtete Varianten
* Client-Authoritative Modell  
* Server-Authoritative Modell  

## Entscheidung
**Gewählte Variante:** *Server-Authoritative Modell*,  
denn es erfüllt Anforderungen an Sicherheit und Performance am besten.

## Status
Angenommen

## Konsequenzen
* **Gut, weil** Cheats weitgehend verhindert werden  
* **Gut, weil** synchronisierte und einheitliche Spielzustände gewährleistet werden
* **Gut, weil** spätere Features einfacher wären  
* **Schlecht, weil** höhere Serverlast entsteht  
* **Schlecht, weil** mehr Bandbreite benötigt wird

---

# ADR2 Datengetriebene Verwaltung von Gegnern und Waffen zur einfachen Erweiterung des Spiels

## Kontext und Problemstellung
Das Spiel soll regelmäßig um neue Gegner, Waffen und weitere Inhalte erweitert werden können. Eine direkte Implementierung neuer Inhalte im Quellcode würde dazu führen, dass bestehende Systeme häufig angepasst und erneut getestet werden müssen.

Wie können neue Gegner, Waffen und Spielinhalte integriert werden, ohne bestehende Systeme verändern zu müssen und mit möglichst geringem Entwicklungsaufwand?

Die Entscheidung unterstützt insbesondere die Qualitätsanforderung W1:
"Neue Gegner oder Waffen sollen in weniger als 4 Stunden integrierbar sein."

## Betrachtete Varianten
* Neue Gegner und Waffen werden direkt im Quellcode implementiert.
* Neue Gegner und Waffen werden über Vererbung und individuelle Klassen hinzugefügt.
* Neue Gegner und Waffen werden datengetrieben über Konfigurationsdateien verwaltet.

## Entscheidung
Gewählte Variante: "Neue Gegner und Waffen werden datengetrieben über Konfigurationsdateien verwaltet", denn diese Lösung ermöglicht die schnellste Erweiterung des Spiels und reduziert Änderungen an bestehenden Systemen auf ein Minimum.

## Status
Angenommen

## Konsequenzen
* **Gut, weil** neue Gegner und Waffen ohne Änderungen an Kernsystemen hinzugefügt werden können.
* **Gut, weil** die Entwicklungszeit für neuen Content deutlich reduziert wird.
* **Gut, weil** die Qualitätsanforderung W1 unterstützt wird.
* **Gut, weil** Balancing-Anpassungen häufig ohne Codeänderungen möglich sind.
* **Schlecht, weil** zusätzliche Parser- und Validierungsmechanismen benötigt werden.
* **Schlecht, weil** fehlerhafte Konfigurationsdateien zu Laufzeitfehlern führen können.
* **Schlecht, weil** komplexes Verhalten weiterhin programmatisch implementiert werden muss.

---

# ADR3 Instanzbasierte Dungeon-Architektur mit zentralem Instanzmanager

## Kontext und Problemstellung
Mehrere Spieler sollen gleichzeitig verschiedene Dungeons betreten und unabhängig voneinander spielen können. Gleichzeitig soll die Generierung neuer Dungeons innerhalb weniger Sekunden erfolgen und der Server mehrere parallele Instanzen verwalten können.

Wie können Dungeons erzeugt und verwaltet werden, sodass kurze Ladezeiten und eine hohe Parallelität erreicht werden?

Die Entscheidung unterstützt insbesondere die Qualitätsanforderungen P2 und S1:

- P2: Dungeon-Generierung in weniger als 4 Sekunden
- S1: Unterstützung von mindestens 5 parallelen Dungeon-Instanzen ohne nennenswerte Leistungseinbrüche

## Betrachtete Varianten
* Alle Spieler teilen sich eine globale Dungeon-Welt.
* Dungeons werden vollständig clientseitig generiert.
* Dungeons werden serverseitig als eigene Instanzen mit einem zentralen Instanzmanager verwaltet.

## Entscheidung
Geählte Variante: "Dungeons werden serverseitig als eigene Instanzen mit einem zentralen Instanzmanager verwaltet", denn diese Variante ermöglicht die parallele Ausführung mehrerer Dungeons, gewährleistet konsistente Spielzustände und unterstützt eine kontrollierte Ressourcenverwaltung.

## Status
Angenommen

## Konsequenzen
* **Gut, weil** mehrere Spielergruppen gleichzeitig unterschiedliche Dungeons spielen können.
* **Gut, weil** die Qualitätsanforderung S1 unterstützt wird.
* **Gut, weil** die Synchronisation zwischen Spielern vereinfacht wird.
* **Gut, weil** der Lebenszyklus von Dungeon-Instanzen zentral verwaltet werden kann.
* **Schlecht, weil** zusätzlicher Verwaltungsaufwand für den Instanzmanager entsteht.
* **Schlecht, weil** mehr Arbeitsspeicher für parallele Instanzen benötigt wird.
* **Schlecht, weil** effiziente Generierungsalgorithmen notwendig sind, um die Generierungszeit von weniger als 4 Sekunden einzuhalten.
