# MyWord
Word cards

I developed this for my TOEFL examination, it worked very well.

![ss](https://github.com/warriorWorld/MyWord/blob/master/app/screenshots/ss1.jpg) ![ss](https://github.com/warriorWorld/MyWord/blob/master/app/screenshots/ss2.jpg) ![ss](https://github.com/warriorWorld/MyWord/blob/master/app/screenshots/ss3.jpg)

There are two types of word cards, one for reading and one for writing, the reading one is just like the above images showed. And in the writing one, you have to input the exact word in the word cards.

##### Technical detail(if you are not interested, just ignore this part)
This app actually has three means to add new words to it.
1, This app can actually provide a service for other applications translating English words, my "Manga Reader", "Stranger Book Reader", "Foreign News" and "Video Crawler" all use this service. Because of this, whenever users use those apps to translate words, those words will be added to the word cards automatically. I implemented this by using AIDL technology.
2, I used a web crawler to get words from GitHub, and this is my word list [here](https://github.com/warriorWorld/Words/blob/master/words.txt)
3, You can add words manually, I provided an EditText in the app in order to do so.
