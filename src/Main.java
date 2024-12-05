import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    final static String adminName = "admin";
    final static String adminPass = "admin";
    public static String duyuru = " ";
    public static String username = "";
    public static String password = "";
    public static Scanner scanner = new Scanner(System.in);
    public static boolean isLoggedIn = false;


    public static void informationUser() {
        System.out.printf("%-25s %-20s %-20s %-40s %-10s %-20s %n", "Öğrenci Kullanıcı Adı", "Öğrenci Şifresi", "Öğrenci Adı", "ID", "Yaş", "Yetki Seviyesi");
        System.out.printf("%-25s %-20s %-20s %-40s %-10s %-20s %n", map.get(username).get(0), map.get(username).get(1), map.get(username).get(2), map.get(username).get(3), map.get(username).get(4), map.get(username).get(6));

    }
    public static void operation(int x, int accessLevel) {

        switch (accessLevel){
            case 1:
                switch (x) {
                    case 1:
                        courseView(username);
                        break;
                    case 2:
                        informationUser();
                        break;
                    case 0:
                        username = "";
                        password = "";
                        isLoggedIn = false;
                        break;
                }
                break;
            case 2:
                switch (x) {
                    case 1:
                        studentList();
                        break;
                    case 2:
                        informationUser();
                        break;
                    case 0:
                        username = "";
                        password = "";
                        isLoggedIn = false;
                        break;
                }
                break;
            case 3:
                switch (x) {
                    //Kayıt işlemini çağırır
                    case 1:
                        studentList();
                        System.out.println("Öğrenci seçenekleri :");
                        System.out.println("Ders Eklemek için 1");
                        System.out.println("Ders Silmek için 2");
                        System.out.println("Derslerini görüntülemek için 3");
                        int y = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Lütfen öğrenci adı giriniz");
                        String targetUsername = scanner.nextLine();
                        switch (y) {
                            case 1:
                                courseAdd(targetUsername);
                                break;
                            case 2:
                                courseRemove(targetUsername);
                                break;
                            case 3:
                                courseView(targetUsername);
                                break;
                        }
                        break;
                    case 2:
                        studentList();
                        break;
                    case 3:
                        authorization(2);
                        break;
                    case 4:
                        duyuru();
                        break;
                    //Çıkış işlemi
                    case 0:
                        username = "";
                        password = "";
                        isLoggedIn = false;
                        break;
                }
                break;
        }
    }

    public static boolean signIn(String username, String password, int phase) {
        if (phase == 1) {
            //Map'in içinde ki değerleri x değerine atayıp döngü içerisinde hepsini döndürür
            for (ArrayList<String> x : map.values()) {
                //Eğer x'in 0. indexi alınan username 1. indexi alınan şifreye eşitse giriş sağlar
                if (x.get(0).equals(username) && x.get(1).equals(password)) {
                    System.out.println("Başarıyla giriş yapıldı");

                    if (map.get(username).get(5).equals("true")) {
                        System.out.println(duyuru);
                        map.get(username).set(5, "false");

                    }
                    isLoggedIn = true;
                    return true;
                }
            }
        } else if (phase == 2) {

            int accessLevel = Integer.parseInt(map.get(username).get(6));

            System.out.printf("Hoşgeldin %s! %n", map.get(username).get(2));
            System.out.println("--------------------------------");
            switch (accessLevel){
                case 1:
                    System.out.println("Ders bilgilerinizi görmek için 1");
                    System.out.println("Kişisel Bilgileriniz için 2");
                    break;
                case 2:
                    System.out.println("Öğrencileri görüntülemek için 1");
                    System.out.println("Kişisel Bilgileriniz için 2");
                    break;
                case 3:
                    System.out.println("Öğrencileri Görüntülemek için 1");
                    System.out.println("Tüm kayıtları görüntülemek için 2");
                    System.out.println("Kayıt oluşturmak için 3");
                    System.out.println("Duyuru eklemek için 4");
                    break;
            }
            System.out.println("Çıkış yapmak için 0");
            System.out.println("--------------------------------");
            int x = scanner.nextInt();
            scanner.nextLine();
            //Operation methodunu çağırır ve girmek istediğimiz işlemi methodun içine atar
            operation(x, accessLevel);
        }

        return false;
    }
    //Kayıt

    //username-password-isim-numara-yaş-duyuru
    public static void signUp(String username, String password, String name, String id, int age, boolean isDuyuru, int permissionLevel) {

        //Map'in içinde ki değerleri x'e atar ve döngü içinde kontrol sağlanır
        for (String x : map.keySet()) {
            //Eğer x değeri daha önce kullanıldıysa uyarı verir
            if (x.equals(username)) {
                System.out.println("Bu kullanıcı adı ile kaydolan başka bir üye zaten mevcut!");
                return;
            }
        }

        //Mapin içine yeni bir key ve keyin içine değerler yerleştirir (Keyin adı "username" valueler ise .add fonksiyonu ile eklenir)
        map.put(username, new ArrayList<>());
        map.get(username).add(username);
        map.get(username).add(password);
        map.get(username).add(name);
        map.get(username).add(id);
        map.get(username).add(age + "");
        map.get(username).add(isDuyuru + "");
        map.get(username).add(permissionLevel + "");
    }

    public static void authorization(int x) {
        try {
            if (x == 1) {
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

    public static void studentList() {
        System.out.printf("%-25s %-20s %-20s %-40s %-10s %-20s %n", "Öğrenci Kullanıcı Adı", "Öğrenci Şifresi", "Öğrenci Adı", "ID", "Yaş", "Yetki Seviyesi");
        for (ArrayList x : map.values()) {
            System.out.printf("%-25s %-20s %-20s %-40s %-10s %-20s %n", x.get(0), x.get(1), x.get(2), x.get(3), x.get(4), x.get(6));
        }
    }

    public static void duyuru() {
        System.out.println("Duyuruyu giriniz:");
        duyuru = scanner.nextLine();


        for (String x2 : map.keySet()) {
            map.get(x2).set(5, "true");

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

    public static void courseAdd(String ogrenciAdi) {

        System.out.println("Bir ders adi giriniz");
        map.get(ogrenciAdi).add(scanner.nextLine());
        System.out.println("Bir ogretmen seciniz");
        map.get(ogrenciAdi).add(scanner.nextLine());
        System.out.println("Ders notunu giriniz");
        map.get(ogrenciAdi).add(scanner.nextLine());
        System.out.println("Devamsizligi giriniz");
        map.get(ogrenciAdi).add(scanner.nextLine());
        System.out.println(map);
    }


    public static void courseRemove(String ogrenciAdi) {

        System.out.println("Silmek istediginiz ders bilgisini yaziniz");
        String delete = scanner.nextLine();
        if (map.get(ogrenciAdi).contains(delete)) {

            for (int i = 6; i < map.get(ogrenciAdi).size(); i++) {

                if (map.get(ogrenciAdi).get(i).equals(delete)) {
                    map.get(ogrenciAdi).remove(i + 3);
                    map.get(ogrenciAdi).remove(i + 2);
                    map.get(ogrenciAdi).remove(i + 1);
                    map.get(ogrenciAdi).remove(i);

                }
            }

        } else {
            System.out.println("Boyle bir ders bilgisi bulunamadi");
        }
    }

    public static void courseView(String ogrenciAdi) {

        System.out.printf("%-10s %-10s %-10s %-10s %n", "Ders", "Öğretmen", "Not", "Devamsızlık");
        for (int i = 7; map.get(ogrenciAdi).size() > i; i += 4) {
            System.out.printf("%-10s %-10s %-10s %-10s %n",map.get(ogrenciAdi).get(i) ,map.get(ogrenciAdi).get(i + 1) , map.get(ogrenciAdi).get(i + 2) , map.get(ogrenciAdi).get(i + 3));
        }
    }
}


