package repo.file;

import domain.User;
import repo.memory.UserMemoryRepository;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo extends UserMemoryRepository {
    private final String fileName;

    public AbstractFileRepo(String fileName, Validator<User> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> attrs = Arrays.asList(line.split(";"));
                User entity = extractEntity(attrs);
                save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User save(User entity) {
        User e = super.save(entity);
        if (e == null) {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public void delete(int ID) {
        super.delete(ID);
        for(User u: getAll()){
            System.out.println(u);
        }
        for(User u: getAll()){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                bw.write(createEntityAsString(u));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //writeToFile(u);
            //3;Freeman;Gordon;gordon.freeman@blackmesa.com;alyxvance1;27
        }
    }

    private void writeToFile(User entity) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(createEntityAsString(entity));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract User extractEntity(List<String> attrs);

    protected abstract String createEntityAsString(User entity);

}
