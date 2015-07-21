package com.tenPines.persistence;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class DatabaseWishlistTest {

    AbstractRepository<Wish> wishlist;

    @Before
    public void setUp() {
        HibernateUtils.addConfiguration(Environment.URL, "jdbc:mysql://localhost/calendardbtest");
        HibernateUtils.addConfiguration(Environment.HBM2DDL_AUTO, "create-drop");

        wishlist = new DatabaseWishlist(HibernateUtils.createSessionFactory());
    }

    @Test
    public void When_A_Wishlist_is_created_it_should_be_empty() {
        assertThat(wishlist.retrieveAll(), empty());
    }

    @Test
    public void When_I_Add_A_Wish_To_A_Wishlist_It_Should_Get_Stored() throws Exception {
        Wish aWish = new Wish(new WorkerBuilder().build(), "Un pony");

        wishlist.save(aWish);
        wishlist.refresh(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(wishlist.retrieveAll(), hasItem(hasProperty("gift", is("Un pony"))));
    }

    @Test
    public void When_I_Remove_A_Wish_From_A_Wishlist_It_Should_Be_No_More() throws Exception {
        Worker worker = new WorkerBuilder().build();

        Wish aWish = new Wish(worker, "Un pony");

        wishlist.save(aWish);
        wishlist.delete(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(0));
        assertThat(wishlist.retrieveAll(), not(hasItem(aWish)));
    }

    @Test
    public void When_I_Edit_A_Wish_From_A_Wishlist_It_Should_Be_Changed() throws Exception {
        Worker worker = new WorkerBuilder().build();
        Wish aWish = new Wish(worker, "Un pony");

        wishlist.save(aWish);

        aWish.setGift("Dos ponys!");
        wishlist.update(aWish);
        wishlist.refresh(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(worker.getWishList(), not(empty()));
        assertThat(worker.getWishList(), contains(hasProperty("gift", is("Dos ponys!"))));
    }
}
