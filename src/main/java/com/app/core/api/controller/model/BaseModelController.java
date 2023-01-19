package com.app.core.api.controller.model;

import com.app.core.api.model.BaseCollection;
import com.app.core.api.service.model.BaseModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
public class BaseModelController<T extends BaseCollection, S extends BaseModelService> {

    protected final S crudService;

    public BaseModelController(S crudService) {
        this.crudService = crudService;
    }

    /** This method support GET request to get all Campaign records */
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<T>> findAll() {

        List<T> list = this.crudService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * This method support GET request to query Campaign record by id
     *
     * @param id object id
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<T>> findById(@PathVariable String id) {

        Optional<T> object = this.crudService.findById(id);

        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    /**
     * This method support POST request to create new campaign Validation is taken care of by the
     * framework using (@Valid) and messages are specified in the Campaign model object
     *
     * @param object new object to be saved
     */
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<T> insert(@Valid @RequestBody T object) {

        T newObject = (T) this.crudService.insert(object);

        return new ResponseEntity<>(newObject, HttpStatus.OK);
    }

    /**
     * This method support PUT request to update existing campaign Validation is taken care of by
     * the framework using (@Valid) and messages are specified in the Campaign model object
     *
     * @param id campaign
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<T> update(@PathVariable String id, @Valid @RequestBody T object) {

        T updatedObject = (T) this.crudService.update(id, object);

        return new ResponseEntity<>(updatedObject, HttpStatus.OK);
    }

    /**
     * This method support DELETE request to delete a campaign record by id
     *
     * @param id object id to be deleted
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<T> delete(@PathVariable String id) {

        this.crudService.delete(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
