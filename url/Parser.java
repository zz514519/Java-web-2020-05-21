package url;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://127.0.0.1:3306/dbname?characterEncoding=utf8";

        parse(url);

    }

    private static Map<String,Integer> scheamToPort = new HashMap<>();
    static {
        scheamToPort.put("jdbc:mysql",3306);
        scheamToPort.put("https",443);
        scheamToPort.put("http",80);
    }
    private static void parse(String url) {
        String remain;
        String schema;
        {
            int i = url.indexOf("://");
            schema = url.substring(0, i);
            System.out.println("协议："+schema);
            remain = url.substring(i+3);//+3就是跳过://
        }
        {
            //确定有没有认证信息（用户名和密码）
            int i = remain.indexOf("@");
            if (i == -1){
                System.out.println("用户名：");
                System.out.println("密码：");
            }else {
                String authentication = remain.substring(0,i);
                int j = authentication.indexOf(":");
                if (j == -1){
                    System.out.println("用户名："+authentication);
                    System.out.println("密码：");
                }else{
                    String user = authentication.substring(0,j);
                    String  password = authentication.substring(j+1);
                    System.out.println("用户名："+user);
                    System.out.println("密码："+password);
                }
                //切割认证信息
                //再根据：分割 用户名和密码部分
                remain = remain.substring(i+1);  //跳过@

            }
        }
        {
            //确定主机+端口部分
            //通过找 / 分割（主机 + 端口）和后面部分
            int i = remain.indexOf("/");
            String ipAndPort = remain.substring(0,i);
            remain = remain.substring(i);       //因为/是属于路径的一部分所以别跳过
            //根据 ：分割  主机  和  端口部分
            //端口有可能没有写出来
            int j = ipAndPort.indexOf(":");
            if (j == -1){
                String host = ipAndPort;
                int port = scheamToPort.get(schema);
                System.out.println("主机："+host);
                System.out.println("端口："+port);
            }else{
                String host = ipAndPort.substring(0,j);
                int port = Integer.parseInt(ipAndPort.substring(j+1));
                System.out.println("主机："+host);
                System.out.println("端口："+port);
            }
        }

        {
            //通过 ？分割路径部分
            int i = remain.indexOf("?");
            if (i == -1) {
                int j = remain.indexOf("#");
                if (j ==-1){
                String path = remain;
                remain = "";
                System.out.println("路径："+path);
            } else{
                    String  path = remain.substring(0,j);
                    remain = remain.substring(j);
                    System.out.println("路径："+path);
                }
            }else{
                String path = remain.substring(0, i);
                remain = remain.substring(i + 1);


                System.out.println("路径：" + path);
            }
        }
        {
            //通过 # 分割 query string部分

            int i = remain.indexOf("#");

            if (i == -1){
                String queryString = remain;
                remain = "";
                System.out.println("查询字符串："+queryString);
            }else{
                String queryString = remain.substring(0,i);
                remain = remain.substring(i+1);
            }
        }
        //segment 部分
        System.out.println("片段标识符："+remain);
    }
}
