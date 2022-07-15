/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Account;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ADMIN
 */
public class AccountDBContextTest {

    AccountDBContextTest accountDBContextTest;
    AccountDBContextTest accountDBContextTestSpy;

    public AccountDBContextTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        accountDBContextTest = new AccountDBContextTest();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetPermission() {
        System.out.println("getPermission");
        String username = "";
        int roleID = 0;
        String uri = "";
        AccountDBContext instance = new AccountDBContext();
        boolean expResult = false;
        boolean result = instance.getPermission(username, roleID, uri);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsExistAccountDoesNotExist() {
        System.out.println("isExistAccount");
        String username = "duylkhe161788@fpt.edu.vn";
        AccountDBContext instance = new AccountDBContext();
        Account expResult = new Account();
        

        Account result = instance.isExistAccount(username); // chay cai method
        assertEquals(username, result.getUsername());

    
    }

    @Test(expected = Exception.class)
    public void testIsExistUser() {
        System.out.println("isExistUser");
        String username = "";
        AccountDBContext instance = new AccountDBContext();
        boolean expResult = false;
        boolean result = instance.isExistUser(username);
        assertEquals("2", "2");

    }

    @Test
    public void testChangePassword_String_String() {
        System.out.println("changePassword");
        String username = "";
        String password = "";
        AccountDBContext instance = new AccountDBContext();
        boolean expResult = false;
        boolean result = instance.changePassword(username, password);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAccount_String_String() {
        System.out.println("getAccount");
        String username = "";
        String password = "";
        AccountDBContext instance = new AccountDBContext();
        Account expResult = null;
        Account result = instance.getAccount(username, password);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAccount_String() {
        System.out.println("getAccount");
        String username = "";
        AccountDBContext instance = new AccountDBContext();
        Account expResult = null;
        Account result = instance.getAccount(username);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testInsertAccount() {
        System.out.println("insertAccount");
        Account account = null;
        AccountDBContext instance = new AccountDBContext();
        boolean expResult = false;
        boolean result = instance.insertAccount(account);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testChangePassword_Account() {
        System.out.println("changePassword");
        Account account = null;
        AccountDBContext instance = new AccountDBContext();
        boolean expResult = false;
        boolean result = instance.changePassword(account);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAccountByRole() {
        System.out.println("getAccountByRole");
        int roleid = 0;
        AccountDBContext instance = new AccountDBContext();
        ArrayList<Account> expResult = null;
        ArrayList<Account> result = instance.getAccountByRole(roleid);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAccountByRoleID() {
        System.out.println("getAccountByRoleID");
        int roleID = 0;
        AccountDBContext instance = new AccountDBContext();
        ArrayList<Account> expResult = null;
        ArrayList<Account> result = instance.getAccountByRoleID(roleID);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
