# Documentation Utilisateur 
# Application de Simulation de Robots Pompiers

## Introduction
Cette application simule une équipe de robots pompiers se déplaçant dans un environnement naturel pour éteindre des incendies. Vous pouvez observer la simulation et contrôler divers aspects du comportement des robots. L'objectif principal est de gérer efficacement les ressources pour éteindre les incendies rapidement.

---

## Prérequis
  
**Java Development Kit (JDK)** : Version 1.8 ou supérieure.  
**IDE** (comme IntelliJ, Eclipse, ou Visual Studio Code) pour faciliter l'exécution.

---

## Compilation et Exécution

### 1. Compiler les test
Pour compiler le projet, un Makefile a été réalisé. Assurez-vous que vous êtes dans le répertoire racine du projet et utilisez les commandes suivantes dans le terminal :

-Pour compiler le fichier principal : 

```bash
make exeMain
```

-Pour compiler le fichier testChemin : 

```bash
make exetestChemin
```

-Pour compiler le fichier Invader : 

```bash
make exeInvader
```
-Pour compiler le fichier testEvenement : 

```bash
make exetestEvenement
```

-Pour créer et ouvrir la javadoc : 
```bash
make javadoc
```

-Pour nettoyer votre cache : 
```bash
make clean
```
\newpage 

### 2. Commandes lors de l'execution 

 Pour utiliser le simulateur, vous avez acces à 2 champs ainsi que 4 boutons.  
 le bouton **Lecture** permet de lancer la simulation, le bouton **Début** la réinitialise et le bouton **Suivant** permet de passer à l'affichage suivant. Pour quitter lma simulation il y a le bouton **Quitter**.  
 Le premier champs permet de regler le temps entre 2 affichages en millisecondes et le deuxième permet de regler le nombre de pas simulés entre 2 affichages. 
 Afin d'avoir un affichage correcte, il est conseillé d'utiliser un temps de 200ms entre 2 affichages. 


## Conclusion 

Cette documentation vous offre un aperçu rapide de l'utilisation de l'application de simulation de robots pompiers. Pour plus de détails techniques, consultez la documentation complète générée par Javadoc.