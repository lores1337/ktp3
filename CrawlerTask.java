package ktp.laba8;

// Импорт библиотек
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;

/**
 * Класс CrawlerTask реализует интерфейс Runnable (для нескольких потоков).
 * Каждый экземпляр класса CrawlerTask имеет ссылку на экземпляр класса URLPool.
 * Также экземпляр получает все URL-адреса страницы.
 */

public class CrawlerTask implements Runnable{

    URLPool myurlPool; // ссылка на экземпляр пула

    // константы
    public static final String prefurl = "https://";
    static final String hreftag = "<a href=\"http";
    public static final String URL_PREFIX = "<a href=\"http";

    public CrawlerTask(URLPool pool){ // конструктор
        myurlPool = pool;
    }

    public static void request(PrintWriter out,URLDepthPair pair) throws MalformedURLException { // метод осуществляет запрос на сервер
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    @Override
    public void run(){
        while (1>0){
            URLDepthPair currentPair = myurlPool.getPair(); // следующая пара
            try{

                /* Инициализируем новый сокет из строки String, содержащей имя хоста, и
                 * из номера порта, равный 80 (http)*/

                Socket mysocket = new Socket(currentPair.getHost(), 80); // создание нового сокета, порт 80 для HTTP-соединений
                System.out.println("Connected to " + currentPair.getURL());
                mysocket.setSoTimeout(8000); // время ожидания сокета на 8 секунд
                BufferedReader buffin =  new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
                PrintWriter printout = new PrintWriter(mysocket.getOutputStream(), true);
                request(printout,currentPair); // осуществление запроса на сервер
                String str; // объявление строки
                while ((str = buffin.readLine()) != null){ // пока строка str не пуста
                    if (str.indexOf(currentPair.URL_PREFIX)!=-1) {
                        try {
                            String currentLink = str.substring(str.indexOf(URL_PREFIX)+9,str.indexOf("\"", str.indexOf(URL_PREFIX)+9));

                            // добавление нового URLDepthPair с глубиной,увеличенной на 1
                            myurlPool.addPair(new URLDepthPair(currentLink, currentPair.getDepth()+1));
                        }
                        catch (Exception e) { // обработка возникшего исключения
                            System.out.println("Exception - " + e.toString());
                        }
                    }
                }
                mysocket.close(); // закрытие сокета mysocket
            }
            catch (Exception e) { // обработка исключения
                System.out.println("Exception - " + e.toString());
            }
        }
    }
}