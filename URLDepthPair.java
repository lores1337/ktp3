package ktp.laba8;

// Импорт библиотек
import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

// URLDepthPair - это класс для представления пар URL, depth

public class URLDepthPair {

    // константы
    public static final String prefurl = "https://";
    static final String hreftag = "<a href=\"http";
    public static final String URL_PREFIX = "<a href=\"http"; // константа URL префикса

    public String URL; // Поле для String URL
    private int depth; // Поле для глубины
    URL host_path;     // Поле для ссылки URL

    public URLDepthPair (String url, int depth){ // Конструктор. url - адрес , depth - глубина
        this.URL=url;
        this.depth=depth;
        try {
            this.host_path= new URL(URL);
        }
        catch (MalformedURLException e) { // Обработка исключения MalformedURLException
            e.printStackTrace();
        }
    }

    public String getHost(){ // метод возвращает значения хоста url
        return host_path.getHost();
    }

    public String getPath(){ // метод возвращает значение path
        return host_path.getPath();
    }

    public int getDepth() { // метод возвращает значение поля depth
        return depth;
    }

    public String getURL() { // метод возвращает значение поля URL
        return URL;
    }

    /** метод возвращает true если url
     // объекта pair содержится хотя бы в одном объекте списка
     Links, false - иначе */
    public static boolean haveatlist(LinkedList<URLDepthPair> Links, URLDepthPair pair) {
        boolean isAlready = true;
        for (URLDepthPair c : Links)
            if (c.getURL().equals(pair.getURL())) isAlready=false;
        return isAlready;
    }

    public String toString(){ // метод, возвращающий строку пары
        String stringDepth = Integer.toString(depth);
        return stringDepth + '\t' + this.URL;
    }
}