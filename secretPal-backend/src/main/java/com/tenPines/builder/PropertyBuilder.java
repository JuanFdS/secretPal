package com.tenPines.builder;

import com.tenPines.utils.PropertyParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class PropertyBuilder {
    public static Properties buildPropertyFrom(String route) throws IOException {
        Properties prop = new PropertyParser();
        prop.load(new FileInputStream(route));
        return prop;
    }
}
