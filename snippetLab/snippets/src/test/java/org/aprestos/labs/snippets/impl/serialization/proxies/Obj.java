package org.aprestos.labs.snippets.impl.serialization.proxies;

import java.util.Map;

public interface Obj {

    String getName();

    int getNumber();

    boolean isStatus();

    Map<String, String> getConfig();

}