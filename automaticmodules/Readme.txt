javac --module-source-path src -p mods -m main.app  -d out
java -p out:mods -m main.app/com.adobe.main.Main