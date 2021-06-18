import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        int counter = 0;
        int sumFlyTime = 0;
        List<Integer> list = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\adm\\Desktop\\tickets.json"));
            JSONArray tickets = (JSONArray) jsonObject.get("tickets");
            for (Object o: tickets){
                JSONObject ticket = (JSONObject) o;
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
                if (((String) ticket.get("origin_name")).equals("Владивосток")
                        && ((String) ticket.get("destination_name")).equals("Тель-Авив")) {
                    String date = (String) ticket.get("departure_date");
                    String time = (String) ticket.get("departure_time");
                    String date1 = (String) ticket.get("arrival_date");
                    String time1 = (String) ticket.get("arrival_time");
                    Date dateDeparture = formatter.parse(date + " " + time);
                    Date dateArrival = formatter.parse(date1 + " " + time1);
                    long msFlyTime = (dateArrival.getTime() - dateDeparture.getTime()) / (1000 * 60);
                    int flyTime = (int) msFlyTime;
                    list.add(flyTime);
                    sumFlyTime += flyTime;
                    counter++;
                }
            }
            int middleTime = sumFlyTime/counter;
            System.out.println(middleTime);
            if ((int) counter*90/100 == counter*90/100) {
                int proc = counter * 90 / 100;
                Collections.sort(list);
                if (list.get(proc - 1) != list.get(proc)){
                    System.out.println(list.get(proc - 1));
                }
                else {
                    System.out.println("Невозможно рассчитать 90-й процентиль, так как времена полетов на границе 90%-й зоны равны");
                }
            }
            else {
                System.out.println("Невозможно точно рассчитать 90-й процентиль, так как 90% полетов - нецелое число");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Такого файла не существует");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Неверный формат текста");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            System.out.println("Неверный формат даты и времени");
        }
    }
}
