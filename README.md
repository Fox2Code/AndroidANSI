# AndroidANSI  
Android ANSI rendering library!  

See [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code)
for more info on ANSI escape codes.  

## Supported ANSI Codes  

0 -> Reset (Style & color)  
1 -> Bold  
2 -> Dim  
3 -> Italic  
4 -> Underline  
7 -> Invert color (Non-standard support)  
9 -> Crossed-out  
21 -> Not bold  
22 -> Not bold & Not dim  
23 -> Not italic  
24 -> Not underlined  
29 -> Not crossed out  
30–37 -> Set foreground color (See: [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors))  
38 -> Set foreground color (Partial, see: [True-color](#supported-true-color-formats))  
39 -> Reset foreground color  
40–47 -> Set background color (See: [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors))  
48 -> Set background color (Partial, see: [True-color](#supported-true-color-formats))  
49 -> Reset background color  
58 -> Set underline color (Only Android10+ & Partial, see: [True-color](#supported-true-color-formats))  
59 -> Reset underline color (Only Android10+)  
73 -> Superscript  
74 -> Subscript  
75 -> Neither superscript nor subscript  
90–97 -> Set bright foreground color (See: [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors))  
100–107 -> Set bright background color (See: [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors))  

## Supported True color formats

True color is `38`, `48`, or `58` with T + args.  
Example: `38;2;164;198;57` set foreground color to rgb(164, 198, 57)  

2 -> R;G;B  
5 -> X (Only support from 0 to 15, see: [Wikipedia ANSI Page](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors))  


# Setup

## Gradle

Add jitpack, example to add to `settings.gradle`:
```groovy
// Only add if `dependencyResolutionManagement` already exists
dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://jitpack.io'
        }
    }
}
```


```groovy
// Only add "repositories" if "dependencyResolutionManagement" didn't exists in "settings.gradle"
repositories {
    maven {
        url 'https://jitpack.io'
    }
}


dependencies {
    implementation 'com.github.Fox2Code:AndroidANSI:1.0.0'
}
```

## TextView
```java
TextView textView = findViewById(R.id.ansiView);
AnsiParser.setAnsiText(textView, // It's "AndroidANSI!" but with color & style
        "\\e[1;38;2;164;198;57mAndroid\\e[0;35mAN\u001B[2mSI\u001B[0;73m!",
        AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT); // Also disable superscript
```

## AnsiTextView
**Layout**
```xml
<com.fox2code.androidansi.AnsiTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/ansiView" />
```
**Activity**
```java
AnsiTextView textView = findViewById(R.id.ansiView);
textView.setAnsiText("\\e[1;38;2;164;198;57mAndroid\\e[0;35mAN\u001B[2mSI\u001B[0;73m!",
        AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT);
```

## AnsiTextView w/o Java/Kotlin (WIP)
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.fox2code.androidansi.AnsiTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:ansiText="\e[38;5;82mHello \e[38;5;198mWorld"
        android:id="@+id/ansiView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Note: AnsiTextView support Emoji2 if the library `androidx.emoji2:emoji2-views-helper:*` is added.
