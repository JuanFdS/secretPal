package com.tenPines.persistence;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class DatabaseWishlistTest {

    Repo<Wish> wishlist;
    @Autowired
    Repo<Worker> databaseWorkerDao;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private SecretPalSystem secretPalSystem;

    public void setDatabaseWorkerDao(Repo<Worker> databaseWorkerDao) {
        this.databaseWorkerDao = databaseWorkerDao;
    }

    @Before
    public void setUp() {
        wishlist = (Repo<Wish>) webApplicationContext.getBean("databaseWishlist");
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
    }

    @Test
    public void When_A_Wishlist_is_created_it_should_be_empty() {
        assertThat(wishlist.retrieveAll(), empty());
    }

    @Test
    public void When_I_Add_A_Wish_To_A_Wishlist_It_Should_Get_Stored() throws Exception {
        Worker worker = new WorkerBuilder().build();
        databaseWorkerDao.save(worker);

        Wish aWish = new Wish(worker, "Un pony");

        wishlist.save(aWish);
        wishlist.refresh(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(wishlist.retrieveAll(), hasItem(hasProperty("gift", is("Un pony"))));
    }

    @Test
    public void When_I_Remove_A_Wish_From_A_Wishlist_It_Should_Be_No_More() throws Exception {
        Worker worker = new WorkerBuilder().build();
        databaseWorkerDao.save(worker);

        Wish aWish = new Wish(worker, "Un pony");

        wishlist.save(aWish);
        wishlist.delete(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(0));
        assertThat(wishlist.retrieveAll(), not(hasItem(aWish)));
    }

    @Test
    public void When_I_Edit_A_Wish_From_A_Wishlist_It_Should_Be_Changed() throws Exception {
        Worker worker = new WorkerBuilder().build();
        databaseWorkerDao.save(worker);

        Wish aWish = new Wish(worker, "Un pony");

        wishlist.save(aWish);

        aWish.setGift("Dos ponys!");
        wishlist.update(aWish);
        wishlist.refresh(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(wishlist.retrieveAll(), hasItem(hasProperty("gift", is("Dos ponys!"))));
        assertThat(worker.getId(), not(nullValue()));
    }

    @Test
    public void When_I_Ask_For_Wishes_Of_A_Worker_I_Should_get_Them() throws Exception {
        Worker ajani = new WorkerBuilder().build();
        secretPalSystem.saveWorker(ajani);

        Worker brand = new WorkerBuilder().build();
        secretPalSystem.saveWorker(brand);

        Wish aWishForAjani = new Wish(ajani, "Un pony");
        Wish anotherWishforAjani = new Wish(ajani, "Otro pony");
        Wish aWishForBrand = new Wish(brand, "Un auto");

        secretPalSystem.saveWish(aWishForAjani);
        secretPalSystem.saveWish(anotherWishforAjani);
        secretPalSystem.saveWish(aWishForBrand);


        assertThat(secretPalSystem.retrievallWishesForWorker(ajani), hasSize(2));
        assertThat(secretPalSystem.retrievallWishesForWorker(ajani), hasItem(hasProperty("gift", is("Un pony"))));
        assertThat(secretPalSystem.retrievallWishesForWorker(ajani), hasItem(hasProperty("gift", is("Otro pony"))));
    }
}
