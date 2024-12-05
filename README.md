# UltimateStoragePlus

UltimateStoragePlus est un plugin Minecraft qui permet aux joueurs de créer et d'utiliser des barils de stockage avec des capacités de stockage extrêmement élevées. Ce plugin utilise une base de données SQLite pour gérer les données de stockage.

## Fonctionnalités

- **Barils de stockage** : Créez des barils avec des capacités de stockage allant de 1K à 256M.
- **Commandes** : Utilisez des commandes pour donner des barils de stockage aux joueurs.
- **Événements** : Gérez les événements de placement, de clic et de destruction des barils.
- **Base de données** : Utilise SQLite pour stocker les données des barils placés.

## Installation

1. Téléchargez le fichier JAR du plugin.
2. Placez le fichier JAR dans le dossier `plugins` de votre serveur Minecraft.
3. Démarrez ou redémarrez votre serveur Minecraft.

## Configuration

Le plugin génère automatiquement un fichier de configuration lors de son premier démarrage. Vous pouvez modifier ce fichier pour personnaliser les messages et d'autres paramètres.

## Commandes

- `/ultimatestorageplus give <type>` : Donne un baril de stockage du type spécifié au joueur.

## Permissions

- `ultimatestorageplus.command.use` : Permet d'utiliser les commandes du plugin.

## Événements

- **BarrelPlaceEvent** : Géré lorsque un baril est placé.
- **BarrelClickEvent** : Géré lorsque un joueur clique sur un baril.
- **BarrelBreakEvent** : Géré lorsque un baril est détruit.

## Développement

### Prérequis

- Java 17 ou supérieur
- Gradle

### Compilation

Pour compiler le projet, exécutez la commande suivante :

```sh
./gradlew build