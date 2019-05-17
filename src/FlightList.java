import java.util.*;

public class FlightList {

  public static void main(String[] args) {
    List<Flight> listFlights = Arrays.asList(
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

    System.out.println("Witamy w systemie linii lotniczej 'Who says the sky is the limit?'");
    System.out.println("Mamy dla Ciebie pytania - wybierz za pomocą klawiszy, na które chcesz odpowiedzieć.");
    System.out.println("\n1. Skąd lecisz?");
    System.out.println("2. Kiedy chcesz lecieć?");
    System.out.println("3. Jaki jest Twój maksymalny budżet na podróż?");

    List<Flight> lot = new ArrayList<>();

    int decision = Integer.valueOf(scanner.nextLine());

    switch (decision) {
      case 1:
        System.out.println("\nDobrze. W takim razie skąd chcesz lecieć?");
        String place = scanner.nextLine();

        listFlights.stream()
                .filter(flight -> flight.getDeparture().equals(place))
                .forEach(lot::add);
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                //.forEach(System.out::println);
        break;

      case 2:
        System.out.println("/nDobrze. Kiedy chcesz lecieć?");
        String date = scanner.nextLine();

        listFlights.stream()
                .filter(flight -> flight.getDeparture_date().equals(date))
                .forEach(lot::add);
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                //.forEach(System.out::println);
        break;

      case 3:
        System.out.println("/nDobrze. Ile maksymalnie chcesz wydać na lot?");
        Double price = scanner.nextDouble();

        listFlights.stream()
                .filter(flight -> flight.getPrice() <= price)
                .forEach(lot::add);
                //.map(flight -> "* lot z lotniska " + flight.getDeparture() + " do " + flight.getArrival()   + " z datą wylotu " + flight.getDeparture_date() + " i powrotem " + flight.getReturn_date() + ", który potrwa " + flight.getTime() + " godziny.")
                //.forEach(System.out::println);
        break;
      default:
        System.out.println("Wprowadzono błędne dane.");
    }

    System.out.println("Oto loty odpowiadające Twojemu zapytaniu:");
    System.out.println(lot);


  }



}
