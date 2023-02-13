package testscripts;//import java.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
        String date = simpleDateFormat.format(date1);
        System.out.println(date);
    }
}
