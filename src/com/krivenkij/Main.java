package com.krivenkij;

public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop("src\\com\\krivenkij\\shop.xml");
        System.out.println(shop.getInfoAboutAllPoducts());
        System.out.println(shop.getInfoAboutOneCategoryProduct("KETTLE"));
        System.out.println(shop.getInfoLowCostProduct());
    }
}

