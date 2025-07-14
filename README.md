# photostagger
Tags photos with a name and an index, making sure all the photos retain the tag, and they all have a different index. Each photograph will be named with a pattern and an index.

The library works with Java JRE greater or equal to Java 8.

# configuration
The 'propertis.properties' file specifies:
- pattern: the text to set on each photo
- start: the start index

For example:
```
pattern=Oya Kephale © Hervé Girod
start=181
```

# usage
Double click on the jar file.
- Set the Input directory
- Set the Output directory
- Clic on the "run" button. A message will be shown on the console when the naming will be finished.

Note that if the Output directory is the Input directory, the ptohots will be renamed, else the photos in the Input directory will be copied wil their name on the Output directory.
