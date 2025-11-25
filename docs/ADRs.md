
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
