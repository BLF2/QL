package net.blf2.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by t_mengxh on 2016/7/30.
 */
@Component
public class HttpClietUtilApi {
    private static Logger logger = Logger.getLogger(HttpClietUtilApi.class);

    public static String getWebPageSource(String url){
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if(statusCode != HttpStatus.SC_OK){
                    System.out.println("fail to get" + url + getMethod.getStatusLine());
            }
            byte[] responseBody = getMethod.getResponseBody();
            return new String(responseBody);
        }catch (HttpException e){
            logger.error("In httpClient.executeMethod(getMethod) HttpException error");
            e.printStackTrace();
        }catch (IOException e){
            logger.error("In httpClient.executeMethod(getMethod) IOException error");
            e.printStackTrace();
        }finally {
            getMethod.releaseConnection();
        }
        return null;
    }
}
