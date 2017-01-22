package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.DishesVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/6
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public interface RecipesContract {
    interface View {
        void showCookBook(List<DishesVo> dishesList);
        void goneRecipes();
    }

    interface Presenter {
       void getCookBook(String date);
    }
}
