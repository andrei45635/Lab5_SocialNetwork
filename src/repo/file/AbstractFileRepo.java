package repo.file;

import domain.User;
import repo.memory.AbstractMemoryRepo;
import repo.memory.UserMemoryRepository;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo<T> extends UserMemoryRepository {
    private final String fileName;

    public AbstractFileRepo(String fileName, Validator<User> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = br.readLine()) != null){
                List<String> attrs = Arrays.asList(line.split(";"));
                User entity = extractEntity(attrs);
                save(entity);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public User save(User entity) {
        User e = super.save(entity);
        if(e == null){
            writeToFile(entity);
        }
        return e;
    }

    private void writeToFile(User entity) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
            bw.write(createEntityAsString(entity));
            bw.newLine();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public abstract User extractEntity(List<String> attrs);
    protected abstract String createEntityAsString(User entity);

}
