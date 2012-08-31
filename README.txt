-----------------------------------------------------------------------------------
										Mappar:
-----------------------------------------------------------------------------------
* doc 			Innehåller Java-dokumentation.
* Iterationer	Innehåller acceptanstester för olika iterationer.
* lib			I denna mapp finns viktiga libraries som behövs för att programmet skall kunna exekveras.
* manual 		Innehåller en html-manual och bilder till denna.
* release	 	Här finns en massa jar-filer som byggts med build.xml.
* data			Här hamnar alla filer som programmet skapar, resultatfilen, startfilen och målfilen.
* src	 		Innehåller källkoden uppdelade efter paket för samtliga klasser i systemet.
* bin			Innehåller alla kompilerade klasser även dessa uppdelade efter paket.
-------------------------------------------------------------------------------------
								Hur man bygger systemet:
-------------------------------------------------------------------------------------
Du bygger systemet genom att exekvera build.xml i ant. När bygget är klart finns en ny, med «$java -jar Enduro-xyz.jar», JAR i katalogen release/.
Alternativt kan start med «$cd project-directory» följt av «$java -classpath lib/:bin/ gui.MainView».
 
-------------------------------------------------------------------------------------
										Notiser:
-------------------------------------------------------------------------------------
För att kunna generera en resultatfil krävs att du har filen persons.txt (eller den fil som du specifierar i enduro.config) 
tillgänglig i programmets rotkatalog.
-------------------------------------------------------------------------------------
							Kort arkitekturell beskrivning:
-------------------------------------------------------------------------------------
Den grundläggande arkitekturen för systemet är en "pipe-filter" arkitektur. 
Data genereras genom registering av starttider respektive måltider, dessa genereras på ett identiskt sätt. Datan registeras för ett 
specifikt startnummer som i genereringssteget länkas ihop med personuppgifter från en given fil. I genereringssteget skapas en sorterad 
resultatlista som innehåller all relevant information som tex Varvtider, Starttider, Totaltider och klassindelning.

För att representera de olika varvtyperna {marathon, varv, etapp}-lopp finns det olika deltagarklasser med olika funktionalitet namngivna med syntaxen XDriver där X är en teckensträng.
För alla deltagarklasser finns en motsvarande generator namngiven på motsvarande sätt.

Programmet startas enligt de parametrar som finns angivna i konfigurationsfilen, dessa bestämmer vilken uppsättning av objekt som kommer att skapas och användas.