## A projektről

A projekt egy végletekig leegyszerűsített álláskeresőt valósít meg. Lehet regisztrálni, bejelentkezni, illetve állásokra jelentkezni, és a jelentkezéseket törölni.

## Troubleshooting

Ha bármi problémád adódik kérlek írj Discordon a Kalkulusták szerón a kurzus szobájába.
Naponta többször megnézem, úgyhogy fogok tudni segíteni ha baj van. Érthető módon nem szeretném hogy lepontozzanak azért mert valami nem működik, aminek egyébként kellene.

## Pontozási segédlet

Készítettem egy pontozási segédletet, hogy ne tartson örökké a projekt pontozása:  

A szempontok a táblázaton megy végig sorban. Értelemszerűen a szubjektív részét kihagytam belőle.

1. Fordítási hibával nem találkoztam, de azért figyeld te is.
2. Futtatási hibával nem találkoztam, de azért figyeld te is.
3. Meg van valósítva, lehet regisztrálni is, és bejelentkezni is.
4. Az adatmodellt a hu.mobilalk.allaskereso package-ben a model mappában találod.
5. Activityből van 3 darab
6. Az input mezők helyességére odafigyeltem
7. ConstraintLayout-ot sok helyen láthatsz, és az 'offer.xml'-ben találsz LinearLayout-ot.
8. Az általam tesztelt eszközökön jól jelenik meg állítva és fektetve is.
9. Két animációm van 'zoom_in' és 'scale_up' néven. Ezek használatára a 'JobOfferElementAdapter' osztályom 'onBindViewHolder' metódusában találsz.
10. A 'LoginActivity' és 'RegisterActivity' osztályokban használok 'onPause'-t, ezekben lementem a már beírt emailt, hogy ne vesszen el az alkalmazás elrejtésekor.
11. Elérhető minden activity, az intentek megfelelően működnek. (A hirdetésektől a vissza gombbal tudsz visszalépni a bejelentkezéshez)
12. Nem használtam erőforrást
13. Nincs notification/alarm/job scheduler
14. A CRUD műveletek mind az 'OffersMenuActivity' osztályban vannak, Update-et nem írtam, azon kívül viszon mindegyik külön szálon fut:
    - Create: Jelentkezés hozzáadása: AddApplication
    - Read: Álláshirdetések lekérdezése: selectOffers()
    - Delete: Jelentkezés törlése: DeleteApplication
15. Komplex lekérdezéseket az 'OffersMenuActivity' oszályban találsz, azon belül is a selectOffers() metódusban és a DeleteApplication alosztályban. Mindkét esetben where-t használtam. (a selectOffers-ben 2-t is)
