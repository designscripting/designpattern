/*
Mediator design pattern:
- Act as middle man between objects, make loosly coupled communitcation/references bw objects
- The essence of the Mediator Pattern is to "define an object that encapsulates how a set of objects interact".
- Mediator - defines the interface for communication between set of objects.
  Examples
    Colleagues via SCRUM Master/JIRA board(Mediator),
    Chat-Users via ChatRoom(Mediator)
    Flights communicate via traffic control room
- Mediator also registers objects similar like observers in Observer pattern
- Observer vs Mediator
Similarity : Both patterns facilitates the communication between objects, and both decouples the link between the sender and the receiver.
Difference : The main difference is that in the Mediator Design Pattern there is the notion of the participants and they communicate with each other using the mediator as a central hub, whereas in the Observer Design Pattern, there is a clear distinction between the sender and the receiver, and the receiver merely listens to the changes in the sender.

*/

import java.util.HashMap;
import java.util.Map;

interface IMediator{
    public void addUser(User user);
    public void sendMessage(String id, String message);
}

class Chatroom implements IMediator{
    private Map<String, User> usersMap = new HashMap<>();
    
    @Override
    public void sendMessage(String userId, String msg)
    {
        User u = usersMap.get(userId);
        u.receive(msg);
    }
    
    @Override
    public void addUser(User user) {
        this.usersMap.put(user.getId(), user);
    }
}

abstract class User{
    private IMediator mediator;
    private String id;
    private String name;
    
    public User(IMediator room, String id, String name){
        this.mediator = room;
        this.name = name;
        this.id = id;
    }
    
    public abstract void send(String userId, String msg);
    public abstract void receive(String msg);
    
    public IMediator getMediator() {
        return mediator;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}

class ChatUser extends User {
    
    public ChatUser(IMediator room, String id, String name) {
        super(room, id, name);
    }
    
    @Override
    public void send(String userId ,String msg) {
        System.out.println(this.getName() + " :: Sending Message : " + msg);
        getMediator().sendMessage(userId, msg);
    }
    
    @Override
    public void receive(String msg) {
        System.out.println(this.getName() + " :: Received Message : " + msg);
    }
    
}

public class MediatorPatternDemo
{
    public static void main(String[] args)
    {
        IMediator chatroom = new Chatroom();
        
        User user1 = new ChatUser(chatroom,"1", "Alex");
        User user2 = new ChatUser(chatroom,"2", "Brian");
        User user3 = new ChatUser(chatroom,"3", "Charles");
        User user4 = new ChatUser(chatroom,"4", "David");
        
        chatroom.addUser(user1);
        chatroom.addUser(user2);
        chatroom.addUser(user3);
        chatroom.addUser(user4);

        user1.send("2","Hello brian" );
        user2.send("1", "Hey buddy");
    }
}

/* Output:
javac MediatorPatternDemo.java
java MediatorPatternDemo
Alex :: Sending Message : Hello brian
Brian :: Received Message : Hello brian
Brian :: Sending Message : Hey buddy
Alex :: Received Message : Hey buddy
 */