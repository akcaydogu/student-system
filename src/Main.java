import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    final static String adminName = "admin";
    final static String adminPass = "admin";
    public static String username = "";
    public static String password = "";
    public static Scanner scanner = new Scanner(System.in);
    public static boolean isLoggedIn = false;


    public static void operation(int x) {
        switch (x) {
            //Kayıt işlemini çağırır
            case 6:
                authorization(2);
                break;
            //Çıkış işlemi
            case 0:
                username = "";
                password = "";
                isLoggedIn = false;
                break;
        }
    }
    public static boolean signIn(String username, String password, int phase) {
        if(phase == 1) {
            //Map'in içinde ki değerleri x değerine atayıp döngü içerisinde hepsini döndürür
            for (ArrayList<String> x : map.values()) {
                //Eğer x'in 0. indexi alınan username 1. indexi alınan şifreye eşitse giriş sağlar
                if (x.get(0).equals(username) && x.get(1).equals(password)) {
                    System.out.println("Başarıyla giriş yapıldı");
                    isLoggedIn = true;
                    return true;
                }
            }
        } else if(phase == 2) {
            System.out.printf("Hoşgeldin %s! %n", map.get(username).get(2));
            System.out.println("--------------------------------");
            System.out.println("Notlar 1");
            System.out.println("Devamsızlık 2");
            System.out.println("Ders Programı 32");
            System.out.println("Bilgileriniz 4");
            System.out.println("--------------------------------");
            System.out.println("Öğrencileri görüntülemek için 1");
            System.out.println("Ders programını görüntülemek için 2");
            System.out.println("Duyuru eklemek için 3");
            System.out.println("--------------------------------");
            System.out.println("Öğretmenleri görüntülemek için 1");
            System.out.println("Tüm kayıtları görüntülemek için 2");
            System.out.println("Kayıt oluşturmak için 6");
            System.out.println("Çıkış yapmak için 0");
            int x = scanner.nextInt();
            scanner.nextLine();
            //Operation methodunu çağırır ve girmek istediğimiz işlemi methodun içine atar
            operation(x);
        }

        return false;
    }
    //Kayıt

    //username-password-isim-numara-yaş-duyuru
    public static void signUp(String username, String password, String name, String id, int age, boolean isDuyuru, int permissionLevel) {

        //Map'in içinde ki değerleri x'e atar ve döngü içinde kontrol sağlanır
        for (String x : map.keySet()) {
            //Eğer x değeri daha önce kullanıldıysa uyarı verir
            if(x.equals(username)) {
                System.out.println("Bu kullanıcı adı ile kaydolan başka bir üye zaten mevcut!");
            }
        }

        //Mapin içine yeni bir key ve keyin içine değerler yerleştirir (Keyin adı "username" valueler ise .add fonksiyonu ile eklenir)
        map.put(username, new ArrayList<>());
        map.get(username).add(username);
        map.get(username).add(password);
        map.get(username).add(name);
        map.get(username).add(id);
        map.get(username).add(age+"");
        map.get(username).add(isDuyuru+"");
        map.get(username).add(permissionLevel+"");
    }

    public static void authorization(int x) {
        try {
            if(x == 1) {
                System.out.print("Lütfen kullanıcı adınızı giriniz: ");
                username = scanner.nextLine();
                System.out.print("Lütfen şifrenizi giriniz: ");
                password = scanner.nextLine();

                //Giriş methodunu çağırır eğer true döndürüyorsa içerisinde 2. giriş metodunu çağırır
                if (signIn(username, password, 1)) {
                    signIn(username, password, 2);
                } else {
                    System.out.println("Yanlış kullanıcı adı veya şifre girdiniz!");
                }
            } else if (x == 2) {
                //Kayıt için gerekli değerleri alır
                System.out.println("Kayıt Sistemi");
                System.out.print("Lütfen kullanıcı adını giriniz: ");
                String username = scanner.nextLine();
                System.out.print("Lütfen şifre giriniz: ");
                String password = scanner.nextLine();
                System.out.print("Lütfen ad giriniz: ");
                String name = scanner.nextLine();
                String uniqueID = UUID.randomUUID().toString();
                System.out.print("Lütfen yaş giriniz: ");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Lütfen yetki seviyesini giriniz 1/2/3 : ");
                int permissionLevel = scanner.nextInt();
                scanner.nextLine();

                //Kayıt methoduna değerleri gönderir
                signUp(username, password, name, uniqueID, age, false, permissionLevel);
                System.out.println(map);
            } else {
                System.out.println("Lütfen 1 veya 2 numaralı işlemi seçiniz!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        //Yöneticiyi program açıldığında kayıt eder
        signUp(adminName, adminPass, "Yönetici", UUID.randomUUID().toString(), 0, false, 3);

        while (true) {
            if (isLoggedIn) {
                signIn(username, password, 2);
            } else {
                authorization(1);

            }
        }

    }
}



