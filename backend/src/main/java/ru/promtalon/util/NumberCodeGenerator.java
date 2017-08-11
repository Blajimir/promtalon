package ru.promtalon.util;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class NumberCodeGenerator {
    private static final String CHARS = "0123456789";
    private final static int LENGTH = 8;
    private final Random random = new Random();
    public String tryUniqueCode(int numberTries, List<String> tokens){
        String result= getToken();
        for (int i = 0; i < numberTries && tokens.contains(result); i++) {
            result = getToken();
        }
        return result;
    }
    public String getToken(){
        return getToken(LENGTH);
    }
    public String getToken(int length){
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }
}
