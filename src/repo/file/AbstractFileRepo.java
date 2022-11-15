package repo.file;

import domain.Entity;
import domain.User;
import repo.memory.MemoryRepo;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo<ID, T extends Entity<ID>> extends MemoryRepo<ID, T> {
    private final String fileName;

    public AbstractFileRepo(String fileName, Validator<T> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> attrs = Arrays.asList(line.split(";"));
                T entity = extractEntity(attrs);
                save(entity);
                System.out.println(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeToFile(T entity) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(createEntityAsString(entity));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract T extractEntity(List<String> attrs);

    protected abstract String createEntityAsString(T entity);

}
