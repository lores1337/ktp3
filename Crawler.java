package ktp.laba7;

// Импорт библиотек

import java.io.*;
import java.net.*;
import java.util.LinkedList;

/* Crawler - это класс, который перемещается по веб-страницам и ищет URL-адреса, поэтому класс сканера
включает в себя код, который фактически открывает и закрывает сокеты */

public class Crawler {

    public static void main(String[] args) throws IOException {
        webs(args[0], Integer.parseInt(args[1]));
        getSites(viewed_links); // для вывода результата поиска
        System.out.println("Итого ссылок: " + viewed_links.size()); // отображение общего количества ссылок
    }

    // список для просмотренных ссылок
    static LinkedList <URLDepthPair> viewed_links = new LinkedList <URLDepthPair>();

    // список для ожидающих ссылок
    static LinkedList <URLDepthPair> finded_links = new LinkedList <URLDepthPair>();

    // константы
    public static final String prefurl = "https://";
    static final String hreftag = "<a href=\"http";

    public static void getSites(LinkedList<URLDepthPair> viewLinks) { // метод возвращает все просмотренные ссылки вместе с глубиной
        for (URLDepthPair c : viewLinks)
            System.out.println("Depth (глубина поиска) : "+c.getDepth() + "\tUrl (ссылка) : "+c.getURL());
    }

    public static void request(PrintWriter out,URLDepthPair pair) throws MalformedURLException { // метод осуществляет запрос на сервер
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    public static void webs(String pair, int maxDepth) throws IOException { // основной метод
        try {
            finded_links.add(new URLDepthPair(pair, 0)); // добавить сайт в ожидающий список ссылок
            while (!finded_links.isEmpty()) { // пока ожидающий список ссылок не пуст
                URLDepthPair currentPair = finded_links.removeFirst(); // удаление первого элемента из списка findLink и присваивание его currentPair
                if (currentPair.depth < maxDepth) { // добавление ссылок,пока текущая глубина будет меньше требуемой

                    /* Инициализируем новый сокет из строки String, содержащей имя хоста, и
                     * из номера порта, равный 80 (http)*/
                    Socket mysocket = new Socket(currentPair.getHost(), 80);// создание нового сокета, порт 80 для HTTP-соединений
                    System.out.println("Connected to " + currentPair.getURL());
                    mysocket.setSoTimeout(45000);  // время ожидания сокета на 45 секунд
                    BufferedReader buffin = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
                    PrintWriter printout = new PrintWriter(mysocket.getOutputStream(), true);
                    request(printout, currentPair); // запрос на сервер
                    String str; // объявление строки
                    while ((str = buffin.readLine()) != null) { // если строка line не пуста
                        if (str.indexOf(currentPair.URL_PREFIX) != -1) {
                            StringBuilder currentLink = new StringBuilder();
                            for (int i = str.indexOf(currentPair.URL_PREFIX) + 9; str.charAt(i) != '"'; i++) {
                                currentLink.append(str.charAt(i));
                            }
                            // создание нового URLDepthPair с большей глубиной
                            URLDepthPair newPair = new URLDepthPair(currentLink.toString(), currentPair.depth + 1); //новая пара с глубиной,ув. на 1
                            if (currentPair.haveatlist(finded_links, newPair) && currentPair.haveatlist(viewed_links, newPair) && !currentPair.URL.equals(newPair.URL))
                                finded_links.add(newPair); // добавить ссылку в список ожидающих ссылок
                        }
                    }
                    mysocket.close(); // закрытие сокета
                }
                viewed_links.add(currentPair);  // добавить просмотренную ссылку в список просмотренных ссылок
            }
        } catch (Exception e){  // в случае ошибки
            System.out.println("usage: java Crawler <URL><depth>");
            System.exit(1);
        }
    }
}
