package core;

import data.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager{
    Twitter tw = TwitterFactory.getSingleton();
    TextValidator tv = new TextValidator();
    
    public TwitterManager(){
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//            .setOAuthConsumerKey("8awOGfQrnGYtofhdhVKF2fjyG")
//            .setOAuthConsumerSecret("PJS0sQgkurGlJzyNzRcYMkIVrrPyKUCF3F2RsyPPzI6ikE9qXf")
//            .setOAuthAccessToken("1108666616814944256-luuXVlhMNRmedjm7AY3SSfx4JhU4rX")
//            .setOAuthAccessTokenSecret("a1XKMS5bnBVcq7k0uIlHbvnJb8gSh99R3jS9eD5yY6DD0");
//        TwitterFactory tf = new TwitterFactory(cb.build());
//        Twitter tw = tf.getInstance();
    }
    
    public String getUser(){
        try{ 
            User u = tw.showUser(tw.getId());
            return u.getName();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void enviarTweet(String msg){
        try{
            tw.updateStatus(tv.check(msg));
            JOptionPane.showMessageDialog(null, "Tweet enviado con éxito.", "Twitter4J", 1);
        }catch(TextEmptyException e1){
            JOptionPane.showMessageDialog(null, e1.getMessage(), "Twitter4J", 2);
        }catch(TextLimitException e2){
            JOptionPane.showMessageDialog(null, e2.getMessage(), "Twitter4J", 2);
        }catch(Exception e){
            System.out.println("Debug console: " + e.getMessage());
        }
    }
    
    public ArrayList<String> filtrarTweet(String msg){
        try{
            Query query = new Query(tv.check(msg));
            QueryResult result = tw.search(query);
            if(result.getTweets().size() > 0){
                ArrayList<String> resultados = new ArrayList<>();
                for (Status status : result.getTweets()) {
                    resultados.add("@" + status.getUser().getScreenName() + ":" + status.getText() + "\n\n");
                }
                return resultados;
            }else{
                JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias.", "Twitter4J", 2);
            }
        }catch(TextEmptyException e1){
            JOptionPane.showMessageDialog(null, e1.getMessage(), "Twitter4J", 2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<String> mostrarTimeLine(){
        try{
            List<Status> listaTL = tw.getHomeTimeline();
            ArrayList<String> salidaTL = new ArrayList<>();
            for(Status status : listaTL){
                salidaTL.add(status.getUser().getName() + ": " + status.getText() + "\n\n");
            }
            return salidaTL;
        }catch(Exception e){
            System.out.println("Debug console: " + e.getMessage());
        }
        return null;
    }
}