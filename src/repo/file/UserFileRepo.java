package repo.file;

import domain.User;
import repo.memory.RepoMemoryUser;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserFileRepo extends RepoMemoryUser {
    private final String fileName;

    public UserFileRepo(String fileName, Validator<User> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    public void addUser(User u){
        super.addUser(u);
        writeToFile(u);
    }

    public List<User> getAllUsers(){
        loadData();
        return super.getAllUsers();
    }

    private void loadData(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = br.readLine()) != null){
                List<String> attr = Arrays.asList(line.split(";"));
                User e = extractEntity(attr);
                save(e);
                //writeToFile(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User extractEntity(List<String> attr){
        return new User(Integer.parseInt(attr.get(0)), attr.get(1), attr.get(2), attr.get(3), attr.get(4), Integer.parseInt(attr.get(5)));
    }

    public User save(User u){
        if(u == null){
            writeToFile(u);
        }
        return u;
    }

    protected void writeToFile(User u){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
            bw.write(createUserAsString(u));
            bw.newLine();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    protected String createUserAsString(User entity) {
        return entity.getID()+";"+entity.getFirstName()+";"+entity.getLastName()+";"+entity.getEmail()+";"+entity.getPasswd()+";"+entity.getAge()+"\n";
    }
}
