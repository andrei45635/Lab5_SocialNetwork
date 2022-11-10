package user_interface;

import domain.User;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import service.Service;
import validators.ValidatorException;

public class UserInterface {
    private static Service service;

    public UserInterface(Service service) {
        UserInterface.service = service;
    }

    public static int menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("1. Prints all the logged in users\n2. Prints all of the users who are logged in and their friendships\n3. Add user\n4. Delete user\n5. Add friend\n6. Delete friend\n7. Connected communities V2\n0. Exit");
        int selection = in.nextInt();
        return selection;
    }

    public static void addUser() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Input the ID: ");
            int id = in.nextInt();
            in.nextLine();
            System.out.println("Input your first name: ");
            String firstName = in.nextLine();
            System.out.println("Input your last name: ");
            String lastName = in.nextLine();
            System.out.println("Input your email: ");
            String email = in.nextLine();
            System.out.println("Input your password: ");
            String passwd = in.nextLine();
            System.out.println("Input your age: ");
            int age = in.nextInt();
            service.addUserService(id, firstName, lastName, email, passwd, age);
        } catch (ValidatorException var7) {
            System.out.println(var7.getMessage());
        }

    }

    public void deleteUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti ID-ul: ");
        int id = in.nextInt();
        User deleted_user = null;
        for(User u: service.getAllService()){
            if(u.getID() == id){
                deleted_user = u;
            }
        }
        service.deleteUserService(id);
        assert deleted_user != null;
        System.out.println("Am sters userul " + deleted_user.toString());
    }

    public void addFriend() {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti id-ul dvs: ");
        int id = in.nextInt();
        System.out.println("Introduceti id-ul prietenului: ");
        int id2 = in.nextInt();
        service.addFriendService(id, id2);
    }

    public void deleteFriend() {
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti id-ul dvs: ");
        int id = in.nextInt();
        System.out.println("Introduceti id-ul prietenului: ");
        int id2 = in.nextInt();
        service.deleteFriendService(id, id2);
    }


    public void connectedCommunitiesUI(){
        int connected = service.connectedCommunities();
        System.out.println(connected);
    }

    public void start() {
        label30:
        while(true) {
            int user_input = menu();
            switch (user_input) {
                case 0:
                    return;
                case 1:
                    for(User u: service.getAllService()){
                        System.out.println(u.toString());
                    }
                    break;
                case 2:
                    for(User u: service.getAllService()){
                        System.out.println(u.toString());
                        System.out.println(u.getFriends());
                    }
                    break;
                case 3:
                    addUser();
                    break;
                case 4:
                    this.deleteUser();
                    break;
                case 5:
                    this.addFriend();
                    break;
                case 6:
                    this.deleteFriend();
                    break;
                case 9:
                    this.connectedCommunitiesUI();
                    break;
                default:
                    System.out.println("Keys: 0 - 7\n");
            }
        }
    }
}
