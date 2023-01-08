# Club Disco BACKEND

## INSTALLATION

1)clonner l'archive et l'ouvrir dans un IDE.  

2)verifier que le projet est bien en version Java 17.  

3)A l'aide de Uwamp ou autre logiciel permettant d'utiliser phpmyadmin créer en local une BDD mysql s'intitulant "club_disco".  

4)Vérifier dans le fichier se trouvant dans src > main > ressources > application.properties que les parametres suivant soit à jour avec votre BDD:  

spring.datasource.url=jdbc:mysql://localhost:3306/club_disco   (url de votre BDD)  
spring.datasource.username=root   (user de votre BDD)  
spring.datasource.password=root   (mdp du user de votre BDD)  

5) Executer la commande "mvn clean install" nb: pour utiliser les commandes mvn sous Windows il faut installer maven ou l'executer grace à un IDE telle que IntellJ ou Eclipse.  

6) Lancer le back-office avec la commande "mvn spring-boot:run"

# Club Disco FRONTEND

## Installation

1) Clonner le répertoire depuis la branche main

2) Installer Node JS sur le site officiel (https://nodejs.org/en/download/)

3) Lancer l'invite de commande (CMD)

4) Installer Angular avec la commande suivante 'npm install -g @angular/cli'

5) Naviguer vers le dossier du projet avec la cmd (ex : cd Documents, cd GitHub, cd T-JAV-501-LIL-5-1-dashboard-lucas.redjaimia)

6) Installer les modules du projet avec la commande 'npm install'

7) Lancer le projet avec la commande 'npm start'

8) Accéder au site à l'adresse 'http://localhost:8081/'

# Club Disco FONCTIONNALITÉS

En tant que user :

    - S'abonner à différents service(Spotify,Météo,Zippopotam et Nasa)

    - Utiliser différents widgets :
		Spotify:
			-Consulter ses playlists
			-Consulter son profil
			-Consulter les titres favoris
			-Consulter les artistes favoris
			-Consulter les nouvelles sorties
			
		Météo:
			-Afficher la météo d'une ville à l'heure actuel
			-Afficher la météo d'une ville pour les 7 prochains jours
			
		Nasa:
			-Afficher une photo du jour avec une description
			
		Zippopotam:
			-Afficher la longitude et latitude d'une ville par rapport à son code postal


En tant qu'admin :

    - Gestion des rôles

    - Suppression d'users

