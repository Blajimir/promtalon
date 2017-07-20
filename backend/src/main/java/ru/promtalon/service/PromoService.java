package ru.promtalon.service;

import ru.promtalon.entity.Client;
import ru.promtalon.entity.Promo;
import ru.promtalon.entity.PromoOperation;

public interface PromoService {
    Promo addPromo(Promo promo);
    Promo updatePromo(Promo promo);
    Promo deletePromo(Promo promo);
    PromoOperation buyPromo(long id, Client user);
    void cancelBuyPromo(PromoOperation promoOperation);
}
