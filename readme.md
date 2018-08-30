# Description
This is library for JavaFX application for update application.

# Include plugin

Use maven repositories:
```
maven {
    url 'http://dl.bintray.com/appscharles/libs'
}
maven {
        url 'http://repo.novus.com/releases'
    }
```

Add dependency:
```
compile group: 'com.appscharles.libs', name: 'updater', version: '1.+'
```

# Detect new version

Detect new version in disk:
```
NewVersionDetector.versionInDisk("MyApp", storageDir);
```

Detect new version in HTTP:
```
NewVersionDetector.versionInHTTP("MyApp", new URL("http://localhost/storageDir));
```

# Validate new version

Validate new version in disk:
```
NewVersionValidator.existInDisk("MyApp", "1.0.0.0-dev1", storageDir);
```

Validate new version in HTTP:
```
NewVersionValidator.existInHTTP("MyApp", "1.0.0.0-dev1", new URL("http://localhost/storageDir"));
```

# Send update files to server

Send update files to disk:
```
NewVersionSender.sendToDisk("MyApp", "1.0.0.0-dev1", dir, storageDir);
```

Send update files to FTP:
```
NewVersionSender.sendToFTP("MyApp", "1.0.0.0-dev1", dir, "localhost", 2221, "root", "secret", "/storageDir", true);
```

# Update new version

Update files by disk:
```
NewVersionUpdater.updateFromDisk("MyApp", myAppDir, storageDir, 4564L, 20000L, "java -jar return.jar", true);
```

Update files by HTTP:
```
NewVersionUpdater.updateFromHTTP("MyApp", myAppDir, urlStorage, 4564L, 20000L, "java -jar return.jar", true);
```

# Send new version to server from gradle

Add dependency:

```
buildscript {
    repositories {
        maven {
            url 'http://dl.bintray.com/appscharles/libs'
        }
        maven {
            url 'http://repo.novus.com/releases'
        }
    }
    dependencies {
        classpath 'com.appscharles.libs:updater:1.+'
    }
}
```

Apply plugin `apply plugin: 'com.appscharles.libs.updater'`.

Include file gradle to `build.gradle` be example: `apply from: 'gradle/NewVersionUpdater.gradle'`.

Content `NewVersionUpdater.gradle` file, exist in github repository in localization `'gradle/NewVersionUpdater.gradle'`.

For FTP send, create txt file in localization `HOME_DIR/updater/data.txt` and fill lines:
- first line: FTP host
- second line: FTP name
- third line: FTP password

Configure properties in defined `updaterConfig` in `NewVersionUpdater.gradle` file.