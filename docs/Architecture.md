# Einführung und Ziele
Ein Softwareprojekt soll in der gegebenen Zeit von zwei Semestern nach Software Engineering Standards umgesetzt werden
## Aufgabenstellung
Es soll ein Roguelike-Spiel auf Java Basis entwickelt werden. Dieses soll es bis zu 4 Spielern ermöglichen eine Lobby zu erstellen/beizutreten, mit anderen Spielern in einer Lobby zu interagieren, Dungeons zu erkunden und dabei Gegner zu bekämpfen, sowie das Sammeln und Nutzen von Gegenständen. Treibende Kräfte sind dabei unsere eigene Motivation ein gutes Produkt zu erstellen, langfristig gesehen auch eine gute Note.
Verweis:
- SRS: https://github.com/RodProgrammer/dhbwrogue/blob/main/docs/SRS.md

## Qualitätsziele
| Priorität | Qualitätsziel   | Beschreibung                                                                 | Konkretes Szenario |
|----------:|------------------|------------------------------------------------------------------------------|--------------------|
| 1 | Wartbarkeit | Änderungen am Spiel sollen mit geringem Aufwand verbunden sein. | Ein Entwickler ändert die Lebenspunkte eines Gegners. Diese Änderung soll nur diesen Gegner betreffen und nicht zusätzlich Bewegung oder Verhalten des Gegners. Der Aufwand soll auf wenige abgegrenzte Klassen beschränkt bleiben. |
| 2 | Erweiterbarkeit | Neue Inhalte sollen leicht ergänzt werden können. | Dem Spiel soll ein neuer Gegnertyp mit Spezialfähigkeit hinzugefügt werden. Dies soll möglich sein, indem eine neue Klasse ergänzt wird, ohne Änderungen an anderer Stelle vornehmen zu müssen. |
| 3 | Performance | Das Spiel soll bei größeren Karten auch auf älteren Rechnern flüssig laufen. | Auf einer großen Dungeon-Ebene mit 50 Gegnern soll das Spielen ohne spürbare Verzögerung erfolgen. |

## Stakeholder
| Rolle | Kontakt | Erwartungshaltung |
|------|---------|-------------------|
| Entwickler-Team | Projektmitglieder | Erwartet klare und wartbare Architektur, damit Features einfach umgesetzt und bestehende Funktionen sicher geändert werden können. |
| SCRUM-Master | Projektkoordination | Erwartet eine gut strukturierte Architektur, damit Aufgaben sauber aufgeteilt werden und bearbeitet werden können. |
| Dozent | Dozent | Erwartet eine sauber dokumentierte Architektur, bei der Entscheidungen klar erkenn- und nachvollziehbar sind. |

# Randbedingungen
| Kategorie | Randbedingung | Erläuterung |
|----------|---------------|-------------|
| Technisch | Implementierung in Java | Die Software soll in Java entwickelt werden. Dadurch sind Bibliotheken eingeschränkt. |
| Technisch | Desktop-Anwendung | Das Spiel wird als lokale Anwendung auf einem Rechner ausgeführt und nicht als bspw. Webanwendung bereitgestellt. |
| Organisatorisch | Scrum wird angewandt | Die Entwicklung erfolgt in Sprints, daher müssen Änderungen/Erweiterungen gut integrierbar sein. |
| Übergreifende Konvention | Versionierung mit Git | Der Code muss versionierbar sein. |

# Kontextabgrenzung
Das System umfasst das eigentliche Roguelike-Spiel mit folgenden Elementen:
- Spiellogik
- Darstellung
- Eingabeverarbeitung
- Gegnerverhalten
- Verwaltung des Spielzustands

Nicht Teil des Systems sind das Betriebssystem, die Java-Laufzeitumgebung sowie die Eingabegeräte selbst.
Externe Kommunikationspartner sind:
- Spieler 
- Betriebs- und Dateisystem
- Eingabegeräte wie Tastatur, etc.

## Fachlicher Kontext
| Kommunikationsbeziehung | Eingabe | Ausgabe |
|-------------------------|---------|---------|
| Spieler -> Spiel | Bewegungen, Interaktionen | Spielreaktionen, Spielfortschritt |
| Spiel <-> Dateisystem | Ladeanfrage, Speicheranfrage | Spielstände |
| Spiel -> Spieler | Darstellung von Map, Gegnern, Inventar, anderen Effekten | Spielgeschehen |

Beschreibung: Das Spiel verarbeitet Eingaben des Spielers und aktualisiert auf dieser Basis den Spielzustand und gibt die veränderte Spielsituation wieder aus. Zusätzlich können Spielstände gespeichert und geladen werden. 

## Technischer Kontext
| Kommunikationsbeziehung | Kanal | Beschreibung |
|-------------------------|-------|--------------|
| Spieler <-> Spiel | Tastatur | Der Spieler steuert das Spiel über Tastatureingaben. |
| Spiel -> Anzeige | Grafikbibliothek | Das Spiel wird in einem Fenster auf dem Bildschirm dargestellt. |
| Spiel <-> Dateisystem | Dateizugriff über Java | Speicherung und Laden von Spielständen oder Konfigurationsdateien. |
| Spiel <-> Betriebssystem | JVM/Betriebssystem | Das Spiel läuft auf der Java Virtual Machine und nutzt Ressourcen des Betriebssystems. |

Mapping:
- Befehle werden über die Tastatur an das Spiel übertragen. 
- Spielausgaben werden über ein Fenster dargestellt. 
- Speichern und Laden erfolgt auf dem lokalen Dateisystem.

# Lösungsstrategie

Die Architektur des Roguelike-Spiels basiert auf einer Zerlegung, damit zentrale Qualitätsziele wie Wartbarkeit, Erweiterbarkeit und Performance unterstützt werden.
Wesentliche Entscheidungen sind:
- Verwendung von Java als Programmiersprache 
- Trennung in zentrale Bereiche wie Spiellogik und Darstellung. 
- Einsatz einer klaren Modellierung von Spielobjekten wie Spieler, Gegner oder Items und Karten, um neue Inhalte leichter ergänzen zu können. 
- Entwicklung im Team mit Scrum, damit neue Funktionen schrittweise umgesetzt  werden können. 
Diese Strategie ist geeignet, da ein Roguelike typischerweise viele wiederverwendbare Spielmechaniken benötigt.

# Bausteinsicht

![Component diagramm](assets/Komponentendiagramm.png) <br>
![Package diagramm](assets/Paketdiagramm.png) <br>

# Laufzeitsicht

![Component diagramm](assets/Sequenzdiagramm.png) <br>

# Verteilungssicht
<i>TODO</i>

# Querschnittliche Konzepte
<i>TODO</i>

# Architekturentscheidungen

## ADR1 Server-Authoritative Multiplayer Architektur

<b>Kontext und Problemstellung</b> <br>
Das Spiel ist ein Koop RogueLike. Um Cheats zu verhindern und synchronen Multiplayer zu gewährleisten, muss festgelegt werden, wer den Spielzustand verwaltet.
Frage: Kontrolliert der Server oder der Client den aktuellen Spielzustand? <br>
<b>Betrachtete Varianten</b>
- Client-Authoritative Modell
- Server-Authoritative Modell
  
<b>Entscheidung</b> <br>
Gewählte Variante: Server-Authoritative Modell, denn es erfüllt Anforderungen an Sicherheit und Performance am besten. <br>
<b>Status</b>
Angenommen <br>

<b>Konsequenzen:</b><br>
- Gut, weil Cheats weitgehend verhindert werden
- Gut, weil synchronisierte und einheitliche Spielzustände gewährleistet werden
- Gut, weil spätere Features einfacherer wären
- Schlecht, weil höhere Serverlast entsteht
- Schlecht, weil mehr Bandbreite benötigt wird

# Qualitätsanforderungen
![Quality Utility Management](docs/assets/Quality%20Utility%20Management.png)

# Risiken und technische Schulden
<i>TODO</i>
