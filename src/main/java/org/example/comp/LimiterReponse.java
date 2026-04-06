package org.example.comp;

public class LimiterReponse {
    String data;
    boolean allowed;

    LimiterReponse(String data, boolean allowed){
        this.data = data;
        this.allowed = allowed;
    }
    public boolean isAllowed(){
        return allowed;
    }
}
