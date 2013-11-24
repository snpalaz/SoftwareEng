SoftwareEng---CourseProject
===========Minimax ve Alpha Beta Pruning algoritmaları===============
Mancala Projesi Tanımı:

Mancala Türk Zeka ve Strateji Oyunu iki kişi ile oynanır. 
Oyun tahtası üzerinde karşılıklı 6'şar adet olmak üzere 12 küçük kase ve 
her oyuncunun taşlarını toplayacağı birer büyük kase bulunmaktadır. 
Mancala Oyunu 48 taş ile oynanır.
Oyuncular 48 taşı her bir kaseye 4'er adet olmak üzere dağıtırlar. 
Oyunda her oyuncunun önünde bulunan yan yana 6 küçük kase, o oyuncunun 
bölgesidir. 
Karşısında bulunan 6 küçük kase rakibinin bölgesidir. 
Oyuncular büyük kaselerinde en fazla taşı biriktirmeye çalışırlar. 
Oyun sonunda en çok taşı toplayan oyuncu oyun setini kazanmış olur. 
Oyunun temel kuralları şöyledir:
*Oyuncu kendi bölgesinde istediği kaseden, kasedeki tüm taşları alır ve 
saatin tersi yönünde, yani sağa doğru her bir kuyuya birer adet taş bırakarak
elindeki taşlar bitene kadar dağıtır.Elindeki son taş kendi büyük kasesine denk 
gelirse, oyuncu tekrar oynama hakkına sahip olur.
*Kendi kuyusundan aldığı taşları dağıtırken elinde taş kaldıysa, rakibinin 
bölgesindeki kuyulara da taş bırakmaya devam eder ama rakibin büyük kasesine 
koyamaz çünkü büyük kaseler oyuncuların skorudur.
*Oyuncu taşları dağıtırken elinde kalan son taş, yine kendi bölgesinde yer alan 
boş bir kaseye denk gelirse ve boş kasenin karşısındaki kasede de rakibine ait 
taş varsa hem rakibinin kasesindeki taşları alır, hem de kendi boş kasesine 
bıraktığı taşı alıp büyük kasesine koyar. Hamle sırası rakibine geçer. 
*Oyunculardan herhangi birinin bölgesinde yer alan taşlar bittiğinde oyun 
seti biter. Oyunda kendi bölgesinde taşlarını ilk bitiren oyuncu, rakibinin 
bölgesinde bulunan tüm taşları da kazanır.


