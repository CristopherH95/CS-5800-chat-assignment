package driver;

import chat.interfaces.messages.MessageData;
import chat.server.ChatServer;
import chat.user.User;

import java.util.*;

public class Main {
    private static final String testNameA = "Jose";
    private static final String testNameB = "Bill";
    private static final String testNameC = "Sam";

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        testChatServerDistributeAll(chatServer);
        testChatServerUndo(chatServer);
        testChatServerBlock(chatServer);
    }

    private static void testChatServerDistributeAll(ChatServer chatServer) {
        User userA = new User(testNameA, chatServer);
        User userB = new User(testNameB, chatServer);
        User userC = new User(testNameC, chatServer);
        Set<String> recipients = new HashSet<>(Arrays.asList(testNameB, testNameC));

        System.out.println("********** ALL DISTRIBUTION TEST **********");
        userA.sendMessage("Distributed to all", recipients);
        printUserPairHistory(userA, userB);
        printUserPairHistory(userA, userC);
        unRegisterAll(chatServer, userA, userB, userC);
        System.out.println("********** TEST END **********");
    }

    private static void testChatServerUndo(ChatServer chatServer) {
        User userA = new User(testNameA, chatServer);
        User userB = new User(testNameB, chatServer);
        User userC = new User(testNameC, chatServer);
        Set<String> recipientsA = new HashSet<>(Arrays.asList(testNameB, testNameC));
        Set<String> recipientsB = new HashSet<>(List.of(testNameB));

        System.out.println("********** UNDO MESSAGE TEST **********");
        userA.sendMessage("First message", recipientsA);
        userA.sendMessage("Second message", recipientsB);
        System.out.println("***** BEFORE UNDO *****");
        printUserPairHistory(userA, userB);
        printUserPairHistory(userA, userC);
        System.out.println("***** AFTER UNDO *****");
        userA.undo();
        printUserPairHistory(userA, userB);
        printUserPairHistory(userA, userC);
        unRegisterAll(chatServer, userA, userB, userC);
        System.out.println("********** TEST END **********");
    }

    private static void testChatServerBlock(ChatServer chatServer) {
        User userA = new User(testNameA, chatServer);
        User userB = new User(testNameB, chatServer);
        User userC = new User(testNameC, chatServer);
        Set<String> recipients = new HashSet<>(Arrays.asList(testNameB, testNameC));
        userB.block(testNameA);
        userC.block(testNameA);

        System.out.println("********** BLOCKED TEST **********");
        userA.sendMessage("Blocked message", recipients);
        // Order matters in this case, user A will see their message but users B and C will not see anything.
        printUserPairHistory(userA, userB);
        printUserPairHistory(userA, userC);
        printUserPairHistory(userB, userA);
        printUserPairHistory(userC, userA);
        unRegisterAll(chatServer, userA, userB, userC);
        System.out.println("********** TEST END **********");
    }

    private static void printUserPairHistory(User firstUser, User secondUser) {
        Iterator<MessageData> iterator = firstUser.userMessagesIterator(secondUser);

        System.out.printf("---- %s <==> %s ----%n", firstUser.getName(), secondUser.getName());
        while (iterator.hasNext()) {
            MessageData messageData = iterator.next();
            System.out.println(messageData);
        }
        System.out.println("---- CHAT END ----");
    }

    private static void unRegisterAll(ChatServer chatServer, User... users) {
        for (User user : users) {
            chatServer.unRegisterUser(user);
        }
    }
}