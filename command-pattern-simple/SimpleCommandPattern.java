/*
Command normally specifies a sender-receiver connection with a subclass.
Command act as magic tokens to be passed around and invoked at a later time.
Two important aspects of the Command pattern: interface separation (the invoker is isolated from the receiver), time separation (stores a ready-to-go processing request that's to be stated later).

Command can use Memento to maintain the state required for an undo operation.

Command pattern have three components: Receiver, Command, Invoker

Command normally specifies a sender-receiver connection with a subclass

Comand pattern is used in Order(Cart), Queue, Undo, Editor, - When we need to have multiple commands executed on source(Receiver) via some handle(Invoker)
*/
import java.io.*;
import java.util.*;
//Receiver
class Stock {
    private String name;
    private int quantity;
    
    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    
    public void buy() {
        System.out.println("Bought " + quantity + " stocks of " + name);
    }
    
    public void sell() {
        System.out.println("Sold " + quantity + " stocks of " + name);
    }
}

interface Order{
     void execute();
}

//Commands
class BuyStock implements Order{
    private Stock stock;
    public BuyStock( Stock stock) {
        this.stock = stock;
    }
    public void execute(){
        stock.buy();
    }
}


class SellStock implements Order{
    private Stock stock;
    public SellStock( Stock stock) {
        this.stock = stock;
    }
    public void execute(){
        stock.sell();
    }
}

//Invoker
class Broker{
    private List<Order> orderList = new ArrayList<Order>();
    public void takeOrder( Order order){
        orderList.add( order );
    }
    public void placeOrder(){
        for(Order order: orderList){
            order.execute();
        }
        orderList.clear();
    }
}

public class SimpleCommandPattern {
    public static void main(String[] args) {
        //Receiver
        Stock stock = new Stock("NSE", "5");
        //Concrete Commands
        BuyStock buy = new BuyStock(stock);
        SellStock sell = new SellStock(stock);
        //Invoker
        Broker broker = new Broker();
        broker.takeOrder(buy);
        broker.takeOrder(sell);
        //Invoker Execute commands
        broker.placeOrder();
    }
}

