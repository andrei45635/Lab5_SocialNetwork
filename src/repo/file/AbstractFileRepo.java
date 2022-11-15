package repo.file;

import domain.Entity;
import repo.memory.MemoryRepo;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo<ID, T extends Entity<ID>> extends MemoryRepo<ID, T> {
    protected final String fileName;

    public AbstractFileRepo(String fileName, Validator<T> validator) {
        super(validator);
        this.fileName = fileName;
    }
    protected void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> attrs = Arrays.asList(line.split(";"));
                T entity = extractEntity(attrs);
                super.save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for(T e: entities){
                bw.write(createEntityAsString(e));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract T extractEntity(List<String> attrs);

    protected abstract String createEntityAsString(T entity);

}
