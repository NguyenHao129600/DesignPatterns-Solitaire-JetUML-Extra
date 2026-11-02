# Chay demo chi voi JDK 17+ tren Windows PowerShell. Khong can Maven.
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$out = Join-Path $PSScriptRoot 'target\classes'
New-Item -ItemType Directory -Path $out -Force | Out-Null
$sources = @(Get-ChildItem -Path 'src\main\java' -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
if ($sources.Count -eq 0) { throw 'Khong tim thay Java source' }
& javac -encoding UTF-8 -d $out @sources
if ($LASTEXITCODE -ne 0) { throw 'Bien dich that bai' }
& java -cp $out vn.edu.patterns.extra.demo.ExtraPatternLauncher
if ($LASTEXITCODE -ne 0) { throw 'Chay demo that bai' }
