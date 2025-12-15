# 🎵 Générer les Sons Automatiquement

## Scripts Python Créés

J'ai créé **2 scripts Python** pour générer automatiquement tous les sons:

1. **`generate_arabic_sounds.py`** - Pour l'arabe (28 sons)
2. **`generate_french_sounds.py`** - Pour le français (26 sons)

---

## 🚀 Installation de l'Outil TTS

### Étape 1: Installer Python (si pas déjà installé)

1. Téléchargez Python: https://www.python.org/downloads/
2. Installez avec l'option "Add to PATH" cochée

### Étape 2: Installer Google TTS

Ouvrez PowerShell et tapez:

```powershell
pip install gtts
```

---

## 🔊 Générer les Sons Arabes

### Dans PowerShell:

```powershell
cd C:\Users\IBTISSAM\AndroidStudioProjects\enfantApp
python generate_arabic_sounds.py
```

**Résultat:**

```
✅ Créé: letter_alif.mp3 (ألف)
✅ Créé: letter_ba.mp3 (باء)
✅ Créé: letter_ta.mp3 (تاء)
...
🎉 28 fichiers créés!
```

---

## 🔊 Générer les Sons Français

### Dans PowerShell:

```powershell
cd C:\Users\IBTISSAM\AndroidStudioProjects\enfantApp
python generate_french_sounds.py
```

**Résultat:**

```
✅ Créé: letter_a.mp3 (a)
✅ Créé: letter_b.mp3 (bé)
✅ Créé: letter_c.mp3 (cé)
...
🎉 26 fichiers créés!
```

---

## 📥 Après Génération

### Dans Android Studio:

1. **Clic droit** sur le dossier `raw/`
2. **Synchronize** (ou `Alt + F5`)
3. **Build > Rebuild Project**
4. **Lancez l'application!**

---

## 🎯 Génerer les DEUX Alphabets en Une Fois

### Script Complet:

```powershell
cd C:\Users\IBTISSAM\AndroidStudioProjects\enfantApp
python generate_arabic_sounds.py
python generate_french_sounds.py
```

**Total: 54 fichiers MP3 créés!** 🎉

---

## ⚠️ Si Vous N'avez Pas Python

### Alternative 1: Sites TTS en Ligne

**Pour l'Arabe:**

- **TTSMaker.com**
    - Langue: Arabic
    - Texte: ألف, باء, تاء, etc.
    - Téléchargez chaque son

**Pour le Français:**

- **TTSMaker.com**
    - Langue: French
    - Texte: a, bé, cé, etc.
    - Téléchargez chaque son

### Alternative 2: Enregistrement Vocal

Utilisez l'enregistreur Windows:

1. **Windows + R** → tapez `soundrecorder`
2. Enregistrez votre voix
3. Sauvegardez comme MP3

---

## 🎨 Personnalisation des Scripts

### Changer la Vitesse de Prononciation

Dans le script, modifiez:

```python
# Prononciation lente (pour enfants)
tts = gTTS(text=pronunciation, lang='ar', slow=True)

# Prononciation normale
tts = gTTS(text=pronunciation, lang='ar', slow=False)
```

### Changer la Voix

Google TTS a des voix différentes par langue:

- `lang='ar'` - Arabe standard
- `lang='fr'` - Français standard
- `lang='fr-CA'` - Français canadien

---

## 📊 Résultat Attendu

### Dossier `res/raw/` Après Génération:

```
raw/
├── letter_a.mp3       (français)
├── letter_b.mp3       (français)
├── ...
├── letter_z.mp3       (français)
├── letter_alif.mp3    (arabe)
├── letter_ba.mp3      (arabe)
├── ...
└── letter_ya.mp3      (arabe)

Total: 54 fichiers MP3 (~2-3 MB)
```

---

## 🧪 Tester Un Son

### Dans PowerShell:

```powershell
# Tester un son généré
Start-Process "C:\Users\IBTISSAM\AndroidStudioProjects\enfantApp\app\src\main\res\raw\letter_alif.mp3"
```

---

## ✅ Checklist

- [ ] Python installé
- [ ] Package gtts installé (`pip install gtts`)
- [ ] Script `generate_arabic_sounds.py` exécuté
- [ ] Script `generate_french_sounds.py` exécuté
- [ ] Dossier `raw/` contient les MP3
- [ ] Android Studio synchronisé
- [ ] Projet rebuilded
- [ ] Application testée

---

## 🎉 Résultat dans l'Application

**Après génération des sons:**

1. L'enfant clique sur **ا** (alif)
2. Clique sur le bouton 🔊 "Son"
3. Entend: **"ألف"** (alif) 🔊

1. L'enfant clique sur **A**
2. Clique sur le bouton 🔊 "Son"
3. Entend: **"a"** 🔊

**Application complète avec vrais sons! 🎵👶📚**
