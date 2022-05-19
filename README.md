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

## Setup
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnsiTextView textView = findViewById(R.id.ansiView); // It's "AndroidANSI!" but with color & style
        textView.setAnsiText("\\e[1;38;2;164;198;57mAndroid\\e[0;35mAN\u001B[2mSI\u001B[0;73m!",
                textView.FLAG_PARSE_DISABLE_SUBSCRIPT); // Also disable superscript
    }
}
```