import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    final static String adminName = "admin";
    final static String adminPass = "admin";


    public static boolean signIn(String username, String password) {
        for (ArrayList x : map.values()) {
            if (x.get(0).equals(username) && x.get(1).equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static boolean signUp(String username, String password) {

        for (String x : map.keySet()) {
            if(x.equals(username)) {
                System.out.println("Bu kullanıcı adı ile kaydolan başka bir üye zaten mevcut!");
                return false;
            }
        }
        map.put(username, new ArrayList<String>());
        map.get(username).add(username);
        map.get(username).add(password);

        return true;
    }

    public static void main(String[] args) {

        map.put(adminName, new ArrayList<String>());
        map.get(adminName).add(adminName);
        map.get(adminName).add(adminPass);

        // new file object
        Scanner scanner = new Scanner(System.in);
        int x = 0;

        while (true){
            System.out.println("1 for SignIn 2 for SignUp");
            try {
                x = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Lütfen sadece sayı giriniz!");
            }
            scanner.nextLine();
            if(x == 1) {
                System.out.println("Giriş sistemi");
                System.out.print("Lütfen kullanıcı adınızı giriniz: ");
                String username = scanner.nextLine();
                System.out.print("Lütfen şifrenizi giriniz: ");
                String password = scanner.nextLine();
                if (signIn(username, password)) {
                    System.out.println("Başarıyla giriş yapıldı!");
                    System.out.printf("Hoşgeldin %s! %n", username);
                } else {
                    System.out.println("Yanlış kullanıcı adı veya şifre girdiniz!");
                }
            } else if (x == 2) {
                System.out.println("Kayıt Sistemi");
                System.out.print("Lütfen kullanıcı adınızı giriniz: ");
                String username = scanner.nextLine();
                System.out.print("Lütfen şifrenizi giriniz: ");
                String password = scanner.nextLine();
                signUp(username, password);
            } else {
                System.out.println("Lütfen 1 veya 2 numaralı işlemi seçiniz!");
            }
        }

    }

}
