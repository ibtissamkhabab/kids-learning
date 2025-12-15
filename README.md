# 🎨 Kids Learning - Application d'Apprentissage des Alphabets

<div align="center">

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)

**Une application éducative interactive pour aider les enfants à apprendre les alphabets français et
arabe**

</div>

---

## 📋 Table des Matières

- [Présentation](#-présentation)
- [Fonctionnalités](#-fonctionnalités)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Architecture](#-architecture)
- [Technologies](#-technologies)
- [Structure du Projet](#-structure-du-projet)
- [Améliorations Futures](#-améliorations-futures)

---

## 🎯 Présentation

**Kids Learning** est une application Android éducative conçue spécialement pour les enfants. Elle
permet d'apprendre et de pratiquer l'écriture des lettres de l'alphabet français et arabe de manière
ludique et interactive.

### Objectifs Pédagogiques

- ✍️ Apprendre à reconnaître les lettres
- 🎨 S'entraîner à tracer les lettres avec le doigt
- 🔊 Associer chaque lettre à son son
- 🧠 Mémoriser grâce aux interactions sonores et visuelles

---

## ✨ Fonctionnalités

### 🔤 Alphabet Français

- Affichage des 26 lettres de A à Z
- Son pour chaque lettre
- Interface de tracé pour pratiquer l'écriture
- Possibilité d'effacer et recommencer

### 🔤 Alphabet Arabe

- Affichage des 28 lettres arabes (ا à ي)
- Son pour chaque lettre
- Taille de police adaptée à l'écriture arabe
- Interface de tracé personnalisée

### 🎨 Interface de Tracé

- Canvas interactif pour dessiner
- Bouton pour jouer le son de la lettre
- Bouton pour effacer et recommencer
- Retour à la liste des lettres

### 🎵 Sons et Interactions

- Son joué automatiquement à l'ouverture d'une lettre
- Bouton pour répéter le son à volonté
- Feedback sonore lors des interactions

### 🌈 Interface Enfant

- Grandes icônes colorées
- Navigation simple et intuitive
- Animations fluides entre les écrans
- Design adapté aux jeunes enfants

---

## 📥 Installation

### Prérequis

- **Android Studio** : Arctic Fox ou supérieur
- **JDK** : Version 11 ou supérieure
- **Android SDK** : API 24+ (Android 7.0)
- **Kotlin** : Version 1.9+

### Étapes d'Installation

1. **Cloner le projet**
   ```bash
   git clone https://github.com/votre-repo/kids-learning.git
   cd kids-learning
   ```

2. **Ouvrir dans Android Studio**
    - Ouvrir Android Studio
    - File > Open > Sélectionner le dossier du projet

3. **Synchroniser Gradle**
    - Android Studio va automatiquement synchroniser les dépendances
    - Ou cliquez sur "Sync Project with Gradle Files"

4. **Compiler et Lancer**
    - Connecter un appareil Android ou démarrer un émulateur
    - Cliquer sur le bouton "Run" (▶️)

---

## 🎮 Utilisation

### Écran d'Accueil

Au lancement, l'enfant voit deux grandes cartes :

- **Alphabet Français** - avec une image représentative
- **الأبجدية العربية** - Alphabet Arabe avec une image

### Sélection d'un Alphabet

1. Cliquer sur la carte de l'alphabet souhaité
2. Une animation glisse vers l'écran de la liste des lettres

### Liste des Lettres

- Les lettres sont affichées dans une grille (3 colonnes)
- Chaque lettre est dans une carte colorée
- Cliquer sur une lettre pour l'ouvrir

### Écran de Tracé

1. **Grande lettre affichée** en haut
2. **Zone de dessin** au centre pour tracer
3. **Boutons d'action** :
    - 🔊 **Son** : Rejouer le son de la lettre
    - 🗑️ **Effacer** : Nettoyer le canvas
    - ⬅️ **Retour** : Retourner à la liste

### Tracé de la Lettre

- Utiliser le doigt pour dessiner sur l'écran
- La ligne apparaît en bleu
- Le tracé reste visible jusqu'à ce qu'on efface

---

## 🏗️ Architecture

L'application suit une architecture **moderne et propre** :

```
app/
├── MainActivity.kt              # Point d'entrée
├── fragments/
│   ├── HomeFragment.kt         # Écran d'accueil
│   ├── AlphabetListFragment.kt # Liste des lettres
│   └── DrawingFragment.kt      # Écran de tracé
├── adapters/
│   ├── LetterAdapter.kt        # Adapter pour les lettres
│   └── AlphabetAdapter.kt      # Adapter pour les alphabets
├── models/
│   └── Letter.kt               # Modèle de données
├── views/
│   └── DrawingView.kt          # Vue personnalisée de dessin
└── utils/
    ├── ErrorHandler.kt         # Gestion des erreurs
    ├── StorageUtils.kt         # Utilitaires de stockage
    └── NetworkUtils.kt         # Utilitaires réseau
```

### Composants Principaux

#### 1. **MainActivity**

- Activité principale conteneur
- Gère les transactions de fragments
- Point d'entrée de l'application

#### 2. **Fragments**

- **HomeFragment** : Choix de l'alphabet (Français/Arabe)
- **AlphabetListFragment** : Affiche la grille des lettres
- **DrawingFragment** : Permet de tracer la lettre

#### 3. **DrawingView**

- Vue personnalisée utilisant Canvas
- Gère le dessin tactile
- Fonctions d'effacement et d'annulation

#### 4. **Adapters**

- Adaptateurs RecyclerView pour l'affichage des listes
- Gestion des clics et interactions

---

## 🛠️ Technologies

### Langages et Frameworks

- **Kotlin** - Langage principal
- **Android SDK** - Framework Android
- **Material Design** - Design system Google

### Bibliothèques

```kotlin
// AndroidX Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")

// Material Design
implementation("com.google.android.material:material:1.11.0")

// UI Components
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.cardview:cardview:1.0.0")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
```

### APIs Android Utilisées

- **Canvas API** - Pour le dessin
- **MediaPlayer** - Pour les sons (à implémenter)
- **ToneGenerator** - Sons actuels
- **Fragment API** - Navigation
- **RecyclerView** - Listes

---

## 📁 Structure du Projet

```
enfantApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/enfantapp/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── HomeFragment.kt
│   │   │   │   ├── AlphabetListFragment.kt
│   │   │   │   ├── DrawingFragment.kt
│   │   │   │   ├── Letter.kt
│   │   │   │   ├── LetterAdapter.kt
│   │   │   │   ├── AlphabetAdapter.kt
│   │   │   │   ├── DrawingView.kt
│   │   │   │   └── utils/
│   │   │   │       ├── ErrorHandler.kt
│   │   │   │       ├── StorageUtils.kt
│   │   │   │       └── NetworkUtils.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/          # Fichiers XML de mise en page
│   │   │   │   ├── drawable/        # Images et icônes
│   │   │   │   ├── values/          # Styles, couleurs, dimensions
│   │   │   │   ├── anim/            # Animations
│   │   │   │   └── mipmap/          # Icônes de l'app
│   │   │   ├── assets/              # Fichiers JSON des données
│   │   │   └── AndroidManifest.xml
│   │   └── test/                    # Tests unitaires
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🚀 Améliorations Futures

### Phase 1 - Sons Réels

- [ ] Remplacer ToneGenerator par de vrais fichiers audio
- [ ] Enregistrer la prononciation native de chaque lettre
- [ ] Ajouter des effets sonores ludiques

### Phase 2 - Gamification

- [ ] Système de récompenses (étoiles, badges)
- [ ] Progression sauvegardée
- [ ] Statistiques de l'enfant
- [ ] Mode quiz pour tester les connaissances

### Phase 3 - Base de Données

- [ ] Implémenter Room Database
- [ ] Sauvegarder la progression
- [ ] Historique des tracés
- [ ] Profils d'enfants multiples

### Phase 4 - Enrichissement

- [ ] Ajouter les chiffres (0-9, ٠-٩)
- [ ] Mots simples associés aux lettres
- [ ] Images pour chaque lettre
- [ ] Mode traçage guidé

### Phase 5 - Accessibilité

- [ ] Support pour lecteurs d'écran
- [ ] Mode daltonien
- [ ] Tailles de police ajustables
- [ ] Support multi-langues (anglais, espagnol...)

---

## 📱 Captures d'Écran

*(À ajouter après compilation)*

1. Écran d'accueil
2. Liste des lettres françaises
3. Liste des lettres arabes
4. Écran de tracé

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

---

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

---

## 👥 Auteurs

- **IBTISSAM** - Développement initial

---

## 🙏 Remerciements

- Inspiration : Applications éducatives pour enfants
- Design : Material Design Guidelines
- Communauté Android pour les ressources

---

## 📞 Contact

Pour toute question ou suggestion :

- 📧 Email : [votre-email@example.com]
- 💬 Issues : [GitHub Issues](https://github.com/votre-repo/kids-learning/issues)

---

<div align="center">

**Fait avec ❤️ pour l'éducation des enfants**

</div>
