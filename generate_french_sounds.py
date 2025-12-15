"""
Script pour générer automatiquement les sons de l'alphabet français
Nécessite: pip install gtts
"""

from gtts import gTTS
import os

# Créer le dossier de sortie
output_dir = "app/src/main/res/raw"
os.makedirs(output_dir, exist_ok=True)

# Dictionnaire des lettres françaises avec leurs prononciations
french_letters = {
    'a': 'a',
    'b': 'bé',
    'c': 'cé',
    'd': 'dé',
    'e': 'e',
    'f': 'effe',
    'g': 'gé',
    'h': 'hache',
    'i': 'i',
    'j': 'ji',
    'k': 'ka',
    'l': 'elle',
    'm': 'emme',
    'n': 'enne',
    'o': 'o',
    'p': 'pé',
    'q': 'ku',
    'r': 'erre',
    's': 'esse',
    't': 'té',
    'u': 'u',
    'v': 'vé',
    'w': 'double vé',
    'x': 'ixe',
    'y': 'i grec',
    'z': 'zède'
}

print("🔊 Génération des sons de l'alphabet français...")
print(f"📁 Dossier de sortie: {output_dir}")
print()

for letter, pronunciation in french_letters.items():
    filename = f"letter_{letter}.mp3"
    filepath = os.path.join(output_dir, filename)
    
    try:
        # Générer le son avec Google TTS (voix française)
        tts = gTTS(text=pronunciation, lang='fr', slow=False)
        tts.save(filepath)
        print(f"✅ Créé: {filename} ({pronunciation})")
    except Exception as e:
        print(f"❌ Erreur pour {filename}: {e}")

print()
print("🎉 Terminé!")
print(f"📊 {len(french_letters)} fichiers créés")
print()
print("📝 Prochaines étapes:")
print("1. Ouvrez Android Studio")
print("2. Clic droit sur 'raw' → Synchronize")
print("3. Rebuild le projet")
print("4. Testez l'application!")
