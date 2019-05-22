import java.text.ParseException;
import java.util.Date;
import java.time.*;
import java.text.SimpleDateFormat;
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

    System.out.println("Witamy w systemie linii lotniczej 'Who says sky is the limit?'");
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
                .filter(flight -> flight.getDeparture().contains(place)) //filtrowanie miejsca wylotu
                .forEach(lot::add);

        break;

      case 2:
        System.out.println("\nDobrze. Kiedy chcesz lecieć? Wpisz datę w formacie dd-mm-yyyy.");
        String date = scanner.nextLine();

        listFlights.stream()
                .filter(flight -> flight.getDeparture_date().equals(date)) //filtrowanie daty wylotu
                .forEach(lot::add);

        break;

      case 3:
        System.out.println("\nDobrze. Ile maksymalnie chcesz wydać na lot?");
        Double price = scanner.nextDouble();

        listFlights.stream()
                .filter(flight -> flight.getPrice() <= price) //filtrowanie ceny lotu
                .forEach(lot::add);

        break;
      default:
        System.out.println("Wprowadzono błędne dane.");
    }

    if(lot.isEmpty()){ // wykonuje się w momencie niespełnienia warunków przez żaden lot
      System.out.println("\nNiestety nie znaleziono lotu o pożądanych przez Panią/Pana parametrach. Dziękujemy za skorzystanie z systemu i życzymy miłego dnia.");
      System.exit(0);
    }

    Map<Integer, Flight> lista_lotow = new HashMap<>(); //utworzenie mapy z indeksem wybranych lotów, żeby wyświetlić indeksy przy lotach i umożliwić użytkownikowi wybór lotu

    for (int i = 0; i < lot.size(); i++) {
      lista_lotow.put(i + 1, lot.get(i));
    }

    System.out.println("Oto loty odpowiadające Twojemu zapytaniu:");

    for(Map.Entry<Integer, Flight> entry: lista_lotow.entrySet()) { //wyświetlenie lotów na ekranie
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

    Date data = Calendar.getInstance().getTime(); //pobranie dzisiejszej daty

    try {
      data = new SimpleDateFormat("dd-MM-yyyy").parse(chosen_flight.getDeparture_date()); //pobranie daty lotu
    }
    catch (ParseException exception){
      System.out.println("Zły format daty.");
      System.out.println("Wystąpił błąd w systemie.");
      System.exit(0);
    }

    Date today = Calendar.getInstance().getTime(); //pobranie dzisiejszej daty

   double total_price = day_of_week_price(chosen_flight.getPrice(), data);
   total_price = date_of_departure_price(total_price, data, today);
   total_price = number_of_passengers_price(total_price, chosen_flight.getNumber_of_passengers());

   System.out.println("Cena Twojego lotu wynosi " + total_price + " PLN.");
   System.out.println("Chcesz wybrać bilet w klasie biznes? Będzie się to wiązało z dopłatą w wysokości 65% podanej wcześniej ceny.");
   System.out.println("Napisz 'tak', jeśli się zgadzasz.");

   Boolean business_ticket = false;

   String agreement = scanner.nextLine();

   if(agreement.equals("tak")){
     business_ticket = true;
     total_price *= 1.65;
   }

   System.out.println("Ile biletów chcesz kupić?");
   int number_of_tickets = scanner.nextInt();
   int total_number = chosen_flight.getNumber_of_passengers() + number_of_tickets;

   group_price_of_tickets(number_of_tickets, total_price, chosen_flight.getNumber_of_passengers());
   /*
   try {
     if() {
       group_price_of_tickets(number_of_tickets, total_price, chosen_flight.getNumber_of_passengers());
     }
   }
   catch (Exception overbooking)
   */

  }

  public static double day_of_week_price(Double price, Date flight_date){
    double new_price = price;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(flight_date); //pobranie daty lotu
    if (calendar.get(Calendar.DAY_OF_WEEK) == 1){ //sprawdzenie, czy dzień lotu to niedziela
       new_price = price * 1.1;
    }
    return new_price;
  }




  public static double date_of_departure_price(Double price, Date flight_date, Date today_date){
    price = (flight_date.compareTo(today_date) > 30) ? (price *= 0.9) : (price); //sprawdzenie liczby dni do odlotu za pomocą operatora warunkowego
    price = (flight_date.compareTo(today_date) < 7) ? (price *= 1.2) : (price);
    return price;
  }

  public static double number_of_passengers_price(Double price, int passengers) {
    if ((100 - passengers) < 20) {
      return price * 1.25;
    } else if ((100 - passengers) < 50) {
      return price * 1.1;
    } else {
      return price;
    }

  }

  public static double group_price_of_tickets(int new_passengers, Double price, int passengers) throws {
      double group_price;
      Scanner scanner = new Scanner(System.in);

      if()

      if(new_passengers + passengers > 100){
        System.out.println("Niestety liczba pasażerów jest za duża. Ale zawsze możesz polecieć jako steward/stewardessa z 20% zniżką.");
        int stewards = (new_passengers + passengers) - 100;
        group_price = (stewards * (price * 0.8)) + ((new_passengers - stewards) * price);

        System.out.println("Tylu pasażerów poleci w roli stewardów:" + stewards);
        System.out.println("Oto ostateczna cena:" + group_price + " PLN");
        System.out.println("Zgadasz się na tą propozycję? Jeśli tak, to napisz 'tak'. Jeśli nie, to napisz 'nie'");

        String agreement = scanner.nextLine();
        if (agreement.equals("tak")){
          return group_price;
        }
        if(agreement.equals("nie")){
          System.out.println("Dobrze. W takim razie jest jeszcze jedna opcja. Miejsce na skrzydle z 50% zniżką.");
          group_price = (stewards * (price * 0.5)) + ((new_passengers - stewards) * price);

          System.out.println("Tylu pasażerów poleci na skrzydle:" + stewards);
          System.out.println("Oto ostateczna cena:" + group_price + " PLN");
          System.out.println("Zgadasz się na tą propozycję? Jeśli tak, to napisz 'tak'. Jeśli nie, to napisz 'nie'");

          if (agreement.equals("tak")){
            return group_price;
          }
          if (agreement.equals("nie")){
            System.out.println("Niestety nie ma możliwości kupna biletów dla tylu osób. Dziękujemy za skorzystanie z systemu.");
            System.exit(0);
          }

        }

      else{
        return group_price;
        }

      }
    }



  }






