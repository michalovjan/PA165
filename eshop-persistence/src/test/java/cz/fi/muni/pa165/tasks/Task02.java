package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

    private Category catElectro;
    private Category catKitchen;
    private Product proFlash;
    private Product proRobot;
    private Product proPlate;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@BeforeClass
	void before() {
        catElectro = new Category();
        catElectro.setName("Elecro");
        catKitchen = new Category();
        catKitchen.setName("Kitchen");

        proFlash = new Product();
        proFlash.setName("Flashlight");
        proRobot = new Product();
        proRobot.setName("Kitchen robot");
        proPlate = new Product();
        proPlate.setName("Plate");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(catElectro);
        em.persist(catKitchen);
        em.persist(proRobot);
        em.persist(proFlash);
        em.persist(proPlate);
        catKitchen.addProduct(proRobot);
        catKitchen.addProduct(proPlate);

        catElectro.addProduct(proFlash);
        catElectro.addProduct(proRobot);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testElectroCategory() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Category el = em.find(Category.class,catElectro.getId());
	    assertContainsProductWithName(el.getProducts(),proFlash.getName());
	    assertContainsProductWithName(el.getProducts(),proRobot.getName());
	    em.getTransaction().commit();
	    em.close();
    }

    @Test
    public void testKitchenCategory() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Category el = em.find(Category.class,catKitchen.getId());
	    assertContainsProductWithName(el.getProducts(),proPlate.getName());
	    assertContainsProductWithName(el.getProducts(),proRobot.getName());
	    em.getTransaction().commit();
	    em.close();
    }

    @Test
    public void testProductFlastlight() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Product pr = em.find(Product.class, proFlash.getId());
	    assertContainsCategoryWithName(pr.getCategories(),catElectro.getName());
	    em.getTransaction().commit();
	    em.close();
    }
    @Test
    public void testProductRobot() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Product robot = em.find(Product.class, proRobot.getId());
	    assertContainsCategoryWithName(robot.getCategories(),catElectro.getName());
	    assertContainsCategoryWithName(robot.getCategories(),catKitchen.getName());
	    em.getTransaction().commit();
	    em.close();
    }
    @Test
    public void testProductPlate() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Product plate = em.find(Product.class, proPlate.getId());
	    assertContainsCategoryWithName(plate.getCategories(),catKitchen.getName());
	    em.getTransaction().commit();
	    em.close();
    }

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void testDoesntSaveNullName(){
        EntityManager em = emf.createEntityManager();
	    try {
            em.getTransaction().begin();
            Product p = new Product();
            p.setName(null);
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
