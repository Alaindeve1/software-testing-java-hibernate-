package com.auca;

import static org.junit.Assert.assertNotNull;
import org.hibernate.Session;
import org.junit.Test;
import com.auca.dao.Connection;

public class ConnectionTest {
    
    Connection connection = new Connection();
    
    @Test
    public void testConnection() {
        Session session = connection.getSession();
        assertNotNull(session);
        session.close();
    }
}