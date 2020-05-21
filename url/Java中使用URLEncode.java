package url;


import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Java中使用URLEncode {
    public static void main(String[] args) throws IOException {
        String url = "https//www.baidu.com/s?wd中国人";
           String urlEncode = URLEncoder.encode(url,"UTF-8");
        System.out.println(urlEncode);
        url = URLDecoder.decode(urlEncode,"UTF-8");
        System.out.println(url);
    }
}
