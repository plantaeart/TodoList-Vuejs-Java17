# Tododo 📃

## Mise en bouche 🍽️

Parce que j'avais envie de prouver à n'importe qui que, quand on sait faire une techno, on peut les faire toutes.
Je commence cette aventure avec une web app de type __To Do List__ avec 2 technologies que j'avais pratiquées il y a fort longtemps (avec de plus vieilles versions) !

## Technologies utilisées ⚒️

* Frontend 🖼️
  On part sur du bon Vue.js 3 (v3.5.13), avec du Pinia pour la gestion d'état et primevue pour les composant css

* Backend 💻
  On part sur du bon Java 17 avec SpringBoost 3 (v3.4.4) (sur Visual Studio Code, ce qui rend quand même la prise en main du Java plus agréable, RIP Eclipse)

* Données 💿
  Je n'avais pas envie de m'embêter, un fichier JSON (donc on est sur du NoSQL) fera l'affaire !

## Lancer le projet 🚀

* Backend :
  * Open VS Code
  * Install this addon pack for Java : https://code.visualstudio.com/docs/languages/java
  * Get the project from Github (Backend/tododo)
  * Launch it
* Front
  * Open VS Code
  * Install VueJs VSCode extension
  * Get the project from Github (Frontend/tododo-frontend)
  * Use "npm i" to get all dependencies
  * Use npm run dev and go to the adress display in the terminal
  * To launch Cyrpess tests :
    * Open one terminal to launch the app in testing mode : npm run dev -- --mode testing --host
    * Open another terminal and run Cypress : npx cypress open

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
  * Mise en place de test unitaire (JUnit) 🟩
      * Création workflow de test du service ToDoList 🟩
        * Mise en place des tests JUnit 🟩
      * Création workflow de test du service Task 🟩
        * Mise en place des tests JUnit 🟩
      * Création workflow de test du service SubTask 🟩
        * Mise en place des tests JUnit 🟩
  * Mise en place du projet front Vuejs 3 🟩
  * Mise en place des tâche à réaliser pour le front 🟩
    * Mise en place des compotement basic (Create, update, delete, switch) for ToDoList 🟩
    * Mise en place du style 🟩
    * Mise en place des compotement basic (Create, update, delete, switch) for Task 🟩
    * Mise en place du style 🟩
    * Mise en place des compotement basic (Create, update, delete, switch) for ToDoList 🟩
    * Mise en place du style 🟩
    * Test that the todo lists behavior are ok with taks and subTasks 🟩
    * Mise en place d'un display en format grille 🟩
    * Mettre la possibilité d'update une tâche (réutilisation addToDoListform component) 🟩
    * Mise en place de la feature du switch des tasks de position 🟩
    * Mise en place de la feature du switch des subTasks de position 🟩
  * Adding some integration tests with cypress 🟩

## Notes à moi même 😐

* 27/03/2025 : Pour revenir sur l'utilisation d'un data.json, j'avoue que j'avais la flemme de passer par du mongodb/supabase/firebase.
  Mais pour mon prochain projet sans doute que je passerai par du Firebase Firestoe, parce que why not.
* 31/03/2025 : Je ne pensais pas un jour refaire du java, mais très franchement avec VS Code, c'est plutôt amusant ! (je ne pensais pas dire ça un jour non plus)
* 01/04/2025 : Wow, mais VueJS + Pinia, c'est un truc de fou en fait 😎
* 03/04/2025 : Des principes très intéressant avec VueJS (au niveau des refs, éléments réactifs et emits --> qui me rappelle les signaux dans Godot).
* 10/04/2025 : J'arrive à la fin des behavior business de cette todo list web app ! Je suis très content d'avoir pu finir tout ça. Celà faisait un bon moment que
                je n'avais pas fait de Java ni de Vuejs, le combo est deux est parfait et Pinia pour la gestion des store est très agréable. Bref, plus que les tests                   d'intégrations et on sera good !
* 21/04/2025 : On arrive sur la fin de ce projet perso ! Très cool à réaliser, la combinaison du VueJS 3 et Java 17 est super. Je recommande cette stack 😏
