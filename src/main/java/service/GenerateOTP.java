// https://github.com/RahulGrover12/
// Rahul Grover
package service;

import model.User;

import java.util.Random;

public class GenerateOTP {
    public static String getOTP(){
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}
