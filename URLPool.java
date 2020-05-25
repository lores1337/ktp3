package ktp.laba8;

// Импорт библиотеки
import java.util.LinkedList;

public class URLPool {

    // список для просмотренных ссылок
    LinkedList<URLDepthPair> viewed_links;

    // список для ожидающих обработки ссылок
    LinkedList<URLDepthPair> finded_links;

    // максимальная заданная глубина для поиска
    int maxdepth;

    // количество потоков, которые ожидают обработки
    int waitT;

    public URLPool(int maxdepth) { // конструктор. maxdepth - максимальная глубина поиска
        waitT = 0;
        this.maxdepth = maxdepth;
        viewed_links = new LinkedList<URLDepthPair>();
        finded_links = new LinkedList<URLDepthPair>();
    }

    public synchronized URLDepthPair getPair() { // метод возвращает следующую пару
        while (finded_links.size() == 0) {
            waitT++;
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Ignoring InterruptedException");
            }
            waitT--;
        }
        URLDepthPair nextPair = finded_links.removeFirst(); // удаление первой пары из списка ожидающих обработки ссылок
        return nextPair;
    }

    public synchronized void addPair(URLDepthPair pair) { // метод для добавления новой пары
        if(URLDepthPair.haveatlist(viewed_links,pair)) { // если в списке viewed_links есть pair
            viewed_links.add(pair);  // добавить просмотренную ссылку в список просмотренных ссылок
            if (pair.getDepth() < maxdepth) { // добавление ссылок,пока текущая глубина будет меньше требуемой
                finded_links.add(pair); // добавить ссылку в список ожидающих ссылок
                notify(); // метод позволяет продолжить работу потока,у которого ранее был вызван метод wait()
            }
        }
    }

    public synchronized int getWait(){ // метод возвращает значения поля waitT (количество потоков,ожидающих обработки)
        return waitT;
    }

    public int getMaxdepth(){ // метод возвращает значение поля maxdepth (максимальная глубина для поиска ссылок)
        return maxdepth;
    }

    public LinkedList<URLDepthPair> getResult() { // метод возвращает список просмотренных ссылок viewed_links
        return viewed_links;
    }
}
