# car-tp2
# Jean-Serge Monbailly

Implémentation d'une passerelle REST pour le serveur FTP

Exécution
=========

+ Lancer le serveur FTP sur localhost:2000
+ Lancer l'application web
+ Accéder aux pages suivantes :
	+ http://localhost:8080/rest/api/test/ 			---------> authentification
	+ http://localhost:8080/rest/api/test/list		---------> lister les fichiers disponibles
	+ http://localhost:8080/rest/api/test/get/<filename>	---------> télécharger le fichier filename
	+ http://localhost:8080/rest/api/test/delete/<filename> ---------> supprimer le fichier indiqué


Travail Réalisé
===============
+ Implémentation de la commande FTP LIST grâce à la méthode GET de HTTP
+ Implémentation de la commande FTP GET grâce à la méthode GET de HTTP
+ Implémentation de l'authentification FTP (USER + PASS) grâce à la méthode POST de HTTP
+ Implémentation de la commande FTP RMD grâve à la méthode GET de HTTP

+ Début non-concluant d'implémentation de la commande RMD avec la méthode DELETE

