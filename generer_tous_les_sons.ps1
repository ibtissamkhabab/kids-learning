# Script PowerShell pour generer les sons automatiquement
# Sans besoin de Python ou installation

Write-Host "Generation Automatique des Sons" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green
Write-Host ""

# Creer le dossier raw
$rawPath = "app\src\main\res\raw"
if (-not (Test-Path $rawPath)) {
    New-Item -ItemType Directory -Path $rawPath -Force | Out-Null
}

Write-Host "Dossier: $rawPath" -ForegroundColor Cyan
Write-Host ""

# Fonction pour telecharger un son
function Get-Sound {
    param([string]$text, [string]$lang, [string]$filename)
    
    $filepath = Join-Path $rawPath $filename
    $encodedText = [System.Web.HttpUtility]::UrlEncode($text)
    $url = "https://translate.google.com/translate_tts?ie=UTF-8`&client=tw-ob`&tl=$lang`&q=$encodedText"
    
    try {
        Write-Host "Telechargement: $filename..." -NoNewline
        Invoke-WebRequest -Uri $url -OutFile $filepath -UserAgent "Mozilla/5.0" -ErrorAction Stop
        Write-Host " OK" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host " ERREUR" -ForegroundColor Red
        return $false
    }
}

Add-Type -AssemblyName System.Web

Write-Host "Sons ARABES..." -ForegroundColor Yellow
Write-Host ""

# Dictionnaire arabe simplifie
$ar = @{}
$ar['alif'] = [char]0x0623 + [char]0x0644 + [char]0x0641
$ar['ba'] = [char]0x0628 + [char]0x0627 + [char]0x0621
$ar['ta'] = [char]0x062A + [char]0x0627 + [char]0x0621
$ar['tha'] = [char]0x062B + [char]0x0627 + [char]0x0621
$ar['jim'] = [char]0x062C + [char]0x064A + [char]0x0645
$ar['ha'] = [char]0x062D + [char]0x0627 + [char]0x0621
$ar['kha'] = [char]0x062E + [char]0x0627 + [char]0x0621
$ar['dal'] = [char]0x062F + [char]0x0627 + [char]0x0644
$ar['dhal'] = [char]0x0630 + [char]0x0627 + [char]0x0644
$ar['ra'] = [char]0x0631 + [char]0x0627 + [char]0x0621
$ar['zay'] = [char]0x0632 + [char]0x0627 + [char]0x064A
$ar['sin'] = [char]0x0633 + [char]0x064A + [char]0x0646
$ar['shin'] = [char]0x0634 + [char]0x064A + [char]0x0646
$ar['sad'] = [char]0x0635 + [char]0x0627 + [char]0x062F
$ar['dad'] = [char]0x0636 + [char]0x0627 + [char]0x062F
$ar['tah'] = [char]0x0637 + [char]0x0627 + [char]0x0621
$ar['zah'] = [char]0x0638 + [char]0x0627 + [char]0x0621
$ar['ayn'] = [char]0x0639 + [char]0x064A + [char]0x0646
$ar['ghayn'] = [char]0x063A + [char]0x064A + [char]0x0646
$ar['fa'] = [char]0x0641 + [char]0x0627 + [char]0x0621
$ar['qaf'] = [char]0x0642 + [char]0x0627 + [char]0x0641
$ar['kaf'] = [char]0x0643 + [char]0x0627 + [char]0x0641
$ar['lam'] = [char]0x0644 + [char]0x0627 + [char]0x0645
$ar['mim'] = [char]0x0645 + [char]0x064A + [char]0x0645
$ar['nun'] = [char]0x0646 + [char]0x0648 + [char]0x0646
$ar['haa'] = [char]0x0647 + [char]0x0627 + [char]0x0621
$ar['waw'] = [char]0x0648 + [char]0x0627 + [char]0x0648
$ar['ya'] = [char]0x064A + [char]0x0627 + [char]0x0621

$arabicOK = 0
foreach ($k in $ar.Keys) {
    if (Get-Sound -text $ar[$k] -lang "ar" -filename "letter_$k.mp3") {
        $arabicOK++
    }
    Start-Sleep -Milliseconds 300
}

Write-Host ""
Write-Host "Sons FRANCAIS..." -ForegroundColor Yellow
Write-Host ""

$fr = @{
    'a'='a'; 'b'='be'; 'c'='ce'; 'd'='de'; 'e'='e'; 'f'='effe'
    'g'='ge'; 'h'='hache'; 'i'='i'; 'j'='ji'; 'k'='ka'; 'l'='elle'
    'm'='emme'; 'n'='enne'; 'o'='o'; 'p'='pe'; 'q'='ku'; 'r'='erre'
    's'='esse'; 't'='te'; 'u'='u'; 'v'='ve'; 'w'='double ve'
    'x'='ixe'; 'y'='i grec'; 'z'='zede'
}

$frenchOK = 0
foreach ($k in $fr.Keys) {
    if (Get-Sound -text $fr[$k] -lang "fr" -filename "letter_$k.mp3") {
        $frenchOK++
    }
    Start-Sleep -Milliseconds 300
}

Write-Host ""
Write-Host "================================" -ForegroundColor Green
Write-Host "TERMINE!" -ForegroundColor Green
Write-Host ""
Write-Host "Arabe: $arabicOK/28" -ForegroundColor White
Write-Host "Francais: $frenchOK/26" -ForegroundColor White
Write-Host "Total: $($arabicOK + $frenchOK)/54" -ForegroundColor White
Write-Host ""
Write-Host "Prochaines etapes:" -ForegroundColor Yellow
Write-Host "1. Ouvrez Android Studio"
Write-Host "2. Clic droit sur 'raw' -> Synchronize"
Write-Host "3. Build -> Rebuild Project"
Write-Host "4. Testez!"
Write-Host ""
