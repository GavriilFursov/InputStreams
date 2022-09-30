package basket;

import java.io.*;

public class Basket {

    protected String[][] productsList;
    protected int[] itemsInCart;
    protected int bill = 0;


    public Basket(String[][] productsList) {
        this.productsList = productsList;
        this.itemsInCart = new int[productsList.length];
    }

    public void addToCart(int productNum, int amount) {
        itemsInCart[productNum] += amount;
        bill += Integer.parseInt(productsList[productNum][1]) * amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина покупок:");
        for (int i = 0; i < itemsInCart.length; i++) {
            if (itemsInCart[i] != 0) {
                System.out.println(productsList[i][0] + ", " + productsList[i][1] + " руб/шт: " + itemsInCart[i] + " шт, " + (Integer.parseInt(productsList[i][1]) * itemsInCart[i]) + " руб");
            }
        }
        System.out.println("Общая стоимость: " + bill);
    }

    public void saveTxt(File textFile) {
        try (BufferedWriter saveCartToFile = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < itemsInCart.length; i++) {
                if (itemsInCart[i] != 0) {
                    saveCartToFile.write((productsList[i][0] + ": " + itemsInCart[i] + " шт, " + Integer.parseInt(productsList[i][1]) * itemsInCart[i]) + " руб");
                    saveCartToFile.append("\n");
                    saveCartToFile.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[][] loadFromFile(File textFile) {
        String dataFromFile = "";
        try (BufferedReader loadCartFromFile = new BufferedReader(new FileReader(textFile))) {
            String s;
            while (true) {
                if ((s = loadCartFromFile.readLine()) == null) break;
                dataFromFile += s + "\n";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String[] splitLines = dataFromFile.split("\\n");
        String[][] productsListFromFile = new String[splitLines.length][2];
        for (int i = 0; i < splitLines.length; i++) {
            String[] split = splitLines[i].split(": |, | ");
            productsListFromFile[i][0] = split[0];
            productsListFromFile[i][1] = split[1];
        }
        return productsListFromFile;
    }
}