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
            case 6:
                authorization(2);
                break;
            case 7:
                username = "";
                password = "";
                isLoggedIn = false;
        }
    }
    public static boolean signIn(String username, String password, int phase) {
        if(phase == 1) {
            for (ArrayList<String> x : map.values()) {
                if (x.get(0).equals(username) && x.get(1).equals(password)) {
                    System.out.println("Başarıyla giriş yapıldı");
                    isLoggedIn = true;
                    return true;
                }
            }
        } else if(phase == 2) {
            System.out.printf("Hoşgeldin %s! %n", map.get(username).get(2));
            System.out.println("Öğrencileri görüntülemek için 1");
            System.out.println("Ders programını görüntülemek için 2");
            System.out.println("Duyuru eklemek için 3");
            System.out.println("Bilgilerinizi değiştirmek için 4");
            System.out.println("Öğretmenleri görüntülemek için 5");
            System.out.println("Kayıt oluşturmak için 6");
            System.out.println("Çıkış yapmak için 7");
            int x = scanner.nextInt();
            scanner.nextLine();
            operation(x);
        }

        return false;
    }
    //Kayıt

    //username-password-isim-numara-yaş-duyuru
    public static void signUp(String username, String password, String name, String id, int age, boolean isDuyuru, int permissionLevel) {
        for (String x : map.keySet()) {
            if(x.equals(username)) {
                System.out.println("Bu kullanıcı adı ile kaydolan başka bir üye zaten mevcut!");
            }
        }

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
                if (signIn(username, password, 1)) {
                    signIn(username, password, 2);
                } else {
                    System.out.println("Yanlış kullanıcı adı veya şifre girdiniz!");
                }
            } else if (x == 2) {
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

        map.put(adminName, new ArrayList<>());
        map.get(adminName).add(adminName);
        map.get(adminName).add(adminPass);
        map.get(adminName).add("Yönetici");
        map.get(adminName).add(UUID.randomUUID().toString());
        map.get(adminName).add(0+"");
        map.get(adminName).add(false+"");
        map.get(adminName).add(3+"");

        while (true) {
            if (isLoggedIn) {
                signIn(username, password, 2);
            } else {
                authorization(1);
            }
        }


    }

}
