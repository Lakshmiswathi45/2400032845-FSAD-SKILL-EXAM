package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;

public class ClientDemo 
{
    public static void main(String[] args) 
    {
        Configuration cfg = new Configuration();
        cfg.configure();
        cfg.addAnnotatedClass(Service.class);

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        // INSERT RECORD
        Service s = new Service();
        s.setId(101);
        s.setName("Network Service");
        s.setDate(new Date());
        s.setStatus("Active");

        session.save(s);

        // UPDATE USING HQL
        String hql = "update Service set name=:n, status=:s where id=:i";

        Query<?> query = session.createQuery(hql);        query.setParameter("n", "Updated Service");
        query.setParameter("s", "Completed");
        query.setParameter("i", 101);

        int result = query.executeUpdate();

        System.out.println("Rows Updated = " + result);

        tx.commit();

        session.close();
        sf.close();
    }
}