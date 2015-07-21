package com.tenPines.persistence;

import com.tenPines.builder.PersonBuilder;
import com.tenPines.model.Wish;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class InMemoryWishlistTest {

    AbstractRepository<Wish> wishlist;

    @Before
    public void setUp() {
        wishlist = new InMemoryWishlist();
    }

    @Test
    public void When_A_Wishlist_is_created_it_should_be_empty() {
        assertThat(wishlist.retrieveAll(), empty());
    }

    @Test
    public void When_I_Add_A_Wish_To_A_Wishlist_It_Should_Get_Stored() {
        Wish aWish = new Wish(new PersonBuilder().build(), "Un pony");

        wishlist.save(aWish);
        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(wishlist.retrieveAll(), hasItem(aWish));
    }

    @Test
    public void When_I_Remove_A_Wish_From_A_Wishlist_It_Should_Be_No_More() {
        Wish aWish = new Wish(new PersonBuilder().build(), "Un pony");

        wishlist.save(aWish);
        wishlist.delete(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(0));
        assertThat(wishlist.retrieveAll(), not(hasItem(aWish)));
    }

    @Test
    public void When_I_Edit_A_Wish_From_A_Wishlist_It_Should_Be_Changed() {
        Wish aWish = new Wish(new PersonBuilder().build(), "Un pony");

        wishlist.save(aWish);

        aWish.setWish("Dos ponys!");
        wishlist.update(aWish);
        wishlist.refresh(aWish);

        assertThat(wishlist.retrieveAll(), hasSize(1));
        assertThat(aWish.getWish(), is("Dos ponys!"));
        assertThat(wishlist.retrieveAll(), hasItem(aWish));
    }
}
