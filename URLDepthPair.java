package ktp.laba7;

// Импорт библиотек
import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

// Класс для представления пар URL, depth
public class URLDepthPair {

    public static final String URL_PREFIX = "<a href=\"http"; // константа URL префикса
    String URL; // Поле для URL
    int depth;  // Поле для глубины

    public URLDepthPair (String url, int depth){ // Конструктор. url - страница , depth - глубина
        this.URL=url;
        this.depth=depth;
    }

    public String getHost() throws MalformedURLException { // метод возвращает имя хоста
        try {
            URL host = new URL(URL);
            return host.getHost();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public String getPath() throws MalformedURLException {
        try {
            URL path = new URL(URL);
            return path.getPath();
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    public int getDepth() { // возвращает значение поля глубины
        return depth;
    }

    public String getURL() { // возвращает значение поля URL
        return URL;
    }

    public static boolean haveatlist(LinkedList<URLDepthPair> Links, URLDepthPair pair) { // метод возвращает true если url
        boolean isAlready = true;                            // объекта pair содержится хотя бы в одном объекте списка
        for (URLDepthPair c : Links)                         // Links, false - иначе
            if (c.getURL().equals(pair.getURL())) isAlready=false;
        return isAlready;
    }

    public String toString(){
        String stringDepth = Integer.toString(depth);
        return stringDepth + '\t' + this.URL;
    }
}