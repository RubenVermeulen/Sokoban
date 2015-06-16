package resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {

    private Locale locale;
    private static ResourceBundle resourceBundle;

    public Taal(String l) {
        if (l.equals("nl")) {
            this.locale = new Locale("nl"/*,"BE"*/);
        }
        if (l.equals("en")) {
            this.locale = new Locale("en"/*,"US"*/);
        }
        if (l.equals("fr")) {
            this.locale = new Locale("fr"/*,"FR"*/);
        }
//        System.out.println("Taal wordt aangemaakt");
        resourceBundle = ResourceBundle.getBundle("resources.rb", locale);
    }

    public static String getText(String key) {
//        System.out.println("Tekst opgehaald: " + key);
        return resourceBundle.getString(key);
    }
}