package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;

public class App 
{
    public static void main(String[] args) 
    {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Service.class);

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        // Check if record already exists
        Service s = session.get(Service.class, 1);

        if(s == null)
        {
            // Insert Record
            Service newService = new Service();
            newService.setId(1);
            newService.setName("Repair Service");
            newService.setDate(new Date());
            newService.setStatus("Pending");

            session.save(newService);
        }

        // HQL Update using named parameters
        String hql = "update Service set name=:n, status=:s where id=:i";

        Query<?> query = session.createQuery(hql);        query.setParameter("n", "Updated Service");
        query.setParameter("s", "Completed");
        query.setParameter("i", 1);

        int result = query.executeUpdate();

        System.out.println("Rows Updated = " + result);

        tx.commit();

        session.close();
        sf.close();
    }
}