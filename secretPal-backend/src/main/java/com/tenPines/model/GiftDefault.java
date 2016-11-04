package com.tenPines.model;

import com.tenPines.errors.InvalidGiftDefaultException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class GiftDefault {

    @Id
    @GeneratedValue
    private Long id;

    public GiftDefault() {
        }

    static public GiftDefault createGiftDfault(String giftDefault, String amountDefault){
        if (amountDefault.isEmpty()){
            throw new InvalidGiftDefaultException(InvalidGiftDefaultException.HAVE_NOT_AMOUNT_DEFAULT);
        }
        if (giftDefault.isEmpty()){
            throw new InvalidGiftDefaultException(InvalidGiftDefaultException.HAVE_NOT_GIFT_DEFAULT);
        }
        GiftDefault aGiftDefault = new GiftDefault();
        aGiftDefault.setAmountDefault(amountDefault);
        aGiftDefault.setGiftDefault(giftDefault);
        return aGiftDefault;
    }

    @NotNull
    public String giftDefault;

    @NotNull
    public String amountDefault;


    public String getAmountDefault() {
        return amountDefault;
    }

    public void setAmountDefault(String password) {
        this.amountDefault = password;
    }

    public String getGiftDefault() {
        return giftDefault;
    }

    public void setGiftDefault(String giftDefault) {
        this.giftDefault = giftDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
