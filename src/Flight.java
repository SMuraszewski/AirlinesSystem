import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Flight {

  private String departure;
  private String departure_code;
  private String arrival;
  private String arrival_code;
  private String departure_date;
  private String return_date;
  private int time;
  private int number_of_passengers;
  private double price;


  public Flight(String departure, String departure_code, String arrival, String arrival_code, String departure_date, String return_date, int time, int number_of_passengers, double price) {
    this.departure = departure;
    this.departure_code = departure_code;
    this.arrival = arrival;
    this.arrival_code = arrival_code;
    this.departure_date = departure_date;
    this.return_date = return_date;
    this.time = time;
    this.number_of_passengers = number_of_passengers;
    this.price = price;
  }

  public String getDeparture() {
    return departure;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getDeparture_code() {
    return departure_code;
  }

  public void setDeparture_code(String departure_code) {
    this.departure_code = departure_code;
  }

  public String getArrival() {
    return arrival;
  }

  public void setArrival(String arrival) {
    this.arrival = arrival;
  }

  public String getArrival_code() {
    return arrival_code;
  }

  public void setArrival_code(String arrival_code) {
    this.arrival_code = arrival_code;
  }

  public String getDeparture_date() {
    return departure_date;
  }

  public void setDeparture_date(String departure_date) {
    this.departure_date = departure_date;
  }

  public String getReturn_date() {
    return return_date;
  }

  public void setReturn_date(String return_date) {
    this.return_date = return_date;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public int getNumber_of_passengers() {
    return number_of_passengers;
  }

  public void setNumber_of_passengers(int number_of_passengers) {
    this.number_of_passengers = number_of_passengers;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return
            "Miejsce wylotu: " + departure + ' ' + departure_code + '\n' +
            "Miejsce przylotu: " + arrival + ' ' + arrival_code + '\n' +
            "Data wylotu: " + departure_date + '\n' +
            "Data powrotu: " + return_date +  '\n' +
            "Długość lotu: " + time + " h \n" +
            "Liczba pasażerów: " + number_of_passengers + '\n' +
            "Cena: " + price + " PLN\n" +
            '\n';
  }

}

