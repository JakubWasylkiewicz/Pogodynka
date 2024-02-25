# Pogodynka
Pogodynka (założenia)
- aplikacja konsolowa
- prezentowane są jednostki metryczne (C, hPa, %, m/s)
Wymagania
- Ja, jako użytkownik, powinienem mieć możliwość wpisania adresu/lokacji, dla której ma zostać zaprezentowana szczegółowa prognoza pogody
— Task: pobranie informacji o adresie/lokacji od użytkownika
— Subtask: walidacja adresu/lokacji
	- jeśli lokacja nie istnieje, to komunikat o błędzie dla użytkownika
	- jeśli nie możemy pobrać informacji o pogodzie, to komunikat dla użytkownika
— Subtask: pobranie od użytkownika adresu/lokacji w formie wizarda. Pytamy się go o:
	- kraj
	- miasto
	- kod pocztowy
	- ulica
— Subtask: przygotowanie modelu danych (klasę { kraj, miasto, kodPocztowy, Ulica, długośćGeograficzna, szerokośćGeograficzna, obiekt: aktualnaPogoda })
— Task: wyliczenie długości i szerokości geograficznej dla zadanej przez użytkownika lokacji
— Subtask: użycie Google Geocoding API
— Subtask: stworzenie apiKey w google console
— Subtask: użycie biblioteki https://github.com/googlemaps/google-maps-services-java do komunikacji z Geocoding API
— Subtask: zapisanie długości i szerokości geograficznej w modelu danych
— Task: implementacja pobrania pogody dla zadanej lokalizacji z serwisów pogodowych
— Subtask: Przygotowanie modelu danych dla rezultatu (temperatura, ciśnienie, wilgotność, kierunek i prędkość wiatru).
— Subtask: Integracja z https://openweathermap.org/api (strategia)
— Subtask: stworzenie apiKey
— Subtask: na podstawie dokumentacji przygotować generator url
— Subtask: przygotować model danych (dla odpowiedzi)
— Subtask: Integracja z https://developer.accuweather.com/apis (strategia)
— Subtask: stworzenie apiKey
— Subtask: na podstawie dokumentacji przygotować generator url
— Subtask: przygotować model danych (dla odpowiedzi)
— Subtask: Integracja z https://weatherstack.com/documentation (strategia)
— Subtask: stworzenie apiKey
— Subtask: na podstawie dokumentacji przygotować generator url
— Subtask: przygotować model danych (dla odpowiedzi)
— Subtask: uśrednienie wartości pobranych z serwisów pogodowych
— Subtask: uzupełnienie aktualnej pogody w modelu danych
— Subtask: zapisanie danych do bazy danych
— Subtask: Prezentacja danych użytkownikowi
— Task: baza danych
— Subtask: przygotowanie schematu bazy danych
— Subtask: integracja aplikacji konsolowej z bazą danych
