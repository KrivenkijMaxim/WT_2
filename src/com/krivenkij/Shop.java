package com.krivenkij;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class that describes interaction with an xml file representing a household appliance store
 * @author Krivenkij Maxim "maksvartanjan@gmail.com"
 * @version 1.0
 * @see Main
 */

public class Shop implements IShop{
    /** Field path to xml file */
    private String filepath;


    /**
     * Method for getting information about all household appliances in the store
     * @return List of string about all household appliances in the store
     */
    @Override
    public String getInfoAboutAllPoducts() {
        List<String> infos = getStringsFromFile();
        return getInfoFromProductsList(infos, "");
    }

    /**
     * Method for obtaining information about the selected category of household appliances
     * @param category household appliances category
     * @return List of string about the selected category of household appliances
     */
    @Override
    public String getInfoAboutOneCategoryProduct(String category) {
        List<String> infos = getStringsFromFile();
        return getInfoFromProductsList(infos, category);
    }


    /**
     * Method for obtaining information about the cheapest piece of household appliances in the store
     * @return string about the cheapest copy of household appliances in the store
     */
    @Override
    public String getInfoLowCostProduct() {
        List<String> infos = getStringsFromFile();

        StringBuilder sb = new StringBuilder();
        StringBuilder tempOneProductString = new StringBuilder();
        StringBuilder lowerPriceProductString = new StringBuilder();

        int index = 0, lowerPriceIndex = 1, lastIndexProperties = 0;
        double lowerPrice = 0, tempPrice = 0;
        String temp = "";

        for(int i = 1; i < infos.size(); i++){
            sb.append(infos.get(i));
            lastIndexProperties = sb.lastIndexOf("<") - 1;
            tempOneProductString.append(sb.substring(sb.indexOf("<", index) + 1, sb.indexOf(">", index))).append(" : ");
            index = sb.indexOf(">", index)+1;
            while(index < lastIndexProperties){
                temp = sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index));
                tempOneProductString.append(temp).append("=");
                index = sb.indexOf(">", index)+1;
                if (temp.equals("PRICE")){
                    tempPrice = Double.parseDouble(sb.substring(index, sb.indexOf("<", index)));
                    if (tempPrice < lowerPrice || lowerPrice == 0) {
                        lowerPrice = tempPrice;
                        lowerPriceIndex = i;
                    }
                }
                tempOneProductString.append(sb.substring(index, sb.indexOf("<", index))).append(" ");
                index = sb.indexOf(">", index)+1;
            }

            if(lowerPriceIndex == i){
                lowerPriceProductString.delete(0, lowerPriceProductString.length());
                lowerPriceProductString.append(tempOneProductString);
            }

            tempOneProductString.delete(0, tempOneProductString.length());
            sb.delete(0, sb.length());
            index = 0;
        }
        return lowerPriceProductString.toString();
    }


    /**
     * Method for obtaining information from a list of strings of all household appliances loaded from an xml file
     * @param infos list of household appliances strings loaded from xml
     * @param category category of household appliances selected by the user
     * @return string with household appliances, selected category
     */
    private String getInfoFromProductsList(List<String> infos, String category) {
        StringBuilder sb = new StringBuilder();
        StringBuilder resultFindProductString = new StringBuilder();
        for(int i = 1; i < infos.size(); i++){
            sb.append(infos.get(i));
            int index = 0;
            int lastIndexProperties = sb.lastIndexOf("<") - 1;
            if(category.equals("") || category.equals(sb.substring(sb.indexOf("<", index)+1, sb.indexOf(">", index)))){
                resultFindProductString.append(sb.substring(sb.indexOf("<", index) + 1, sb.indexOf(">", index))).append(" : ");
                index = sb.indexOf(">", index)+1;
                while(index < lastIndexProperties){
                    resultFindProductString.append(sb.substring(sb.indexOf("<", index) + 1, sb.indexOf(">", index))).append("=");
                    index = sb.indexOf(">", index)+1;
                    resultFindProductString.append(sb.substring(index, sb.indexOf("<", index))).append(" ");
                    index = sb.indexOf(">", index)+1;

                }
                resultFindProductString.append("\n");
            }
            sb.delete(0, sb.length());

        }
        return resultFindProductString.toString();
    }

    /**
     * Method for loading data from xml file into string list
     * @return list of all household appliances strings from xml file
     */
    private List<String> getStringsFromFile() {
        List<String> appliancesInfos = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filepath);
            Scanner scanner = new Scanner(fr);
            while (scanner.hasNext()) {
                appliancesInfos.add(scanner.nextLine());
            }
            fr.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return appliancesInfos;
        }
    }

    /**
     * Shop class constructor
     * @param filepath path to xml file
     */
    public Shop(String filepath){
        this.filepath = filepath;
    }

}
