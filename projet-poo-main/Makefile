# Cible par défaut
all: exeMain 

# Cible pour compiler Main
testMain:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/Main.java

# Cible pour compiler TestInvader
testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestInvader.java

# Cible pour compiler TestLecteurDonnees
testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

# Cible pour compiler desertOfDeath
testdesertOfDeath:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/desertOfDeath.java

# Cible pour compiler testChemin
testChemin:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/testChemin.java

# Cible pour compiler testEvenement 
testEvenement:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/testEvenement.java

# Exécution des classes
# Exécution de Main sous Windows avec le séparateur `;` pour le classpath
exeMain: testMain
	java -classpath bin:lib/gui.jar Main

exeInvader: testInvader
	java -classpath bin:lib/gui.jar TestInvader

exeLecture: testInvader
	java -classpath bin:lib/gui.jar TestLecteurDonnees

exetestChemin: testChemin
	java -classpath bin:lib/gui.jar testChemin

exetestEvenement: testEvenement
	java -classpath bin:lib/gui.jar testEvenement

# Nettoyage des fichiers compilés sous Windows
clean:
	rm -r bin 
	mkdir bin
	rm -r docs 
	mkdir docs

# Création et ouverture de la javadoc

javadoc:
	javadoc -d docs -sourcepath src -subpackages robots:carte:simulation:plus_court_chemin -classpath lib/gui.jar
	open docs/allclasses-index.html



