package org.centerom.almistack.servicesimpl.connector.redmine.internal;


public class Joiner {
    // TODO add unit tests
    public static String join(String delimToUse, RedmineManager.INCLUDE... include) {
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (RedmineManager.INCLUDE i : include) {
            sb.append(delim).append(i);
            delim = delimToUse;
        }
        return sb.toString();
    }

}
