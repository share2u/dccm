package cn.ncut.util.mail;

import javax.mail.*;   

/**
 * 
*
*<p>Title:MyAuthenticator</p>
*<p>Description:发送邮件需要使用的基本信息</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-6上午10:12:34
*
 */
public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}   
