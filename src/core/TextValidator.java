package core;

import data.*;

public class TextValidator{
    private final short CHARLIMIT = 280;
    
    public String check(String msg) throws TextEmptyException, TextLimitException{
        if(msg.isEmpty()){
            throw new TextEmptyException("El campo texto no puede quedar vacío.");
        }else if(msg.length() > 280){
            throw new TextLimitException("El tweet no puede superar los " + CHARLIMIT + " caracteres.");
        }
        return msg;
    }
}