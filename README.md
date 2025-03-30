# Tododo 📃

## Mise en bouche 🍽️

Parce que j'avais envie de prouver à n'importe qui que, quand on sait faire une techno, on peut les faire toutes.
Je commence cette aventure avec une web app de type __To Do List__ avec 2 technologies que j'avais pratiquées il y a fort longtemps (avec de plus vieilles versions) !

## Technologies utilisées ⚒️

* Frontend 🖼️
  On part sur du bon Vue.js 3 (v3.5.13)

* Backend 💻
  On part sur du bon Java 17 avec SpringBoost 3 (v3.4.4) (sur Visual Studio Code, ce qui rend quand même la prise en main du Java plus agréable, RIP Eclipse)

* Données 💿
  Je n'avais pas envie de m'embêter, un fichier JSON (donc on est sur du NoSQL) fera l'affaire !

## En place pour l'instant 💭

🟩 : Done
🟧 : In progress
🟪 : To do

* Backend Java 17 avec :
  * Mise en place de l'architecture DB (avec un data.json) 🟩
      * ToDoList __has__ Tasks __has__ SubTasks (basique mais efficace) 🟩
  * Mise en place des différentes classes 🟩
  * Mise en place du service ToDoList 🟩
  * Mise en place du CRUD ToDoList 🟩
  * Mise en place des endpoint API REST ToDoList 🟩
  * Mise en place du service Task 🟩
  * Mise en place du CRUD Task 🟩
  * Mise en place des endpoint API REST Task 🟩
  * Mise en place du service SubTask 🟩
  * Mise en place du CRUD SubTask 🟩
  * Mise en place des endpoint API REST SubTask 🟩
  * Test workflow de l'API 🟩
  * Mise en place de test unitaire (JUnit) 🟧
      * Création workflow de test du service ToDoList 🟩
        * Mise en place des tests JUnit 🟩
      * Création workflow de test du service Task 🟩
        * Mise en place des tests JUnit 🟩
      * Création workflow de test du service SubTask 🟧
        * Mise en place des tests JUnit 🟧
  * Mise en place du projet front Vuejs 3 🟪
  * Mise en place des tâche à réaliser pour le front 🟪 

## Notes à moi même 😐

* 27/03/2025 : Pour revenir sur l'utilisation d'un data.json, j'avoue que j'avais la flemme de passer par du mongodb/supabase/firebase.
  Mais pour mon prochain projet sans doute que je passerai par du Firebase Firestoe, parce que why not.
