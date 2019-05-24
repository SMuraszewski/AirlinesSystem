import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.time.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.*;

public class FlightSystem {


  public static void main(String[] args) throws IOException {
    List<Flight> listFlights = Arrays.asList( //lista lotów
            new Flight("Warszawa Chopina", "WAW", "Wiedeń", "VIE", "29-05-2019", "30-05-2019", 2, 80, 125.00),
            new Flight("Berlin Schoenefeld", "SXF", "Londyn Heathrow", "LHR", "01-06-2019", "04-06-2019", 1, 20, 100.00),
            new Flight("Paryż Charles de Gaulle", "CDG", "Pekin", "PEK", "23-06-2019", "01-07-2019", 8, 90, 225),
            new Flight("Szczecin Goleniów", "SZZ", "Warszawa Chopina", "WAW", "17-06-2019", "21-06-2019", 1, 8, 35.00),
            new Flight("Warszawa Chopina", "WAW", "Berlin Schoenefeld", "SXF", "14-06-2019", "30-06-2019", 2, 5, 75.00),
            new Flight("Szczecin Goleniów", "SZZ", "Pekin", "PEK", "29-09-2019", "30-10-2019", 10, 50, 250.00),
            new Flight("Berlin Schoenefeld", "SXF", "Wiedeń", "VIE", "01-06-2019", "02-06-2019", 1, 70, 55.00),
            new Flight("Warszawa Chopina", "WAW", "Paryż Charles de Gaulle", "CDG", "27-05-2019", "29-05-2019", 3, 40, 200.00),
            new Flight("Paryż Charles de Gaulle", "CDG", "Berlin Schoenefeld", "SXF", "18-08-2019", "22-08-2019", 2, 100, 50.00),
            new Flight("Szczecin Goleniów", "SZZ", "Londyn Heathrow", "LHR", "25-07-2019", "26-07-2019", 1, 95, 45.00)
    );

    Scanner scanner = new Scanner(System.in);

    System.out.println("Witamy w systemie linii lotniczej 'Who says sky is the limit?'");
    System.out.println("Masz trzy opcje. Wciśnij odpowiadającą pytaniu cyfrę na klawiaturze, by wybrać jedną z nich.");
    System.out.println("\n1. Skąd lecisz?");
    System.out.println("2. Kiedy chcesz lecieć?");
    System.out.println("3. Ile ma maksymalnie wynosić cena bazowa lotu?");


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
        System.out.println("\nDobrze. Ile maksymalnie ma wynosić cena bazowa lotu?");
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

   Boolean business_ticket = false; //zmienna booleanowska pokazuje, czy został wybrany bilet biznesowy

   String agreement = scanner.next();

   if(agreement.equals("tak")){
     business_ticket = true;
     total_price *= 1.65;
   }

   System.out.println("Ile biletów chcesz kupić?");
   int number_of_tickets = scanner.nextInt();

   double whole_price = total_price; //utworzenie zmiennej dla ceny grupowej

   try {
     whole_price = group_price_of_tickets(number_of_tickets, total_price, chosen_flight.getNumber_of_passengers());
   }
   catch (RuntimeException exception){
      System.out.println("Niestety, nie ma tylu miejsc w samolocie. Dziękujemy za korzystanie z systemu.");
      System.exit(0);
   }

   double final_price = additional_price(whole_price, business_ticket);

   Random r = new Random(); //implementacja losowej wartości do id

   int id = r.nextInt((999999 - 100000) + 1) + 100000; //generowanie 6-cyfrowej wartości

   BigDecimal currency_price = BigDecimal.valueOf(final_price); //przekształcanie ceny na typ BigDecimal
   BigDecimal round_price = currency_price.setScale(2, RoundingMode.HALF_DOWN); //zaokrąglanie ceny

   String t_price = round_price.toString();
   String ticket_price = t_price.replaceAll("\\.", ","); //zamiana kropki w cenie na przecinek

   String start_date = chosen_flight.getDeparture_date().replaceAll("-","."); //zamienianie myślników w datach na kropki
   String ret_date = chosen_flight.getReturn_date().replaceAll("-", ".");

   String filePath = "D:\\ticket.txt"; //ścieżka do pliku z biletem
   FileWriter fileWriter = null;


    try {
      fileWriter = new FileWriter(filePath);
      fileWriter.write("*********************************************************");
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Identyfikator rezerwacji: " + chosen_flight.getDeparture_code() + chosen_flight.getArrival_code() + id);
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Miejsce wylotu: " + chosen_flight.getDeparture() + " " + chosen_flight.getDeparture_code());
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Miejsce przylotu: " + chosen_flight.getArrival() + " " + chosen_flight.getArrival_code());
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Data wylotu: " + start_date);
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Data powrotu: " + ret_date);
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Liczba miejsc: " + number_of_tickets); //nie było w treści zadania, ale powinno moim zdaniem się znaleźć
      fileWriter.write(System.lineSeparator());
      fileWriter.write("Cena: " + ticket_price +  " PLN");
      fileWriter.write(System.lineSeparator());
      fileWriter.write("*********************************************************");
    } finally {
      if (fileWriter != null){
        fileWriter.close();
      }
    }

    System.out.println("\nTwój bilet powinien być już obecny na dysku w pliku ticket.txt.");
    System.out.println("Dziękujemy za skorzystanie z systemu i życzymy miłego lotu.");

  }

  public static double day_of_week_price(Double price, Date flight_date){ //wyliczenie ceny w zależności od dnia tygodnia
    double new_price = price;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(flight_date); //pobranie daty lotu
    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){ //sprawdzenie, czy dzień lotu to niedziela
       new_price = price * 1.1;
    }
    return new_price;
  }


  public static double date_of_departure_price(Double price, Date flight_date, Date today_date){ //wyliczenie ceny w zależności od daty
    price = (flight_date.compareTo(today_date) > 30) ? (price *= 0.9) : (price); //sprawdzenie liczby dni do odlotu za pomocą operatora warunkowego
    price = (flight_date.compareTo(today_date) < 7) ? (price *= 1.2) : (price);
    return price;
  }

  public static double number_of_passengers_price(Double price, int passengers) { //wyliczenie ceny w zależności od liczby zarezerwowanych miejsc
    if ((100 - passengers) < 20) {
      return price * 1.25;
    } else if ((100 - passengers) < 50) {
      return price * 1.1;
    } else {
      return price;
    }

  }

  public static double group_price_of_tickets(int new_passengers, Double price, int passengers) {
    double group_price = 0;
    Scanner scanner = new Scanner(System.in);

    if((new_passengers + passengers) > 110) {
      throw new RuntimeException("Zbyt duża liczba podróżnych"); //rzucenie wyjątku przy zbyt dużej liczbie podróżnych
    }

    if((new_passengers + passengers) > 100) {
      System.out.println("\nNiestety liczba pasażerów jest za duża. Ale zawsze możesz polecieć jako steward/stewardessa z 20% zniżką.");
      int stewards = (new_passengers + passengers) - 100;
      group_price = (stewards * (price * 0.8)) + ((new_passengers - stewards) * price);

      System.out.println("Tylu pasażerów poleci w roli stewardów: " + stewards);
      System.out.println("Cena wynosi: " + group_price + " PLN");
      System.out.println("Zgadasz się na tą propozycję? Jeśli tak, napisz 'tak'. Jeśli nie, to napisz 'nie'");

      String agreement = scanner.nextLine();

      if (agreement.equals("nie")) {
        System.out.println("\nDobrze. W takim razie jest jeszcze jedna opcja. Miejsce na skrzydle z 50% zniżką.");
        group_price = (stewards * (price * 0.5)) + ((new_passengers - stewards) * price);

        System.out.println("Tylu pasażerów poleci na skrzydle: " + stewards);
        System.out.println("Cena wynosi: " + group_price + " PLN");
        System.out.println("Zgadasz się na tą propozycję? Jeśli tak, napisz 'tak'. Jeśli nie, to napisz 'nie'");

        String final_agreement = scanner.nextLine();

        if (final_agreement.equals("nie")) {
          System.out.println("Niestety nie ma możliwości kupna biletów. Dziękujemy za skorzystanie z systemu.");
          System.exit(0);
        }
       }
      }
      if((new_passengers + passengers) <= 100){
        group_price = new_passengers * price;
        System.out.println("\nCena wynosi: " + group_price + " PLN.");
      }

    return group_price;
    }

  public static double additional_price(Double price, boolean business) { //dodanie opłat dodatkowych do ceny
    Scanner scanner = new Scanner(System.in);
    System.out.println("Dodatkowo do łącznej ceny dochodzi opłata serwisowa w wysokości 50 PLN.");
    price += 50;

    System.out.println("\nCzy chcesz zabrać bagaż? Jeśli tak, to napisz 'tak'. Jego maksymalna waga wynosi 10 kg i wiąże się z dodatkową opłatą w wysokości 120 PLN.");
    String answer = scanner.next();
    if (answer.equals("tak")){
      price += 120;
    }
    System.out.println("\nIle posiłków chcesz wcześniej zarezerwować? Cena wynosi 40 PLN za posiłek.");
    int meal = scanner.nextInt();
    price = price + (meal * 40);

    System.out.println("\nCzy chcesz mieć pierwszeństwo przy wsiadaniu do samolotu? Jeśli tak, to napisz 'tak'.");
    String boarding = scanner.next();
    if(boarding.equals("tak")){
      if(business == true){
        price += 25;
      }
      else{
        price += 15;
      }
    }
    return price;
  }


  }






