package ktp.laba8;

// Импорт библиотек
import java.io.IOException;
import java.util.LinkedList;

public class Crawler {

    /**
     *
     * @param args
     * args[0] - url-адрес
     * args[1] - требуемая глубина поиска
     * args[2] - количество потоков
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        webs(args);
    }

    public static void getSites(LinkedList<URLDepthPair> viewLinks){ // метод возвращает все просмотренные ссылки вместе с их глубиной
        for (URLDepthPair c : viewLinks)
            System.out.println("Depth (глубина поиска) : "+c.getDepth() + "\t  Url (ссылка) : "+c.getURL());
    }

    public static boolean isDigit(String str){  // метод возвращает true ,если строка str является числом; false - иначе
        for(int i = 0;i<str.length();i++){
            if(!(Character.isDigit(str.charAt(i)))) return false;
        }
        return true;
    }

    public static void webs(String[] args) throws IOException { // основной метод
        if (args.length == 3 && isDigit(args[1]) && isDigit(args[2])) { // проверка введенных данных

            String pair = args[0]; // url адрес
            int maxdepth = Integer.parseInt(args[1]); // глубина поиска
            int thrs = Integer.parseInt(args[2]); // количество потоков

            // Создать экземпляр пула URL-адресов и поместить указанный пользователем URL-адрес в пул с глубиной 0
            URLPool pool = new URLPool(maxdepth);
            pool.addPair(new URLDepthPair(pair, 0));

            for (int i = 0; i < thrs; i++) {
                CrawlerTask c = new CrawlerTask(pool);
                Thread th = new Thread(c);
                th.start();
            }

            // если количество ожидающих потоков не равно количеству настоящих потоков,создаем больше потоков
            while (pool.getWait() != thrs) {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) { // обработка исключения InterruptedException
                    System.out.println("Exception - InterruptedException");
                }
            }

            // Когда все потоки готовы
            try {
                getSites(pool.getResult()); // для вывода результата поиска страниц
                System.out.println("Итого ссылок: " + pool.getResult().size()); // отображение общего количества найденных ссылок
            }
            catch (NullPointerException e){ // обработка исключения NullPointerException
                System.out.println("Exception - NullPointerException");
            }
            System.exit(0);
        }
        else {
            System.out.println("usage: java Crawler <URL><depth><threads>"); // при неправильном вводе начальных данных
            System.exit(1);
        }
    }
}
