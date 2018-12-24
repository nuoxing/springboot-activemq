package com.work.util.result;

/*
 * 文件名：HttpResult.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年7月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

import java.io.Serializable;

public class HttpResult implements Serializable
{
    
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    private boolean error = false;
    private Object data;

    public HttpResult()
    {
      
    }
    

    /**
     * 
     * 描述: 提示信息<br>
     * 
     * @return 
     * @see
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * 
     * 描述: 提示信息<br>
     * 
     * @return 
     * @see
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * 
     * 描述:是否出错标志<br>
     * 
     * @return 
     * @see
     */
    public boolean isError()
    {
        return error;
    }

    /**
     * 
     * 描述:是否出错标志<br>
     * 
     * @return 
     * @see
     */
    public void setError(boolean error)
    {
        this.error = error;
    }

    /**
     * 
     * 描述: 返回前端的数据<br>
     * 
     * @return 
     * @see
     */
    public Object getData()
    {
        return data;
    }

    /**
     * 
     * 描述: 返回前端的数据<br>
     * 
     * @return 
     * @see
     */
    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        String str = "message="+message+"  error="+error+"    data="+data;
        System.out.println(str);
        return str;
    }
    
    

}
