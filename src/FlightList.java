
import java.util.*;

public class FlightList {

  public static void main(String[] args) {
    List<Flight> listFlights = Arrays.asList( //lista lotów
            new Flight("Warszawa Chopina", "WAW", "Wiedeń", "VIE", "22-05-2019", "30-05-2019", 2, 80, 125.00),
            new Flight("Berlin Schoenefeld", "SXF", "Londyn Heathrow", "LHR", "01-06-2019", "04-06-2019", 1, 20, 100.00),
            new Flight("Paryż Charles de Gaulle", "CDG", "Pekin", "PEK", "24-05-2019", "01-06-2019", 8, 90, 225),
            new Flight("Szczecin Goleniów", "SZZ", "Warszawa Chopina", "WAW", "17-05-2019", "21-05-2019", 1, 8, 35.00),
            new Flight("Warszawa Chopina", "WAW", "Berlin Schoenefeld", "SXF", "14-06-2019", "30-06-2019", 2, 5, 75.00),
            new Flight("Szczecin Goleniów", "SZZ", "Pekin", "PEK", "29-09-2019", "30-10-2019", 10, 50, 250.00),
            new Flight("Berlin Schoenefeld", "SXF", "Wiedeń", "VIE", "01-06-2019", "02-06-2019", 1, 70, 55.00),
            new Flight("Warszawa Chopina", "WAW", "Paryż Charles de Gaulle", "CDG", "24-05-2019", "29-05-2019", 3, 40, 200.00),
            new Flight("Paryż Charles de Gaulle", "CDG", "Berlin Schoenefeld", "SXF", "18-08-2019", "22-08-2019", 2, 100, 50.00),
            new Flight("Szczecin Goleniów", "SZZ", "Londyn Heathrow", "LHR", "25-07-2019", "26-07-2019", 1, 95, 45.00)
    );

    Scanner scanner = new Scanner(System.in);

    System.out.println("Witamy w systemie linii lotniczej 'Who says  sky is the limit?'");
    System.out.println("Mamy dla Ciebie pytania - wybierz za pomocą klawiszy, na które chcesz odpowiedzieć.");
    System.out.println("\n1. Skąd lecisz?");
    System.out.println("2. Kiedy chcesz lecieć?");
    System.out.println("3. Jaki jest Twój maksymalny budżet na podróż?");


    List<Flight> lot = new ArrayList<>(); //stworzenie pustej listy do gromadzenia lotów spełniających warunki

    int decision = Integer.valueOf(scanner.nextLine());

    switch (decision) {
      case 1:
        System.out.println("\nDobrze. W takim razie skąd chcesz lecieć?");
        String place = scanner.nextLine();

        listFlights.stream()
                .filter(flight -> flight.getDeparture().equals(place)) //filtrowanie miejsca wylotu
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                .forEach(lot::add);

                //.forEach(System.out::println);
        break;

      case 2:
        System.out.println("\nDobrze. Kiedy chcesz lecieć?");
        String date = scanner.nextLine();

        listFlights.stream()
                .filter(flight -> flight.getDeparture_date().equals(date)) //filtrowanie daty wylotu
                .forEach(lot::add);
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                //.forEach(System.out::println);
        break;

      case 3:
        System.out.println("\nDobrze. Ile maksymalnie chcesz wydać na lot?");
        Double price = scanner.nextDouble();

        listFlights.stream()
                .filter(flight -> flight.getPrice() <= price) //filtrowanie ceny lotu
                .forEach(lot::add);
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                //.forEach(System.out::println);
        break;
      default:
        System.out.println("Wprowadzono błędne dane.");
    }

    if(lot == null){ // wykonuje się w momencie niespełnienia warunków przez żaden lot
      System.out.println("Niestety nie znaleziono lotu o pożądanych przez Panią/Pana parametrach. Dziękujemy za skorzystanie z systemu i życzymy miłego dnia.");
    }

    Map<Integer, Flight> lista_lotow = new HashMap<>(); //utworzenie mapy z indeksem wybranych lotów, żeby wyświetlić indeksy przy lotach i umożliwić użytkownikowi wybór lotu

    for (int i = 0; i < lot.size(); i++) {
      lista_lotow.put(i + 1, lot.get(i));
    }

    System.out.println("Oto loty odpowiadające Twojemu zapytaniu:");

    for(Map.Entry<Integer, Flight> entry: lista_lotow.entrySet()) {
      Integer key = entry.getKey();
      Flight value = entry.getValue();
      System.out.println("Opcja " + key + ": " + "lot z lotniska " + value.getDeparture() + " do " + value.getArrival()  + " z datą wylotu " + value.getDeparture_date() + " i powrotem " + value.getReturn_date() + ", który potrwa " + value.getTime() + " h.");
    }

    System.out.println("Wybierz odpowiadający Ci lot za pomocą cyfry na klawiaturze.");
    int chosen = scanner.nextInt(); //wybór lotu przez użytkownika
    Flight chosen_flight = lista_lotow.get(chosen); //wybrany lot

    System.out.println("Zdecydował(a) się Pan/Pani na lot o następujących parametrach: " +
            "\nPort początkowy: " + chosen_flight.getDeparture() +
            "\nPort końcowy: " + chosen_flight.getArrival()  +
            "\nData wylotu: " + chosen_flight.getDeparture_date() +
            "\nData powrotu: " + chosen_flight.getReturn_date() +
            "\nDługość trwania lotu: " + chosen_flight.getTime() + " h");

    //chosen_flight.getPrice()
    //System.out.println();

  }



}
