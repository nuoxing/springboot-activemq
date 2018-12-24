/*
 * 文件名：HttpClientUtils.java 版权：Copyright by www.chinauip.com 描述： 修改人：suwy 修改时间：2017年8月14日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.work.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


@SuppressWarnings("deprecation")
public class HttpClientUtils
{

    public HttpClientUtils()
    {
    }

    /**
     * 接口调用 GET , stUrl 请求路径 encode读取数据编码
     */
    public static String httpURLConectionGET(String stUrl, String encode)
    {
        String res = "error";
        try
        {
            URL url = new URL(stUrl); // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();// 打开连接
            connection.connect();// 连接会话
            connection.setReadTimeout(30000);
            // 获取输入流
            BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encode));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
            {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            res = sb.toString();
            // res = new String(res.getBytes(),"UTF-8");
            System.out.println("res=" + res);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("失败!");
            res = "error";
        }
        return res;
    }

    
    /**
     * 
     * @param url
     *            请求url
     * @param params
     *            参数 json格式
     * @param property
     *            请求属性设置
     * @param wEncoding
     *            写出内容编码
     * @param rEncoding
     *            读取内容编码
     * @return
     * @see
     */
    public static String commonPost(String url, String params, Map<String, String> property,
                                    String wEncoding, String rEncoding)
    {
        OutputStream outStream = null;
        BufferedReader in = null;
        String result = "error";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            if (property != null)
            {
                Set<String> keySet = property.keySet();
                for (String key : keySet)
                {
                    String res = property.get(key);
                    conn.setRequestProperty(key, res);
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(30000);
            outStream = conn.getOutputStream();
            outStream.write(params.getBytes(wEncoding));
            outStream.flush();
            outStream.close();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), rEncoding));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            result = "error";
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (outStream != null)
                {
                    outStream.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public String post1(String path, String params)
        throws IOException
    {
        String result = "";
        String response = "";
        String encoding = "UTF-8";

        byte[] data = params.getBytes(encoding);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        // application/x-javascript text/xml->xml数据 application/x-javascript->json对象
        // application/x-www-form-urlencoded->表单数据
        conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        System.out.println(conn.getResponseCode()); // 响应代码 200表示成功
        if (conn.getResponseCode() == 200)
        {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            while ((result = reader.readLine()) != null)
            {
                response = result;
                System.out.println(result);
            }
            // String result=new String(StreamTool.readInputStream(inStream), "UTF-8");
        }
        System.out.println("response:" + response);
        return response;
    }
    
    
    
    
    
    public static String methodPost(String postUrl,List<BasicNameValuePair> nvps) throws Exception {  
    	@SuppressWarnings("resource")
		DefaultHttpClient httpclient = new DefaultHttpClient();  
        // // 代理的设置  
        // HttpHost proxy = new HttpHost("10.60.8.20", 8080);  
        // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,  
        // proxy);  
  
        // 目标地址  
        HttpPost httppost = new HttpPost(postUrl);  
        System.out.println("请求: " + httppost.getRequestLine());  
   
        // post 参数 传递  
        /*List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();  
        nvps.add(new BasicNameValuePair("content", "11111111")); // 参数  
        nvps.add(new BasicNameValuePair("path", "D:/file")); // 参数  
        nvps.add(new BasicNameValuePair("name", "8")); // 参数  
        nvps.add(new BasicNameValuePair("age", "9")); // 参数  
        nvps.add(new BasicNameValuePair("username", "wzt")); // 参数  
*/  
        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); // 设置参数给Post  
  
        // 执行  
        HttpResponse response = httpclient.execute(httppost);  
        HttpEntity entity = response.getEntity();  
        System.out.println(response.getStatusLine());  
        if (entity != null) {  
            System.out.println("Response content length: "  
                    + entity.getContentLength());  
        }  
        // 显示结果  
        BufferedReader reader = new BufferedReader(new InputStreamReader(  
                entity.getContent(), "UTF-8"));  
  
        String line = null;  
        String result = "";
        while ((line = reader.readLine()) != null) {  
        	result = line;
            System.out.println(line);  
        }  
        if (entity != null) {  
            entity.consumeContent();  
        }  
        return result;  
  
    } 
    
    
    public static String postJson(String strURL, String params) {  
        System.out.println(strURL);  
        System.out.println(params); 
        String result = "";
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            int length = (int) connection.getContentLength();// 获取长度  
            InputStream is = connection.getInputStream();
            if(length != -1) {
            	byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                result = new String(data, "UTF-8"); // utf-8编码  
                System.out.println(result);  
                return result;
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result; // 自定义错误信息  
    } 
    
}
