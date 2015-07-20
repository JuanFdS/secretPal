package com.tenPines.persistence;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    private static Map<String, String> configurationMap = new HashMap<>();

    public static void addConfiguration(String configuration, String value) {
        configurationMap.put(configuration, value);
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();

        for (Map.Entry<String, String> conf : configurationMap.entrySet()) {
            configuration.setProperty(conf.getKey(), conf.getValue());

        }

        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
