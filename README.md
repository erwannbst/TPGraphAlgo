# TP de graph algo

Maël SUARD | Nathanaël GEY | Erwann BESTARD

## TP 1

## TP 2

La classe DirectedGraph et AdjacencyMatrixDirectedGraph contiennent les algorithmes de parcours en profondeur, largeur
et de calcul des composantes fortement connexes.

Les calculs de performance sont effectués dans les tests de ces deux classes.

## TP 3

La classe UndirectedValuedGraph contient l'algorithme de PRIM.

**Q5.**

* *Comment utiliseriez-vous la structure de tas binaire dans votre mise en œuvre de l’algorithme de PRIM ?*

  On utilise la structure de tas binaire pour obtenir à chaque fois l'arête du plus faible poids.


* *Quel opération faut-il ajouter dans la structure de tas binaire ?*

  On ajoute la possibilité de stocker les deux noeuds en plus du poid de l'arête.


* *Quel gain en terme de complexité par rapport à votre algorithme ?*

  Les gains en complexité se font lors de la recherche de l'arrête du plus faible poids. L'implémentation classique à
  besoin de comparer toutes les arêtes dans le graphe O(m).
  Avec la structure de tas binaire, on peut supprimer l'arête du plus faible poids en O(1)
  Si on prend en compte les operations internes pour réorganiser la structure de tas, on obtient O(log(m))

## TP 4

### Consignes

Vous remettrez la partie 6 du TP 2, la partie 2 du TP 3, et les algorithmes de Dijkstra et Belmann en projet archive
zip (contenant les noms) avant le 30 avril au soir.

Ce projet comportera le framework qui vous était proposé avec les algorithmes demandés. Vous y rajouterez également un
fichier texte expliquant simplement où se trouvent vos méthodes et comment les lancer.

Votre archive portera vos noms de famille et sera à déposer sur campus, merci d'envoyer un email avec les noms de votre
groupe (un groupe sera composé de trois personnes). Un dépôt sur un des membres du groupe suffira.

INFO : Pour chaque méthode, vous pouvez choisir une seule implémentation de structure (liste d'adjacence, matrices
etc..)
