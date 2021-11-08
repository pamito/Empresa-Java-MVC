/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Directivo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pablo
 */
public class PruebaDirectivo {
    
    public PruebaDirectivo() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Metodo unico ante de la clase");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Metodo al finalizar la clase");
    }
    
    @Before
    public void setUp() {
        System.out.println("Inicio del metodo");
    }
    
    @After
    public void tearDown() {
        System.out.println("Fin del metodo");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}   
    @Test
    public void GuardarDirectivo(){
        
        Directivo directivo = new Directivo(1, "Empleado");
        assertEquals(1, directivo.guardarDirectivo(directivo));
        
    }
    
    
}
