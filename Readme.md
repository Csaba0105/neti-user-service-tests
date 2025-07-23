# Otthoni Feladat - Medior Automata Tesztelő
## Bevezetés
Köszönjük, hogy érdeklődsz a Medior Automata Tesztelő pozíciónk iránt! Ez a feladat arra szolgál, hogy felmérjük a technikai képességeidet, a szoftverfejlesztési és tesztelési módszertanokban való jártasságodat, valamint a problémamegoldó képességedet egy valósághű, de egyszerűsített projekt keretében.
A feladat célja, hogy egy komplett tesztautomatizálási megoldást készíts egy egyszerűsített "Felhasználókezelő" (User Management) szolgáltatáshoz.

**A megoldásod értékelése során a következő fő területeket vizsgáljuk:**
-   A tesztesetek alapossága és a lefedettség.

-   A kód minősége, olvashatósága és strukturáltsága.

-   Design pattern-ek és "best practice"-ek alkalmazása.

-   A projekt CI/CD-barát felépítése.

-   A dokumentáció minősége

Sok sikert kívánunk a feladathoz!

## 1. A Tesztelendő Alkalmazás: "User Service"

A feladathoz biztosítunk egy egyszerű, előre elkészített Spring Boot alapú backend alkalmazást, amely egy `User` entitás CRUD (Create, Read, Update, Delete) műveleteit kezeli REST API-n keresztül. Az alkalmazás egy beágyazott H2 in-memory adatbázist használ, így nincs szükség külső adatbázis beállítására.

### Az alkalmazás letöltése és futtatása:

A projekt egy külön Git repository-ban található. Kérjük, klónozd le és futtasd lokálisan:

```
# Klónozd a repository-t
git clone https://github.com/Csaba0105/neti-user-service-tests.git

# Lépj be a könyvtárba
cd neti-user-service-tests

# Futtasd az alkalmazást Maven segítségével
mvn spring-boot:run

```

Az alkalmazás a `http://localhost:8080` címen lesz elérhető.

### User Entitás

A kezelt `User` objektum a következő mezőkkel rendelkezik:

-   `id` (Long, auto-generált)

-   `username` (String, egyedi)

-   `email` (String, egyedi)

-   `fullName` (String)

-   `active` (boolean)
### REST API Végpontok
| Metódus | Végpont         | Leírás                                                                                       | Siker Kód      | Hiba Kódok                                   |
|---------|-----------------|----------------------------------------------------------------------------------------------|----------------|----------------------------------------------|
| POST    | /api/users      | Új felhasználó létrehozása. A request body-ban egy User objektumot vár (ID nélkül).          | 201 Created    | 400 Bad Request, 409 Conflict                |
| GET     | /api/users      | Az összes felhasználó listázása.                                                             | 200 OK         | -                                            |
| GET     | /api/users/{id} | Egy felhasználó lekérdezése ID alapján.                                                      | 200 OK         | 404 Not Found                                |
| PUT     | /api/users/{id} | Meglévő felhasználó adatainak frissítése. A request body-ban egy teljes User objektumot vár. | 200 OK         | 400 Bad Request, 404 Not Found, 409 Conflict |
| DELETE  | /api/users/{id} | Felhasználó törlése ID alapján.                                                              | 204 No Content | 404 Not Found                                |

### Validációs Szabályok
A `username` és `email` mezők kitöltése kötelező.

-   Az `email` formátumának validnak kell lennie.

-   A `username` és `email` értékeknek egyedinek kell lenniük. Ha létező `username`-mel vagy `email`-lel próbálsz új felhasználót létrehozni, az alkalmazás `409 Conflict` hibát ad vissza.

## 2. A Te Feladatod
Készíts egy új, önálló Maven projektet, amely a fent leírt "User Service" alkalmazás automatizált tesztelését valósítja meg. A megoldásodat egy általad létrehozott, publikus GitHub repository-ba töltsd fel.

### Kötelező Feladatok (Backend Tesztelés)

**1. Projekt Struktúra és Eszközök:**

-   Hozz létre egy új Maven projektet.

-   Használd a `JUnit 5` vagy `TestNG` tesztelési keretrendszert.

-   Az API tesztekhez használd a `RestAssured` könyvtárat.

-   A kód legyen jól strukturált, olvasható és könnyen karbantartható. Kövesd a standard Java konvenciókat.


**2. API Tesztesetek:**

-   Írj teszteket a CRUD műveletek mindegyikére.

-   **Pozitív tesztesetek:**

   -   Sikeres felhasználó létrehozása és validálása (státuszkód, response body, adatbázis állapot).

   -   Felhasználó lekérdezése ID alapján.

   -   Összes felhasználó listázása.

   -   Felhasználó adatainak sikeres frissítése.

   -   Felhasználó törlése.

-   **Negatív tesztesetek és határérték-analízis:**

   -   Felhasználó létrehozása hiányos adatokkal (pl. `username` nélkül). Ellenőrizd a `400 Bad Request` státuszkódot és a hibaüzenetet.

   -   Felhasználó létrehozása már létező `username` vagy `email` címmel (`409 Conflict`).

   -   Nem létező ID-val történő lekérdezés, frissítés, törlés (`404 Not Found`).

   -   Érvénytelen email formátum tesztelése.

**3. Tesztadatok Kezelése:**

-   A tesztadatok ne legyenek "hardcode-olva" a tesztesetekben. Használj valamilyen adatkezelési stratégiát (pl. Builder pattern, adatgeneráló segédfüggvények, adatfájlok).


**4. Dokumentáció:**

-   Készíts egy `README.md` fájlt a projekted gyökerébe, amely tartalmazza:

   -   A projekt rövid leírását.

   -   A projekt felállításához szükséges lépéseket (pl. függőségek telepítése).

   -   A tesztek futtatásának módját (pl. `mvn clean test`).

   -   Rövid leírást az általad alkalmazott struktúráról és design döntésekről.

**5. Frontend Tesztelés (Web UI):**

-   A "User Service" projekt tartalmaz egy rendkívül egyszerű webes felületet a `src/main/resources/static/index.html` címen. Ez egy alapvető űrlapot és egy táblázatot jelenít meg a felhasználók kezelésére.

-   Írj Selenium WebDriver alapú teszteket, amelyek lefedik a következőket:

   -   Új felhasználó hozzáadása az űrlapon keresztül.

   -   Ellenőrzés, hogy a létrehozott felhasználó megjelenik-e a táblázatban.

### Opcionális (Bónusz) Feladatok
Ezek a feladatok nem kötelezőek, de elvégzésükkel demonstrálhatod mélyebb tudásodat és plusz pontot érnek az értékelés során.

---

**2. CI/CD Integráció:**

-   Készíts egy `.gitlab-ci.yml` fájlt a projekt gyökerébe.
-   A fájl definiáljon egy egyszerű pipeline-t, amely képes:
    -   A projektet **buildelni**
    -   A teszteket lefuttatni (pl. `test` stage-ben a `mvn clean test` parancs)
-   Nem szükséges működő GitLab Runner-t beállítanod, a cél a pipeline definíciós fájl megléte és helyessége.

**+ Dockerizáció (bónusz CI/CD feladat):**

-   Hozz létre egy `Dockerfile`-t a projekt gyökerében, amely képes konténerizálni az automatizált tesztprojektet.
-   A Docker image-nek képesnek kell lennie:
    -   A szükséges függőségek telepítésére
    -   A tesztek futtatására `ENTRYPOINT` vagy `CMD` segítségével (pl. `mvn clean test`)
-   Frissítsd a `.gitlab-ci.yml` fájlt, hogy tartalmazzon egy `docker build` lépést is:
    -   Pl.: `docker build -t user-test-runner .`
-   Opcionálisan a pipeline buildelheti és futtathatja is a konténert (CI futtatás dockerben)

Ez a lépés segít demonstrálni, hogy otthonosan mozogsz modern DevOps eszközök között is.

## 3. Leadás és Értékelés

### Leadás

1.  Hozd létre a teszt-projektet egy új, publikus GitHub repository-ban.

2.  Győződj meg róla, hogy a `README.md` fájl teljes és informatív.

3.  A repository linkjét küldd el annak a személynek, akivel a kapcsolatot tartod.
