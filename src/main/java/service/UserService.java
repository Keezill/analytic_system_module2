package service;

import dao.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserService {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final SubscriberDao subscriberDao = new SubscriberDao();
    private final MessageDao messageDao = new MessageDao();
    private final CallsDao callsDao = new CallsDao();
    private final InternetAccessDao internetAccessDao = new InternetAccessDao();
    private final DeviceDao deviceDao = new DeviceDao();

    public void run() {
        System.out.println("Select your option");
        try {
            showMenu();
            String c = reader.readLine();
            userInput(c);
        } catch (IOException e) {
            System.out.println("Sorry, something went wrong. Try again.");
        }
    }

    private void userInput(String choice) {
        switch (choice) {
            case "1":
                smsSearcher();
            case "2":
                System.out.println("Subscribers which consumes the most internet: " + internetAccessDao.getTop5InternetUsers());
                break;
            case "3":
                System.out.println("Subscribers which consumes the most calls: " + callsDao.getTop5CallUsers());
                break;
            case "4":
                System.out.println("Subscribers which consumes the most messages: " + messageDao.getTop5MessageUsers());
                break;
            case "5":
                System.out.println(subscriberDao.getMostPopularService());
                break;
            case "6":
                System.out.println(deviceDao.getMostPopularDevice());
                break;
            case "0":
                System.exit(0);
            default:
                userInput(choice);
        }
    }

    private void smsSearcher() {
        System.out.println();
        System.out.println("Please enter the word you wanna find in the storage: ");
        try {
            String c = reader.readLine();
            System.out.println(messageDao.getMessagesByText(c));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("1 - Search in sms storage");
        System.out.println("2 - Display top 5 subscribers which consumes the most internet");
        System.out.println("3 - Display top 5 subscribers which consumes the most calls");
        System.out.println("4 - Display top 5 subscribers which consumes the most sms");
        System.out.println("5 - Display which service is the most popular at the moment");
        System.out.println("6 - Display the most popular device which is used on the network");
        System.out.println("0 - Exit");
        System.out.println();
    }
}
