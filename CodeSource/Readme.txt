
Projet fin 1ère année : PhotoBoost

Auteurs : Florent Catiau-Tristant & Mehdi Haddad, groupe B


Photoboost est conçue principalement pour Linux !


Structure de l'archive .tgz :
-----------------------------

 pda/						: racine
	|-- Readme.txt				: ce fichier
	|-- ant						: fichiers d'automatisation de ANT
	|-- build       			: fichiers .class, javadoc
	|-- data					: données et images de l'application
	|    `-- parametres.out			: fichier de données contenant les paramètres de l'application
	|    `-- font 					: répertoire contenant la police d'écriture utilisée
	|    `-- img 					: images utilisées par l'application
	|-- lib						: librairies exterieures à conserver (vide)
	|-- src						: sources Java
	|    `-- pda     				: paquetage principal de l'application
	`-- ww          			: répertoire de travail du développeur (!! n'existe pas encore après désarchivage !!)


Mode d'emploi :
---------------

	Extraire l'archive .tgz : (Ce qui logiquement, est déjà fait.)
	`````````````````````````
	$ tar xzvf pda.tgz


	Initialisation :
	````````````````
	$ cd pda/ww	
	$ ln -s ../data data					: Création des liens symboliques pour une utilisation manuelle de ant
	$ ln -s ../ant/build.xml build.xml
	$ ln -s ../Exemple_Images Exemple_Images


	Lancement :
	```````````
	$ cd pda/
	$ java -jar pda.jar						: Lancement de l'application

	Un dossier "Exemple_Image" a été rajouté à l'archive afin de donner un dossier par defaut contenant des images, au cas où l'utilisateur n'ai aucune image.

	/!\ Attention : L'archive pda.jar doit obligatoirement se situer dans le même répertoire que le répertoire "data" /!\
	Si vous souhaitez déplacer l'archive, n'oubliez pas de déplacer le répertoire data avec, 
	ou alors créez un lien symbolique vers l'archive à l'aide de la commande suivante :
	$ ln -s pda.jar [chemin cible]


	Lancements des tests :
	``````````````````````
	$ cd pda/ww
	$ java -cp ../build/class TestEffect


