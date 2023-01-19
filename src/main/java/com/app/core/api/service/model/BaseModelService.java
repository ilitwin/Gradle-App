package com.app.core.api.service.model;

import com.app.core.api.model.BaseCollection;
import com.app.core.api.service.utils.ClockUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public class BaseModelService<T extends BaseCollection, R extends MongoRepository<T, String>> {

    protected final R crudRepository;

    public BaseModelService(R crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<T> findAll() {

        return crudRepository.findAll();
    }

    public Optional<T> findById(String id) {

        return crudRepository.findById(id);
    }

    public T insert(T object) {

        if(object.getCreatedDate() == null){
            object.setCreatedDate(ClockUtils.getNow());
        }

        return crudRepository.insert(object);
    }

    public void delete(String id) {
        crudRepository.deleteById(id);
    }

    public T update(String id, T object) {
        boolean exists = this.crudRepository.existsById(id);
        if(exists){
            object.setId(id);
            return this.crudRepository.save(object);
        }
        else{
            return null;
        }
    }

    public T save(T object){
        return this.crudRepository.save(object);
    }

    public boolean exists(Example<T> example) {
        return this.crudRepository.exists(example);
    }

    public Optional<T> findOne(Example<T> example){
        return this.crudRepository.findOne(example);
    }
}
