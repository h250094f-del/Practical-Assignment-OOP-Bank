Set-Location $PSScriptRoot
Write-Host "Compiling Main.java with Java 21+ compatibility..." -ForegroundColor Cyan
javac --release 21 Main.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Failed to compile Main.java. Please ensure JDK is on your PATH." -ForegroundColor Red
    exit $LASTEXITCODE
}
java Main $args
