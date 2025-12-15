# Script PowerShell pour générer les sons automatiquement
# Utilise une API TTS gratuite pour créer les fichiers audio

Write-Host "🎵 Génération Automatique des Sons" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""

# Créer le dossier raw s'il n'existe pas
$rawPath = "app\src\main\res\raw"
if (-not (Test-Path $rawPath)) {
    New-Item -ItemType Directory -Path $rawPath -Force | Out-Null
}

Write-Host "📁 Dossier de sortie: $rawPath" -ForegroundColor Cyan
Write-Host ""

# Fonction pour télécharger un son via API TTS
function Download-Sound {
    param(
        [string]$text,
        [string]$lang,
        [string]$filename
    )
    
    $filepath = Join-Path $rawPath $filename
    
    # URL de l'API Google TTS (alternative gratuite)
    $url = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=$lang&q=$([System.Web.HttpUtility]::UrlEncode($text))"
    
    try {
        Write-Host "⏳ Téléchargement: $filename ($text)..." -NoNewline
        Invoke-WebRequest -Uri $url -OutFile $filepath -UserAgent "Mozilla/5.0" -ErrorAction Stop
        Write-Host " ✅" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host " ❌" -ForegroundColor Red
        return $false
    }
}

# Ajouter l'assembly pour l'encodage URL
Add-Type -AssemblyName System.Web

# Sons pour l'alphabet arabe
Write-Host "🔊 Génération des sons ARABES..." -ForegroundColor Yellow
Write-Host ""

$arabicLetters = @{
    'alif' = 'ألف'
    'ba' = 'باء'
    'ta' = 'تاء'
    'tha' = 'ثاء'
    'jim' = 'جيم'
    'ha' = 'حاء'
    'kha' = 'خاء'
    'dal' = 'دال'
    'dhal' = 'ذال'
    'ra' = 'راء'
    'zay' = 'زاي'
    'sin' = 'سين'
    'shin' = 'شين'
    'sad' = 'صاد'
    'dad' = 'ضاد'
    'tah' = 'طاء'
    'zah' = 'ظاء'
    'ayn' = 'عين'
    'ghayn' = 'غين'
    'fa' = 'فاء'
    'qaf' = 'قاف'
    'kaf' = 'كاف'
    'lam' = 'لام'
    'mim' = 'ميم'
    'nun' = 'نون'
    'haa' = 'هاء'
    'waw' = 'واو'
    'ya' = 'ياء'
}

$arabicSuccess = 0
foreach ($key in $arabicLetters.Keys) {
    $result = Download-Sound -text $arabicLetters[$key] -lang "ar" -filename "letter_$key.mp3"
    if ($result) { $arabicSuccess++ }
    Start-Sleep -Milliseconds 500  # Pause pour ne pas surcharger l'API
}

Write-Host ""
Write-Host "🔊 Génération des sons FRANÇAIS..." -ForegroundColor Yellow
Write-Host ""

# Sons pour l'alphabet français
$frenchLetters = @{
    'a' = 'a'
    'b' = 'bé'
    'c' = 'cé'
    'd' = 'dé'
    'e' = 'e'
    'f' = 'effe'
    'g' = 'gé'
    'h' = 'hache'
    'i' = 'i'
    'j' = 'ji'
    'k' = 'ka'
    'l' = 'elle'
    'm' = 'emme'
    'n' = 'enne'
    'o' = 'o'
    'p' = 'pé'
    'q' = 'ku'
    'r' = 'erre'
    's' = 'esse'
    't' = 'té'
    'u' = 'u'
    'v' = 'vé'
    'w' = 'double vé'
    'x' = 'ixe'
    'y' = 'i grec'
    'z' = 'zède'
}

$frenchSuccess = 0
foreach ($key in $frenchLetters.Keys) {
    $result = Download-Sound -text $frenchLetters[$key] -lang "fr" -filename "letter_$key.mp3"
    if ($result) { $frenchSuccess++ }
    Start-Sleep -Milliseconds 500  # Pause pour ne pas surcharger l'API
}

Write-Host ""
Write-Host "=====================================" -ForegroundColor Green
Write-Host "🎉 TERMINÉ!" -ForegroundColor Green
Write-Host ""
Write-Host "📊 Résumé:" -ForegroundColor Cyan
Write-Host "  - Sons arabes: $arabicSuccess/28" -ForegroundColor White
Write-Host "  - Sons français: $frenchSuccess/26" -ForegroundColor White
Write-Host "  - Total: $($arabicSuccess + $frenchSuccess)/54" -ForegroundColor White
Write-Host ""
Write-Host "📁 Fichiers sauvegardés dans: $rawPath" -ForegroundColor Cyan
Write-Host ""
Write-Host "📝 Prochaines étapes:" -ForegroundColor Yellow
Write-Host "  1. Ouvrez Android Studio" -ForegroundColor White
Write-Host "  2. Clic droit sur 'raw' → Synchronize" -ForegroundColor White
Write-Host "  3. Build → Rebuild Project" -ForegroundColor White
Write-Host "  4. Testez l'application! 🚀" -ForegroundColor White
Write-Host ""

Read-Host "Appuyez sur Entrée pour fermer"
