import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FlightListTest {


  @org.junit.jupiter.api.Test
  void day_of_week_price() {
    Date data = Calendar.getInstance().getTime();

    try {
      data = new SimpleDateFormat("dd-MM-yyyy").parse("26-05-2019");
    }
    catch (ParseException exception){
    }


    assertEquals(FlightList.day_of_week_price(100.00, data), 110.00000000000001); //interpretacja wartości po przecinku przez Javę
  }

  @org.junit.jupiter.api.Test
  void date_of_departure_price() {
    Date data1 = Calendar.getInstance().getTime();

    try {
      data1 = new SimpleDateFormat("dd-MM-yyyy").parse("26-05-2019");
    }
    catch (ParseException exception){
    }

    Date data2 = Calendar.getInstance().getTime();

    try {
      data2 = new SimpleDateFormat("dd-MM-yyyy").parse("27-05-2019");
    }
    catch (ParseException exception){
    }


    assertEquals(FlightList.date_of_departure_price(100.00, data1, data2), 120);
  }

  @org.junit.jupiter.api.Test
  void number_of_passengers_price() {
    assertEquals(FlightList.number_of_passengers_price(100.00, 70), 110.00000000000001); //interpretacja wartości po przecinku przez Javę
  }
}