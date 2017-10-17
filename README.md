# TP 2 Programmation Dirigée par la Syntaxe

## Compilation

1. Compiler les nouvelles sources `.java` et `.g` (par exemple avec le make d'*IntelliJ*).
2. Lancer `gradle build` depuis la racine du projet

## Exécution

Faire `java -jar build/libs/TP2.jar ` après avoir compiler au moins une fois.

## Ajouter une expression à la grammaire

1. Ajouter l'expression dans le *lexer* et dans le *parser*
2. Ajouter l'expression dans l'ASD
3. Ajouter dans LLVM l'expression
    - Créer une classe héritant d'`Instruction`
    - Écrire le `toString()` retournant le code dans le langage cible
4. Faire le lien entre ASD et LLVM par `toIR()`

