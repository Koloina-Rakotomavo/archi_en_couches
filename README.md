# Banque — Architecture en couches

Projet pédagogique Java du Master MIAGE : gestion des données bancaires, persistance JPA, services métier et API REST pour la consultation et les virements.

**Pour comprendre le code et refaire le TP pas à pas : [Documentation technique](docs/ARCHITECTURE.md).** Ce README sert principalement à installer les outils, créer un projet équivalent et lancer le dépôt.

## 1. Outils et prérequis

| Outil | Version / rôle | Téléchargement officiel |
|---|---|---|
| JDK Java | 21, exigé par le pom.xml | [Eclipse Temurin 21](https://adoptium.net/temurin/releases/?version=21) |
| IntelliJ IDEA | IDE Java | [JetBrains](https://www.jetbrains.com/idea/download/) |
| Spring Boot | 4.1.1, déclaré dans le pom.xml | [Spring Initializr](https://start.spring.io/) |
| Maven | Wrapper fourni : 3.9.16 | [Apache Maven](https://maven.apache.org/download.cgi) |
| MySQL Server | Base locale (port 3306) | [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) |
| MySQL Workbench | Facultatif : visualisation des tables | [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) |
| Postman | Requêtes HTTP et tests de l'API | [Postman](https://www.postman.com/downloads/) |
| Git | Versionnement | [Git](https://git-scm.com/downloads) |

Spring Data JPA, Hibernate, le pilote MySQL, Spring MVC, Thymeleaf, DevTools et les dépendances de test sont téléchargés automatiquement par Maven. Inutile de les installer séparément. Thymeleaf figure dans le POM mais aucune interface graphique n'est nécessaire pour tester l'API REST.

Vérifier le JDK et Git dans un terminal :

```sh
java -version
git --version
```

## 2. Récupérer le projet existant (recommandé)

```sh
git clone https://github.com/Koloina-Rakotomavo/archi_en_couches.git
cd archi_en_couches
```

Dans IntelliJ : **File > Open**, sélectionner le dossier cloné (ou son pom.xml), accepter l'import Maven. Dans **File > Project Structure > Project**, choisir le JDK 21. Si nécessaire, régler aussi le JDK de Maven dans Settings > Build, Execution, Deployment > Build Tools > Maven. Attendre la synchronisation des dépendances.

Alternative : écran d'accueil IntelliJ > Get from VCS / Clone Repository > URL du dépôt > Clone.

## 3. Créer le même projet à partir de zéro

Cette partie sert à refaire le TD ; elle n'est pas nécessaire si le dépôt est déjà cloné.

1. Ouvrir IntelliJ, puis **New Project > Spring Boot** si cette option est disponible. Sinon, ouvrir [start.spring.io](https://start.spring.io/), générer le ZIP, le décompresser puis ouvrir le dossier dans IntelliJ.
2. Choisir **Maven**, **Java**, **JAR**, **Java 21**, groupe `com.example`, artifact `banque`, package `com.example.banque`. Sélectionner Spring Boot 4.1.1 si proposé ; sinon utiliser le POM du dépôt pour reproduire précisément la version et les dépendances.
3. Sélectionner Spring Web (MVC), Spring Data JPA, MySQL Driver, Thymeleaf et Spring Boot DevTools. Vérifier le POM généré par rapport au [POM de référence](pom.xml), y compris les dépendances de test.
4. Cliquer sur Create/Generate, importer Maven, puis créer les packages sous `src/main/java/com/example/banque` : model, repository, service, service.impl, restAPI, dataTransferObjects, mapper. Clic droit sur le package parent > New > Package.
5. Construire ensuite les entités, repositories, services, contrôleurs puis DTO et mappers dans l'ordre décrit dans la [documentation](docs/ARCHITECTURE.md).

## 4. Configurer MySQL

Démarrer le serveur MySQL et exécuter dans Workbench ou le client SQL :

```sql
CREATE DATABASE IF NOT EXISTS banque CHARACTER SET utf8mb4;
```

Le fichier `src/main/resources/application.properties` utilise la base locale `jdbc:mysql://localhost:3306/banque`. Adapter les identifiants à son installation. On peut les surcharger par des variables d'environnement dans la configuration Run d'IntelliJ : `SPRING_DATASOURCE_USERNAME` et `SPRING_DATASOURCE_PASSWORD`. Ne jamais publier de vrais identifiants sur GitHub.

**Attention :** le projet utilise `spring.jpa.hibernate.ddl-auto=create-drop` et le `CommandLineRunner` de démonstration appelle aussi `deleteAll()` sur les types de produit. Utiliser exclusivement une base de TP jetable. Passer à `update` seul ne suffit pas à conserver les données : il faut également désactiver ou adapter ce runner.

## 5. Lancer et tester

Dans IntelliJ, ouvrir `BanqueApplication.java` puis cliquer sur le triangle vert à côté de main. Ou, depuis la racine :

```sh
# macOS / Linux
./mvnw spring-boot:run
# Si permission refusée : chmod +x mvnw
# Compiler
./mvnw clean compile
# Tests unitaires ciblés
./mvnw -Dtest=BanqueMapperTest,MobileBanqueServiceTest test
```

Sous Windows PowerShell :

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd "-Dtest=BanqueMapperTest,MobileBanqueServiceTest" test
```

Si le wrapper n'est pas utilisable, installer Maven et remplacer `./mvnw` par `mvn`. La suite complète (`./mvnw test`) peut nécessiter MySQL selon les tests de contexte. Les commandes sont des instructions de reproduction, pas une certification de réussite sur votre machine.

## 6. Premier test Postman

Une fois le serveur démarré (port Spring Boot par défaut : 8080), créer une requête GET :

```http
GET http://localhost:8080/banque_rest/mobile/produits
```

Puis tester `GET /banque_rest/mobile/produits/MOBILE001/operations`. Pour un virement, choisir POST, Body > raw > JSON :

```http
POST http://localhost:8080/banque_rest/mobile/virements
Content-Type: application/json
```

```json
{
  "compteSource": "MOBILE001",
  "compteDestination": "MOBILE002",
  "montant": 100.25
}
```

Sur les données initiales de démonstration, les soldes attendus sont 899.75 et 600.25 ; un second envoi effectue un nouveau virement. Consulter les deux comptes pour vérifier les modifications. Tous les endpoints, scénarios et codes HTTP sont détaillés dans la [documentation](docs/ARCHITECTURE.md#7-api-rest-et-tests-postman).

## 7. Dépannage

| Symptôme | Vérification |
|---|---|
| Invalid target release: 21 | Sélectionner le JDK 21 pour IntelliJ et Maven. |
| Communications link failure | Démarrer MySQL, vérifier localhost:3306. |
| Access denied / Unknown database | Vérifier les identifiants et créer la base banque. |
| Port 8080 already in use | Arrêter l'autre application ou choisir un autre port Spring. |
| Classes rouges dans IntelliJ | Recharger les projets Maven, contrôler le JDK et la connexion réseau. |
| 404 dans Postman | Vérifier le port, la casse exacte du chemin, le numéro de compte et le démarrage complet. |

## 8. Aller plus loin

- [Architecture, modèle de données, historique des commits et explications](docs/ARCHITECTURE.md)
- [Historique GitHub](https://github.com/Koloina-Rakotomavo/archi_en_couches/commits/main/)
- [Documentation Spring Boot](https://docs.spring.io/spring-boot/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/)
- [Tutoriel Postman](https://learning.postman.com/docs/getting-started/first-steps/)

Projet de TP, non destiné à des données bancaires réelles.
