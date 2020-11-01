# WORD FREQUENCY - WEB CRAWLER

## About the project
This project scans through a given webpage and displays the top 10 most frequently occurring words and the top 10 most frequently occurring word pairs(two words in the same order) along with their frequency. In case the webpage contains hyperlinks, these hyperlinked urls are also expanded and the words on these pages are also scanned to come up with the frequent words. 

Important points to note:
- The maximum number of levels being expanded for a given url is 4.
- Any external url links are ignored

## Setup

- Run from the root of the project: ./gradlew clean build
- This will build an executable: `build/libs/web-word-frequency.jar`

## Running the project

- After building, run the following command `java -jar build/libs/web-word-frequency.jar "https://www.google.com/"` to run the project

## Project dependencies

- Project is developed using Java (tested on a JDK 11 setup)
- Project uses JSoup to parse web page content

## How it works
1. The user passes a url that needs to be crawled to get most frequently occurring words.
2. The program parses the url and all the links found within each web page recursively. It goes maximum 4 link levels deep and makes sure no page is parsed twice.
3. The program gets the content from all the pages in a concatenated string.
4. Then, the content string is parsed once character by character.
5. All words in the string are extracted and kept in a Map along with their frequencies.
6. Words containing characters apart from [A-za-z] are ignored.
7. Word frequencies are calculated for words in a case insensitive manner.
8. In the end the words are sorted based on their frequencies and printed.

## Example input and output
<details>
    <summary>Sample input</summary>
    
    java -jar build/libs/web-word-frequency.jar "https://www.google.com/"
</details>
<details>
    <summary>Sample output</summary>
    
    Parsing: https://www.google.com/, level: 1
    Parsing: https://accounts.google.com/ServiceLogin?continue=https://www.google.com/preferences%3Fhl%3Dkn&hl=kn, level: 3
    Parsing: https://accounts.google.com/ServiceLogin?hl=kn&passive=true&continue=https://www.google.com/preferences%3Fhl%3Dkn&ec=GAZAAQ, level: 3
    Parsing: https://www.google.com/preferences?hl=en-IN&fg=1, level: 2
    Parsing: https://accounts.google.com/ServiceLogin?hl=ta&passive=true&continue=https://www.google.com/preferences%3Fhl%3Dta&ec=GAZAAQ, level: 3
    Parsing: https://accounts.google.com/ServiceLogin?continue=https://www.google.com/preferences%3Fhl%3Dta&hl=ta, level: 3
    Parsing: https://www.google.com/intl/en_in/ads/?subid=ww-ww-et-g-awa-a-g_hpafoot1_1!o2&utm_source=google.com&utm_medium=referral&utm_campaign=google_hpafooter&fg=1, level: 2
    Parsing: https://www.google.com, level: 3
    Parsing: https://www.google.com/services/?subid=ww-ww-et-g-awa-a-g_hpbfoot1_1!o2&utm_source=google.com&utm_medium=referral&utm_campaign=google_hpbfooter&fg=1, level: 4
    Parsing: https://www.google.com/preferences?hl=en, level: 4
    Parsing: https://www.google.com/setprefs?sig=0_f2aPBCfiUUYONpQbl54FW44mslY%3D&hl=ml&source=homepage&sa=X&ved=0ahUKEwinw8SyvuHsAhWNzTgGHZbSBi4Q2ZgBCBY, level: 4
    Parsing: https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=https://www.google.com/&ec=GAZAAQ, level: 4
    Parsing: https://www.google.com/admob/?utm_source=Internal&utm_medium=et&utm_campaign=google-ads, level: 3
    Parsing: https://www.google.com/services/?utm_source=admob.google.com&utm_medium=et&utm_campaign=admob.google.com%2Fhome%2F, level: 4
    Parsing: https://www.google.com/intl/en/policies/terms/, level: 4
    Parsing: https://www.google.com/intl/en/policies/privacy/, level: 4
    Parsing: https://www.google.com/ads/publisher/partners/find-a-partner/?utm_source=admob&utm_medium=website&utm_campaign=footer3, level: 4
    Parsing: https://www.google.com/intl/en_in/chrome/, level: 3
    Parsing: https://www.google.com/chromebook/, level: 4
    Parsing: https://www.google.com/chrome/cleanup-tool, level: 4
    Parsing: https://www.google.com/support/chrome/bin/answer.py?answer=96817&hl=en, level: 4
    Parsing: https://www.google.com/chromecast/, level: 4
    var 24543
    instanceof 5439
    in 3802
    new 3428
    solid 3086
    and 2590
    function 1968
    jsslot 1748
    the 1476
    all 1441
    all and 1302
    a instanceof 576
    the closure 480
    closure library 430
    copyright the 430
    sans myanmar 340
    screen and 339
    c in 284
    family link 220
    d in 215
</details>
