# Client d'une médiathèque
## Projet d'*Application Serveur Java*, réalisé entre décembre 2019 et janvier 2020

### CONTEXTE
Une médiathèque se digitalise et veut mettre en place un service de réservation, d'emprunt, et de retour de ses documents.

### RENDU
Le client est généralisé pour les trois services. On estime que l'application serait lancée avec un fichier de configuration adéquat (définissant le port sur lequel se connecter) en fonction du contexte de déploiement du client.

### BILAN
Ce premier projet exploitant la communication entre applications m'a permis de mettre en place celle-ci par le biais de :
- L'utilisation d'une socket pour la communication avec [le serveur](https://github.com/SWIL0Z/serveur-mediatheque)
- La lecture d'un protocole pour la réception des messages

J'ai également dû apporter une rélfexion toute particulière sur la généralisation du client, car j'avais -au départ- créé un client par service. Or, il est bien plus propre de faire un seul client qui adapte son comportement à la situation en fonction de ce qu'il reçoit. La communication entre les deux parties est donc entièrement menée par le serveur.
