package com.abc.bank.abc.utilities;

public class TokenNumberGenerator {

    private Integer tokenValue;
    private static TokenNumberGenerator tokenNumberGenerator = null;
    private TokenNumberGenerator(){
        this.tokenValue = 0;
    }

    public static TokenNumberGenerator getInstance(){
        if(tokenNumberGenerator == null){
            tokenNumberGenerator = new TokenNumberGenerator();
        }
        return tokenNumberGenerator;
    }

    public int getCounter() {
        return tokenValue++;
    }

    public void resetCounter() {
        this.tokenValue = 0;
    }
}