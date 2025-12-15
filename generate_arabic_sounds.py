"""
Script pour générer automatiquement les sons de l'alphabet arabe
Nécessite: pip install gtts
"""

from gtts import gTTS
import os

# Créer le dossier de sortie
output_dir = "app/src/main/res/raw"
os.makedirs(output_dir, exist_ok=True)

# Dictionnaire des lettres arabes avec leurs prononciations
arabic_letters = {
    'alif': 'ألف',
    'ba': 'باء',
    'ta': 'تاء',
    'tha': 'ثاء',
    'jim': 'جيم',
    'ha': 'حاء',
    'kha': 'خاء',
    'dal': 'دال',
    'dhal': 'ذال',
    'ra': 'راء',
    'zay': 'زاي',
    'sin': 'سين',
    'shin': 'شين',
    'sad': 'صاد',
    'dad': 'ضاد',
    'tah': 'طاء',
    'zah': 'ظاء',
    'ayn': 'عين',
    'ghayn': 'غين',
    'fa': 'فاء',
    'qaf': 'قاف',
    'kaf': 'كاف',
    'lam': 'لام',
    'mim': 'ميم',
    'nun': 'نون',
    'haa': 'هاء',
    'waw': 'واو',
    'ya': 'ياء'
}

print("🔊 Génération des sons de l'alphabet arabe...")
print(f"📁 Dossier de sortie: {output_dir}")
print()

for name, pronunciation in arabic_letters.items():
    filename = f"letter_{name}.mp3"
    filepath = os.path.join(output_dir, filename)
    
    try:
        # Générer le son avec Google TTS (voix arabe)
        tts = gTTS(text=pronunciation, lang='ar', slow=False)
        tts.save(filepath)
        print(f"✅ Créé: {filename} ({pronunciation})")
    except Exception as e:
        print(f"❌ Erreur pour {filename}: {e}")

print()
print("🎉 Terminé!")
print(f"📊 {len(arabic_letters)} fichiers créés")
print()
print("📝 Prochaines étapes:")
print("1. Ouvrez Android Studio")
print("2. Clic droit sur 'raw' → Synchronize")
print("3. Rebuild le projet")
print("4. Testez l'application!")
