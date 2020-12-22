package com.teste.southsystem.account.util;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int generateAccountNumber() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

    public static char getAccountType(String personType) {
        if (personType.equals("PF")) {
            return 'C';
        } else if (personType.equals("PJ")) {
            return 'E';
        } else {
            throw new IllegalArgumentException(String.format("Unable to parse person type %s", personType));
        }
    }
}
