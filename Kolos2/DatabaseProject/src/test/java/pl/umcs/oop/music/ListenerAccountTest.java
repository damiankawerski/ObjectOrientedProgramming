package pl.umcs.oop.music;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.umcs.oop.auth.Account;
import pl.umcs.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ListenerAccountTest {

    @BeforeAll
    public static void setup() {
        DatabaseConnection.connect("songs.db");
    }

    @AfterAll
    public static void close() {
        DatabaseConnection.disconnect();
    }


    @Test
    public void testNewAccountRegister() throws SQLException {
        ListenerAccount.Persistence.init();
        int accountId = ListenerAccount.Persistence.register("username", "password");
        assertTrue(accountId > 0);
    }

    @Test
    public void testNewAccountLogin() throws SQLException, AuthenticationException {
        Account la = Account.Persistence.authenticate("username", "password");
        assertNotNull(la);
    }

    @Test
    public void testAccountBegin
}