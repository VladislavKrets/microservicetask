package com.task.model;

import com.task.model.entities.Settings;
import com.task.model.entities.Subsettings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Map;

public class DAO {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static DAO instance;

    static {
        configuration = new Configuration()
                .addAnnotatedClass(Settings.class)
                .addAnnotatedClass(Subsettings.class)
                .configure("/hibernate.cfg.xml");
        Map<String, String> properties = Utils.cfgFileReader();

        if (!properties.containsKey("password") || !properties.containsKey("username") || !properties.containsKey("url")) {
            System.out.println("Bad configuration, please check the file");
            System.exit(1);
        }

        configuration.setProperty("hibernate.connection.password", properties.get("password").trim());
        configuration.setProperty("hibernate.connection.username", properties.get("username").trim());
        configuration.setProperty("hibernate.connection.url", properties.get("url").trim());

        sessionFactory = configuration.buildSessionFactory();

    }


    public List<Settings> getAllSettings() {
        Session session = sessionFactory.openSession();
        List<Settings> settings = session.createQuery("from Settings", Settings.class).list();
        session.close();
        return settings;
    }

    public Settings getSettingById(int id) {
        Session session = sessionFactory.openSession();
        Settings settings = session.createQuery("from Settings where setting_id=:id", Settings.class)
                .setParameter("id", id)
                .getSingleResult();
        session.close();
        return settings;
    }

    public void deleteSettingByItem1(Settings settings) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Subsettings subsettings : settings.getSubsettings()) {
            session.remove(subsettings);
        }
        session.remove(settings);
        session.getTransaction().commit();
        session.close();
    }

    public void updateSettingsByItem1(Settings settings) {
        Session session = sessionFactory.openSession();
        Settings origSettings = session.createQuery("from Settings where item1=:item1", Settings.class)
                .setParameter("item1", settings.getItem1())
                .getResultList().get(0);
        if (settings.getType() == null) settings.setType(origSettings.getType());
        if (settings.getSubsettings() == null || settings.getSubsettings().isEmpty()) settings.setSubsettings(origSettings.getSubsettings());
        session.beginTransaction();
        for (Subsettings subsettings : origSettings.getSubsettings()) {
            session.remove(subsettings);
        }
        session.update("item1", settings);
        for (Subsettings subsettings : settings.getSubsettings()) {
            subsettings.setSettingId(origSettings.getId());
            session.save(subsettings);
        }
        session.getTransaction().commit();
        session.close();

    }

    public void addSettings(Settings settings) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(settings);
        session.getTransaction().commit();
        for (Subsettings subsettings : settings.getSubsettings()) {
            subsettings.setSettingId(settings.getId());
            session.save(subsettings);
            session.getTransaction().commit();
        }
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }
}
